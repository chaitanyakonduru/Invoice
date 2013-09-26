package com.example.invoiceapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.invoiceapp.fragments.CustomerDetailsFragment;
import com.example.invoiceapp.fragments.InvoiceFragment;
import com.example.invoiceapp.fragments.OrdersFragment;
import com.example.invoiceapp.fragments.RemindersFragment;

public class CustomerActivity extends FragmentActivity implements TabListener {

	public static final String EXTRA_CUSTOMER = "customer";
	private Fragment ordersFragment;
	private Fragment customerDetailsFragment;
	private Fragment invoiceFragment;
	private RemindersFragment reminderFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_customer);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(EXTRA_CUSTOMER)) {
			String customerName = bundle.getString(EXTRA_CUSTOMER);
			
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_HOME);
			 View customView=LayoutInflater.from(this).inflate(R.layout.layout_custom_actionbar, null);
			  actionBar.setCustomView(customView);
			  
			TextView textView = (TextView) actionBar.getCustomView()
					.findViewById(R.id.actionbar_custom_title);
			textView.setText(customerName);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.addTab(actionBar.newTab().setText("Orders")
					.setTabListener(this).setTag("orders"));
			actionBar.addTab(actionBar.newTab().setText("Details")
					.setTabListener(this).setTag("details"));
			actionBar.addTab(actionBar.newTab().setText("Invoice")
					.setTabListener(this).setTag("invoice"));
			actionBar.addTab(actionBar.newTab().setText("Reminders")
					.setTabListener(this).setTag("reminders"));
		}
		
		 
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction fragmentTransaction) {
		switch (arg0.getPosition()) {
		case 0:
			if(ordersFragment==null)
			{
				ordersFragment=OrdersFragment.newInstance();
			}
			
			 fragmentTransaction.replace(R.id.container, ordersFragment);
			break;
		case 1:
			
			if(customerDetailsFragment==null)
			{
				customerDetailsFragment=CustomerDetailsFragment.newInstance();
			}
			 fragmentTransaction.replace(R.id.container, customerDetailsFragment);
			 break;
		case 2:
			if(invoiceFragment==null)
			{
				invoiceFragment=InvoiceFragment.newInstance();
			}
			 fragmentTransaction.replace(R.id.container, invoiceFragment);
			break;
		case 3:
			if(reminderFragment==null)
			{
				reminderFragment=RemindersFragment.newInstance();
			}
			 fragmentTransaction.replace(R.id.container, reminderFragment);
			 break;
		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home)
		{
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	
}
