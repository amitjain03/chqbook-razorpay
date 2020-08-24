package com.chqbook.razorpay.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chqbook.razorpay.dto.LoginDto;
import com.chqbook.razorpay.dto.ResponseDto;
import com.chqbook.razorpay.exception.CustomException;
import com.chqbook.razorpay.repository.ApplicationUserRespository;
import com.chqbook.razorpay.security.JwtTokenProvider;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	@ResponseBody
	public ResponseDto login(@RequestBody @Valid LoginDto loginDto , HttpServletResponse response) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			return new ResponseDto(jwtTokenProvider.createToken(loginDto.getUsername()), HttpStatus.OK);
		} catch (AuthenticationException e) {
			throw new CustomException("Either Username or password is not correct", HttpStatus.UNAUTHORIZED);
		}
	}
	
}
