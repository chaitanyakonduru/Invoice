package com.example.invoiceapp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.models.Order;
import com.example.invoiceapp.models.OrderProduct;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.models.RouteInfo;

public class InvoiceAppDatabase {

	private static InvoiceAppDatabase invoiceAppDatabase;
	private static InvoiceDatabaseHelper databaseHelper;
	private static final String DATABASE_NAME="invoice.db";
	private static final int DATABASE_VERSION=2;
	private Context mContext;
	public InvoiceAppDatabase(Context context) {
		databaseHelper=new InvoiceDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext=context;
	}

	public static InvoiceAppDatabase getInstance(Context context)
	{
		if(invoiceAppDatabase==null)
		{
			invoiceAppDatabase=new InvoiceAppDatabase(context);
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
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("Drop table if exists "+CustomerColumns.TABLE_NAME);
			db.execSQL("Drop table if exists "+OrderColumns.TABLE_NAME);
			db.execSQL("Drop table if exists "+ProductColumns.TABLE_NAME);
			db.execSQL("Drop table if exists "+RouteColumns.TABLE_NAME);
			db.execSQL("Drop table if exists "+DriverColumns.TABLE_NAME);
			db.execSQL("Drop table if exists "+OrderProductColumns.TABLE_NAME);
		}
		
		
	}
	
	private static class CustomerColumns implements BaseColumns
	{
		public static final String TABLE_NAME="Customers";
		public static final String CUSTOMER_ID="customer_id";
		public static final String CUSTOMER_NAME="customer_name";
		public static final String CUSTOMER_ADDRESS="customer_address";
		public static final String CUSTOMER_LANDMARK="customer_landmark";
		public static final String CUSTOMER_LATITUDE="customer_latitude"; 
		public static final String CUSTOMER_LONGITUDE="customer_longitude";
		public static final String CUSTOMER_PHONE="customer_phone";
		public static final String CUSTOMER_CITY="customer_city"; 
		
		private static final String DATABASE_CREATE = "create table Customers(_id integer primary key autoincrement,"
				+ "customer_id integer,"
				+ "customer_name text,customer_address text,"
				+ "customer_landmark text,customer_latitude text,"
				+ "customer_longitude text ,"
				+ "customer_phone text,customer_city text);";
	}
	
	public static class OrderColumns implements BaseColumns
	{
		public static final String TABLE_NAME="Orders";
		public static final String ORDER_ID="order_id";
		public static final String ORDER_DATE="order_date";
		public static final String ORDER_TOTAL_AMOUNT="order_amount";
		
		
		private static final String DATABASE_CREATE = "create table Orders(_id integer primary key autoincrement,"
				+ "order_id integer,"
				+ "order_date text,order_amount text);";
	}
	
	public static class ProductColumns implements BaseColumns
	{
		public static final String TABLE_NAME="Products";
		public static final String PRODUCT_ID="product_id";
		public static final String PRODUCT_NAME="product_name";
		public static final String ORDERED_QUANTITY="ordered_qty";
		public static final String PRODUCT_PRICE="product_price";
		
		
		private static final String DATABASE_CREATE = "create table Products(_id integer primary key autoincrement,"
				+ "product_id integer,"
				+ "product_price text,"
				+ "product_name text,ordered_qty text);";
	}
	
	public static class DriverColumns implements BaseColumns
	{
		public static final String TABLE_NAME="Drivers";
		public static final String DRIVER_ID="driver_id";
		public static final String DRIVER_NAME="driver_name";
		public static final String DRIVER_CODE="driver_code";
		public static final String DRIVER_PASSWORD="driver_password";
		
		private static final String DATABASE_CREATE = "create table Drivers(_id integer primary key autoincrement,"
				+ "driver_id integer,"
				+ "driver_name text,"
				+ "driver_password text,"
				+ "driver_code text);";
	}
	
	private static class RouteColumns implements BaseColumns
	{
		public static final String TABLE_NAME="Routes";
		public static final String ROUTE_ID="route_id";
		public static final String ROUTE_NAME="route_name";
		public static final String ROUTE_CODE="route_code";
		
		private static final String DATABASE_CREATE = "create table Routes(_id integer primary key autoincrement,"
				+ "route_id integer,"
				+ "route_name text,"
				+ "route_code text);";
	}
	
	private static class OrderProductColumns implements BaseColumns
	{
		public static final String TABLE_NAME="OrderProducts";
		public static final String ORDER_PRODUCT_ID="order_product_id";
		public static final String ORDER_ID="order_id";
		public static final String PRODUCT_ID="product_id";
		public static final String QUANTITY_ORDERED="quantity_ordered";
		public static final String PRODUCT_PRICE="price";
		
		private static final String DATABASE_CREATE = "create table OrderProducts(_id integer primary key autoincrement,"
				+ "order_product_id integer,"
				+ "product_id text,"
				+ "quantity_ordered text,"
				+ "price text);";
	}
	
  
	public boolean insertDriver(Driver driver)
	{
		boolean isUpdate=false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Drivers WHERE driver_id = ?",
						new String[] { driver.getmDriverId()});
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(DriverColumns.DRIVER_ID, driver.getmDriverId());
		contentValues.put(DriverColumns.DRIVER_NAME, driver.getmDriverName());
		contentValues.put(DriverColumns.DRIVER_PASSWORD, driver.getmDriverPassword());
		contentValues.put(DriverColumns.DRIVER_CODE, driver.getmDriverCode());
		
		if(isUpdate)
		{
			return sqLiteDatabase.update(DriverColumns.TABLE_NAME, contentValues, DriverColumns.DRIVER_ID+"=?", new String[]{driver.getmDriverId()})>0?true:false;
		}
		else
		{
			return sqLiteDatabase.insert(DriverColumns.TABLE_NAME, null, contentValues)>0?true:false;
		}
		
	}
	
	public boolean insertRoutes(RouteInfo routeInfo)
	{
		boolean isUpdate=false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Routes WHERE route_id = ?",
						new String[] { routeInfo.getmRouteId()});
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(RouteColumns.ROUTE_ID, routeInfo.getmRouteId());
		contentValues.put(RouteColumns.ROUTE_NAME, routeInfo.getmRouteName());
		contentValues.put(RouteColumns.ROUTE_CODE, routeInfo.getmRouteCode());
		
		if(isUpdate)
		{
			return sqLiteDatabase.update(RouteColumns.TABLE_NAME, contentValues, RouteColumns.ROUTE_ID+"=?", new String[]{routeInfo.getmRouteId()})>0?true:false;
		}
		else
		{
			return sqLiteDatabase.insert(RouteColumns.TABLE_NAME, null, contentValues)>0?true:false;
		}
		
	}
	
	public boolean insertCustomer(Customer customer)
	{
		boolean isUpdate=false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Customers WHERE customer_id = ?",
						new String[] { customer.getmCustomerId()});
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(CustomerColumns.CUSTOMER_ID, customer.getmCustomerId());
		contentValues.put(CustomerColumns.CUSTOMER_NAME, customer.getmCustomerName());
		contentValues.put(CustomerColumns.CUSTOMER_ADDRESS, customer.getmCustomerAddress());
		contentValues.put(CustomerColumns.CUSTOMER_CITY, customer.getmCustomerCity());
		contentValues.put(CustomerColumns.CUSTOMER_LANDMARK, customer.getmLandmark());
		contentValues.put(CustomerColumns.CUSTOMER_LATITUDE, customer.getmLatitude());
		contentValues.put(CustomerColumns.CUSTOMER_LONGITUDE,customer.getmLongitude());
		contentValues.put(CustomerColumns.CUSTOMER_PHONE, customer.getmPhoneNo());
		
		if(isUpdate)
		{
			return sqLiteDatabase.update(CustomerColumns.TABLE_NAME, contentValues, CustomerColumns.CUSTOMER_ID+"=?", new String[]{customer.getmCustomerId()})>0?true:false;
		}
		else
		{
			return sqLiteDatabase.insert(CustomerColumns.TABLE_NAME, null, contentValues)>0?true:false;
		}
		
	}
	
	public boolean insertOrder(Order order)
	{
		boolean isUpdate=false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Orders WHERE customer_id = ?",
						new String[] { order.getmOrderId()});
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(OrderColumns._ID, order.getmOrderId());
		contentValues.put(OrderColumns.ORDER_DATE, order.getmOrderDate());
		contentValues.put(OrderColumns.ORDER_TOTAL_AMOUNT, order.getmOrderTotalAmt());
		
		if(isUpdate)
		{
			return sqLiteDatabase.update(OrderColumns.TABLE_NAME, contentValues, OrderColumns.ORDER_ID+"=?", new String[]{order.getmOrderId()})>0?true:false;
		}
		else
		{
			return sqLiteDatabase.insert(OrderColumns.TABLE_NAME, null, contentValues)>0?true:false;
		}
		
	}
	
	public boolean insertProduct(Product product)
	{
		boolean isUpdate=false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Products WHERE product_id = ?",
						new String[] { product.getProductId()});
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(ProductColumns.PRODUCT_ID, product.getProductId());
		contentValues.put(ProductColumns.PRODUCT_NAME, product.getProductName());
		contentValues.put(ProductColumns.PRODUCT_PRICE, product.getmPrice());
		contentValues.put(ProductColumns.ORDERED_QUANTITY,product.getmQuantityOrdered());
		
		if(isUpdate)
		{
			return sqLiteDatabase.update(ProductColumns.TABLE_NAME, contentValues, ProductColumns.PRODUCT_ID+"=?", new String[]{product.getProductId()})>0?true:false;
		}
		else
		{
			return sqLiteDatabase.insert(ProductColumns.TABLE_NAME, null, contentValues)>0?true:false;
		}
		
	}
	
	public boolean insertOrderProduct(OrderProduct orderProduct)
	{
		boolean isUpdate=false;
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM Products WHERE product_id = ?",
						new String[] { orderProduct.getmOrderProductId()});
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				isUpdate = true;
			}
			cursor.close();
			cursor = null;
		}
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(OrderProductColumns.ORDER_PRODUCT_ID, orderProduct.getmOrderProductId());
		contentValues.put(OrderProductColumns.ORDER_ID, orderProduct.getmOrderId());
		contentValues.put(OrderProductColumns.PRODUCT_ID, orderProduct.getmProductId());
		contentValues.put(OrderProductColumns.QUANTITY_ORDERED, orderProduct.getmOrderQty());
		contentValues.put(OrderProductColumns.PRODUCT_PRICE, orderProduct.getmProductPrice());
		
		if(isUpdate)
		{
			return sqLiteDatabase.update(OrderProductColumns.TABLE_NAME, contentValues, OrderProductColumns.ORDER_PRODUCT_ID+"=?", new String[]{orderProduct.getmOrderProductId()})>0?true:false;
		}
		else
		{
			return sqLiteDatabase.insert(OrderProductColumns.TABLE_NAME, null, contentValues)>0?true:false;
		}
		
	}
	
	
	public long getLastInsertRecordId(String tableName) {
		long index = 0;
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Cursor cursor = db.query("sqlite_sequence", new String[] { "seq" },
				"name = ?", new String[] { tableName }, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			index = cursor.getLong(cursor.getColumnIndex("seq"));
		}
		cursor.close();
		return index;
	}
	
	public boolean checkTableNullOrNot(String tableName)
	{
		SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
		Cursor cursor = sqLiteDatabase
				.rawQuery(
						"SELECT count(*) FROM "+tableName,
						null);
		
		
		if (null != cursor && cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count >= 1) {
				return true;
			}
			cursor.close();
			cursor = null;
		}
		return false;
	}
	
	public List<Driver> getAllDrivers()
	{
		List<Driver> driverList=null;
		Driver driver=null;
		SQLiteDatabase database=databaseHelper.getReadableDatabase();
		Cursor cursor=database.query(DriverColumns.TABLE_NAME, null, null, null, null, null, null);
		if(cursor!=null && cursor.getCount()>0)
		{
			driverList=new ArrayList<Driver>();
			while (cursor.moveToNext()) {
				driver=new Driver();
				driver.setmDriverId(cursor.getString(cursor.getColumnIndex(DriverColumns.DRIVER_ID)));
				driver.setmDriverName(cursor.getString(cursor.getColumnIndex(DriverColumns.DRIVER_NAME)));
				driver.setmDriverPassword(cursor.getString(cursor.getColumnIndex(DriverColumns.DRIVER_PASSWORD)));
				driverList.add(driver);
			}
		}
		return driverList;
	}
	
	public List<Product> getAllProducts()
	{
		List<Product> productsList=null;
		Product product=null;
		SQLiteDatabase database=databaseHelper.getReadableDatabase();
		Cursor cursor=database.query(ProductColumns.TABLE_NAME, null, null, null, null, null, null);
		if(cursor!=null && cursor.getCount()>0)
		{
			productsList=new ArrayList<Product>();
			while (cursor.moveToNext()) {
				product=new Product();
				product.setProductId(cursor.getString(cursor.getColumnIndex(ProductColumns.PRODUCT_ID)));
				product.setProductName(cursor.getString(cursor.getColumnIndex(ProductColumns.PRODUCT_NAME)));
				productsList.add(product);
			}
		}
		return productsList;
	}
}
