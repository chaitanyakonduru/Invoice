package com.example.invoiceapp.utils;

import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invoiceapp.R;
import com.google.android.gms.maps.model.LatLng;

public class Utilities {

	private static ProgressDialog progressDialog;
	public static void showToastMessage(Context context,String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean checkIfNull(String string)
	{
		return TextUtils.isEmpty(string);
	}
	
	public static boolean checkZero(String string)
	{
		try
		{
			int a=Integer.parseInt(string);
			if(a>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	

	public static void setActionBarTitle(Context context,String title) {
		Activity activity=(Activity) context;
		TextView textView=(TextView) activity.getActionBar().getCustomView().findViewById(R.id.actionbar_custom_title);
		textView.setText(title);
	}
	
	public static void showProgressDialog(Context context)
	{
		try
		{
		if(progressDialog==null)
		{
		progressDialog=ProgressDialog.show(context, "", "Please wait..",false,false);
		}
		progressDialog.show();
		}
		catch(Exception e)
		{
			
		}
	}
	public static void dismissProgressDialog()
	{
		if(progressDialog!=null && progressDialog.isShowing())
		{
			try
			{
			progressDialog.dismiss();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getDirectionsUrl(LatLng origin, LatLng destination,
			List<LatLng> wayPoints) {

		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;
		String str_dest = "destination=" + destination.latitude + ","
				+ destination.longitude;

		String sensor = "sensor=false";
		String waypoints = "";
		if (wayPoints != null && !wayPoints.isEmpty()) {
			waypoints = "waypoints=";
			for (LatLng latLng : wayPoints) {
				waypoints += latLng.latitude + "," + latLng.longitude
						+ URLEncoder.encode("|");
			}

		}
		String parameters = str_origin + "&" + str_dest + "&" + sensor + "&"
				+ waypoints;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	
}
