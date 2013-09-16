package com.example.invoiceapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		TextView textView=new TextView(getActivity());
		textView.setText("Reminders");
		return textView;
	}

}
