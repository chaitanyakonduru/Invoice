package com.example.invoiceapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer extends Priority implements Parcelable {

	private int _id;
	private String mDriverId;
	private String mCustomerId;
	private String mCustomerName;
	private String mCustomerAddress;
	private String mLandmark;
	private String mLatitude;
	private String mLongitude;
	private String mPhoneNo;
	private String mCustomerCity;
	private Order order;

	public int get_id() {
		return _id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getmDriverId() {
		return mDriverId;
	}

	public void setmDriverId(String mDriverId) {
		this.mDriverId = mDriverId;
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

	@Override
	public String toString() {
		return this.mCustomerName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(Parcel parcel) {
		String[] data = new String[10];
		parcel.readStringArray(data);
		this._id = Integer.parseInt(data[0]);
		this.mCustomerAddress = data[1];
		this.mCustomerCity = data[2];
		this.mCustomerId = data[3];
		this.mCustomerName = data[4];
		this.mDriverId = data[5];
		this.mLandmark = data[6];
		this.mLatitude = data[7];
		this.mLongitude = data[8];
		this.mPhoneNo = data[9];
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] { String.valueOf(this._id),
				this.mCustomerAddress, this.mCustomerCity, this.mCustomerId,
				this.mCustomerName, this.mDriverId, this.mLandmark,
				this.mLatitude, this.mLongitude, this.mPhoneNo });
	}

	public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
		public Customer createFromParcel(Parcel in) {
			return new Customer(in);
		}

		public Customer[] newArray(int size) {
			return new Customer[size];
		}
	};
}