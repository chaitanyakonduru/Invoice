package com.example.invoiceapp.database;

@SuppressWarnings("hiding")
public interface DbQueryCallback<Object> {
	
	void onQueryExecuted(int requestCode,Object object);

}
