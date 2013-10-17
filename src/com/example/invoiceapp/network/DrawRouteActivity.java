package com.example.invoiceapp.network;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.invoiceapp.R;
import com.example.invoiceapp.network.FetchCurrentLocation.LocationResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class DrawRouteActivity extends Activity implements LocationResult{
	private GoogleMap mMap;
	private LatLng fromPosition;
	private GMapV2Direction gMapV2Direction;
	public ProgressDialog progressDialog;
	private MapView mMapView;
	private LatLng toPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_custom_view);
		gMapV2Direction=new GMapV2Direction();
		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		mMapView=(MapView) findViewById(R.id.map1);

		mMapView.onCreate(savedInstanceState);
		mMapView.onResume();
		mMap=mMapView.getMap();
		FetchCurrentLocation currentLocation=new FetchCurrentLocation();
		currentLocation.fetchLocation(this, this);
		
	}
	@Override
	public void gotLocation(Location location) {
		Toast.makeText(this, "Location Found", Toast.LENGTH_SHORT).show();
		fromPosition=new LatLng(location.getLatitude(), location.getLongitude());
		toPosition=new LatLng(28.38, 77.12);
		new sampleTask().execute(GMapV2Direction.MODE_DRIVING);
	}
	
	class sampleTask extends AsyncTask<String, Void, Document> {

		@Override
		protected Document doInBackground(String... params) {
			Document doc = null;
			if (fromPosition != null && toPosition != null) {
				doc = gMapV2Direction.getDocument(fromPosition, toPosition, params[0]);
			}
			return doc;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			mMap.addMarker(new MarkerOptions()
					.position(fromPosition)
					.title("My Location")
					.icon(BitmapDescriptorFactory.defaultMarker()));
			mMap.addMarker(new MarkerOptions()
					.position(toPosition)
					.icon(BitmapDescriptorFactory
							.defaultMarker()));
			progressDialog = ProgressDialog.show(DrawRouteActivity.this,
					"", "Getting Directions..", false, true);

		}

		@Override
		protected void onPostExecute(Document result) {
			super.onPostExecute(result);
			if (result != null) {

				ArrayList<LatLng> directionPoint = gMapV2Direction.getDirection(result);
				PolylineOptions rectLine = new PolylineOptions().width(3)
						.color(Color.RED);

				for (int i = 0; i < directionPoint.size(); i++) {
					rectLine.add(directionPoint.get(i));
				}
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						fromPosition, 16));
				mMap.addPolyline(rectLine);
			} else {
				Toast.makeText(DrawRouteActivity.this, "No Route Found",
						Toast.LENGTH_SHORT).show();
			}
			if (progressDialog != null) {
				progressDialog.dismiss();
			}

		}
	}

}
