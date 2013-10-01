package com.example.invoiceapp.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class PurchaseProducts extends Priority implements Parcelable {

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		List<String> stringList = new ArrayList<String>();
		stringList.add(this.mOrderQty);
		stringList.add(this.productId);
		stringList.add(this.productName);
		stringList.add(this.productPrice);
		stringList.add(this.qtyPurchased);
		stringList.add(this.qtyReturned);
		dest.writeStringList(stringList);
	}

	public PurchaseProducts(Parcel parcel) {
		List<String> stringList = new ArrayList<String>();
		parcel.readStringList(stringList);
		this.mOrderQty = stringList.get(0);
		this.productId = stringList.get(1);
		this.productName = stringList.get(2);
		this.productPrice = stringList.get(3);
		this.qtyPurchased = stringList.get(4);
		this.qtyReturned = stringList.get(5);

	}

	public static final Creator<PurchaseProducts> CREATOR = new Creator<PurchaseProducts>() {

		@Override
		public PurchaseProducts[] newArray(int size) {
			return new PurchaseProducts[size];
		}

		@Override
		public PurchaseProducts createFromParcel(Parcel source) {
			return new PurchaseProducts(source);
		}
	};

	public PurchaseProducts() {

	}
}
