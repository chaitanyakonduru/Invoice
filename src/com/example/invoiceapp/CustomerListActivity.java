package com.example.invoiceapp;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.Order;
import com.example.invoiceapp.models.OrderProduct;
import com.example.invoiceapp.models.RouteInfo;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.DatabaseThread.onDatabaseUpdateCompletion;
import com.example.invoiceapp.network.InvoiceAppNetworkServiceManager;
import com.example.invoiceapp.network.JsonResponseParser;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class CustomerListActivity extends BaseActivity implements
		OnItemClickListener, NetworkCallback<Object>,
		onDatabaseUpdateCompletion, DbQueryCallback<Object> {

	private static final String TAG = null;
	private InvoiceApplication invoiceApplication;
	private DatabaseThread databaseThread;
	private String mDriverId;
	private ListView listView;
	private List<Customer> customersList;
	private DatabaseQueryManager databaseQueryManager;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_customes);
		progressBar = (ProgressBar) findViewById(R.id.customer_list_progressbar);
		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		Utilities.setActionBarTitle(this, "Customers List");
		invoiceApplication = (InvoiceApplication) getApplication();
		databaseQueryManager = DatabaseQueryManager.getInstance(this);
		databaseThread = new DatabaseThread(this, this);
		mDriverId = invoiceApplication.getmDriverId();
		fetchRoutesInfoCallback(mDriverId);

	}

	private void fetchRoutesInfoCallback(String DriverId) {
		InvoiceAppNetworkServiceManager manager = InvoiceAppNetworkServiceManager
				.getInstance(this);
		if (manager != null) {
			manager.fetchDriversRouteInfoRequest(Constants.REQ_FETCH_ROUTES,
					this);
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "HOME").setIcon(R.drawable.ic_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
				startActivity(new Intent(this,HomeScreenActivity.class));
				finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Customer customer = customersList.get(arg2);
		invoiceApplication.setCustomerId(customer.getmCustomerId());
		Intent intent = new Intent(this, CustomerActivity.class);
		intent.putExtra(CustomerActivity.EXTRA_CUSTOMER, customer);
		startActivity(intent);
	}

	@Override
	public void onSuccess(int requestCode, Object object) {
		if (progressBar != null && progressBar.isShown()) {
			progressBar.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}
		if (requestCode == Constants.REQ_FETCH_ROUTES) {
			if (object instanceof String) {
				RouteInfo routeInfo = JsonResponseParser
						.parseDriverRouteInfoResponse((String) object);
				if (routeInfo != null) {
					List<Customer> customersList = routeInfo.getCustomesList();
					if (customersList != null && !customersList.isEmpty()) {
						if (!databaseThread.isAlive()) {
							databaseThread.start();
						}
						for (Customer customer : customersList) {
							customer.setmDriverId(mDriverId);
							notifyUserWithAlarmService();
							databaseThread.addJob(customer);

							if (customer.getOrder() != null) {
								Order order = customer.getOrder();
								order.setmCustomerId(customer.getmCustomerId());
								databaseThread.addJob(order);
								if (order.getmOrderedProductsList() != null
										&& !order.getmOrderedProductsList()
												.isEmpty()) {
									for (OrderProduct orderProduct : order
											.getmOrderedProductsList()) {
										Log.v(TAG,
												"Order Product Id:"
														+ orderProduct
																.getmOrderProductId());
										databaseThread.addJob(orderProduct);
									}
								}
							}
						}
					}
				}

			}
		}
	}

	private void notifyUserWithAlarmService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		if (progressBar != null && progressBar.isShown()) {
			progressBar.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void databaseCompleted() {
		Utilities.showToastMessage(CustomerListActivity.this,
				"Update Database Completed!!!");
		loadCustomers();

	}

	protected void loadCustomers() {
		databaseQueryManager.getCustomers(mDriverId,
				Constants.DB_REQ_FETCH_CUSTOMERS, this);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_CUSTOMERS:
			if (object != null && object instanceof List)
				customersList = ((List<Customer>) object);
			if (customersList != null && !customersList.isEmpty()) {
				listView.setAdapter(new ArrayAdapter<Customer>(this,
						android.R.layout.simple_list_item_1, customersList));
			}
			break;

		default:
			break;
		}
	}

}
