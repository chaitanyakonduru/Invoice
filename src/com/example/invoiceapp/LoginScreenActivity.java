package com.example.invoiceapp;

import java.util.List;

import android.app.ProgressDialog;
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
import com.example.invoiceapp.network.AskZiggyNetworkServiceManager;
import com.example.invoiceapp.network.JsonResponseParser;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Utilities;

public class LoginScreenActivity extends BaseActivity implements
		OnItemSelectedListener, NetworkCallback<Object> {

	// private static final String[]
	// DRIVERS_LIST={"Select Driver","Driver1","Driver2","Driver3","Driver4","Driver5"};
	private Spinner driversListSpinner;
	private EditText passWordET;
	private ProgressDialog progressDialog;
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
		// InvoiceAppDatabase appDatabase=InvoiceAppDatabase.getInstance(this);
		application = (InvoiceApplication) getApplication();
		database = application.shareDatabaseInstance();
		// if(database)
		if (!database
				.checkTableNullOrNot(InvoiceAppDatabase.DriverColumns.TABLE_NAME)) {
			showProgressDialog();
			makeRequestCall();
		}

		driversListSpinner.setOnItemSelectedListener(this);
	}

	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, "", "Please wait",
					false, true);
		}
		progressDialog.show();
	}

	private void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
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
					startActivity(new Intent(this, BreadListActivity.class));
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
		AskZiggyNetworkServiceManager askZiggyNetworkServiceManager = AskZiggyNetworkServiceManager
				.getInstance(this);
		askZiggyNetworkServiceManager.sendUserFeedback(100, this);
	}

	@Override
	public void onSuccess(int requestCode, Object object) {
		// TODO Auto-generated method stub
		dismissDialog();
		if (object instanceof String) {
			String response = (String) object;
			driversList = JsonResponseParser.parseFeedbackResponse(response);
			if (driversList != null && !driversList.isEmpty()) {
				insertDriverInDatabase(driversList);
				Driver driver = new Driver();
				driver.setmDriverName("Select Driver");
				driversList.add(0, driver);
				driversListSpinner.setAdapter(new ArrayAdapter<Driver>(this,
						android.R.layout.simple_list_item_1, driversList));
			}
		}
	}

	private void insertDriverInDatabase(List<Driver> driversList) {
		for(Driver driver:driversList)
		{
			database.insertDriver(driver);
		}
	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		dismissDialog();
	}
}
