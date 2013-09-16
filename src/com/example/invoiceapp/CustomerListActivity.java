package com.example.invoiceapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.invoiceapp.utils.Utilities;

public class CustomerListActivity extends BaseActivity implements OnItemClickListener{
	
	private List<String> customersList=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView=new ListView(this);
		setContentView(listView);
		listView.setOnItemClickListener(this);
		Utilities.setActionBarTitle(this,"Customers List");
		generateCustomersList();
		if(!customersList.isEmpty())
		{
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,customersList));
		}
		
	}

	private void generateCustomersList() {
		customersList=new ArrayList<String>();
		for(int i=0;i<10;i++)
		{
			customersList.add("Customer"+i);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String customerName=customersList.get(arg2);
		Intent intent=new Intent(this,CustomerActivity.class);
		intent.putExtra(CustomerActivity.EXTRA_CUSTOMER, customerName);
		startActivity(intent);
		
	}

}
