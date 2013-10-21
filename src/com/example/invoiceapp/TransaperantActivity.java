package com.example.invoiceapp;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

import com.example.invoiceapp.models.Reminder;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.utils.SyncBroadcast;

public class TransaperantActivity extends Activity {

	private static final String TAG = TransaperantActivity.class
			.getSimpleName();
	private DatabaseThread databaseThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		databaseThread=new DatabaseThread(this);
		if(!databaseThread.isAlive())
		{
			databaseThread.start();
		}
		Bundle bundle=getIntent().getExtras();
		Reminder reminder=(Reminder) bundle.get(SyncBroadcast.EXTRA_REMINDER);
		reminder.setmIsNotified(true);
		databaseThread.addJob(reminder);
		displayDialog(reminder);
	}

	private void displayDialog(Reminder reminder) {
		
		Builder builder=new Builder(this);
		builder.setMessage(reminder.getmReminderNotes());
		builder.setPositiveButton("Ok",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setTitle("Invoice App Reminder");
		builder.setCancelable(false);
	builder.create().show();	
	}



}
