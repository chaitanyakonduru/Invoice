package com.example.invoiceapp.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.util.Log;

public class InvoiceAppNetworkServiceManager {

	private static InvoiceAppNetworkServiceManager serviceManager;
	private static ExecutorService executorService;
	private static final String BASE_URL = "http://avml.in/";
	private static final String TAG = InvoiceAppNetworkServiceManager.class
			.getSimpleName();

	public static InvoiceAppNetworkServiceManager getInstance(Context context) {
		return serviceManager == null ? serviceManager = new InvoiceAppNetworkServiceManager(
				context) : serviceManager;
	}

	public InvoiceAppNetworkServiceManager(Context context) {
		executorService = Executors.newSingleThreadExecutor();
	}

	public Future fetchDriversRequest(int requestCode,
			NetworkCallback<Object> callback) {

		String driversUrl = "drivers.json";
		final String UrlString = String.format(BASE_URL) + driversUrl;

		Log.v(TAG, "Request Url:" + UrlString);
		final InvoiceAppHandler handler = new InvoiceAppHandler(requestCode,
				callback);
		return executorService.submit(new HttpRestConn(UrlString, handler));

	}

	public Future fetchProductsRequest(int requestCode,
			NetworkCallback<Object> callback) {
		String driversUrl = "products_list.json";
		final String UrlString = String.format(BASE_URL) + driversUrl;

		Log.v(TAG, "Request Url:" + UrlString);
		final InvoiceAppHandler handler = new InvoiceAppHandler(requestCode,
				callback);
		return executorService.submit(new HttpRestConn(UrlString, handler));

	}

	public Future fetchDriversRouteInfoRequest(int requestCode,
			NetworkCallback<Object> callback) {
		final String UrlString = BASE_URL + "ravi/routeinfo_list_driver.json";

		Log.v(TAG, "Request Url:" + UrlString);
		final InvoiceAppHandler handler = new InvoiceAppHandler(requestCode,
				callback);
		return executorService.submit(new HttpRestConn(UrlString, handler));

	}
	
	public Future getWayPoints(int requestCode,String url,NetworkCallback<Object> callback)
	{
		final String UrlString = url;
		Log.v(TAG, "Request Url:" + UrlString);
		final InvoiceAppHandler handler = new InvoiceAppHandler(requestCode,
				callback);
		return executorService.submit(new HttpRestConn(UrlString, handler));
	}

}