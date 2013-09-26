package com.example.invoiceapp;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.InvoiceAppNetworkServiceManager;
import com.example.invoiceapp.network.JsonResponseParser;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class LoginScreenActivity extends BaseActivity implements
		OnItemSelectedListener, NetworkCallback<Object> {

	private Spinner driversListSpinner;
	private EditText passWordET;
	private List<Driver> driversList;
	private InvoiceApplication application;
	private InvoiceAppDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		Utilities.setActionBarTitle(this, "Authentication");
		driversListSpinner = (Spinner) findViewById(R.id.spinner_driver);
		passWordET = (EditText) findViewById(R.id.et_password);
		application = (InvoiceApplication) getApplication();
		database = application.shareDatabaseInstance();
		// if(database)
		if (!database
				.checkTableNullOrNot(InvoiceAppDatabase.DriverColumns.TABLE_NAME)) {
			Utilities.showProgressDialog(this);
			makeRequestCall();
		} else {
			driversList = database.getAllDrivers();
			setAdapterToSpinner();
		}

		driversListSpinner.setOnItemSelectedListener(this);
	}

	private void setAdapterToSpinner() {
		// TODO Auto-generated method stub

		Driver driver = new Driver();
		driver.setmDriverName("Select Driver");
		driversList.add(0, driver);
		driversListSpinner.setAdapter(new ArrayAdapter<Driver>(this,
				android.R.layout.simple_list_item_1, driversList));
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg2 != 0) {
			findViewById(R.id.tr_password).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.tr_password).setVisibility(View.GONE);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	public void onAuthenticating(View v) {
		boolean loginStatus = false;
		if (driversList != null && !driversList.isEmpty()) {
			Driver driver = (Driver) driversListSpinner.getSelectedItem();
			String respMessage = null;

			if (driver.getmDriverName().equalsIgnoreCase(
					driversList.get(0).getmDriverName())) {
				respMessage = "Please select a Driver";
			}

			else if (Utilities.checkIfNull(passWordET.getText().toString()
					.trim())) {
				respMessage = "Please enter a valid password";
			} else {
				if (passWordET.getText().toString()
						.equalsIgnoreCase(driver.getmDriverPassword())) {
					loginStatus = true;
				} else {
					loginStatus = false;
				}

				if (loginStatus) {
					respMessage = "Login Success";
					loginStatus = false;
					startActivity(new Intent(this, HomeScreenActivity.class));
					finish();
				} else {
					respMessage = "Login Failure";
					passWordET.setText("");
					passWordET.requestFocus();
				}
			}
			Utilities.showToastMessage(this, respMessage);
		}
	}

	private void makeRequestCall() {
		InvoiceAppNetworkServiceManager askZiggyNetworkServiceManager = InvoiceAppNetworkServiceManager
				.getInstance(this);
		askZiggyNetworkServiceManager.fetchDriversRequest(Constants.REQ_FETCH_DRIVERS, this);
	}

	@Override
	public void onSuccess(int requestCode, Object object) {
		// TODO Auto-generated method stub
		Utilities.dismissProgressDialog();
		if (object instanceof String) {
			String response = (String) object;
			driversList = JsonResponseParser.parseDriversResponse(response);
			if (driversList != null && !driversList.isEmpty()) {
				insertDriverInDatabase(driversList);
				setAdapterToSpinner();
			}
		}
	}

	private void insertDriverInDatabase(List<Driver> driversList) {
		DatabaseThread databaseThread=application.shareDatabaseThreadInstance();
		databaseThread.start();
		for (Driver driver : driversList) {
			databaseThread.addJob(driver);
		}
	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		Utilities.dismissProgressDialog();
	}
}
