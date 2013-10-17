package com.example.invoiceapp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.models.Invoice;
import com.example.invoiceapp.models.Order;
import com.example.invoiceapp.models.OrderProduct;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.models.PurchasedProduct;
import com.example.invoiceapp.models.SelectedProducts;
import com.example.invoiceapp.models.RouteInfo;
import com.example.invoiceapp.utils.Constants;

public class InvoiceAppDatabase {

	private static InvoiceAppDatabase invoiceAppDatabase;
	private static InvoiceDatabaseHelper databaseHelper;
	private static final String DATABASE_NAME = "invoice.db";
	private static final int DATABASE_VERSION = 2;
	private static final String TAG = InvoiceAppDatabase.class.getSimpleName();

	public InvoiceAppDatabase(Context context) {
		databaseHelper = new InvoiceDatabaseHelper(context, DATABASE_NAME,
				null, DATABASE_VERSION);
	}

	public static InvoiceAppDatabase getInstance(Context context) {
		if (invoiceAppDatabase == null) {
			invoiceAppDatabase = new InvoiceAppDatabase(context);
		}
		return invoiceAppDatabase;
	}

	private static class InvoiceDatabaseHelper extends SQLiteOpenHelper {

		public InvoiceDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CustomerColumns.DATABASE_CREATE);
			db.execSQL(OrderColumns.DATABASE_CREATE);
			db.execSQL(ProductColumns.DATABASE_CREATE);
			db.execSQL(RouteColumns.DATABASE_CREATE);
			db.execSQL(DriverColumns.DATABASE_CREATE);
			db.execSQL(OrderProductColumns.DATABASE_CREATE);
			db.execSQL(InvoiceColumns.DATABASE_CREATE);
			db.execSQL(PurchasedProductColumns.DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("Drop table if exists " + CustomerColumns.TABLE_NAME);
			db.execSQL("Drop table if exists " + OrderColumns.TABLE_NAME);
			db.execSQL("Drop table if exists " + ProductColumns.TABLE_NAME);
			db.execSQL("Drop table if exists " + RouteColumns.TABLE_NAME);
			db.execSQL("Drop table if exists " + DriverColumns.TABLE_NAME);
			db.execSQL("Drop table if exists " + OrderProductColumns.TABLE_NAME);
			db.execSQL("Drop table if exists " + InvoiceColumns.TABLE_NAME);
			db.execSQL("Drop table if exists "
					+ PurchasedProductColumns.TABLE_NAME);
		}

	}

	private static class CustomerColumns implements BaseColumns {
		public static final String TABLE_NAME = "Customers";
		public static final String CUSTOMER_ID = "customer_id";
		public static final String DRIVER_ID = "driver_id";
		public static final String CUSTOMER_NAME = "customer_name";
		public static final String CUSTOMER_ADDRESS = "customer_address";
		public static final String CUSTOMER_LANDMARK = "customer_landmark";
		public static final String CUSTOMER_LATITUDE = "customer_latitude";
		public static final String CUSTOMER_LONGITUDE = "customer_longitude";
		public static final String CUSTOMER_PHONE = "customer_phone";
		public static final String CUSTOMER_CITY = "customer_city";

		private static final String DATABASE_CREATE = "create table Customers(_id integer primary key autoincrement,"
				+ "customer_id integer,"
				+ "driver_id integer,"
				+ "customer_name text,customer_address text,"
				+ "customer_landmark text,customer_latitude text,"
				+ "customer_longitude text ,"
				+ "customer_phone text,customer_city text);";
	}

	public static class OrderColumns implements BaseColumns {
		public static final String TABLE_NAME = "Orders";
		public static final String CUSTOMER_ID = "customer_id";
		public static final String ORDER_ID = "order_id";
		public static final String ORDER_DATE = "order_date";
		public static final String ORDER_TOTAL_AMOUNT = "order_amount";

		private static final String DATABASE_CREATE = "create table Orders(_id integer primary key autoincrement,"
				+ "order_id integer,"
				+ "customer_id integer,"
				+ "order_date text,order_amount text);";
	}

	public static class ProductColumns implements BaseColumns {
		public static final String TABLE_NAME = "Products";
		public static final String PRODUCT_ID = "product_id";
		public static final String PRODUCT_NAME = "product_name";
		public static final String QTY_PICKUP = "qty_pickup";
		public static final String PRODUCT_PRICE = "product_price";
		public static final String QTY_DELIVERED = "qty_delivered";
		public static final String QTY_RETURNED="qty_returned";
		public static final String QTY_STOCK_IN_HAND="qty_stock_in_hand";

		private static final String DATABASE_CREATE = "create table Products(_id integer primary key autoincrement,"
				+ "product_id integer,"
				+ "product_price text,"
				+ "product_name text," +
				 "qty_pickup text," +
				 "qty_stock_in_hand text," +
				 "qty_returned text," +
				"qty_delivered text);";
	}

	public static class DriverColumns implements BaseColumns {
		public static final String TABLE_NAME = "Drivers";
		public static final String DRIVER_ID = "driver_id";
		public static final String DRIVER_NAME = "driver_name";
		public static final String DRIVER_CODE = "driver_code";
		public static final String DRIVER_PASSWORD = "driver_password";

		private static final String DATABASE_CREATE = "create table Drivers(_id integer primary key autoincrement,"
				+ "driver_id integer,"
				+ "driver_name text,"
				+ "driver_password text," + "driver_code text);";
	}

	public static class InvoiceColumns implements BaseColumns {
		public static final String TABLE_NAME = "Invoices";
		public static final String invoice_id = "invoice_id";
		public static final String purchased_date = "purchased_date";
		public static final String customer_id = "customer_id";
		public static final String paid = "paid";
		public static final String payment_mode = "payment_mode";
		public static final String total_amt = "totalAmount";
		public static final String dues = "dues";

		private static final String DATABASE_CREATE = "create table Invoices(_id integer primary key autoincrement,"
				+ "invoice_id integer,"
				+ "purchased_date text,"
				+ "customer_id text,"
				+ "paid int,"
				+ "payment_mode text,"
				+ "totalAmount int," + "dues int);";
	}

	private static class PurchasedProductColumns implements BaseColumns {
		public static final String TABLE_NAME = "PurchasedProducts";
		public static final String PURCHASE_PRODUCT_ID = "purchase_productid";
		public static final String INVOICE_ID = "invoice_id";
		public static final String PRODUCT_ID = "product_id";
		public static final String QUANTITY_PURCHASED = "quantity_purchased";
		public static final String PRODUCT_COST = "product_cost";

		private static final String DATABASE_CREATE = "create table PurchasedProducts(_id integer primary key autoincrement,"
				+ "purchase_productid integer,"
				+ "invoice_id integer,"
				+ "product_id text,"
				+ "quantity_purchased text,"
				+ "product_cost text);";
	}

	private static class RouteColumns implements BaseColumns {
		public static final String TABLE_NAME = "Routes";
		public static final String ROUTE_ID = "route_id";
		public static final String ROUTE_NAME = "route_name";
		public static final String ROUTE_CODE = "route_code";

		private static final String DATABASE_CREATE = "create table Routes(_id integer primary key autoincrement,"
				+ "route_id integer,"
				+ "route_name text,"
				+ "route_code text);";
	}

	private static class OrderProductColumns implements BaseColumns {
		public static final String TABLE_NAME = "OrderProducts";
		public static final String ORDER_PRODUCT_ID = "order_product_id";
		public static final String ORDER_ID = "order_id";
		public static final String PRODUCT_ID = "product_id";
		public static final String QUANTITY_ORDERED = "quantity_ordered";
		public static final String PRODUCT_PRICE = "price";

		private static final String DATABASE_CREATE = "create table OrderProducts(_id integer primary key autoincrement,"
				+ "order_product_id integer,"
				+ "order_id integer,"
				+ "product_id text,"
				+ "quantity_ordered text,"
				+ "price text);";
	}

	public boolean insertDriver(Driver driver) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Drivers WHERE driver_id = ?",
				new String[] { driver.getmDriverId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(DriverColumns.DRIVER_ID, driver.getmDriverId());
		contentValues.put(DriverColumns.DRIVER_NAME, driver.getmDriverName());
		contentValues.put(DriverColumns.DRIVER_PASSWORD,
				driver.getmDriverPassword());
		contentValues.put(DriverColumns.DRIVER_CODE, driver.getmDriverCode());

		if (isUpdate) {
			return sqLiteDatabase.update(DriverColumns.TABLE_NAME,
					contentValues, DriverColumns.DRIVER_ID + "=?",
					new String[] { driver.getmDriverId() }) > 0 ? true : false;
		} else {
			return sqLiteDatabase.insert(DriverColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public boolean insertRoutes(RouteInfo routeInfo) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Routes WHERE route_id = ?",
				new String[] { routeInfo.getmRouteId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(RouteColumns.ROUTE_ID, routeInfo.getmRouteId());
		contentValues.put(RouteColumns.ROUTE_NAME, routeInfo.getmRouteName());
		contentValues.put(RouteColumns.ROUTE_CODE, routeInfo.getmRouteCode());

		if (isUpdate) {
			return sqLiteDatabase.update(RouteColumns.TABLE_NAME,
					contentValues, RouteColumns.ROUTE_ID + "=?",
					new String[] { routeInfo.getmRouteId() }) > 0 ? true
					: false;
		} else {
			return sqLiteDatabase.insert(RouteColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public boolean insertCustomer(Customer customer) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Customers WHERE customer_id = ?",
				new String[] { customer.getmCustomerId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(CustomerColumns.CUSTOMER_ID,
				customer.getmCustomerId());
		contentValues.put(CustomerColumns.DRIVER_ID, customer.getmDriverId());
		contentValues.put(CustomerColumns.CUSTOMER_NAME,
				customer.getmCustomerName());
		contentValues.put(CustomerColumns.CUSTOMER_ADDRESS,
				customer.getmCustomerAddress());
		contentValues.put(CustomerColumns.CUSTOMER_CITY,
				customer.getmCustomerCity());
		contentValues.put(CustomerColumns.CUSTOMER_LANDMARK,
				customer.getmLandmark());
		contentValues.put(CustomerColumns.CUSTOMER_LATITUDE,
				customer.getmLatitude());
		contentValues.put(CustomerColumns.CUSTOMER_LONGITUDE,
				customer.getmLongitude());
		contentValues.put(CustomerColumns.CUSTOMER_PHONE,
				customer.getmPhoneNo());

		if (isUpdate) {
			return sqLiteDatabase.update(CustomerColumns.TABLE_NAME,
					contentValues, CustomerColumns.CUSTOMER_ID + "=?",
					new String[] { customer.getmCustomerId() }) > 0 ? true
					: false;
		} else {
			return sqLiteDatabase.insert(CustomerColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public boolean insertOrder(Order order) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Orders WHERE customer_id = ?",
				new String[] { order.getmOrderId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(OrderColumns.ORDER_ID, order.getmOrderId());
		contentValues.put(OrderColumns.CUSTOMER_ID, order.getmCustomerId());
		contentValues.put(OrderColumns.ORDER_DATE, order.getmOrderDate());
		contentValues.put(OrderColumns.ORDER_TOTAL_AMOUNT,
				order.getmOrderTotalAmt());

		if (isUpdate) {
			return sqLiteDatabase.update(OrderColumns.TABLE_NAME,
					contentValues, OrderColumns.ORDER_ID + "=?",
					new String[] { order.getmOrderId() }) > 0 ? true : false;
		} else {
			return sqLiteDatabase.insert(OrderColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public boolean insertProduct(Product product) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Products WHERE product_id = ?",
				new String[] { product.getProductId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(ProductColumns.PRODUCT_ID, product.getProductId());
		contentValues
				.put(ProductColumns.PRODUCT_NAME, product.getProductName());
		contentValues.put(ProductColumns.PRODUCT_PRICE, product.getmPrice());
		contentValues.put(ProductColumns.QTY_PICKUP,
				product.getmQuantityPickup());
		contentValues.put(ProductColumns.QTY_DELIVERED,
				product.getmQtyDelivered());
		contentValues.put(ProductColumns.QTY_RETURNED,
				product.getmQtyReturned());
		contentValues.put(ProductColumns.QTY_STOCK_IN_HAND,
				product.getmQtyStockInHand());

		if (isUpdate) {
			return sqLiteDatabase.update(ProductColumns.TABLE_NAME,
					contentValues, ProductColumns.PRODUCT_ID + "=?",
					new String[] { product.getProductId() }) > 0 ? true : false;
		} else {
			return sqLiteDatabase.insert(ProductColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public boolean insertInvoice(Invoice invoice) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"SELECT count(*) FROM Invoices WHERE invoice_id = ?",
				new String[] { invoice.getInvoiceId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(InvoiceColumns.invoice_id, invoice.getInvoiceId());
		contentValues.put(InvoiceColumns.dues, invoice.getDues());
		contentValues.put(InvoiceColumns.paid, invoice.isPaid() ? 1 : 0);
		contentValues
				.put(InvoiceColumns.payment_mode, invoice.getPaymentMode());
		contentValues.put(InvoiceColumns.purchased_date,
				invoice.getPurchased_date());
		contentValues.put(InvoiceColumns.total_amt, invoice.getTotalAmount());
		contentValues.put(InvoiceColumns.customer_id, invoice.getCustomerId());

		if (isUpdate) {
			return sqLiteDatabase.update(InvoiceColumns.TABLE_NAME,
					contentValues, InvoiceColumns.invoice_id + "=?",
					new String[] { invoice.getInvoiceId() }) > 0 ? true : false;
		} else {
			return sqLiteDatabase.insert(InvoiceColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public boolean insertPurchasedProduct(PurchasedProduct purchasedProduct) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM PurchasedProducts WHERE purchase_productid = ?",
						new String[] { purchasedProduct.getInvoice_prodcutid() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				// isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(PurchasedProductColumns.PURCHASE_PRODUCT_ID,
				purchasedProduct.getInvoice_prodcutid());
		contentValues.put(PurchasedProductColumns.INVOICE_ID,
				purchasedProduct.getInvoiceId());
		contentValues.put(PurchasedProductColumns.PRODUCT_COST,
				purchasedProduct.getProductCost());
		contentValues.put(PurchasedProductColumns.PRODUCT_ID,
				purchasedProduct.getProduct_id());
		contentValues.put(PurchasedProductColumns.QUANTITY_PURCHASED,
				purchasedProduct.getProductQuantity());
		if (isUpdate) {
			return sqLiteDatabase.update(PurchasedProductColumns.TABLE_NAME,
					contentValues, PurchasedProductColumns.PURCHASE_PRODUCT_ID
							+ "=?",
					new String[] { purchasedProduct.getInvoice_prodcutid() }) > 0 ? true
					: false;
		} else {
			return sqLiteDatabase.insert(PurchasedProductColumns.TABLE_NAME,
					null, contentValues) > 0 ? true : false;
		}

	}

	public boolean insertOrderProduct(OrderProduct orderProduct) {
		boolean isUpdate = false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM OrderProducts WHERE order_product_id = ?",
						new String[] { orderProduct.getmOrderProductId() });

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put(OrderProductColumns.ORDER_PRODUCT_ID,
				orderProduct.getmOrderProductId());
		contentValues.put(OrderProductColumns.ORDER_ID,
				orderProduct.getmOrderId());
		contentValues.put(OrderProductColumns.PRODUCT_ID,
				orderProduct.getmProductId());
		contentValues.put(OrderProductColumns.QUANTITY_ORDERED,
				orderProduct.getmOrderQty());
		contentValues.put(OrderProductColumns.PRODUCT_PRICE,
				orderProduct.getmProductPrice());

		if (isUpdate) {
			return sqLiteDatabase.update(OrderProductColumns.TABLE_NAME,
					contentValues, OrderProductColumns.ORDER_PRODUCT_ID + "=?",
					new String[] { orderProduct.getmOrderProductId() }) > 0 ? true
					: false;
		} else {
			return sqLiteDatabase.insert(OrderProductColumns.TABLE_NAME, null,
					contentValues) > 0 ? true : false;
		}

	}

	public long getLastInsertRecordId(String tableName) {
		long index = 0;
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Cursor cursor = db.query("sqlite_sequence", new String[] { "seq" },
				"name = ?", new String[] { tableName }, null, null, null, null);
		if (cursor.moveToFirst()) {
			index = cursor.getLong(cursor.getColumnIndex("seq"));
		}
		cursor.close();
		return index;
	}

	public void checkTableNullOrNot(String tableName, DatabaseHandler handler) {
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		boolean resultStatus = false;
		Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM "
				+ tableName, null);

		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				resultStatus = true;
			}
			cursor.close();
			cursor = null;
		}
		Message.obtain(handler, Constants.SUCCESS, resultStatus).sendToTarget();
	}

	public void getAllDrivers(DatabaseHandler handler) {
		List<Driver> driverList = null;
		Driver driver = null;
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = database.query(DriverColumns.TABLE_NAME, null, null,
				null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			driverList = new ArrayList<Driver>();
			while (cursor.moveToNext()) {
				driver = new Driver();
				driver.setmDriverId(cursor.getString(cursor
						.getColumnIndex(DriverColumns.DRIVER_ID)));
				driver.setmDriverName(cursor.getString(cursor
						.getColumnIndex(DriverColumns.DRIVER_NAME)));
				driver.setmDriverPassword(cursor.getString(cursor
						.getColumnIndex(DriverColumns.DRIVER_PASSWORD)));
				driverList.add(driver);
			}
		}
		Message.obtain(handler, Constants.SUCCESS, driverList).sendToTarget();
	}

	public void getAllProducts(DatabaseHandler databaseHandler) {
		List<Product> productsList = null;
		Product product = null;
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = database.query(ProductColumns.TABLE_NAME, null, null,
				null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			productsList = new ArrayList<Product>();
			while (cursor.moveToNext()) {
				product = new Product();
				product.setProductId(cursor.getString(cursor
						.getColumnIndex(ProductColumns.PRODUCT_ID)));
				product.setProductName(cursor.getString(cursor
						.getColumnIndex(ProductColumns.PRODUCT_NAME)));
				product.setmQtyDelivered(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_DELIVERED)));
				product.setmQtyReturned(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_RETURNED)));
				product.setmQtyStockInHand(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_STOCK_IN_HAND)));
				product.setmQuantityPickup(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_PICKUP)));
				productsList.add(product);
			}
		}
		Message.obtain(databaseHandler, Constants.SUCCESS, productsList)
				.sendToTarget();
	}

	public void getAllCustomers(String driverId, DatabaseHandler handler) {
		List<Customer> customersList = null;
		Customer customer = null;
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = database.query(CustomerColumns.TABLE_NAME, null,
				CustomerColumns.DRIVER_ID + "=?", new String[] { driverId },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			customersList = new ArrayList<Customer>();
			while (cursor.moveToNext()) {
				customer = new Customer();
				customer.setmCustomerId(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_ID)));
				customer.setmCustomerName(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_NAME)));
				customer.setmCustomerAddress(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_ADDRESS)));
				customer.setmCustomerCity(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_CITY)));
				customer.setmDriverId(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.DRIVER_ID)));
				customer.setmLandmark(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_LANDMARK)));
				customer.setmLatitude(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_LATITUDE)));
				customer.setmLongitude(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_LONGITUDE)));
				customer.setmPhoneNo(cursor.getString(cursor
						.getColumnIndex(CustomerColumns.CUSTOMER_PHONE)));
				customersList.add(customer);
			}
		}
		Message.obtain(handler, Constants.SUCCESS, customersList)
				.sendToTarget();
	}

	public void getOrderedProducts(String customerId,
			DatabaseHandler databaseHandler) {
		List<SelectedProducts> purchaseProductsList = null;
		SelectedProducts purchaseProducts;
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		String query = null;
		query = "select p.product_id,p.qty_delivered,p.qty_stock_in_hand,p.qty_pickup,p.product_name,o.price,o.quantity_ordered from Products p Join OrderProducts o on p.product_id=o.product_id where o.order_id=(select Orders.order_id from Orders where Orders.customer_id="
				+ customerId + ") group by o.order_product_id";
		// query="select p.product_id,p.product_name,o.price,o.quantity_ordered from Products p LEFT OUTER JOIN  OrderProducts o on p.product_id=o.order_product_id where o.order_id=(select Orders.order_id from Orders where Orders.customer_id="+customerId+") group by o.order_product_id";
		Cursor cursor = database.rawQuery(query, null);
		if (cursor != null && cursor.getCount() > 0) {
			purchaseProductsList = new ArrayList<SelectedProducts>();
			while (cursor.moveToNext()) {
				purchaseProducts = new SelectedProducts();
				purchaseProducts.setmOrderQty(cursor.getString(cursor
						.getColumnIndex(OrderProductColumns.QUANTITY_ORDERED)));
				purchaseProducts.setProductId(cursor.getString(cursor
						.getColumnIndex(ProductColumns.PRODUCT_ID)));
				purchaseProducts.setProductName(cursor.getString(cursor
						.getColumnIndex(ProductColumns.PRODUCT_NAME)));
				purchaseProducts.setQtyPickedUp(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_PICKUP)));
				purchaseProducts.setProductPrice(cursor.getString(cursor
						.getColumnIndex(OrderProductColumns.PRODUCT_PRICE)));
				purchaseProducts.setQtyStockInHand(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_STOCK_IN_HAND)));
				purchaseProducts.setQtyDelivered(cursor.getString(cursor
						.getColumnIndex(ProductColumns.QTY_DELIVERED)));
				purchaseProductsList.add(purchaseProducts);
				
			}
		} else {
			Log.v(TAG, "Cursor NUll");
		}
		Message.obtain(databaseHandler, Constants.SUCCESS, purchaseProductsList)
				.sendToTarget();
	}

	public void getInvoices(String customerId, DatabaseHandler databaseHandler) {
		List<Invoice> invoicesList = null;
		final SQLiteDatabase database = databaseHelper.getReadableDatabase();

		Cursor cursor = database.query(InvoiceColumns.TABLE_NAME, new String[] {
				InvoiceColumns.invoice_id, InvoiceColumns.paid,
				InvoiceColumns.total_amt }, InvoiceColumns.customer_id + "=?",
				new String[] { customerId }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			invoicesList = new ArrayList<Invoice>();
			while (cursor.moveToNext()) {
				Invoice invoice = new Invoice();
				invoice.setId(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.invoice_id)));
				invoice.setInvoiceId(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.invoice_id)));
				invoice.setPaid(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.paid)) > 0 ? true
						: false);
				invoice.setTotalAmount(String.valueOf(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.total_amt))));
				invoicesList.add(invoice);
			}
		} else {

		}
		Message.obtain(databaseHandler, Constants.SUCCESS, invoicesList)
				.sendToTarget();

	}

	public void getInvoiceDetails(String invoiceId,
			DatabaseHandler databaseHandler) {
		List<PurchasedProduct> purchasedProducts = null;
		PurchasedProduct product;
		final SQLiteDatabase sqLiteDatabase = databaseHelper
				.getReadableDatabase();
		String query = "Select  pp.purchase_productid,i.invoice_id,p.product_name,i.totalAmount,i.payment_mode,PP.product_cost,PP.quantity_purchased from Invoices i  inner join PurchasedProducts PP on pp.invoice_id=i.invoice_id inner join products p on p.product_id = pp.product_id where i.invoice_id=?"
				+ " group by PP._id";
		Cursor cursor = sqLiteDatabase.rawQuery(query,
				new String[] { invoiceId });
		if (cursor != null && cursor.getCount() > 0) {
			Log.v(TAG, "Cursor Not Null");
			purchasedProducts = new ArrayList<PurchasedProduct>();
			while (cursor.moveToNext()) {
				product = new PurchasedProduct();
				product.setInvoice_prodcutid(cursor.getString(cursor
						.getColumnIndex(PurchasedProductColumns.PURCHASE_PRODUCT_ID)));
				product.setInvoiceId(cursor.getString(cursor
						.getColumnIndex(PurchasedProductColumns.INVOICE_ID)));
				product.setmProductName(cursor.getString(cursor
						.getColumnIndex(ProductColumns.PRODUCT_NAME)));
				product.setProductCost(cursor.getString(cursor
						.getColumnIndex(PurchasedProductColumns.PRODUCT_COST)));
				product.setProductQuantity(cursor.getString(cursor
						.getColumnIndex(PurchasedProductColumns.QUANTITY_PURCHASED)));
				purchasedProducts.add(product);

				Log.v(TAG, "" + purchasedProducts.size());
			}

		} else {
			Log.v(TAG, "Cursor is Null");
		}
		Message.obtain(databaseHandler, Constants.SUCCESS, purchasedProducts)
				.sendToTarget();
	}

	public void getPurchasedItemsDetails(String invoiceId,
			DatabaseHandler databaseHandler) {
		Invoice invoice = null;
		final SQLiteDatabase sqLiteDatabase = databaseHelper
				.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(InvoiceColumns.TABLE_NAME, null,
				InvoiceColumns.invoice_id + "=?", new String[] { invoiceId },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			Log.v(TAG, "Cursor Not Null");
			invoice = new Invoice();
			while (cursor.moveToNext()) {
				invoice.setInvoiceId(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.invoice_id)));
				invoice.setDues(String.valueOf(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.dues))));
				invoice.setPaid(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.paid)) > 0 ? true
						: false);
				invoice.setPaymentMode(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.payment_mode)));
				invoice.setPurchased_date(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.purchased_date)));

			}

		} else {
			Log.v(TAG, "Cursor is Null");
		}
		Message.obtain(databaseHandler, Constants.SUCCESS, invoice)
				.sendToTarget();
	}

	public void getPendingInvoices(String driverId,
			DatabaseHandler databaseHandler) {
		List<Invoice> invoices = null;
		Invoice invoice = null;
		final SQLiteDatabase sqLiteDatabase = databaseHelper
				.getReadableDatabase();
		String rawQuery = "select * from Invoices where customer_id in (select customer_id from Customers where driver_id=?)";
		Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,
				new String[] { driverId });
		if (cursor != null && cursor.getCount() > 0) {
			invoices = new ArrayList<Invoice>();
			while (cursor.moveToNext()) {
				invoice = new Invoice();
				invoice.setInvoiceId(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.invoice_id)));
				invoice.setDues(String.valueOf(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.dues))));
				invoice.setPaid(cursor.getInt(cursor
						.getColumnIndex(InvoiceColumns.paid)) > 0 ? true
						: false);
				invoice.setPaymentMode(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.payment_mode)));
				invoice.setPurchased_date(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.purchased_date)));
				invoice.setTotalAmount(cursor.getString(cursor
						.getColumnIndex(InvoiceColumns.total_amt)));
				invoice.setCustomerId(cursor.getString(cursor.getColumnIndex(InvoiceColumns.customer_id)));
				invoices.add(invoice);
				Log.v(TAG, "INVOICE LIST SIZE:" + invoices.size());
			}

		} else {
			Log.v(TAG, "Cursor is Nullll");
		}

		Message.obtain(databaseHandler, Constants.SUCCESS, invoices)
				.sendToTarget();
	}

}
