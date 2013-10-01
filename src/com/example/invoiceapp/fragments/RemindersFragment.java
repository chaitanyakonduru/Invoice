package com.example.invoiceapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invoiceapp.R;

public class RemindersFragment extends Fragment {
	
	
	private static RemindersFragment reminderFragment;


	public RemindersFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static RemindersFragment newInstance() {

		reminderFragment= new RemindersFragment();
		return reminderFragment;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.layout_reminders, null);
		return view;
	}

}
