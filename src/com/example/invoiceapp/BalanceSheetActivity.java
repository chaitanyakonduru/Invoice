package com.example.invoiceapp;

import java.util.List;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.invoiceapp.adapters.BalanceSheetCustomAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.FinishActivityReceiver;
import com.example.invoiceapp.utils.Utilities;

public class BalanceSheetActivity extends BaseActivity implements DbQueryCallback<Object>{
	
	private DatabaseQueryManager databaseQueryManager;
	private ListView listView;
	private ProgressBar progressBar;
	private FinishActivityReceiver activityReceiver;
	private List<Invoice> pendingInvoiceList;
	private String totalAmountByCash;
	private String totalAmountByCheque;
	private String totalDues;
	private TextView amtByCashView;
	private TextView amtByChequeView;
	private TextView duesView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerReceiver();
		setContentView(R.layout.layout_balance_sheet);
		prepareViews();
		Utilities.setActionBarTitle(this, "Balance Sheet");
		databaseQueryManager=DatabaseQueryManager.getInstance(this);
		databaseQueryManager.getAllProducts(Constants.DB_REQ_FETCH_PRODUCTS, this);
		InvoiceAppDatabase database=InvoiceAppDatabase.getInstance(this);
		totalAmountByCash=database.getTotalAmountByCash();
		totalAmountByCheque=database.getTotalAmountByCheque();
		totalDues=database.getDues();
	
		amtByCashView.setText("By Cash:"+totalAmountByCash);
		amtByChequeView.setText("By Cheque:"+totalAmountByCheque);
		duesView.setText("Dues:"+totalDues);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver();
	}
	private void prepareViews() {
		listView=(ListView) findViewById(R.id.balance_sheet_listview);
		progressBar=(ProgressBar) findViewById(R.id.progressbar);
		listView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		amtByCashView=(TextView) findViewById(R.id.total_amt_cash);
		amtByChequeView=(TextView) findViewById(R.id.total_amt_cheque);
		duesView=(TextView) findViewById(R.id.total_dues);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_PRODUCTS:
			progressBar.setVisibility(View.GONE);
			if(object!=null && object instanceof List)
			{
				List<Product> productsList=(List<Product>) object;
				if(productsList!=null && !productsList.isEmpty())
				{
					listView.setVisibility(View.VISIBLE);
					listView.setAdapter(new BalanceSheetCustomAdapter(this, -1, productsList));
				}
			}
			
			break;
			
		case Constants.DB_REQ_FETCH_PENDING_INVOICE_DETAILS:
			progressBar.setVisibility(View.GONE);
			
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "HOME").setIcon(R.drawable.ic_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, 2, 0, "Sign Off").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		else if(item.getItemId()==1)
		{
			startActivity(new Intent(this,HomeScreenActivity.class));
			finish();
		}
		else if(item.getItemId()==2)
		{
			Intent intent=new Intent(this, OrderConfirmationActivity.class);
			intent.putExtra(OrderConfirmationActivity.KEY_PICK_UP_STATUS, false);
				startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void registerReceiver() {
		IntentFilter filter = new IntentFilter(Constants.CUSTOM_ACTION_INTENT);
		activityReceiver = new FinishActivityReceiver(this);
		registerReceiver(activityReceiver, filter);
	}

	public void unregisterReceiver() {
		if (activityReceiver != null) {
			unregisterReceiver(activityReceiver);
			activityReceiver = null;
		}
	}

}
