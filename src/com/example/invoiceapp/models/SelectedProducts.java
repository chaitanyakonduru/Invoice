package com.example.invoiceapp.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedProducts extends Priority implements Parcelable {

	private String productId;
	private String productName;
	private String mOrderQty;
	private String qtyPurchased;
	private String productPrice;
	private String qtyReturned;
	private String qtyPickedUp;
	private String qtyStockInHand;
	private String qtyDelivered;
	
	public String getQtyDelivered() {
		return qtyDelivered;
	}

	public void setQtyDelivered(String qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}

	public String getQtyStockInHand() {
		return qtyStockInHand;
	}

	public void setQtyStockInHand(String qtyStockInHand) {
		this.qtyStockInHand = qtyStockInHand;
	}

	public String getQtyPickedUp() {
		return qtyPickedUp;
	}

	public void setQtyPickedUp(String qtyPickedUp) {
		this.qtyPickedUp = qtyPickedUp;
	}

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
		stringList.add(this.qtyPickedUp);
		stringList.add(this.qtyStockInHand);
		stringList.add(this.qtyDelivered);
		
		dest.writeStringList(stringList);
	}

	public SelectedProducts(Parcel parcel) {
		List<String> stringList = new ArrayList<String>();
		parcel.readStringList(stringList);
		this.mOrderQty = stringList.get(0);
		this.productId = stringList.get(1);
		this.productName = stringList.get(2);
		this.productPrice = stringList.get(3);
		this.qtyPurchased = stringList.get(4);
		this.qtyReturned = stringList.get(5);
		this.qtyPickedUp=stringList.get(6);
		this.qtyStockInHand=stringList.get(7);
		this.qtyDelivered=stringList.get(8);
	}

	public static final Creator<SelectedProducts> CREATOR = new Creator<SelectedProducts>() {

		@Override
		public SelectedProducts[] newArray(int size) {
			return new SelectedProducts[size];
		}

		@Override
		public SelectedProducts createFromParcel(Parcel source) {
			return new SelectedProducts(source);
		}
	};

	public SelectedProducts() {

	}
}
