package com.chqbook.razorpay.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chqbook.razorpay.entity.ApplicationUser;
import com.chqbook.razorpay.repository.ApplicationUserRespository;

@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private ApplicationUserRespository userRepository;
	
	/**
	 * checks if the user is exist in our db or not 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final ApplicationUser applicationUser = userRepository.findByUsername(username);

		if (applicationUser == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return User.withUsername(username).password(applicationUser.getPassword()).authorities(new ArrayList<>())
				.build();
	}
}
