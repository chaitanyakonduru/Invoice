package com.example.invoiceapp;

import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.invoiceapp.adapters.PurchasedProductsCustomdapter;
import com.example.invoiceapp.models.SelectedProducts;
import com.example.invoiceapp.network.DatabaseThread;

public class PurchaseActivity extends BaseActivity {

	private ListView listView;
	private List<SelectedProducts> purchasedProducts;
	private TextView totalView;
	private int totalPrice;
	public static final String EXTRA_PURCHASE_ITEMS = "purchaseitems";
	public DatabaseThread databaseThread;
	public InvoiceApplication application;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_customes);
		application=(InvoiceApplication) getApplication();
		databaseThread=application.shareDatabaseThreadInstance();
		listView = (ListView) findViewById(R.id.listview);
		totalView = (TextView) findViewById(R.id.total_price_view);
		;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(EXTRA_PURCHASE_ITEMS)) {
			purchasedProducts = ((List<SelectedProducts>) bundle
					.get(EXTRA_PURCHASE_ITEMS));

			if (purchasedProducts != null && !purchasedProducts.isEmpty()) {
				listView.setVisibility(View.VISIBLE);
				findViewById(R.id.customer_list_progressbar).setVisibility(
						View.GONE);
				findViewById(R.id.purchased_orders_list_item_header_view)
						.setVisibility(View.VISIBLE);
				totalView.setVisibility(View.VISIBLE);
				setTotaltoView();
				listView.setAdapter(new PurchasedProductsCustomdapter(this, -1,
						purchasedProducts));
			}

		}

	}

	private void setTotaltoView() {

		totalPrice = 0;
		for (SelectedProducts purchaseProducts : purchasedProducts) {
			totalPrice += (Integer.parseInt(purchaseProducts.getProductPrice()) * Integer
					.parseInt(purchaseProducts.getQtyPurchased()));
		}
		totalView.setText("Total: " + totalPrice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add(0, 1, 0, "Pay");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			displayPaymentDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	private void displayPaymentDialog() {
		Builder builder = new Builder(this);
		builder.setTitle("Total:" + totalPrice);
		View v = LayoutInflater.from(this).inflate(
				R.layout.layout_payment_dialog, null);
		builder.setView(v);

		builder.setPositiveButton("Save", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(!databaseThread.isAlive())
				{
					databaseThread.start();
				}
				
			}
		});

		builder.setNegativeButton("Print n Save", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}

}
