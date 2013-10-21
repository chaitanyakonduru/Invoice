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
	private boolean status;
	public static final String KEY_PICK_UP_STATUS="pick_up_status";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_confirmation_order);
		Utilities.setActionBarTitle(this, "Confirmation Page");
		confirmationPassword=(EditText) findViewById(R.id.et_password);
			Bundle bundle=getIntent().getExtras();
			if(bundle!=null && bundle.containsKey(KEY_PICK_UP_STATUS))
			{
				status=bundle.getBoolean(KEY_PICK_UP_STATUS);
			}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void onAuthenticating(View v)
	{
		if(!Utilities.checkIfNull(confirmationPassword.getText().toString().trim()))
		{
			if(status)
			{
			Intent intent=new Intent(this,HomeScreenActivity.class);
			startActivity(intent);
			}
			saveAuthentication(status);
			Intent inten=new Intent(Constants.CUSTOM_ACTION_INTENT);
			sendBroadcast(inten);
			inten=new Intent(Constants.CUSTOM_HOME_ACTION_INTENT);
			sendBroadcast(inten);
			finish();
		}
		
		
	}

	private void saveAuthentication(boolean status) {
		 
		        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		        SharedPreferences.Editor editor = appPreferences.edit();
		        editor.putBoolean(getString(R.string.is_pickup_products), status);
		        editor.commit();
	}

}
