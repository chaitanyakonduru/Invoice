package com.example.invoiceapp.network;


@SuppressWarnings("hiding")
public interface NetworkCallback<Object> {
	
	void onSuccess(int requestCode,Object object);
	void onFailure(int requestCode,String errorMessge);
}