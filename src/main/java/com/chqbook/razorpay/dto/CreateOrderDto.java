package com.chqbook.razorpay.dto;

import javax.validation.constraints.NotNull;

public class CreateOrderDto {
	@NotNull
	public long productId;
	@NotNull
	public int quantity;
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
