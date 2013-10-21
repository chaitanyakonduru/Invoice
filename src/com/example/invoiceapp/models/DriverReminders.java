package com.example.invoiceapp.models;

import java.util.List;

public class DriverReminders {

	private String mDriverId;
	private List<Reminder> remindersList;
	public String getmDriverId() {
		return mDriverId;
	}
	public void setmDriverId(String mDriverId) {
		this.mDriverId = mDriverId;
	}
	public List<Reminder> getRemindersList() {
		return remindersList;
	}
	public void setRemindersList(List<Reminder> remindersList) {
		this.remindersList = remindersList;
	}
	
}
