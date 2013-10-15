package com.example.invoiceapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.Invoice;

public class InvoiceCustomAdapter extends
		ArrayAdapter<Invoice> {

	private Context mContext;
	private List<Invoice> productsList;

	public InvoiceCustomAdapter(Context context, int resource,
			List<Invoice> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.productsList = objects;
	}

	@Override
	public int getCount() {
		return productsList.size();
	}

	@Override
	public Invoice getItem(int position) {
		return productsList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.purchased_custom_list_item, null);
			holder.breadItemNameTV = (TextView) convertView
					.findViewById(R.id.ordered_product_name);

			holder.purchasedQtyTV = (TextView) convertView
					.findViewById(R.id.purchased_qty_tv);
			holder.priceTV = (TextView) convertView
					.findViewById(R.id.purchased_price_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Invoice product = getItem(position);
		
		holder.purchasedQtyTV.setText(product.isPaid()?"Yes":"No");
		holder.priceTV.setText(product.getTotalAmount());

		if(product.isPaid())
		{
			holder.breadItemNameTV.setText(product.getInvoiceId());
		}
		else
		{
			holder.breadItemNameTV.setText(String.valueOf(product.getId()));
		}
			return convertView;
	}

	public class ViewHolder {
		TextView breadItemNameTV, purchasedQtyTV, priceTV;
	}

}
