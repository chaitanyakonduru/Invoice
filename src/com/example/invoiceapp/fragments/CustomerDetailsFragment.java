package com.example.invoiceapp.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.network.DirectionsJSONParser;
import com.example.invoiceapp.network.FetchCurrentLocation.LocationResult;
import com.example.invoiceapp.network.FetchCurrentLocation;
import com.example.invoiceapp.network.InvoiceAppNetworkServiceManager;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class CustomerDetailsFragment extends Fragment implements
		LocationResult, NetworkCallback<Object> {

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
	private InvoiceAppNetworkServiceManager manager;
	private List<List<HashMap<String, String>>> response;
	private GoogleMap mMap;
	private LatLng origin;
	private LatLng destination;
	private ProgressDialog progressDialog;

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
		helper = new PlotLocationMapHelper(getActivity(), savedInstanceState);
		manager = InvoiceAppNetworkServiceManager.getInstance(getActivity());
		FetchCurrentLocation currentLocation=new FetchCurrentLocation();
		progressDialog=ProgressDialog.show(getActivity(), "", "Fetching Current Location", false, true);
		currentLocation.fetchLocation(getActivity(), this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_customer_details, null);
		customerName = (TextView) v.findViewById(R.id.cust_details_cust_name);
		customerAddress = (TextView) v
				.findViewById(R.id.cust_details_cust_address);
		customerCityView = (TextView) v
				.findViewById(R.id.cust_details_cust_city);
		customerLandmark = (TextView) v
				.findViewById(R.id.cust_details_cust_landmark);
		customerPhoneView = (TextView) v
				.findViewById(R.id.cust_details_cust_phone);
		mapView = (MapView) v.findViewById(R.id.map1);
		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		mapView.onCreate(savedInstanceState);
		
		mapView.onResume();
		mMap = mapView.getMap();
		mMap.setMyLocationEnabled(true);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (customer != null) {
			customerName.setText(customer.getmCustomerName());
			customerAddress.setText(customer.getmCustomerAddress());
			customerCityView.setText(customer.getmCustomerCity());
			customerLandmark.setText(customer.getmLandmark());
			customerPhoneView.setText(customer.getmPhoneNo());
			// helper.plotLocation(mapView,customer);
		}
	}

	@Override
	public void gotLocation(Location location) {
		progressDialog.dismiss();
		if (location != null) {
			origin = new LatLng(location.getLatitude(),
					location.getLongitude());
			Location location2=mMap.getMyLocation();
			if(location2!=null)
			{
				origin = new LatLng(location2.getLatitude(),
						location2.getLongitude());
				Utilities.showToastMessage(getActivity(), "My Location Found");
			}
			if (customer != null) {
				destination = new LatLng(Double.parseDouble(customer
						.getmLatitude()), Double.parseDouble(customer
						.getmLongitude()));
				String url = Utilities.getDirectionsUrl(origin, destination,
						null);
				manager.getWayPoints(Constants.REQ_FETCH_WAYPOINTS, url, this);
			}
		}
	}

	@Override
	public void onSuccess(int requestCode, Object object) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case Constants.REQ_FETCH_WAYPOINTS:
			if (object != null && object instanceof String) {
				DirectionsJSONParser directionsJSONParser = new DirectionsJSONParser();
				response = directionsJSONParser.parse((String) object);
				ArrayList<LatLng> points = null;
				PolylineOptions lineOptions = null;

				// Traversing through all the routes
				for (int i = 0; i < response.size(); i++) {
					points = new ArrayList<LatLng>();
					lineOptions = new PolylineOptions();

					// Fetching i-th route
					List<HashMap<String, String>> path = response.get(i);

					// Fetching all the points in i-th route
					for (int j = 0; j < path.size(); j++) {
						HashMap<String, String> point = path.get(j);

						double lat = Double.parseDouble(point.get("lat"));
						double lng = Double.parseDouble(point.get("lng"));
						LatLng position = new LatLng(lat, lng);

						points.add(position);
					}

					// Adding all the points in the route to LineOptions
					lineOptions.addAll(points);
					lineOptions.width(5);
					lineOptions.color(Color.RED);
				}

				mMap.addPolyline(lineOptions);

				if (points != null && !points.isEmpty()) {

					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
origin, 12));
					mMap.addMarker(new MarkerOptions().position(origin)
							.title("My Location")
							.icon(BitmapDescriptorFactory.defaultMarker()));
					
					mMap.addMarker(new MarkerOptions().position(destination)
							.title(customer.getmCustomerName())
							.icon(BitmapDescriptorFactory.defaultMarker()));
				}

			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		// TODO Auto-generated method stub

	}

}
