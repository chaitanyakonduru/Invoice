package com.example.invoiceapp.database;

@SuppressWarnings("hiding")
public interface DbQueryCallback<Object> {
	
	void onQueryCompleted(int requestCode,Object object);

}
