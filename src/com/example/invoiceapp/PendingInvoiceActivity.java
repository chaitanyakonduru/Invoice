package com.example.invoiceapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.invoiceapp.adapters.InvoiceCustomAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.fragments.OrdersFragment;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.PurchasedProduct;
import com.example.invoiceapp.utils.Constants;

public class PendingInvoiceActivity extends BaseActivity implements DbQueryCallback<Object>, OnItemClickListener {
	
	private ListView listView;
	private DatabaseQueryManager databaseQueryManager;
	private InvoiceApplication application;
	private List<Invoice> pendingInvoiceList;
	private String customerId;
	private ProgressBar progressBar;
	private TextView emptyView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_invoices);
		application=(InvoiceApplication) getApplication();
		listView=(ListView) findViewById(R.id.listview);
		progressBar=(ProgressBar)findViewById(R.id.progressbar);
		emptyView=(TextView)findViewById(R.id.empty_view);
		listView.setOnItemClickListener(this);
		databaseQueryManager=DatabaseQueryManager.getInstance(this);
		databaseQueryManager.getPendingInvoiceDetails(Constants.DB_REQ_FETCH_PENDING_INVOICE_DETAILS, application.getmDriverId(), this);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		progressBar.setVisibility(View.GONE);
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_PENDING_INVOICE_DETAILS:
			
			if(object!=null && object instanceof List)
			{
				listView.setVisibility(View.VISIBLE);
				emptyView.setVisibility(View.GONE);
				pendingInvoiceList=((List<Invoice>) object);
				if(pendingInvoiceList!=null && !pendingInvoiceList.isEmpty())
				{
					listView.setAdapter(new InvoiceCustomAdapter(this, -1, pendingInvoiceList));
				}
			}
			else
			{
				listView.setVisibility(View.GONE);
				emptyView.setText("No Pending Invoices");
				emptyView.setVisibility(View.VISIBLE);
			}
			
			break;
		case Constants.DB_REQ_FETCH_INVOICES_DETAILS:
			if(object!=null && object instanceof List)
			{
				List<PurchasedProduct> purchasedProducts=(List<PurchasedProduct>) object;
				Intent intent=new Intent(this,PurchaseActivity.class);
				intent.putParcelableArrayListExtra(PurchaseActivity.EXTRA_PURCHASE_ITEMS, ((ArrayList)purchasedProducts));
				intent.putExtra(OrdersFragment.CUSTOMER_NAME,customerId);
				intent.putExtra(PurchaseActivity.EXTRA__IS_FROM_INVOICE_PAGE, true);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		customerId=pendingInvoiceList.get(arg2).getCustomerId();
		databaseQueryManager.getInvoiceDetails(Constants.DB_REQ_FETCH_INVOICES_DETAILS, pendingInvoiceList.get(arg2).getInvoiceId(), this);
	}

}
