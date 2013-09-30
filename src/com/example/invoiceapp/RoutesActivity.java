package com.example.invoiceapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.invoiceapp.fragments.GoogleMapFragment;

public class RoutesActivity extends FragmentActivity {

	private GoogleMapFragment mapFragment;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_customer);

		mapFragment = new GoogleMapFragment();

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.container, mapFragment).commit();
	}

}
