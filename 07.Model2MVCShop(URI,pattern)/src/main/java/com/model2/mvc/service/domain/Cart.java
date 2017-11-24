package com.model2.mvc.service.domain;

import java.sql.Date;

public class Cart {

	private int cartNo;
	private int prodNo;
	private String userId;
	private Date cartDate;
	
	public Cart() {
		
	}

	public int getCartNo() {
		return cartNo;
	}

	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCartDate() {
		return cartDate;
	}

	public void setCartDate(Date cartDate) {
		this.cartDate = cartDate;
	}

	@Override
	public String toString() {
		return "Cart [cartNo=" + cartNo + ", prodNo=" + prodNo + ", userId=" + userId + ", cartDate=" + cartDate + "]";
	}

}
