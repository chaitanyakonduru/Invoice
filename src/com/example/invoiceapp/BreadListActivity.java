package com.example.invoiceapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.invoiceapp.adapters.CustomBreadItemAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.InvoiceAppNetworkServiceManager;
import com.example.invoiceapp.network.JsonResponseParser;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.FinishActivityReceiver;
import com.example.invoiceapp.utils.Utilities;

public class BreadListActivity extends BaseActivity implements
		NetworkCallback<Object>, DbQueryCallback<Object> {

	private ListView listView;
	private InvoiceAppNetworkServiceManager appNetworkServiceManager;
	private List<Product> productsList;
	private InvoiceApplication application;
	private DatabaseQueryManager databaseQueryManager;
	private FinishActivityReceiver activityReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		listView = new ListView(this);
		listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		setContentView(listView);
		
		registerReceiver();
		Utilities.setActionBarTitle(this, "Select Products");
		application = (InvoiceApplication) getApplication();
		appNetworkServiceManager = InvoiceAppNetworkServiceManager
				.getInstance(this);
		databaseQueryManager = DatabaseQueryManager.getInstance(this);
		databaseQueryManager.checkTableNullOrNot(
				Constants.DB_REQ_CHECK_TABLE_NULL_NOT,
				InvoiceAppDatabase.ProductColumns.TABLE_NAME, this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Next");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			if(productsList!=null && !productsList.isEmpty())
			{
			filterItems();
			}
			break;
		case android.R.id.home:
			finish();
		default:
			break;
		}
		return true;
	}

	private void filterItems() {

		CustomBreadItemAdapter adapter = (CustomBreadItemAdapter) listView
				.getAdapter();

		List<Product> filteredList = new ArrayList<Product>();
		for (int i = 0; i < productsList.size(); i++) {
			Product bread = productsList.get(i);
			View v = adapter.getView(i, null, null);

			EditText quantityEText = (EditText) v
					.findViewById(R.id.et_qunatity);
			if (!Utilities.checkIfNull(quantityEText.getText().toString()
					.trim())) {
				bread.setOrdered(true);
				filteredList.add(bread);
			}

		}
		if (!filteredList.isEmpty()) {
			Intent intent = new Intent(this, PreviewBreadListActivity.class);
			intent.putParcelableArrayListExtra(
					PreviewBreadListActivity.EXTRA_BREAD_LIST,
					(ArrayList<Product>) filteredList);
			startActivity(intent);
		}

	}

	@Override
	public void onSuccess(int requestCode, Object object) {
		Utilities.dismissProgressDialog();
		if (requestCode == Constants.REQ_FETCH_PRODUCTS) {
			if (object instanceof String) {
				productsList = JsonResponseParser
						.parseProductsResponse((String) object);
				bindProductstoListView(productsList);
			}
		}
	}

	private void bindProductstoListView(List<Product> productsList2) {
		if (productsList2 != null && !productsList2.isEmpty()) {
			insertProductIntoDatabase();
			listView.setAdapter(new CustomBreadItemAdapter(this, -1,
					Constants.BREAD_LIST, productsList2));
		}
	}

	private void insertProductIntoDatabase() {
		DatabaseThread databaseThread = application
				.shareDatabaseThreadInstance();
		if (!databaseThread.isAlive()) {
			databaseThread.start();
		}
		for (Product product : productsList) {
			databaseThread.addJob(product);
		}
	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		Utilities.dismissProgressDialog();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		switch (requestCode) {

		case Constants.DB_REQ_CHECK_TABLE_NULL_NOT:

			if (object != null && object instanceof Boolean) {
				boolean status = (Boolean) object;
				if (status) {
					databaseQueryManager.getAllProducts(
							Constants.DB_REQ_FETCH_PRODUCTS, this);
				} else {
					Utilities.showProgressDialog(this);
					appNetworkServiceManager.fetchProductsRequest(
							Constants.REQ_FETCH_PRODUCTS, this);
				}
			}
			break;
		case Constants.DB_REQ_FETCH_PRODUCTS:
			if (object != null && object instanceof List) {
				productsList = ((List<Product>) object);
				;
				bindProductstoListView(productsList);
			}
			break;
		}

	}
	

	public void registerReceiver()
	{
		IntentFilter filter=new IntentFilter(Constants.CUSTOM_ACTION_INTENT);
		activityReceiver=new FinishActivityReceiver(this);
		registerReceiver(activityReceiver, filter);
	}

	public  void unregisterReceiver()
	{
		if(activityReceiver!=null)
		{
			unregisterReceiver(activityReceiver);
			activityReceiver=null;
		}
	}
}
