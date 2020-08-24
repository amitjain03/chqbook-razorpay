package com.chqbook.razorpay.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.chqbook.razorpay.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey; // secret key to generate the jwt token

	@Value("${security.jwt.token.expire-length:990000000}")
	private long validityInMilliseconds = 990000000; // validity of the token

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // convert secret key to byte 64
	}

	/**
	 * Create a jet token from the username only
	 * @param username
	 * @return
	 */
	public String createToken(String username) {

		Claims claims = Jwts.claims().setSubject(username);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)// data and time of issusing
				.setExpiration(validity) // set expiry date
				.signWith(SignatureAlgorithm.HS256, secretKey) // setting the secret key to generate
				.compact();
	}

	/**
	 * checks of the user is authenticated or not
	 * @param token
	 * @return
	 */
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUsername(token)); // we can check through db or 
																								//just passing the username from the token to the UserDetails 
		return new UsernamePasswordAuthenticationToken(userDetails,"",new ArrayList<>());
	}
	
	/**
	 * get username from the token
	 * @param token
	 * @return
	 */
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	/**
	 * Resolve the token
	 * @param req
	 * @return
	 */
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	/**
	 * Validate token
	 * @param token
	 * @return
	 * @throws CustomException
	 */
	public boolean validateToken(String token) throws CustomException {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
