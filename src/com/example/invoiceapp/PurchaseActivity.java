package com.example.invoiceapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.invoiceapp.database.DatabaseQueryManager;
import com.example.invoiceapp.database.DbQueryCallback;
import com.example.invoiceapp.fragments.OrdersFragment;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.models.PurchasedProduct;
import com.example.invoiceapp.network.DatabaseThread;
import com.example.invoiceapp.network.DatabaseThread.onDatabaseUpdateCompletion;
import com.example.invoiceapp.utils.Constants;
import com.example.invoiceapp.utils.Utilities;

public class PurchaseActivity extends BaseActivity implements
		onDatabaseUpdateCompletion, DbQueryCallback<Object> {

	private ListView listView;
	private List<PurchasedProduct> purchasedProducts;
	private TextView totalView;
	private int totalPrice;
	public static final String EXTRA_PURCHASE_ITEMS = "purchaseitems";
	public static final String EXTRA__IS_FROM_INVOICE_PAGE = "isfrom_invoice";
	public DatabaseThread databaseThread;
	public InvoiceApplication application;
	private String customer_name;
	private boolean isFromInvoice = false;
	private DatabaseQueryManager databaseQueryManager;
	private Invoice invoice;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_customes);
		databaseQueryManager = DatabaseQueryManager.getInstance(this);

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
			isFromInvoice = bundle.containsKey(EXTRA__IS_FROM_INVOICE_PAGE) ? bundle
					.getBoolean(EXTRA__IS_FROM_INVOICE_PAGE) : false;
			if (isFromInvoice) {
				databaseQueryManager = DatabaseQueryManager.getInstance(this);
				databaseQueryManager.getPurchaseItemsDetails(
						Constants.DB_REQ_FETCH_PURCHASED_DETAILS,
						purchasedProducts.get(0).getInvoiceId(), this);
				findViewById(R.id.purchased_details)
						.setVisibility(View.VISIBLE);
			}

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
		MenuItem menuItem = menu.add(0, 2, 0, "Pay");
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add(0, 1, 0, "HOME").setIcon(R.drawable.ic_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 2) {
			displayPaymentDialog();
		}
		else if(item.getItemId()==1)
		{
			startActivity(new Intent(this,HomeScreenActivity.class));
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void displayPaymentDialog() {

		if (invoice == null) {
			invoice = new Invoice();
			invoice.setInvoiceId(customer_name + "_" + new Date().getTime());
		} else {
			invoice.setInvoiceId(purchasedProducts.get(0).getInvoiceId());
		}
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
		invoice.setPaid(true);
		invoice.setPaymentMode("Cash");
		paymentStatus.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
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

				if (!databaseThread.isAlive()) {
					databaseThread.start();
				}
				if(!invoice.isPaid())
				{
					invoice.setPaymentMode("N/A");
				}
				invoice.setCustomerId(customer_name);
				invoice.setDues(duesEditView.getText().toString());
				invoice.setTotalAmount(String.valueOf(totalPrice));
				SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy, hh:mm aa",Locale.US);
				String date=dateFormat.format(new Date());
				invoice.setPurchased_date(date);
				databaseThread.addJob(invoice);
				for (PurchasedProduct purchasedProduct : purchasedProducts) {
					purchasedProduct.setInvoiceId(invoice.getInvoiceId());
					purchasedProduct.setInvoice_prodcutid(invoice
							.getInvoiceId());
					
					
					Product product=new Product();
					product.setProductId(purchasedProduct.getProduct_id());
					product.setProductName(purchasedProduct.getmProductName());
					
					product.setmQtyDelivered(updateDelivery(purchasedProduct.getQtyDelivered(), purchasedProduct.getProductQuantity()));
					product.setmQuantityPickup(purchasedProduct.getQtyPickedUp());
					product.setmQtyStockInHand(String.valueOf(Integer.parseInt(purchasedProduct.getQtyStockInHand())-Integer.parseInt(purchasedProduct.getProductQuantity())));
					databaseThread.addJob(product);
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
		Utilities.showToastMessage(PurchaseActivity.this, "Saved Successfully");
		Intent intent = new Intent(Constants.CUSTOM_ACTION_INTENT);
		sendBroadcast(intent);
		finish();
	}

	private String updateDelivery(String prev,String current)
	{
		if(prev==null || prev.equalsIgnoreCase("null") || prev.equalsIgnoreCase(""))
		{
			prev="0";
		}
		int pre=Integer.parseInt(prev);
		int curr=Integer.parseInt(current);
		return String.valueOf(pre+curr);
		
	}
	@Override
	public void onQueryExecuted(int requestCode, Object object) {

		switch (requestCode) {
		case Constants.DB_REQ_FETCH_PURCHASED_DETAILS:
			if (object != null && object instanceof Invoice) {
				invoice = (Invoice) object;
				bindInvoiceDetailsToViews(invoice);

			}

			break;

		default:
			break;
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.getItem(0);
		if (invoice != null && invoice.isPaid()) {
			menuItem.setVisible(false);
		} else {
			menuItem.setVisible(true);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	private void bindInvoiceDetailsToViews(Invoice invoice) {

		TextView textView = (TextView) findViewById(R.id.purchased_purchase_date);
		textView.setText(invoice.getPurchased_date());

		textView = (TextView) findViewById(R.id.purchased_payment_dues);
		textView.setText(invoice.getDues());

		textView = (TextView) findViewById(R.id.purchased_payment_mode);
		textView.setText(invoice.getPaymentMode());

		textView = (TextView) findViewById(R.id.purchased_payment_status);
		textView.setText(invoice.isPaid() ? "Yes" : "No");

		if (invoice.isPaid()) {
			invalidateOptionsMenu();
		}

	}

}
