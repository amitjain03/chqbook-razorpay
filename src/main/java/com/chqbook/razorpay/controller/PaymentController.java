package com.chqbook.razorpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chqbook.razorpay.dto.ResponseDto;
import com.chqbook.razorpay.dto.VerifyPaymentDto;
import com.chqbook.razorpay.service.PaymentService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/verify")
	public ResponseDto verifyPaymentFromRazorPay(@RequestHeader(name = "signature", required = true) String signature, @RequestBody VerifyPaymentDto verifyPaymentDto) throws RazorpayException {
		String status = paymentService.verifyPayment(signature, verifyPaymentDto);
		return new ResponseDto(status, HttpStatus.OK);
	}
}
