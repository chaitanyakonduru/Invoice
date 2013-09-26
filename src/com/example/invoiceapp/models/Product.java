package com.example.invoiceapp.models;


public class Product extends Priority {

	private int id;
	private String productId;
	private String productName;
	private String mQuantityOrdered;
	private String mPrice;
	private String orderId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getmQuantityOrdered() {
		return mQuantityOrdered;
	}
	public void setmQuantityOrdered(String mQuantityOrdered) {
		this.mQuantityOrdered = mQuantityOrdered;
	}
	public String getmPrice() {
		return mPrice;
	}
	public void setmPrice(String mPrice) {
		this.mPrice = mPrice;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	
}