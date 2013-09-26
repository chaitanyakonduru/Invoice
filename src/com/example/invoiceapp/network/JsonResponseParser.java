package com.example.invoiceapp.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.models.Product;

public class JsonResponseParser {

	private static final String TAG_MESSAGE = "drivers";
	private static final String TAG_DRIVER_ID = "driver_id";
	private static final String TAG_DRIVER_NAME = "driver_name";
	private static final String TAG_DRIVER_PASSWORD = "password";
	private static final String TAG_PRODUCTS_LIST= "products_list";
	private static final String TAG_PRODUCT_ID="product_id";
	private static final String TAG_PRODUCT_NAME="product_name";

	public static List<Driver> parseDriversResponse(String string) {
		List<Driver> driversList = null;
		try {
			JSONObject jsonObject = new JSONObject(string);
			if (jsonObject.has(TAG_MESSAGE)) {
				driversList = new ArrayList<Driver>();
				JSONArray jsonArray = jsonObject.getJSONArray(TAG_MESSAGE);
				if (jsonArray != null) {
					Driver driver = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						driver = new Driver();
						JSONObject driverObject = (JSONObject) jsonArray.get(i);
						driver.setmDriverId(driverObject.has(TAG_DRIVER_ID) ? driverObject
								.getString(TAG_DRIVER_ID) : null);
						driver.setmDriverName(driverObject.has(TAG_DRIVER_NAME) ? driverObject
								.getString(TAG_DRIVER_NAME) : null);
						driver.setmDriverPassword(driverObject
								.has(TAG_DRIVER_PASSWORD) ? driverObject
								.getString(TAG_DRIVER_PASSWORD) : null);
						driversList.add(driver);
					}
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return driversList;

	}
	
	public static List<Product> parseProductsResponse(String string) {
		List<Product> productsList = null;
		try {
			JSONObject jsonObject = new JSONObject(string);
			if (jsonObject.has(TAG_PRODUCTS_LIST)) {
				productsList = new ArrayList<Product>();
				JSONArray jsonArray = jsonObject.getJSONArray(TAG_PRODUCTS_LIST);
				if (jsonArray != null) {
					Product product= null;
					for (int i = 0; i < jsonArray.length(); i++) {
						product=new Product();
						JSONObject productObject = (JSONObject) jsonArray.get(i);
						product.setProductId(productObject.has(TAG_PRODUCT_ID)?productObject.getString(TAG_PRODUCT_ID):null);
						product.setProductName(productObject.has(TAG_PRODUCT_NAME)?productObject.getString(TAG_PRODUCT_NAME):null);
						productsList.add(product);
					}
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productsList;

	}
}