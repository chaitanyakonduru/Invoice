package com.example.invoiceapp.models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Bread implements Parcelable {

	private String itemName;
	private String quantitiy;
	private boolean isOrdered;

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getQuantitiy() {
		return quantitiy;
	}

	public void setQuantitiy(String quantitiy) {
		this.quantitiy = quantitiy;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		List<String> stringList=new ArrayList<String>();
		stringList.add(this.itemName);
		stringList.add(this.quantitiy);
		stringList.add(String.valueOf(this.isOrdered));
		arg0.writeStringList(stringList);
	}

	public Bread(Parcel parcel) {
		List<String> stringList=new ArrayList<String>();
		parcel.readStringList(stringList);
		this.itemName=stringList.get(0);
		this.quantitiy=stringList.get(1);
		this.isOrdered=Boolean.valueOf(stringList.get(2));
	}

	public Bread() {

	}

	
	public static final Parcelable.Creator<Bread> CREATOR = new Parcelable.Creator<Bread>() {
		public Bread createFromParcel(Parcel in) {
			return new Bread(in);
		}

		public Bread[] newArray(int size) {
			return new Bread[size];
		}
	};

}
