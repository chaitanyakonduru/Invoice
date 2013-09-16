package com.example.invoiceapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.Bread;
import com.example.invoiceapp.utils.Constants;

public class CustomBreadItemAdapter extends ArrayAdapter<Bread> {

	private Context mContext;
	private List<Bread> breadList;
	private int isFrom;
	
	public CustomBreadItemAdapter(Context context, int resource,int from,
			List<Bread> objects) {
		super(context, resource, objects);
		this.mContext=context;
		this.breadList=objects;
		this.isFrom=from;
		}
	
	@Override
	public int getCount() {
		return breadList.size();
	}
	
	@Override
	public Bread getItem(int position) {
		return breadList.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null)
		{
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.list_item_bread, null);
			holder.breadItemNameTV=(TextView) convertView.findViewById(R.id.tv_bread_item_name);
			holder.quantityTV=(TextView) convertView.findViewById(R.id.tv_qunatity);
			holder.quantityET=(EditText)convertView.findViewById(R.id.et_qunatity);
			convertView.setTag(holder);
		}
		else
		{
		holder=(ViewHolder) convertView.getTag();
		}
		
		if(isFrom==Constants.BREAD_LIST)
		{
			holder.quantityET.setVisibility(View.VISIBLE);
			holder.quantityTV.setVisibility(View.GONE);
		}
		else if(isFrom==Constants.PREVIEW_BREAD_LIST)
		{
			holder.quantityET.setVisibility(View.GONE);
			holder.quantityTV.setVisibility(View.VISIBLE);
		}
		final Bread bread=getItem(position);
		holder.breadItemNameTV.setText(bread.getItemName());
		holder.quantityET.setText(bread.getQuantitiy());
		holder.quantityTV.setText(bread.getQuantitiy());
		holder.quantityET.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				
				if(!arg1)
				{
					EditText editText=(EditText) arg0;
					bread.setQuantitiy(editText.getText().toString());
				}
			}
		});
		return convertView;
	}
	
	public class ViewHolder
	{
		TextView breadItemNameTV,quantityTV;
		EditText quantityET;
	}

}
