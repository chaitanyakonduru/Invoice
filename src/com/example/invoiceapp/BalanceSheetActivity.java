package com.example.invoiceapp;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.invoiceapp.adapters.BalanceSheetCustomAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class BalanceSheetActivity extends BaseActivity implements DbQueryCallback<Object>{
	
	private DatabaseQueryManager databaseQueryManager;
	private ListView listView;
	private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_balance_sheet);
		prepareViews();
		Utilities.setActionBarTitle(this, "Balance Sheet");
		databaseQueryManager=DatabaseQueryManager.getInstance(this);
		databaseQueryManager.getAllProducts(Constants.DB_REQ_FETCH_PRODUCTS, this);
		
	}
	private void prepareViews() {
		listView=(ListView) findViewById(R.id.listview);
		progressBar=(ProgressBar) findViewById(R.id.progressbar);
		listView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
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

		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "HOME").setIcon(R.drawable.ic_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
		return super.onOptionsItemSelected(item);
	}

}
