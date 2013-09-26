package com.example.invoiceapp.models;

public class OrderProduct {
	
	private int _id;
	private String mOrderProductId;
	
	private String mOrderId;
	private String mProductId;
	private String mOrderQty;
	private String mProductPrice;
	
	
	public String getmProductPrice() {
		return mProductPrice;
	}
	public void setmProductPrice(String mProductPrice) {
		this.mProductPrice = mProductPrice;
	}
	public String getmOrderProductId() {
		return mOrderProductId;
	}
	public void setmOrderProductId(String mOrderProductId) {
		this.mOrderProductId = mOrderProductId;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getmOrderId() {
		return mOrderId;
	}
	public void setmOrderId(String mOrderId) {
		this.mOrderId = mOrderId;
	}
	public String getmProductId() {
		return mProductId;
	}
	public void setmProductId(String mProductId) {
		this.mProductId = mProductId;
	}
	public String getmOrderQty() {
		return mOrderQty;
	}
	public void setmOrderQty(String mOrderQty) {
		this.mOrderQty = mOrderQty;
	}
	
	

}
