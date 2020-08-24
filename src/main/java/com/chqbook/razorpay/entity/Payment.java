package com.chqbook.razorpay.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment" )
public class Payment {

	public final static String PAYMNET_STATUS_SUCCESS = "SUCCESS";
	public final static String PAYMNET_STATUS_FAILURE = "FAILURE";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	private String razorpayPaymentId;
	private String razorpaySignature;
	private String PaymentStatus;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "order_detail_id", referencedColumnName = "id")
	private Order order;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}
	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}
	public String getRazorpaySignature() {
		return razorpaySignature;
	}
	public void setRazorpaySignature(String razorpaySignature) {
		this.razorpaySignature = razorpaySignature;
	}
	public String getPaymentStatus() {
		return PaymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
