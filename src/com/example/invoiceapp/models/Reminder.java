package com.example.invoiceapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable {

	private String mReminderId;
	private String mReminderTime;
	private String mReminderNotes;
	private String mDriverId;
	private boolean mIsNotified;
	
	
	
	public boolean ismIsNotified() {
		return mIsNotified;
	}
	public void setmIsNotified(boolean mIsNotified) {
		this.mIsNotified = mIsNotified;
	}
	public String getmDriverId() {
		return mDriverId;
	}
	public void setmDriverId(String mDriverId) {
		this.mDriverId = mDriverId;
	}
	public String getmReminderId() {
		return mReminderId;
	}
	public void setmReminderId(String mReminderId) {
		this.mReminderId = mReminderId;
	}
	public String getmReminderTime() {
		return mReminderTime;
	}
	public void setmReminderTime(String mReminderTime) {
		this.mReminderTime = mReminderTime;
	}
	public String getmReminderNotes() {
		return mReminderNotes;
	}
	public void setmReminderNotes(String mReminderNotes) {
		this.mReminderNotes = mReminderNotes;
	}
	@Override
	public String toString() {
		return "Reminder [mReminderNotes=" + mReminderTime + "]";
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.mDriverId);
		dest.writeString(this.mReminderId);
		dest.writeString(this.mReminderNotes);
		dest.writeString(this.mReminderTime);
		dest.writeString(String.valueOf(this.mIsNotified));
	}
	
	public Reminder()
	{
		
	}
	
	public Reminder(Parcel source)
	{
		this.mDriverId=source.readString();
		this.mReminderId=source.readString();
		this.mReminderNotes=source.readString();
		this.mReminderTime=source.readString();
		this.mIsNotified=Boolean.valueOf(source.readString());
	}
	
	
	public static final Creator<Reminder> CREATOR=new Creator<Reminder>() {
		
		@Override
		public Reminder[] newArray(int size) {
			return new Reminder[size];
		}
		
		@Override
		public Reminder createFromParcel(Parcel source) {
			return new Reminder(source);
		}
	};
}
