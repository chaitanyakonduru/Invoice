package com.example.invoiceapp.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Product extends Priority implements Parcelable {

	private int id;
	private String productId;
	private String productName;
	private String mQuantityOrdered;
	private String mPrice;
	private String orderId;
	private boolean ordered;

	public boolean isOrdered() {
		return ordered;
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		List<String> stringList = new ArrayList<String>();

		stringList.add(this.productId);
		stringList.add(this.productName);
		stringList.add(this.mPrice);
		stringList.add(this.mQuantityOrdered);
		stringList.add(String.valueOf(this.ordered));
		arg0.writeStringList(stringList);
	}

	public Product(Parcel parcel) {
		List<String> stringList = new ArrayList<String>();
		parcel.readStringList(stringList);
		this.productId = stringList.get(0);
		this.productName = stringList.get(1);
		this.mPrice = stringList.get(2);
		this.mQuantityOrdered = stringList.get(3);
		this.ordered = stringList.get(4).equalsIgnoreCase("true") ? true
				: false;
	}

	public Product() {
		
	}
	
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}