package com.example.invoiceapp.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.invoiceapp.CustomerActivity;
import com.example.invoiceapp.PurchaseActivity;
import com.example.invoiceapp.R;
import com.example.invoiceapp.adapters.InvoiceCustomAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.PurchasedProduct;
import com.example.invoiceapp.utils.Constants;

public class InvoiceFragment extends Fragment implements
		DbQueryCallback<Object>, OnItemClickListener {

	private static InvoiceFragment invoiceFragment;
	private DatabaseQueryManager databaseQueryManager;
	private CustomerActivity activity;
	private ListView listView;
	private TextView emptyView;
	private ProgressBar progressBar;
	private Future<Object> future;
	private List<Invoice> invoiceList;
	private static Customer customer;
	public InvoiceFragment() {

	}

	public static InvoiceFragment newInstance(Customer customer) {

		InvoiceFragment.customer=customer;;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_invoices, null);
		listView = (ListView) view.findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		emptyView = (TextView) view.findViewById(R.id.empty_view);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		return view;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		emptyView.setVisibility(View.GONE);
		future=databaseQueryManager.getInvoices(Constants.DB_REQ_FETCH_INVOICES,
				this.activity.getCustomerId(), this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryExecuted(int requestCode, Object object) {

		switch (requestCode) {
		case Constants.DB_REQ_FETCH_INVOICES:
			if (object != null && object instanceof List && !future.isCancelled()) {
				listView.setVisibility(View.VISIBLE);
				emptyView.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
				invoiceList = ((List<Invoice>) object);
				if (invoiceList != null && !invoiceList.isEmpty()) {
					listView.setAdapter(new InvoiceCustomAdapter(getActivity(),
							-1, invoiceList));
				}
			} else {
				emptyView.setText("No Invoices");
				emptyView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				listView.setVisibility(View.GONE);
			}
			break;
		case Constants.DB_REQ_FETCH_INVOICES_DETAILS:
			if(object!=null && object instanceof List)
			{
				List<PurchasedProduct> purchasedProducts=(List<PurchasedProduct>) object;
				Intent intent=new Intent(getActivity(),PurchaseActivity.class);
				intent.putParcelableArrayListExtra(PurchaseActivity.EXTRA_PURCHASE_ITEMS, (ArrayList)purchasedProducts);
				intent.putExtra(OrdersFragment.CUSTOMER_NAME, customer.getmCustomerId());
				intent.putExtra(PurchaseActivity.EXTRA__IS_FROM_INVOICE_PAGE, true);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		databaseQueryManager.getInvoiceDetails(Constants.DB_REQ_FETCH_INVOICES_DETAILS, invoiceList.get(arg2).getInvoiceId(), this);
	}

}
