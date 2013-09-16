package com.example.invoiceapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InvoiceFragment extends Fragment {
	
	
	private static InvoiceFragment invoiceFragment;


	public InvoiceFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static InvoiceFragment newInstance() {

		invoiceFragment = new InvoiceFragment();
		return invoiceFragment;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView=new TextView(getActivity());
		textView.setText("Invoice");
		return textView;
	}

}
