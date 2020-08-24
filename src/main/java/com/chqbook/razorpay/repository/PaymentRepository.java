package com.chqbook.razorpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chqbook.razorpay.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
