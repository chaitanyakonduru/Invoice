package com.example.invoiceapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.PurchasedProduct;

public class PurchasedProductsCustomdapter extends
		ArrayAdapter<PurchasedProduct> {

	private Context mContext;
	private List<PurchasedProduct> productsList;

	public PurchasedProductsCustomdapter(Context context, int resource,
			List<PurchasedProduct> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.productsList = objects;
	}

	@Override
	public int getCount() {
		return productsList.size();
	}

	@Override
	public PurchasedProduct getItem(int position) {
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

		final PurchasedProduct product = getItem(position);
		holder.breadItemNameTV.setText(product.getmProductName());
		holder.purchasedQtyTV.setText(product.getProductQuantity());
		holder.priceTV.setText(product.getProductCost());

		return convertView;
	}

	public class ViewHolder {
		TextView breadItemNameTV, purchasedQtyTV, priceTV;
	}

}
