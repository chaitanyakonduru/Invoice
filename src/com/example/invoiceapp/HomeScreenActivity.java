package com.example.invoiceapp;

import android.content.Intent;
import android.os.Bundle;
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
		findViewById(R.id.home_btn_settings).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.home_btn_customers:
			loadCustomers();
			break;
		case R.id.home_btn_route:
			startActivity(new Intent(this,DrawRouteActivity.class));
			break;
		case R.id.home_btn_settings:
			startActivity(new Intent(this,SettingsActivity.class));
			break;
		case R.id.home_btn_invoice:
			startActivity(new Intent(this,PendingInvoiceActivity.class));
			break;
		case R.id.home_btn_balancesheet:
			startActivity(new Intent(this,BalanceSheetActivity.class));
			break;
		default:
			break;
		}
	}

	private void loadCustomers() {
		Intent intent=null;
		 intent=new Intent(this,CustomerListActivity.class);
		 startActivity(intent);
	}
	
	

}
