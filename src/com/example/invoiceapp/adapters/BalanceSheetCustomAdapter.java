package com.example.invoiceapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.Product;

public class BalanceSheetCustomAdapter extends
		ArrayAdapter<Product> {

	private Context mContext;
	private List<Product> productsList;

	public BalanceSheetCustomAdapter(Context context, int resource,
			List<Product> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.productsList = objects;
	}

	@Override
	public int getCount() {
		return productsList.size();
	}

	@Override
	public Product getItem(int position) {
		return productsList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.balance_sheet_custom_list_item, null);
			holder.productName = (TextView) convertView
					.findViewById(R.id.product_name);

			holder.delivered_qty = (TextView) convertView
					.findViewById(R.id.product_delivered_qty);
			holder.pickup_qty = (TextView) convertView
					.findViewById(R.id.product_pickup_qty);
			holder.balance_qty = (TextView) convertView
					.findViewById(R.id.product_balance_qty);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Product product = getItem(position);
		
		holder.productName.setText(product.getProductName());
		holder.pickup_qty.setText(trimNullValue(product.getmQuantityPickup()));
		holder.delivered_qty.setText(trimNullValue(product.getmQtyDelivered()));
		holder.balance_qty.setText(trimNullValue(product.getmQtyStockInHand()));
			return convertView;
	}

	public class ViewHolder {
		TextView productName, pickup_qty, delivered_qty,balance_qty;
	}
	
	private String trimNullValue(String value)
	{
		if(value==null ||value.trim().equalsIgnoreCase("") || value.trim().equalsIgnoreCase("null"))
		{
		return "0";
		}
		else
		{
			return value;
		}
	}

}
