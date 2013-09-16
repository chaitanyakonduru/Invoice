package com.example.invoiceapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OrdersFragment extends Fragment {

	private static OrdersFragment orderFragment;

	public OrdersFragment() {
		// TODO Auto-generated constructor stub
	}

	public static OrdersFragment newInstance() {

		orderFragment = new OrdersFragment();
		return orderFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());
		textView.setText("Orders");
		return textView;
	}

}
