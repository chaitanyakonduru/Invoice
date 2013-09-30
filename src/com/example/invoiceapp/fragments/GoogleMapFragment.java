package com.example.invoiceapp.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invoiceapp.InvoiceApplication;
import com.example.invoiceapp.R;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends Fragment implements
		OnMarkerClickListener, DbQueryCallback<Object> {

	private static final String TAG = GoogleMapFragment.class.getSimpleName();
	private MapView mMapView;
	private GoogleMap map;
	private List<Customer> customers;
	private DatabaseQueryManager databaseQueryManager;
	private InvoiceApplication invoiceApplication;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		databaseQueryManager = DatabaseQueryManager.getInstance(getActivity());
		invoiceApplication = (InvoiceApplication) getActivity()
				.getApplication();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_mapview, null);
		mMapView = (MapView) view.findViewById(R.id.map);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		databaseQueryManager = DatabaseQueryManager.getInstance(getActivity());

		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();

	}

	@Override
	public void onResume() {
		super.onResume();

		setUpMap();
	}

	private void setUpMap() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = mMapView.getMap();

			if (map != null) {
				map.clear();
				addMarkersToMap(map);
			}
		} else {
			map.clear();
			addMarkersToMap(map);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mMapView.onDestroy();

	}

	private void addMarkersToMap(GoogleMap map2) {
		Log.v(TAG, "IN ADD MARKERS TOP MAP");

		if (customers != null && !customers.isEmpty()) {
			plotCustomersonMap(customers);
		} else {
			databaseQueryManager.getCustomers(
					invoiceApplication.getmDriverId(),
					Constants.DB_REQ_FETCH_CUSTOMERS, this);
		}

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onQueryCompleted(int requestCode, Object object) {
		if (requestCode == Constants.DB_REQ_FETCH_CUSTOMERS) {
			if (object != null && object instanceof List) {
				customers = ((List<Customer>) object);
				plotCustomersonMap(customers);
			}
			else
			{
				Utilities.showToastMessage(getActivity(), "No Routes Found");
			}
		}
	}

	private void plotCustomersonMap(List<Customer> customers) {
		if (customers != null && !customers.isEmpty()) {
			for (Customer customer : customers) {
				LatLng latLng = new LatLng(Double.valueOf(customer
						.getmLatitude()), Double.valueOf(customer
						.getmLongitude()));
				map.addMarker(new MarkerOptions().position(latLng)
						.title(customer.getmCustomerName())
						.snippet(customer.getmCustomerCity())
						.icon(BitmapDescriptorFactory.defaultMarker()));

				map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

			}
		} else {
			Log.v(TAG, "Restauants Are Empty");
		}
	}

}
