package com.example.invoiceapp.fragments;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.invoiceapp.InvoiceApplication;
import com.example.invoiceapp.R;
import com.example.invoiceapp.adapters.OrderedProductsCustomdapter;
import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.PurchaseProducts;

public class OrdersFragment extends Fragment {

	private static OrdersFragment orderFragment;
	private static Customer customer = null;
	private ListView listview;
	private InvoiceApplication application;
	private InvoiceAppDatabase database;
	public OrdersFragment() {

	}

	public static OrdersFragment newInstance(Customer customer) {

		OrdersFragment.customer = customer;
		orderFragment = new OrdersFragment();
		return orderFragment;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		application=(InvoiceApplication) getActivity().getApplication();
		database=application.shareDatabaseInstance();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem menuItem=menu.add(0, 1, 0, "NEXT");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.layout_orders,
				null);
		listview = (ListView) v.findViewById(R.id.listview);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(customer!=null)
		{
			List<PurchaseProducts> purchasedProductsList=database.getOrderedProducts(customer.getmCustomerId());
			if(purchasedProductsList!=null && !purchasedProductsList.isEmpty())
			{
				listview.setAdapter(new OrderedProductsCustomdapter(getActivity(), -1,purchasedProductsList));
			}
		}
	}

}
