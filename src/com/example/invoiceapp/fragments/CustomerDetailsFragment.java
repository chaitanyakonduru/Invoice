package com.example.invoiceapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.Customer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class CustomerDetailsFragment extends Fragment {

	private static CustomerDetailsFragment customerDetailsFragment;
	public static Customer customer = null;
	private TextView customerName;
	private TextView customerAddress;
	private TextView customerCityView;
	private TextView customerLandmark;
	private TextView customerPhoneView;
	private MapView mapView;
	private com.example.invoiceapp.fragments.PlotLocationMapHelper helper;
	private GoogleMap googleMap;

	public CustomerDetailsFragment() {
	}

	public static CustomerDetailsFragment newInstance(Customer customer) {
		CustomerDetailsFragment.customer = customer;
		customerDetailsFragment = new CustomerDetailsFragment();
		return customerDetailsFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new PlotLocationMapHelper(
				getActivity(), savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(
				R.layout.layout_customer_details, null);
		customerName = (TextView) v
				.findViewById(R.id.cust_details_cust_name);
		customerAddress = (TextView) v
				.findViewById(R.id.cust_details_cust_address);
		customerCityView = (TextView) v
				.findViewById(R.id.cust_details_cust_city);
		customerLandmark = (TextView) v
				.findViewById(R.id.cust_details_cust_landmark);
		customerPhoneView = (TextView) v
				.findViewById(R.id.cust_details_cust_phone);
		mapView=(MapView) v.findViewById(R.id.map1);
		mapView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(googleMap!=null && customer!=null)
				{
					Location location=googleMap.getMyLocation();
					  Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                              Uri.parse("http://maps.google.com/maps?saddr="
                                              + location.getLatitude() + ","
                                              + location.getLongitude() + "&daddr="
                                              + customer.getmLatitude()+","+customer.getmLongitude()));
					  intent.setClassName("com.google.android.apps.maps",
                              "com.google.android.maps.MapsActivity");
					  startActivity(intent);
				}
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(customer!=null)
		{
			customerName.setText(customer.getmCustomerName());
			customerAddress.setText(customer.getmCustomerAddress());
			customerCityView.setText(customer.getmCustomerCity());
			customerLandmark.setText(customer.getmLandmark());
			customerPhoneView.setText(customer.getmPhoneNo());
			helper.plotLocation(mapView,customer);
		}
	}
	

}
