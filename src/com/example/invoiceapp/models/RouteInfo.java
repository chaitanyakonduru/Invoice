package com.example.invoiceapp.models;

import java.util.List;

public class RouteInfo {

	private int _id;
	private String mRouteId;
	private String mRouteName;
	private String mRouteCode;
	private List<Customer> customesList;
	
	
	public List<Customer> getCustomesList() {
		return customesList;
	}
	public void setCustomesList(List<Customer> customesList) {
		this.customesList = customesList;
	}
	public String getmRouteCode() {
		return mRouteCode;
	}
	public void setmRouteCode(String mRouteCode) {
		this.mRouteCode = mRouteCode;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getmRouteId() {
		return mRouteId;
	}
	public void setmRouteId(String mRouteId) {
		this.mRouteId = mRouteId;
	}
	public String getmRouteName() {
		return mRouteName;
	}
	public void setmRouteName(String mRouteName) {
		this.mRouteName = mRouteName;
	}
	
	
	
}
