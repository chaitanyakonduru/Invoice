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

import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.InvoiceAppNetworkServiceManager;
import com.example.invoiceapp.network.JsonResponseParser;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class LoginScreenActivity extends BaseActivity implements
		OnItemSelectedListener, NetworkCallback<Object>,
		DbQueryCallback<Object> {

	private Spinner driversListSpinner;
	private EditText passWordET;
	private List<Driver> driversList;
	private InvoiceApplication application;
	private DatabaseQueryManager databaseQueryManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		Utilities.setActionBarTitle(this, "Authentication");
		driversListSpinner = (Spinner) findViewById(R.id.spinner_driver);
		passWordET = (EditText) findViewById(R.id.et_password);
		passWordET.setText("123456");
		application = (InvoiceApplication) getApplication();
		databaseQueryManager = DatabaseQueryManager.getInstance(this);
		databaseQueryManager.checkTableNullOrNot(
				Constants.DB_REQ_CHECK_TABLE_NULL_NOT,
				InvoiceAppDatabase.DriverColumns.TABLE_NAME, this);

		driversListSpinner.setOnItemSelectedListener(this);
	}

	private void setAdapterToSpinner(List<Driver> driversList) {
		// TODO Auto-generated method stub

		Driver driver = new Driver();
		driver.setmDriverName("Select Driver");
		driversList.add(0, driver);
		driversListSpinner.setAdapter(new ArrayAdapter<Driver>(this,
				android.R.layout.simple_list_item_1, driversList));
		driversListSpinner.setSelection(1);
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
					application.setmDriverId(driver.getmDriverId());
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
		askZiggyNetworkServiceManager.fetchDriversRequest(
				Constants.REQ_FETCH_DRIVERS, this);
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
				setAdapterToSpinner(driversList);
			}

		}
	}

	private void insertDriverInDatabase(List<Driver> driversList) {
		DatabaseThread databaseThread = application
				.shareDatabaseThreadInstance();
		databaseThread.start();
		for (Driver driver : driversList) {
			databaseThread.addJob(driver);
		}
	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		Utilities.dismissProgressDialog();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryCompleted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_CHECK_TABLE_NULL_NOT:

			if (object != null && object instanceof Boolean) {
				boolean status = (Boolean) object;
				if (status) {
					databaseQueryManager.getAllDrivers(
							Constants.DB_REQ_FETCH_DRIVERS, this);
				} else {
					Utilities.showProgressDialog(this);
					makeRequestCall();
				}
			}
			break;
		case Constants.DB_REQ_FETCH_DRIVERS:
			if (object != null && object instanceof List) {
				driversList = ((List<Driver>) object);
				setAdapterToSpinner(driversList);

			}
		default:
			break;
		}

	}
}
