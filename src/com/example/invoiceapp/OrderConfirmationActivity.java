package com.example.invoiceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class OrderConfirmationActivity  extends BaseActivity{
	
	private EditText confirmationPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utilities.registerReceiver(this);
		setContentView(R.layout.layout_confirmation_order);
		Utilities.setActionBarTitle(this, "Confirmation Page");
		confirmationPassword=(EditText) findViewById(R.id.et_password);
			
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Utilities.unregisterReceiver(this);
	}
	
	public void onAuthenticating(View v)
	{
		String respMessage;
		if(!Utilities.checkIfNull(confirmationPassword.getText().toString().trim()))
		{
			Intent intent=new Intent(this,CustomerListActivity.class);
			startActivity(intent);
			respMessage="Ordered Success";
			saveAuthentication();
			Intent inten=new Intent(Constants.CUSTOM_ACTION_INTENT);
			sendBroadcast(inten);
			
			
		}
		else
		{
			respMessage="Please enter your password";
		}
		Utilities.showToastMessage(this,respMessage);
		
	}

	private void saveAuthentication() {
		 
		        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		        SharedPreferences.Editor editor = appPreferences.edit();
		        editor.putBoolean(getString(R.string.is_pickup_products), true);
		        editor.commit();
	}

}
