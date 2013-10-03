package com.example.invoiceapp.network;

import java.util.PriorityQueue;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.invoiceapp.InvoiceApplication;
import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.Order;
import com.example.invoiceapp.models.OrderProduct;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.models.PurchasedProduct;

public class DatabaseThread extends Thread {
	private InvoiceApplication app;

	private static final String TAG = DatabaseThread.class.getSimpleName();
	private PriorityQueue<Object> jobQueue1;

	private boolean pause;
	private onDatabaseUpdateCompletion databaseUpdateCompletion;

	public DatabaseThread(Context context) {
		Log.v(TAG, "tag");
		jobQueue1 = new PriorityQueue<Object>();
		app = (InvoiceApplication) context.getApplicationContext();
	}

	public DatabaseThread(Context context,
			onDatabaseUpdateCompletion databaseUpdateCompletion) {
		Log.v(TAG, "tag");
		jobQueue1 = new PriorityQueue<Object>();
		app = (InvoiceApplication) context.getApplicationContext();
		this.databaseUpdateCompletion = databaseUpdateCompletion;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			synchronized (this) {
				while (!isInterrupted()
						&& (this.jobQueue1.isEmpty() || this.pause)) {
					try {
						wait();
					} catch (InterruptedException e) {
						interrupt();
					}
				}
			}

			if (interrupted()) {
				Log.v(TAG, "interrupted");
				return;
			}

			Object object = null;

			try {
				object = jobQueue1.poll();
			} catch (Exception ex) {
				ex.printStackTrace();
				Log.v(TAG, "poll  " + ex.getMessage());
			}

			if (object != null) {
				if (object instanceof Driver) {
					Driver driver = (Driver) object;
					app.shareDatabaseInstance().insertDriver(driver);
				} else if (object instanceof Product) {
					Product product = (Product) object;
					app.shareDatabaseInstance().insertProduct(product);
				} else if (object instanceof Customer) {
					Customer customer = (Customer) object;
					app.shareDatabaseInstance().insertCustomer(customer);
				} else if (object instanceof Order) {
					Order order = (Order) object;
					app.shareDatabaseInstance().insertOrder(order);
				} else if (object instanceof OrderProduct) {
					OrderProduct orderProduct = (OrderProduct) object;
					app.shareDatabaseInstance()
							.insertOrderProduct(orderProduct);
				} else if (object instanceof Invoice) {
					Invoice invoice = (Invoice) object;
					app.shareDatabaseInstance().insertInvoice(invoice);
				} else if (object instanceof PurchasedProduct) {
					PurchasedProduct product = (PurchasedProduct) object;
					app.shareDatabaseInstance().insertPurchasedProduct(product);
				}

			}

			if (jobQueue1.isEmpty()) {
				if (databaseUpdateCompletion != null) {
					Message.obtain(handler, 100).sendToTarget();
				}
			}

			if (isInterrupted()) {
				Log.v(TAG, "MovingBack");
				break;
			}
		}

		Log.v(TAG, "InRunWait " + isInterrupted());
		if (this.jobQueue1 != null) {
			Log.v(TAG, "clear ");
			this.jobQueue1.clear();
			this.jobQueue1 = null;
		}
	}

	public synchronized void addJob(Object object) {
		if (jobQueue1 != null && object != null) {
			boolean isEmpty = jobQueue1.isEmpty();
			jobQueue1.offer(object);
			if (isEmpty) {
				Log.v(TAG, "empty");
				notify();
			}
		}
	}

	public void clearJobs() {
		if (jobQueue1 != null) {
			jobQueue1.clear();
		}
	}

	public synchronized void pause() {
		this.pause = true;
	}

	public synchronized void unPause() {
		this.pause = false;
		notify();
	}

	public static interface onDatabaseUpdateCompletion {
		public void databaseCompleted();

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 100) {
				databaseUpdateCompletion.databaseCompleted();
			}
		}

	};
}