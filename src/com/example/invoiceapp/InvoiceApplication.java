package com.example.invoiceapp;

import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.network.DatabaseThread;

import android.app.Application;

public class InvoiceApplication extends Application{
	
	private InvoiceAppDatabase invoiceAppDatabase;
	private DatabaseThread databaseThread;
	public InvoiceAppDatabase shareDatabaseInstance()
	{
		return invoiceAppDatabase==null?InvoiceAppDatabase.getInstance(this):invoiceAppDatabase;
	}
	
	public DatabaseThread shareDatabaseThreadInstance()
	{
		return databaseThread==null?databaseThread=new DatabaseThread(this):databaseThread;
	}

}
