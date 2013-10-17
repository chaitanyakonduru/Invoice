package com.example.invoiceapp.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Product extends Priority implements Parcelable {

	private int id;
	private String productId;
	private String productName;
	private String mQuantityPickup;
	private String mPrice;
	private String orderId;
	private boolean ordered;
	private String mQtyStockInHand;
	private String mQtyDelivered;
	private String mQtyReturned;
	
	public String getmQtyDelivered() {
		return mQtyDelivered;
	}
	
	

	public String getmQtyStockInHand() {
		return mQtyStockInHand;
	}



	public void setmQtyStockInHand(String mQtyStockInHand) {
		this.mQtyStockInHand = mQtyStockInHand;
	}



	public void setmQtyDelivered(String mQtyDelivered) {
		this.mQtyDelivered = mQtyDelivered;
	}

	public String getmQtyReturned() {
		return mQtyReturned;
	}

	public void setmQtyReturned(String mQtyReturned) {
		this.mQtyReturned = mQtyReturned;
	}

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

	public String getmQuantityPickup() {
		return mQuantityPickup;
	}

	public void setmQuantityPickup(String mQuantityOrdered) {
		this.mQuantityPickup = mQuantityOrdered;
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
		stringList.add(this.mQuantityPickup);
		stringList.add(String.valueOf(this.ordered));
		stringList.add(this.mQtyStockInHand);
		stringList.add(this.mQtyDelivered);
		stringList.add(this.mQtyReturned);
		arg0.writeStringList(stringList);
	}

	public Product(Parcel parcel) {
		List<String> stringList = new ArrayList<String>();
		parcel.readStringList(stringList);
		this.productId = stringList.get(0);
		this.productName = stringList.get(1);
		this.mPrice = stringList.get(2);
		this.mQuantityPickup = stringList.get(3);
		this.ordered = stringList.get(4).equalsIgnoreCase("true") ? true
				: false;
		this.mQtyStockInHand=stringList.get(5);
		this.mQtyDelivered=stringList.get(6);
		this.mQtyReturned=stringList.get(7);
		
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