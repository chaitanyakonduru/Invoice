package com.example.invoiceapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.Reminder;

public class ReminderCustomAdapter extends ArrayAdapter<Reminder> {

	private Context mContext;
	private List<Reminder> remindersList;

	public ReminderCustomAdapter(Context context, int resource,
			List<Reminder> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.remindersList = objects;
	}

	@Override
	public int getCount() {
		return remindersList.size();
	}

	@Override
	public Reminder getItem(int position) {
		return remindersList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_custom_reminder, null);
			holder.reminderNotes = (TextView) convertView
					.findViewById(R.id.reminder_notes);

			holder.reminderDateView = (TextView) convertView
					.findViewById(R.id.reminder_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Reminder reminder = getItem(position);

		holder.reminderDateView.setText(reminder.getmReminderTime());
		holder.reminderNotes.setText(reminder.getmReminderNotes());
		return convertView;
	}

	public class ViewHolder {
		TextView reminderNotes, reminderDateView;
	}

}
