package com.chqbook.razorpay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/razorpay")
public class RazorpayDashboard {
	@GetMapping("/login")
	public String GetLoginPage() {
		return "login";
	}
	
	@GetMapping("/product")
	public String GetProductPage() {
		return "product";
	}
}
