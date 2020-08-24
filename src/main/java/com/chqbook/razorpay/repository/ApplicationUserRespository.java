package com.chqbook.razorpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import com.chqbook.razorpay.entity.ApplicationUser;

public interface ApplicationUserRespository extends JpaRepository<ApplicationUser,Long>{

	ApplicationUser findByUsername(String username);

}
