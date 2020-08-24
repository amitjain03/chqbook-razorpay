package com.chqbook.razorpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chqbook.razorpay.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Order findFirstByRazorpayOrderId(String razorpayOrderId);
}
