package com.example.invoiceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.invoiceapp.utils.Utilities;

public class OrderConfirmationActivity  extends BaseActivity{
	
	private EditText confirmationPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_confirmation_order);
		confirmationPassword=(EditText) findViewById(R.id.et_password);
			
	}
	
	public void onAuthenticating(View v)
	{
		String respMessage;
		if(!Utilities.checkIfNull(confirmationPassword.getText().toString().trim()))
		{
			respMessage="Ordered Success";
			Intent intent=new Intent(this,CustomerListActivity.class);
			startActivity(intent);
		}
		else
		{
			respMessage="Please enter your password";
		}
		Utilities.showToastMessage(this,respMessage);
		
	}

}
