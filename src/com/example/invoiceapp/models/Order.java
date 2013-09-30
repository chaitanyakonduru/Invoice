package com.example.invoiceapp.models;

import java.util.List;

public class Order extends Priority {

	private int _id;
	private String mCustomerId;
	private String mOrderId;
	private String mOrderDate;
	private String mOrderTotalAmt;
	private List<OrderProduct> mOrderedProductsList;

	public String getmCustomerId() {
		return mCustomerId;
	}

	public void setmCustomerId(String mCustomerId) {
		this.mCustomerId = mCustomerId;
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

	public List<OrderProduct> getmOrderedProductsList() {
		return mOrderedProductsList;
	}

	public void setmOrderedProductsList(List<OrderProduct> mOrderedProductsList) {
		this.mOrderedProductsList = mOrderedProductsList;
	}

}