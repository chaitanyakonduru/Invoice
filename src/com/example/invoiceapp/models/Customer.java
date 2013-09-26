package com.example.invoiceapp.models;

import java.util.List;

public class Customer extends Priority {

	private int _id;
	private String mCustomerId;
	private String mCustomerName;
	private String mCustomerAddress;
	private String mLandmark;
	private String mLatitude;
	private String mLongitude;
	private String mPhoneNo;
	private String mCustomerCity;
	private List<Order> ordersList;

	public int get_id() {
		return _id;
	}

	
	public String getmCustomerCity() {
		return mCustomerCity;
	}


	public void setmCustomerCity(String mCustomerCity) {
		this.mCustomerCity = mCustomerCity;
	}


	public void set_id(int _id) {
		this._id = _id;
	}

	public String getmCustomerId() {
		return mCustomerId;
	}

	public void setmCustomerId(String mCustomerId) {
		this.mCustomerId = mCustomerId;
	}

	public String getmCustomerName() {
		return mCustomerName;
	}

	public void setmCustomerName(String mCustomerName) {
		this.mCustomerName = mCustomerName;
	}

	public String getmCustomerAddress() {
		return mCustomerAddress;
	}

	public void setmCustomerAddress(String mCustomerAddress) {
		this.mCustomerAddress = mCustomerAddress;
	}

	public String getmLandmark() {
		return mLandmark;
	}

	public void setmLandmark(String mLandmark) {
		this.mLandmark = mLandmark;
	}

	public String getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmPhoneNo() {
		return mPhoneNo;
	}

	public void setmPhoneNo(String mPhoneNo) {
		this.mPhoneNo = mPhoneNo;
	}

	public List<Order> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<Order> ordersList) {
		this.ordersList = ordersList;
	}

}