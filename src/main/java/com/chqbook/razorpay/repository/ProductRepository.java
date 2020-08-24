package com.chqbook.razorpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chqbook.razorpay.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
