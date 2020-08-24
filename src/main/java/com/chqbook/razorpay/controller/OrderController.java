package com.chqbook.razorpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chqbook.razorpay.dto.CreateOrderDto;
import com.chqbook.razorpay.dto.RazorPayDto;
import com.chqbook.razorpay.dto.ResponseDto;
import com.chqbook.razorpay.exception.CustomException;
import com.chqbook.razorpay.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public ResponseDto CreateOrder(@RequestBody CreateOrderDto createOrderDto) throws CustomException {
		return new ResponseDto(orderService.CreateOrder(createOrderDto),HttpStatus.OK);
	}

}
