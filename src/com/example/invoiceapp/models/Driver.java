package com.example.invoiceapp.models;


public class Driver {

	private String mDriverId;
	private String mDriverName;
	private String mDriverCode;
	
	public String getmDriverCode() {
		return mDriverCode;
	}
	public void setmDriverCode(String mDriverCode) {
		this.mDriverCode = mDriverCode;
	}

	private String mDriverPassword;
	
	public String getmDriverId() {
		return mDriverId;
	}
	public void setmDriverId(String mDriverId) {
		this.mDriverId = mDriverId;
	}
	public String getmDriverName() {
		return mDriverName;
	}
	public void setmDriverName(String mDriverName) {
		this.mDriverName = mDriverName;
	}
	public String getmDriverPassword() {
		return mDriverPassword;
	}
	public void setmDriverPassword(String mDriverPassword) {
		this.mDriverPassword = mDriverPassword;
	}
	
	@Override
	public String toString() {
		return this.getmDriverName();
	}
	
}
