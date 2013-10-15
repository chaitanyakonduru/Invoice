package com.example.invoiceapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.invoiceapp.fragments.GoogleMapFragment;

public class RoutesActivity extends FragmentActivity {

	private GoogleMapFragment mapFragment;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_customer);

		mapFragment = new GoogleMapFragment();
		prepareActionBar();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.container, mapFragment).commit();
	}
	
	private void prepareActionBar() {
		ActionBar actionBar = getActionBar();
		View customView = LayoutInflater.from(this).inflate(
				R.layout.layout_custom_actionbar, null);
		actionBar.setCustomView(customView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
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

}
