package com.chqbook.razorpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.chqbook.razorpay.dto.VerifyPaymentDto;
import com.chqbook.razorpay.entity.Order;
import com.chqbook.razorpay.entity.Payment;
import com.chqbook.razorpay.exception.CustomException;
import com.chqbook.razorpay.repository.OrderRepository;
import com.chqbook.razorpay.repository.PaymentRepository;
import com.razorpay.Utils;

@Service
public class PaymentService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Value("${razorpay.secret}")
	private String secretKey;

	public String verifyPayment(String razorpaySignature, VerifyPaymentDto verifyPaymentDto) throws CustomException {

		Order order = orderRepository.findFirstByRazorpayOrderId(verifyPaymentDto.getRazorPayOrderId());

		if (order != null) {
			String payload = verifyPaymentDto.getRazorPayOrderId() + "|" + verifyPaymentDto.getRazorPayPaymentId();
			Payment payment = new Payment();
			try {
				Utils.verifySignature(payload, razorpaySignature, secretKey);
				payment.setPaymentStatus(Payment.PAYMNET_STATUS_SUCCESS);
			} catch (Exception ex) {
				payment.setPaymentStatus(Payment.PAYMNET_STATUS_FAILURE);
			}
			
			payment.setOrder(order);
			payment.setRazorpayPaymentId(verifyPaymentDto.getRazorPayPaymentId());
			payment.setRazorpaySignature(razorpaySignature);
			
			paymentRepository.save(payment);
			return payment.getPaymentStatus();
		} else {
			throw new CustomException("Order with the respective razor order id doesnot exist", HttpStatus.NOT_FOUND);
		}
	}
}
