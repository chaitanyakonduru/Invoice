package com.example.invoiceapp;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.network.DirectionsJSONParser;
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

public class DrawRouteActivity extends Activity implements
		NetworkCallback<Object>, DbQueryCallback<Object> {

	private GoogleMap mMap;
	public ProgressDialog progressDialog;
	private MapView mMapView;
	private InvoiceAppNetworkServiceManager invoiceAppNetworkServiceManager;
	private List<List<HashMap<String, String>>> result;
	private DatabaseQueryManager databaseQueryManager;
	private InvoiceApplication invoiceApplication;
	private List<Customer> customers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_view);
		databaseQueryManager = DatabaseQueryManager.getInstance(this);
		invoiceAppNetworkServiceManager = InvoiceAppNetworkServiceManager
				.getInstance(this);
		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		mMapView = (MapView) findViewById(R.id.map1);

		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();
		mMap = mMapView.getMap();
		invoiceApplication = (InvoiceApplication) getApplication();
		databaseQueryManager.getCustomers(invoiceApplication.getmDriverId(),
				Constants.DB_REQ_FETCH_CUSTOMERS, this);

	}

	

	@Override
	public void onSuccess(int requestCode, Object object) {

		switch (requestCode) {
		case 1000:
			if (object != null && object instanceof String) {
				DirectionsJSONParser directionsJSONParser = new DirectionsJSONParser();
				result = directionsJSONParser.parse((String) object);
				ArrayList<LatLng> points = null;
				PolylineOptions lineOptions = null;

				// Traversing through all the routes
				for (int i = 0; i < result.size(); i++) {
					points = new ArrayList<LatLng>();
					lineOptions = new PolylineOptions();

					// Fetching i-th route
					List<HashMap<String, String>> path = result.get(i);

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

				// Drawing polyline in the Google Map for the i-th route
				mMap.addPolyline(lineOptions);
				
				
				if(points!=null && !points.isEmpty())
				{
					
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 12));
				}

			}
		}

	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_CUSTOMERS:
			if (object != null && object instanceof List) {
				customers = ((List<Customer>) object);
				plotCustomersonMap(customers);
			} else {
				Utilities.showToastMessage(this, "No Routes Found");
			}
			break;

		default:
			break;
		}
	}

	private void plotCustomersonMap(List<Customer> customers) {

		if (customers.size() >= 2) {
			Customer first = customers.get(0);
			List<LatLng> wayPointsList = new ArrayList<LatLng>();
			LatLng origin = new LatLng(
					Double.parseDouble(first.getmLatitude()),
					Double.parseDouble(first.getmLongitude()));
			Customer customer = customers.get(customers.size() - 1);
			LatLng destination = new LatLng(Double.parseDouble(customer
					.getmLatitude()), Double.parseDouble(customer
					.getmLongitude()));
			for (int i = 1; i < customers.size() - 1; i++) {
				wayPointsList.add(new LatLng(Double.parseDouble(customers
						.get(i).getmLatitude()), Double.parseDouble(customers
						.get(i).getmLongitude())));
			}
			String url = Utilities.getDirectionsUrl(origin, destination, wayPointsList);
			
			for(Customer customer2:customers)
			{
				mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(customer2.getmLatitude()), Double.parseDouble(customer2.getmLongitude())))
						.title(customer2.getmCustomerName())
						.icon(BitmapDescriptorFactory.defaultMarker()));
			}
			invoiceAppNetworkServiceManager.getWayPoints(1000, url, this);
		} else {
			Utilities.showToastMessage(this, "Destination Not Found");
		}

	}

}
