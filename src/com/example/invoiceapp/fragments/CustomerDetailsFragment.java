package com.example.invoiceapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomerDetailsFragment extends Fragment {
	
	
	private static CustomerDetailsFragment customerDetailsFragment;
	public CustomerDetailsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static CustomerDetailsFragment newInstance() {

		customerDetailsFragment = new CustomerDetailsFragment();
		return customerDetailsFragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView=new TextView(getActivity());
		textView.setText("Customer Details");
		return textView;
	}

}
