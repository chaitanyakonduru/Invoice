package com.example.invoiceapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.invoiceapp.models.Reminder;

public class SyncBroadcast extends BroadcastReceiver {

	private static final String TAG = "SyncBroadcast";
	public static final String EXTRA_REMINDER="reminder";

	@Override
	public void onReceive(Context context, Intent arg1) {
			Bundle bundle=arg1.getExtras();
			Reminder reminder=(Reminder) bundle.get(EXTRA_REMINDER);
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			Log.v(TAG, "Message Received at "+sdf.format(d)+" ");
			showNotification(context, reminder.getmReminderNotes(),Integer.parseInt(reminder.getmReminderId()));
	}

	private void showNotification(Context context, String msg,int id) {

		CharSequence text = "Newtec sync";

		Notification notification = new Notification(android.R.drawable.ic_notification_overlay, text,
				System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(), 0);

		notification.setLatestEventInfo(context,msg,
				text, contentIntent);

		NotificationManager manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(id, notification);
	}

	
}
