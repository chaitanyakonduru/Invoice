package com.example.invoiceapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.invoiceapp.fragments.CustomerDetailsFragment;
import com.example.invoiceapp.fragments.InvoiceFragment;
import com.example.invoiceapp.fragments.OrdersFragment;
import com.example.invoiceapp.fragments.RemindersFragment;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.FinishActivityReceiver;

public class CustomerActivity extends FragmentActivity implements TabListener {

	public static final String EXTRA_CUSTOMER = "customer";
	private Fragment ordersFragment;
	private Fragment customerDetailsFragment;
	private Fragment invoiceFragment;
	private RemindersFragment reminderFragment;
	private Customer customer;
	public String mCustomerId = null;
	private FinishActivityReceiver activityReceiver;
	private ActionBar actionBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_customer);
		registerReceiver();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(EXTRA_CUSTOMER)) {
			customer = (Customer) bundle.get(EXTRA_CUSTOMER);
			mCustomerId = customer.getmCustomerId();
			actionBar = getActionBar();
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_HOME);
			View customView = LayoutInflater.from(this).inflate(
					R.layout.layout_custom_actionbar, null);
			actionBar.setCustomView(customView);

			TextView textView = (TextView) actionBar.getCustomView()
					.findViewById(R.id.actionbar_custom_title);
			textView.setText(customer.getmCustomerName());
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
	protected void onResume() {
		super.onResume();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "HOME").setIcon(R.drawable.ic_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		else if(item.getItemId()==1)
		{
			startActivity(new Intent(this,HomeScreenActivity.class));
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver();
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction fragmentTransaction) {
		switch (arg0.getPosition()) {
		case 0:
			if (ordersFragment == null) {
				ordersFragment = OrdersFragment.newInstance(customer);
			}

			fragmentTransaction.replace(R.id.container, ordersFragment);
			break;
		case 1:

			if (customerDetailsFragment == null) {
				customerDetailsFragment = CustomerDetailsFragment
						.newInstance(customer);
			}
			fragmentTransaction
					.replace(R.id.container, customerDetailsFragment);
			break;
		case 2:
			if (invoiceFragment == null) {
				invoiceFragment = InvoiceFragment.newInstance(customer);
			}
			fragmentTransaction.replace(R.id.container, invoiceFragment);
			break;
		case 3:
			if (reminderFragment == null) {
				reminderFragment = RemindersFragment.newInstance();
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

	

	public String getCustomerId() {
		return mCustomerId;
	}
	
	public void registerReceiver()
	{
		IntentFilter filter=new IntentFilter(Constants.CUSTOM_ACTION_INTENT);
		activityReceiver=new FinishActivityReceiver(this);
		registerReceiver(activityReceiver, filter);
	}

	public  void unregisterReceiver()
	{
		if(activityReceiver!=null)
		{
			unregisterReceiver(activityReceiver);
			activityReceiver=null;
		}
	}

}
