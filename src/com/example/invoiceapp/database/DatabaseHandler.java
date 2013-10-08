package com.example.invoiceapp.database;


import android.os.Handler;
import android.os.Message;

import com.example.invoiceapp.utils.Constants;

public class DatabaseHandler extends Handler{
	
	private DbQueryCallback<Object> callback;
	private int requestCode;
	public DatabaseHandler(int requestCode,DbQueryCallback<Object> callback)
	{
		this.requestCode=requestCode;
		this.callback=callback;
	}
	
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		if(msg.what==Constants.SUCCESS)
		{
			Object object=msg.obj;
			callback.onQueryExecuted(requestCode, object);
		}
		
	}
}
