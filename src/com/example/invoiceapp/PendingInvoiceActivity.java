package com.example.invoiceapp;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.invoiceapp.adapters.InvoiceCustomAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.utils.Constants;

public class PendingInvoiceActivity extends BaseActivity implements DbQueryCallback<Object> {
	
	private ListView listView;
	private DatabaseQueryManager databaseQueryManager;
	private InvoiceApplication application;
	private List<Invoice> pendingInvoiceList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_invoices);
		application=(InvoiceApplication) getApplication();
		listView=(ListView) findViewById(R.id.listview);
		databaseQueryManager=DatabaseQueryManager.getInstance(this);
		databaseQueryManager.getPendingInvoiceDetails(Constants.DB_REQ_FETCH_PENDING_INVOICE_DETAILS, application.getmDriverId(), this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_PENDING_INVOICE_DETAILS:
			findViewById(R.id.progressbar).setVisibility(View.GONE);
			if(object!=null && object instanceof List)
			{
				listView.setVisibility(View.VISIBLE);
				pendingInvoiceList=((List<Invoice>) object);
				if(pendingInvoiceList!=null && !pendingInvoiceList.isEmpty())
				{
					listView.setAdapter(new InvoiceCustomAdapter(this, -1, pendingInvoiceList));
				}
			}
			
			break;

		default:
			break;
		}
	}

}
