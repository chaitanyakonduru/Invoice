package com.example.invoiceapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.invoiceapp.adapters.CustomBreadItemAdapter;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class PreviewBreadListActivity extends BaseActivity {

	private List<Product> breadList = null;
	private ListView listView;
	private InvoiceApplication application;
	private DatabaseThread databaseThread;
	public static final String EXTRA_BREAD_LIST = "breadlist";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utilities.registerReceiver(this);
		listView = new ListView(this);
		setContentView(listView);
		Utilities.setActionBarTitle(this, "Ordered Items");
		application = (InvoiceApplication) getApplication();
		databaseThread = application.shareDatabaseThreadInstance();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(EXTRA_BREAD_LIST)) {

			breadList = (ArrayList) bundle
					.getParcelableArrayList(EXTRA_BREAD_LIST);
		}
		if (!breadList.isEmpty()) {
			listView.setAdapter(new CustomBreadItemAdapter(this, -1,
					Constants.PREVIEW_BREAD_LIST, breadList));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem menuItem = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Back");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menuItem = menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Next");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			finish();
			break;
		case Menu.FIRST + 1:
			updateQuantityInfoIntoDatabase();
			startActivity(new Intent(this, OrderConfirmationActivity.class));
			break;
		case android.R.id.home:
			finish();
		default:
			break;
		}
		return true;
	}

	private void updateQuantityInfoIntoDatabase() {
		if (!databaseThread.isAlive()) {
			databaseThread.start();
		}
		for (Product product : breadList) {
			databaseThread.addJob(product);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Utilities.unregisterReceiver(this);
	}

}
