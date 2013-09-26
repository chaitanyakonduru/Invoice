package com.example.invoiceapp.models;

import java.util.List;



public class Order extends Priority  {
	
	private int _id;
	private String mOrderId;
	private String mOrderDate;
	private String mOrderTotalAmt;
	private List<Product> mProductsList;
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
	public String getmOrderDate() {
		return mOrderDate;
	}
	public void setmOrderDate(String mOrderDate) {
		this.mOrderDate = mOrderDate;
	}
	public String getmOrderTotalAmt() {
		return mOrderTotalAmt;
	}
	public void setmOrderTotalAmt(String mOrderTotalAmt) {
		this.mOrderTotalAmt = mOrderTotalAmt;
	}
	public List<Product> getmProductsList() {
		return mProductsList;
	}
	public void setmProductsList(List<Product> mProductsList) {
		this.mProductsList = mProductsList;
	}
	

	
}