package com.example.invoiceapp.fragments;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.invoiceapp.CustomerActivity;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.utils.Constants;

public class InvoiceFragment extends ListFragment implements
		DbQueryCallback<Object> {

	private static InvoiceFragment invoiceFragment;
	private DatabaseQueryManager databaseQueryManager;
	private CustomerActivity activity;

	public InvoiceFragment() {

	}

	public static InvoiceFragment newInstance() {

		invoiceFragment = new InvoiceFragment();
		return invoiceFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (CustomerActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		databaseQueryManager = DatabaseQueryManager.getInstance(getActivity());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		databaseQueryManager.getInvoices(Constants.DB_REQ_FETCH_INVOICES,
				this.activity.getCustomerId(), this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryCompleted(int requestCode, Object object) {

		switch (requestCode) {
		case Constants.DB_REQ_FETCH_INVOICES:
			if (object != null && object instanceof List) {
				List<String> invoiceList = ((List<String>) object);
				if (invoiceList != null && !invoiceList.isEmpty()) {
					setListAdapter(new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_list_item_1, invoiceList));
				}
			}

			break;

		default:
			break;
		}
	}

}
