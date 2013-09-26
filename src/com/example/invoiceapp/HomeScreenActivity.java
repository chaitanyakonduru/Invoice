package com.example.invoiceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.invoiceapp.utils.Utilities;

public class HomeScreenActivity extends BaseActivity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		Utilities.setActionBarTitle(this, "Home");
		prepareUI();
	}

	private void prepareUI() {
		findViewById(R.id.home_btn_customers).setOnClickListener(this);
		findViewById(R.id.home_btn_route).setOnClickListener(this);
		findViewById(R.id.home_btn_invoice).setOnClickListener(this);
		findViewById(R.id.home_btn_balancesheet).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.home_btn_customers:
			loadCustomers();
			break;

		default:
			break;
		}
	}

	private void loadCustomers() {
		Intent intent=null;
		 if(isPickedProducts())
		 {
			 intent=new Intent(this,CustomerListActivity.class);
		 }
		 else
		 {
			 intent=new Intent(this,BreadListActivity.class);
		 }
		 startActivity(intent);
	}
	
	private boolean isPickedProducts()
	{
		 SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		  return appPreferences.getBoolean(getString(R.string.is_pickup_products), false);
	       
	}

}
