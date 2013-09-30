package com.example.invoiceapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.invoiceapp.models.Customer;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlotLocationMapHelper {
	
	private static final String TAG = PlotLocationMapHelper.class.getSimpleName();
	private Context context;
	private Bundle savedInstanceState;
	
	public PlotLocationMapHelper(Context context,Bundle saveBundle) {
	this.context=context;	
	this.savedInstanceState=saveBundle;
	}
	
	public void plotLocation(MapView mapView,Customer customer)
	{
		try {
			MapsInitializer.initialize(context);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		
		mapView.onCreate(savedInstanceState);
		mapView.onResume();
		GoogleMap mMap=setUpMapIfNeeded(mapView);
		addMarkersToMap(mMap,customer);
	}
	
	private GoogleMap setUpMapIfNeeded(MapView mapView) {
		GoogleMap mMap=null;
		if (mMap == null) {
			mMap = mapView.getMap();
			if (mMap != null) {
				mMap.clear();
			} else {
				Log.v(TAG, "Map Is Null");
			}
		} 
		return mMap;
	}
	
	private void addMarkersToMap(GoogleMap mMap,Customer customer) {

		// mMap.addMarker(new MarkerOptions().position(position))
		LatLng latLng = new LatLng(Double.valueOf(customer.getmLatitude()), Double.valueOf(customer.getmLongitude()));
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		mMap.addMarker(new MarkerOptions()
				.position(latLng)
				.title(customer.getmCustomerName())
				.snippet(customer.getmCustomerCity())
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
	}

}
