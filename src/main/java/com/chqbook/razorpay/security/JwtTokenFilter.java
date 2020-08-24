package com.chqbook.razorpay.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chqbook.razorpay.exception.CustomException;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(request); // resolve token coming from the request
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) { // checks for the token is valid or not
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth); // set the value in the Securitycontextholder for further use
			}
		} catch (CustomException ex) {
			SecurityContextHolder.clearContext();
			response.sendError(ex.getHttpStatus().value(), ex.getMessage()); // if the token is invalid
			return;

		}
		filterChain.doFilter(request, response);
	}

}
