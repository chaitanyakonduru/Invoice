package com.example.invoiceapp.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.invoiceapp.InvoiceApplication;
import com.example.invoiceapp.R;
import com.example.invoiceapp.TransaperantActivity;
import com.example.invoiceapp.adapters.ReminderCustomAdapter;
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.database.InvoiceAppDatabase;
import com.example.invoiceapp.database.InvoiceAppDatabase.RemindersColumns;
import com.example.invoiceapp.models.DriverReminder;
import com.example.invoiceapp.models.InvoiceReminders;
import com.example.invoiceapp.models.Reminder;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.DatabaseThread.onDatabaseUpdateCompletion;
import com.example.invoiceapp.network.InvoiceAppNetworkServiceManager;
import com.example.invoiceapp.network.JsonResponseParser;
import com.example.invoiceapp.network.NetworkCallback;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.DateTimePicker;
import com.example.invoiceapp.utils.SyncBroadcast;

public class RemindersFragment extends Fragment implements
		NetworkCallback<Object>, onDatabaseUpdateCompletion,
		DbQueryCallback<Object> {

	private static final String TAG = RemindersFragment.class.getSimpleName();
	private static RemindersFragment reminderFragment;
	private DatabaseThread databaseThread;
	private ListView remindersListView;
	private PendingIntent pendingIntent;
	private AlarmManager alarmManager;
	private DatabaseQueryManager databaseQueryManager;
	private InvoiceAppNetworkServiceManager appNetworkServiceManager;
	private TextView emptyView;

	public RemindersFragment() {
		// TODO Auto-generated constructor stub
	}

	public static RemindersFragment newInstance() {

		reminderFragment = new RemindersFragment();
		return reminderFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		databaseThread = new DatabaseThread(getActivity(), this);
		alarmManager = (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);
		databaseQueryManager = DatabaseQueryManager
				.getInstance(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		appNetworkServiceManager = InvoiceAppNetworkServiceManager
				.getInstance(getActivity());
		databaseQueryManager.checkTableNullOrNot(Constants.DB_REQ_CHECK_TABLE_NULL_NOT, RemindersColumns.TABLE_NAME, this);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_reminders, null);
		remindersListView = (ListView) view
				.findViewById(R.id.reminders_list_view);
		emptyView=(TextView)view.findViewById(R.id.emptyview);
		return view;
	}

	@Override
	public void onSuccess(int requestCode, Object object) {

		switch (requestCode) {
		case Constants.REQ_FETCH_REMINDERS:
			if (object != null && object instanceof String) {
				if (!databaseThread.isAlive()) {
					databaseThread.start();
				}
				InvoiceReminders invoiceReminders = JsonResponseParser
						.parseRemindersResponse((String) object);
				if (invoiceReminders != null) {

					List<DriverReminder> driverRemindersList = invoiceReminders
							.getDriverRemindersList();
					if (driverRemindersList != null
							&& !driverRemindersList.isEmpty()) {
						for (DriverReminder driverReminder : driverRemindersList) {
							String mDriverId = driverReminder.getmDriverId();
							List<Reminder> remindersList = driverReminder
									.getRemindersList();
							if (remindersList != null
									&& !remindersList.isEmpty()) {
								for (Reminder reminder : remindersList) {
									reminder.setmDriverId(mDriverId);
									databaseThread.addJob(reminder);
								}
							}
						}
					}
				}
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onFailure(int requestCode, String errorMessge) {

	}

	@Override
	public void databaseCompleted() {
		Log.v(TAG, "Reminders Insertion Completed");
		loadReminders();
	}

	private void loadReminders() {
		// TODO Auto-generated method stub
		databaseQueryManager.getAllReminders(Constants.DB_REQ_FETCH_REMINDERS,
				this);
	}

	@Override
	public void onQueryExecuted(int requestCode, Object object) {
		switch (requestCode) {
		case Constants.DB_REQ_FETCH_REMINDERS:
			if (object != null && object instanceof List) {
				@SuppressWarnings("unchecked")
				List<Reminder> remindersList = (List<Reminder>) object;
				if (remindersList != null && !remindersList.isEmpty()) {
					remindersListView.setVisibility(View.VISIBLE);
					emptyView.setVisibility(View.GONE);
					remindersListView.setAdapter(new ReminderCustomAdapter(
							getActivity(), -1, remindersList));
					
					for(Reminder  reminder1:remindersList)
					{
						setAlarm(reminder1);
					}
					
				}
				else
				{
					remindersListView.setVisibility(View.GONE);
					emptyView.setVisibility(View.VISIBLE);
				}
			}
			else
			{
				remindersListView.setVisibility(View.GONE);
				emptyView.setVisibility(View.VISIBLE);
			}
			break;
		case Constants.DB_REQ_CHECK_TABLE_NULL_NOT:
			if(object!=null && object instanceof Boolean)
			{
				boolean status=(Boolean) object;
				if(status)
				{
					loadReminders();
				}
				else
				{
					appNetworkServiceManager.fetchReminderRequest(
							Constants.REQ_FETCH_REMINDERS, this);
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, 5, 0, "Add ").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS).setIcon(R.drawable.ic_alarm);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 5:
				displayAddReminderDialog();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void displayAddReminderDialog() {
		Builder builder=new Builder(getActivity());
		final InvoiceAppDatabase appDatabase=InvoiceAppDatabase.getInstance(getActivity());
		
		View v=LayoutInflater.from(getActivity()).inflate(R.layout.reminders_dialog_custom_view, null);
		final DateTimePicker dateTimePicker=(DateTimePicker) v.findViewById(R.id.dateTimePicker1);
			dateTimePicker.setIs24HourView(false);
		final EditText descriptionView=(EditText)v.findViewById(R.id.description_view);
		builder.setView(v);
		builder.setTitle("Add Reminder");
		
		builder.setPositiveButton("Set", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Reminder reminder=new Reminder();
				InvoiceApplication application=(InvoiceApplication) getActivity().getApplication();
				
				Date d=new Date(dateTimePicker.getDateTimeMillis());
				SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy,HH:mm");
				String dateFormatString=dateFormat.format(d);
				reminder.setmDriverId(application.getmDriverId());
				reminder.setmReminderId(String.valueOf(appDatabase.getLastInsertRecordId(RemindersColumns.TABLE_NAME)+1));
				reminder.setmReminderTime(dateFormatString);
				reminder.setmReminderNotes(descriptionView.getText().toString());
				reminder.setmIsNotified(false);
				if(!databaseThread.isAlive())
				{
					databaseThread.start();
				}
				databaseThread.addJob(reminder);
			}
		});
		builder.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
		
	}

	private void setAlarm(Reminder reminder)
	{
		Intent intent = new Intent(getActivity(), TransaperantActivity.class);
		intent.putExtra(SyncBroadcast.EXTRA_REMINDER, reminder);
		Log.v(TAG, "Reminder Id"+reminder.getmReminderId());
		pendingIntent = PendingIntent.getActivity(getActivity(), Integer.parseInt(reminder.getmReminderId()), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,HH:mm");
		Date date;
		try {
			date = sdf.parse(reminder.getmReminderTime());
			calendar.setTime(date);
			String date1 = sdf.format(calendar.getTimeInMillis());
			Log.v(TAG,"Alarm Will Trigger at @:"+ date1);
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
