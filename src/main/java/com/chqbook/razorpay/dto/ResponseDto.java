package com.chqbook.razorpay.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto {
	private Object data;
	private HttpStatus statusCode;
	
	public ResponseDto(Object data, HttpStatus statusCode) {
		this.data = data;
		this.statusCode = statusCode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
}
