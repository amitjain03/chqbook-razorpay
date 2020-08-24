package com.chqbook.razorpay.dto;

import javax.validation.constraints.NotBlank;

public class VerifyPaymentDto {

	@NotBlank
	private String razorPayOrderId;
	@NotBlank
	private String razorPayPaymentId;
	
	public String getRazorPayOrderId() {
		return razorPayOrderId;
	}
	public void setRazorPayOrderId(String razorPayOrderId) {
		this.razorPayOrderId = razorPayOrderId;
	}
	public String getRazorPayPaymentId() {
		return razorPayPaymentId;
	}
	public void setRazorPayPaymentId(String razorPayPaymentId) {
		this.razorPayPaymentId = razorPayPaymentId;
	}
	
}
