package com.example.invoiceapp.utils;

import com.example.invoiceapp.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class Utilities {

	private static ProgressDialog progressDialog;
	private static FinishActivityReceiver activityReceiver;
	public static void showToastMessage(Context context,String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean checkIfNull(String string)
	{
		return TextUtils.isEmpty(string);
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
			progressDialog.dismiss();
		}
	}

	
	
	public static void registerReceiver(Context context)
	{
		IntentFilter filter=new IntentFilter(Constants.CUSTOM_ACTION_INTENT);
		activityReceiver=new FinishActivityReceiver(context);
		context.registerReceiver(activityReceiver, filter);
	}

	public static void unregisterReceiver(Context context)
	{
		if(activityReceiver!=null)
		{
			context.unregisterReceiver(activityReceiver);
		}
	}
}
