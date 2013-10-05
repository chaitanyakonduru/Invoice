package com.example.invoiceapp.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FinishActivityReceiver extends BroadcastReceiver {

	private static final String TAG = FinishActivityReceiver.class
			.getSimpleName();
	private Activity activity = null;

	public FinishActivityReceiver(Context context) {
		this.activity = (Activity) context;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		if (activity != null) {
			activity.finish();
		} else {
			Log.v(TAG, "Activity Is Nulllll");
		}

	}

}
