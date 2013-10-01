package com.example.invoiceapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.example.invoiceapp.PurchaseActivity;
import com.example.invoiceapp.R;
import com.example.invoiceapp.adapters.OrderedProductsCustomdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.SelectedProducts;
import com.example.invoiceapp.utils.Constants;

public class OrdersFragment extends Fragment implements DbQueryCallback<Object> {

	private static final String TAG = null;
	private static OrdersFragment orderFragment;
	private static Customer customer = null;
	private ListView listview;
	private DatabaseQueryManager databaseQueryManager;
	private List<SelectedProducts> orderedProductsList;

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
		databaseQueryManager = DatabaseQueryManager.getInstance(getActivity());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem menuItem = menu.add(0, 1, 0, "NEXT");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	private void dismissKeyBoard(View v) {
		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			dismissKeyBoard(getView());
			purchaseItems();
		}
		return super.onOptionsItemSelected(item);
	}

	private void purchaseItems() {

		ArrayList<SelectedProducts> selectedProducts = new ArrayList<SelectedProducts>();
		for (SelectedProducts purchaseProducts : orderedProductsList) {
			if (purchaseProducts != null
					&& purchaseProducts.getQtyPurchased() != null && !purchaseProducts.getQtyPurchased().equalsIgnoreCase("null")) {

				selectedProducts.add(purchaseProducts);
				Log.v(TAG, "Name:" + purchaseProducts.getProductName()
						+ ":Qty:" + purchaseProducts.getQtyPurchased());
			}
		}

		if (selectedProducts != null && !selectedProducts.isEmpty()) {
			Intent intent = new Intent(getActivity(), PurchaseActivity.class);
			intent.putParcelableArrayListExtra(
					PurchaseActivity.EXTRA_PURCHASE_ITEMS, selectedProducts);
			startActivity(intent);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.layout_orders, null);
		listview = (ListView) v.findViewById(R.id.listview);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (customer != null) {
			databaseQueryManager.getOrderedProducts(
					Constants.DB_REQ_FETCH_ORDERED_PRODUCTS,
					customer.getmCustomerId(), this);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryCompleted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_ORDERED_PRODUCTS:
			if (object != null && object instanceof List) {
				orderedProductsList = ((List<SelectedProducts>) object);
				if (orderedProductsList != null
						&& !orderedProductsList.isEmpty()) {
					listview.setAdapter(new OrderedProductsCustomdapter(
							getActivity(), -1, orderedProductsList));
				}
			}
			break;

		default:
			break;
		}
	}

}
