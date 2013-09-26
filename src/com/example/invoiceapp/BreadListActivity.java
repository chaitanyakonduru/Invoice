package com.example.invoiceapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.invoiceapp.adapters.CustomBreadItemAdapter;
import com.example.invoiceapp.models.Bread;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class BreadListActivity extends BaseActivity {

	private static final String TAG = BreadListActivity.class.getSimpleName();
	private List<Bread> breadList = null;
	private Bread bread = null;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listView=new ListView(this);
		setContentView(listView);
		// prepareActionBar();
		Utilities.setActionBarTitle(this,"Select Products");
		generateBreadList();
		if (!breadList.isEmpty()) {
			listView.setAdapter(new CustomBreadItemAdapter(this, -1,Constants.BREAD_LIST, breadList));
		}
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Next");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	private void generateBreadList() {
		breadList = new ArrayList<Bread>();
		for (int i = 0; i < 10; i++) {
			bread = new Bread();
			bread.setItemName("Bread" + i);
			breadList.add(bread);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			filterItems();
			break;
		case android.R.id.home:
			finish();
		default:
			break;
		}
		return true;
	}
	
	private void filterItems() {

		CustomBreadItemAdapter adapter = (CustomBreadItemAdapter) listView
				.getAdapter();
		List<Bread> filteredList=new ArrayList<Bread>();
		for (int i = 0; i < breadList.size(); i++) {
			Bread bread = breadList.get(i);
			View v = adapter.getView(i, null, null);
			
			EditText quantityEText = (EditText) v
					.findViewById(R.id.et_qunatity);
			if (!Utilities.checkIfNull(quantityEText.getText().toString()
					.trim())) {
				filteredList.add(bread);
				Log.v(TAG,
						"Bread Name:" + bread.getItemName() + "Qty:"
								+ bread.getQuantitiy());
				bread.setOrdered(true);
				
			}

		}
		if(!filteredList.isEmpty())
		{
			Intent intent=new Intent(this,PreviewBreadListActivity.class);
			intent.putParcelableArrayListExtra(PreviewBreadListActivity.EXTRA_BREAD_LIST, (ArrayList<Bread>)filteredList);
			startActivity(intent);
		}
		
		

	}

}
