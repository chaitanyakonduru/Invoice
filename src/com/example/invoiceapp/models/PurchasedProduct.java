package com.example.invoiceapp.models;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class PurchasedProduct extends Priority implements Parcelable{

	private int id;
	private String invoice_prodcutid;
	private String invoiceId;
	private String product_id;
	private String mProductName;
	private String productCost;
	private String productQuantity;
	private String qtyPickedUp;
	public String getQtyPickedUp() {
		return qtyPickedUp;
	}
	public void setQtyPickedUp(String qtyPickedUp) {
		this.qtyPickedUp = qtyPickedUp;
	}
	public String getmProductName() {
		return mProductName;
	}
	public void setmProductName(String mProductName) {
		this.mProductName = mProductName;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvoice_prodcutid() {
		return invoice_prodcutid;
	}
	public void setInvoice_prodcutid(String invoice_prodcutid) {
		this.invoice_prodcutid = invoice_prodcutid;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProductCost() {
		return productCost;
	}
	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		ArrayList<String> arrayList=new ArrayList<String>();
		arrayList.add(String.valueOf(this.id));
		arrayList.add(String.valueOf(this.invoice_prodcutid));
		arrayList.add(String.valueOf(this.invoiceId));
		arrayList.add(String.valueOf(this.product_id));
		arrayList.add(String.valueOf(this.productCost));
		arrayList.add(String.valueOf(this.productQuantity));
		arrayList.add(this.mProductName);
		arrayList.add(this.qtyPickedUp);
		arg0.writeStringList(arrayList);
	}
	
	public PurchasedProduct() {
	}
	
	public PurchasedProduct(Parcel parcel)
	{
		ArrayList<String> list=new ArrayList<String>();
		parcel.readStringList(list);
		this.id=Integer.parseInt(list.get(0));
		this.invoice_prodcutid=list.get(1);
		this.invoiceId=list.get(2);
		this.product_id=list.get(3);
		this.productCost=list.get(4);
		this.productQuantity=list.get(5);
		this.mProductName=list.get(6);
		this.qtyPickedUp=list.get(7);
	}
	
	public static final Creator<PurchasedProduct> CREATOR=new Creator<PurchasedProduct>() {
		
		@Override
		public PurchasedProduct[] newArray(int arg0) {
			return new PurchasedProduct[arg0];
		}
		
		@Override
		public PurchasedProduct createFromParcel(Parcel arg0) {
			// TODO Auto-generated method stub
			return new PurchasedProduct(arg0);
		}
	};
	
	
}
