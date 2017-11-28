package com.model2.mvc.service.domain;

import java.sql.Date;

public class Cart {

	private int cartNo;
	private int prodNo;
	private String prodName;
	private String userId;
	private Date cartDate;
	private int price;
	private String status;
	
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Cart [cartNo=" + cartNo + ", prodNo=" + prodNo + ", prodName=" + prodName + ", userId=" + userId
				+ ", cartDate=" + cartDate + ", price=" + price + ", status=" + status + "]";
	}

}
