package com.example.invoiceapp.utils;

import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ViewSwitcher;

import com.example.invoiceapp.R;

public class DateTimePicker extends RelativeLayout implements
		View.OnClickListener, OnDateChangedListener, OnTimeChangedListener {

	private DatePicker datePicker;
	private TimePicker timePicker;
	private ViewSwitcher viewSwitcher;
	private Calendar mCalendar;

	public DateTimePicker(Context context) {
		this(context, null);
	}

	public DateTimePicker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DateTimePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.datetimepicker, this, true);

		final LinearLayout datePickerView = (LinearLayout) inflater.inflate(
				R.layout.datepicker, null);
		final LinearLayout timePickerView = (LinearLayout) inflater.inflate(
				R.layout.timepicker, null);

		mCalendar = Calendar.getInstance();
		viewSwitcher = (ViewSwitcher) this.findViewById(R.id.DateTimePickerVS);

		datePicker = (DatePicker) datePickerView.findViewById(R.id.DatePicker);
		datePicker.init(mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH), this);
		datePicker.setCalendarViewShown(false);
		
		timePicker = (TimePicker) timePickerView.findViewById(R.id.TimePicker);
		timePicker.setIs24HourView(false);
		timePicker.setOnTimeChangedListener(this);

		((Button) findViewById(R.id.SwitchToTime)).setOnClickListener(this);

		((Button) findViewById(R.id.SwitchToDate)).setOnClickListener(this);

		viewSwitcher.addView(datePickerView, 0);
		viewSwitcher.addView(timePickerView, 1);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mCalendar.set(year, monthOfYear, dayOfMonth,
				mCalendar.get(Calendar.HOUR_OF_DAY),
				mCalendar.get(Calendar.MINUTE));
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		mCalendar.set(mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.SwitchToDate:
			v.setEnabled(false);
			findViewById(R.id.SwitchToTime).setEnabled(true);
			viewSwitcher.showPrevious();
			break;

		case R.id.SwitchToTime:
			v.setEnabled(false);
			findViewById(R.id.SwitchToDate).setEnabled(true);
			viewSwitcher.showNext();
			break;
		}
	}

	public int get(final int field) {
		return mCalendar.get(field);
	}

	public void reset() {
		final Calendar c = Calendar.getInstance();
		updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));
		updateTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}

	public long getDateTimeMillis() {
		return mCalendar.getTimeInMillis();
	}

	public void setIs24HourView(boolean is24HourView) {
		timePicker.setIs24HourView(is24HourView);
	}

	public boolean is24HourView() {
		return timePicker.is24HourView();
	}

	public void updateDate(int year, int monthOfYear, int dayOfMonth) {
		datePicker.updateDate(year, monthOfYear, dayOfMonth);
	}

	public void updateTime(int currentHour, int currentMinute) {
		timePicker.setCurrentHour(currentHour);
		timePicker.setCurrentMinute(currentMinute);
	}
}

