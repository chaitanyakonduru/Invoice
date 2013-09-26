package com.example.invoiceapp.network;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.util.Log;

public class AskZiggyNetworkServiceManager {

	private static AskZiggyNetworkServiceManager serviceManager;
	private static ExecutorService executorService;
	private static final String BASE_URL = "http://avml.in/drivers.json";
	private static final String TAG = AskZiggyNetworkServiceManager.class
			.getSimpleName();

	public static AskZiggyNetworkServiceManager getInstance(Context context) {
		return serviceManager == null ? serviceManager = new AskZiggyNetworkServiceManager(
				context) : serviceManager;
	}

	public AskZiggyNetworkServiceManager(Context context) {
		executorService = Executors.newSingleThreadExecutor();
	}


	public Future sendUserFeedback(int requestCode,NetworkCallback<Object> callback)
	{
		final String UrlString = String.format(BASE_URL);
		
		Log.v(TAG, "Request Url:" + UrlString);
		final AskZiggyHandler handler = new AskZiggyHandler(requestCode,callback);
		return executorService.submit(new HttpRestConn(UrlString, handler));
			
	}

}