package com.example.invoiceapp.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;

import com.example.invoiceapp.InvoiceApplication;

public class DatabaseQueryManager {
	private static DatabaseQueryManager databaseQueryManager;
	private ExecutorService executorService;
	private InvoiceApplication application;
	private InvoiceAppDatabase database;
	
	public static DatabaseQueryManager getInstance(Context context)
	{
		return databaseQueryManager==null?new DatabaseQueryManager(context):databaseQueryManager;
	}
	
	 public DatabaseQueryManager(Context context) {
		executorService = Executors.newSingleThreadExecutor();
		application=(InvoiceApplication) context.getApplicationContext();
		database=application.shareDatabaseInstance();
	}
	 
	 public void getCustomers(final String driverId,final int reqCode,final DbQueryCallback<Object> callback)
	 {
		 final DatabaseHandler databaseHandler=new DatabaseHandler(reqCode, callback);
		 executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				database.getAllCustomers(driverId, databaseHandler);
			}
		});
		 
	 }
	 
	 public void getAllDrivers(final int reqCode,final DbQueryCallback<Object> callback)
	 {
		 final DatabaseHandler databaseHandler=new DatabaseHandler(reqCode, callback);
		 executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				database.getAllDrivers(databaseHandler);
			}
		});
		 
	 }
	 
	 public void checkTableNullOrNot(int reqCode,final String tableName ,DbQueryCallback<Object> callback)
	 {
		 final DatabaseHandler databaseHandler=new DatabaseHandler(reqCode, callback);
		 executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					database.checkTableNullOrNot(tableName,databaseHandler);
				}
			});
	 }
	 

	 public void getAllProducts(final int reqCode,final DbQueryCallback<Object> callback)
	 {
		 final DatabaseHandler databaseHandler=new DatabaseHandler(reqCode, callback);
		 executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				database.getAllProducts(databaseHandler);
			}
		});
		 
	 }
	

}
