package com.example.invoiceapp;

import com.example.invoiceapp.utils.Utilities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
