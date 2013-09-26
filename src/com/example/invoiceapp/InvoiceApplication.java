package com.example.invoiceapp;

import com.example.invoiceapp.database.InvoiceAppDatabase;

import android.app.Application;

public class InvoiceApplication extends Application{
	
	private InvoiceAppDatabase invoiceAppDatabase;
	
	public InvoiceAppDatabase shareDatabaseInstance()
	{
		return invoiceAppDatabase==null?InvoiceAppDatabase.getInstance(this):invoiceAppDatabase;
	}

}
