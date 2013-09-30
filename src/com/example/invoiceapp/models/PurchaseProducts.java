package com.example.invoiceapp.models;

public class PurchaseProducts {

	private String productId;
	private String productName;
	private String mOrderQty;
	private String qtyPurchased;
	private String productPrice;
	private String qtyReturned;
	
	
	
	
	public String getQtyReturned() {
		return qtyReturned;
	}
	public void setQtyReturned(String qtyReturned) {
		this.qtyReturned = qtyReturned;
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
	public String getmOrderQty() {
		return mOrderQty;
	}
	public void setmOrderQty(String mOrderQty) {
		this.mOrderQty = mOrderQty;
	}
	public String getQtyPurchased() {
		return qtyPurchased;
	}
	public void setQtyPurchased(String qtyPurchased) {
		this.qtyPurchased = qtyPurchased;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	
}
