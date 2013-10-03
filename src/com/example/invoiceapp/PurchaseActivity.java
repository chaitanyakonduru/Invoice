package com.example.invoiceapp;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.invoiceapp.adapters.PurchasedProductsCustomdapter;
import com.example.invoiceapp.fragments.OrdersFragment;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.PurchasedProduct;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.DatabaseThread.onDatabaseUpdateCompletion;
import com.example.invoiceapp.utils.Utilities;

public class PurchaseActivity extends BaseActivity implements
		onDatabaseUpdateCompletion {

	private ListView listView;
	private List<PurchasedProduct> purchasedProducts;
	private TextView totalView;
	private int totalPrice;
	public static final String EXTRA_PURCHASE_ITEMS = "purchaseitems";
	public DatabaseThread databaseThread;
	public InvoiceApplication application;
	private String customer_name;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_customes);
		application = (InvoiceApplication) getApplication();
		databaseThread = new DatabaseThread(this, this);
		listView = (ListView) findViewById(R.id.listview);
		totalView = (TextView) findViewById(R.id.total_price_view);
		;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey(EXTRA_PURCHASE_ITEMS)
				&& bundle.containsKey(OrdersFragment.CUSTOMER_NAME)) {
			purchasedProducts = ((List<PurchasedProduct>) bundle
					.get(EXTRA_PURCHASE_ITEMS));
			customer_name = bundle.getString(OrdersFragment.CUSTOMER_NAME);
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
		for (PurchasedProduct purchaseProducts : purchasedProducts) {
			totalPrice += Integer.parseInt(purchaseProducts.getProductCost());
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
		final Invoice invoice = new Invoice();
		Builder builder = new Builder(this);
		builder.setTitle("Total:" + totalPrice);
		View v = LayoutInflater.from(this).inflate(
				R.layout.layout_payment_dialog, null);
		builder.setView(v);
		RadioGroup paymentStatus = (RadioGroup) v
				.findViewById(R.id.payment_status_radio_group);
		final LinearLayout linearLayout = (LinearLayout) v
				.findViewById(R.id.payment_mode_linear_layout);
		RadioGroup paymentMode = (RadioGroup) v
				.findViewById(R.id.payment_mode_radio_group);
		final EditText duesEditView = (EditText) v
				.findViewById(R.id.due_payments);
		paymentStatus.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg0.getCheckedRadioButtonId()) {
				case R.id.payment_rb_yes:
					duesEditView.setText("");
					invoice.setPaid(true);
					linearLayout.setVisibility(View.VISIBLE);
					break;
				case R.id.payment_rb_no:
					invoice.setPaid(false);
					linearLayout.setVisibility(View.GONE);
					duesEditView.setText(String.valueOf(totalPrice));
				default:
					break;
				}
			}
		});
		paymentMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg0.getCheckedRadioButtonId()) {
				case R.id.payment_mode_rb_cheque:
					invoice.setPaymentMode("Cheque");
					break;
				case R.id.payment_mode_rb_cash:
					invoice.setPaymentMode("Cash");
				default:
					break;
				}
			}
		});

		builder.setPositiveButton("Save", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (!databaseThread.isAlive()) {
					databaseThread.start();
				}

				invoice.setInvoiceId(customer_name + "_" + new Date().getTime());
				invoice.setCustomerId(customer_name);
				invoice.setDues(duesEditView.getText().toString());
				invoice.setTotalAmount(String.valueOf(totalPrice));
				invoice.setPurchased_date(new Date().toString());
				databaseThread.addJob(invoice);
				for (PurchasedProduct purchasedProduct : purchasedProducts) {
					purchasedProduct.setInvoiceId(invoice.getInvoiceId());
					purchasedProduct.setInvoice_prodcutid(invoice
							.getInvoiceId());
					databaseThread.addJob(purchasedProduct);
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

	@Override
	public void databaseCompleted() {
		Message.obtain(handler, 100).sendToTarget();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 100) {
				Utilities.showToastMessage(PurchaseActivity.this,
						"Saved Successfully");
			}
		}

	};

}
