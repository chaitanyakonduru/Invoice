package com.example.invoiceapp.adapters;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.invoiceapp.R;
import com.example.invoiceapp.models.PurchaseProducts;

public class OrderedProductsCustomdapter extends ArrayAdapter<PurchaseProducts> {

	private Context mContext;
	private List<PurchaseProducts> productsList;

	public OrderedProductsCustomdapter(Context context, int resource,
			List<PurchaseProducts> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.productsList = objects;
	}

	@Override
	public int getCount() {
		return productsList.size();
	}

	@Override
	public PurchaseProducts getItem(int position) {
		return productsList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.purchase_orders_custom_adapter, null);
			holder.breadItemNameTV = (TextView) convertView
					.findViewById(R.id.ordered_product_name);
			holder.orderedquantityTV = (TextView) convertView
					.findViewById(R.id.ordered_qty);

			holder.purchasedQtyTV = (TextView) convertView
					.findViewById(R.id.purchased_qty_tv);
			holder.purchasedQtyTV.setVisibility(View.GONE);
			holder.returnQtyTV = (TextView) convertView
					.findViewById(R.id.returned_qty_tv);
			holder.returnQtyTV.setVisibility(View.GONE);
			holder.purchasedQtyTV.setVisibility(View.GONE);
			holder.qtyPurchasedET = (EditText) convertView
					.findViewById(R.id.purchased_qty_et);
			holder.returnItemQtyET = (EditText) convertView
					.findViewById(R.id.returned_qty_et);
			convertView.setPadding(12, 12, 12, 12);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final PurchaseProducts product = getItem(position);
		holder.breadItemNameTV.setText(product.getProductName());
		holder.orderedquantityTV.setText(product.getmOrderQty());
		holder.qtyPurchasedET.setVisibility(View.VISIBLE);
		holder.returnItemQtyET.setVisibility(View.VISIBLE);

		holder.qtyPurchasedET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				product.setQtyPurchased(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				product.setQtyPurchased(s.toString());
			}
		});

		return convertView;
	}

	public class ViewHolder {
		TextView breadItemNameTV, orderedquantityTV, purchasedQtyTV,
				returnQtyTV;
		EditText qtyPurchasedET, returnItemQtyET;
	}

}
