package com.example.invoiceapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.invoiceapp.utils.Utilities;

public class LoginScreenActivity extends Activity implements OnItemSelectedListener {

	private static final String[] DRIVERS_LIST={"Select Driver","Driver1","Driver2","Driver3","Driver4","Driver5"};
	private Spinner driversListSpinner;
	private EditText passWordET;
   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        driversListSpinner=(Spinner) findViewById(R.id.spinner_driver);
        passWordET=(EditText)findViewById(R.id.et_password);
        driversListSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DRIVERS_LIST));
        driversListSpinner.setOnItemSelectedListener(this);
    }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if(arg2!=0)
		{
			findViewById(R.id.tr_password).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.tr_password).setVisibility(View.GONE);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	public void onAuthenticating(View v)
	{
		String string=(String) driversListSpinner.getSelectedItem();
		String respMessage;
		if(string.equalsIgnoreCase(DRIVERS_LIST[0]))
		{
			respMessage="Please select a Driver";
		}
		else if(Utilities.checkIfNull(passWordET.getText().toString().trim()))
		{
			respMessage="Please enter a valid password";
		}
		else if(string.equalsIgnoreCase("Driver2") && passWordET.getText().toString().equalsIgnoreCase("123456"))
		{
//		
			startActivity(new Intent(this,BreadListActivity.class));
			respMessage="Login Success";
		}
		else
		{
			respMessage="Login Failure";
		}
		Utilities.showToastMessage(this,respMessage);
	}
    
}
