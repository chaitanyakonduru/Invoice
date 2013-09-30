package com.example.invoiceapp.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.invoiceapp.models.Customer;
import com.example.invoiceapp.models.Driver;
import com.example.invoiceapp.models.Order;
import com.example.invoiceapp.models.OrderProduct;
import com.example.invoiceapp.models.Product;
import com.example.invoiceapp.models.RouteInfo;

public class JsonResponseParser {

	private static final String TAG_MESSAGE = "drivers";
	private static final String TAG_DRIVER_ID = "driver_id";
	private static final String TAG_DRIVER_NAME = "driver_name";
	private static final String TAG_DRIVER_PASSWORD = "password";
	private static final String TAG_PRODUCTS_LIST = "products_list";
	private static final String TAG_PRODUCT_ID = "product_id";
	private static final String TAG_PRODUCT_NAME = "product_name";

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
				JSONArray jsonArray = jsonObject
						.getJSONArray(TAG_PRODUCTS_LIST);
				if (jsonArray != null) {
					Product product = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						product = new Product();
						JSONObject productObject = (JSONObject) jsonArray
								.get(i);
						product.setProductId(productObject.has(TAG_PRODUCT_ID) ? productObject
								.getString(TAG_PRODUCT_ID) : null);
						product.setProductName(productObject
								.has(TAG_PRODUCT_NAME) ? productObject
								.getString(TAG_PRODUCT_NAME) : null);
						productsList.add(product);
					}
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productsList;

	}

	public static RouteInfo parseDriverRouteInfoResponse(String response) {
		RouteInfo routeInfo = null;
		List<Customer> customersList = null;
		Customer customer = null;
		Order order = null;
		List<OrderProduct> orderProductsList = null;
		OrderProduct orderProduct = null;
		try {
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("Route")) {
				routeInfo = new RouteInfo();
				JSONArray routeArray = jsonObject.getJSONArray("Route");
				if (routeArray != null && routeArray.length() > 0) {
					routeInfo = new RouteInfo();
					JSONObject routeObject = routeArray.getJSONObject(0);
					routeInfo
							.setmRouteId(routeObject.has("route_id") ? routeObject
									.getString("route_id") : null);
					routeInfo
							.setmRouteName(routeObject.has("route_name") ? routeObject
									.getString("route_name") : null);

					if (routeObject.has("customers")) {
						JSONArray customersArrayObject = routeObject
								.getJSONArray("customers");
						if (customersArrayObject != null
								&& customersArrayObject.length() > 0) {
							customersList = new ArrayList<Customer>();
							for (int i = 0; i < customersArrayObject.length(); i++) {
								customer = new Customer();
								JSONObject custmerObject = customersArrayObject
										.getJSONObject(i);
								customer.setmCustomerId(custmerObject
										.has("customer_id") ? custmerObject
										.getString("customer_id") : null);
								customer.setmCustomerAddress(custmerObject
										.has("customer_address") ? custmerObject
										.getString("customer_address") : null);
								customer.setmCustomerCity(custmerObject.has("") ? custmerObject
										.getString("") : null);
								customer.setmCustomerName(custmerObject
										.has("customer_name") ? custmerObject
										.getString("customer_name") : null);
								customer.setmLandmark(custmerObject
										.has("landmark") ? custmerObject
										.getString("landmark") : null);
								customer.setmLatitude(custmerObject
										.has("latitude") ? custmerObject
										.getString("latitude") : null);
								customer.setmLongitude(custmerObject
										.has("longitude") ? custmerObject
										.getString("longitude") : null);
								customer.setmPhoneNo(custmerObject
										.has("phone_no") ? custmerObject
										.getString("phone_no") : null);

								if (custmerObject.has("orders")) {
									JSONObject orderObject = custmerObject
											.getJSONObject("orders");
									if (orderObject != null) {
										order = new Order();
										order.setmOrderId(orderObject
												.has("OrderID") ? orderObject
												.getString("OrderID") : null);
										order.setmOrderDate(orderObject
												.has("OrderDate") ? orderObject
												.getString("OrderDate") : null);
										order.setmOrderTotalAmt(orderObject
												.has("OrderTotalAmoount") ? orderObject
												.getString("OrderTotalAmoount")
												: null);

										if (orderObject.has("OrderProducts")) {
											JSONArray orderProductArray = orderObject
													.getJSONArray("OrderProducts");
											if (orderProductArray != null
													&& orderProductArray
															.length() > 0) {
												orderProductsList = new ArrayList<OrderProduct>();
												for (int k = 0; k < orderProductArray
														.length(); k++) {
													orderProduct = new OrderProduct();
													JSONObject orderProductObject = orderProductArray
															.getJSONObject(k);
													orderProduct
															.setmOrderProductId(orderProductObject
																	.has("order_product_id") ? orderProductObject
																	.getString("order_product_id")
																	: null);
													orderProduct
															.setmOrderId(orderProductObject
																	.has("OrderID") ? orderProductObject
																	.getString("OrderID")
																	: null);

													orderProduct
															.setmOrderQty(orderProductObject
																	.has("quantity_ordered") ? orderProductObject
																	.getString("quantity_ordered")
																	: null);
													orderProduct
															.setmProductId(orderProductObject
																	.has("product_id") ? orderProductObject
																	.getString("product_id")
																	: null);
													orderProduct
															.setmProductPrice(orderProductObject
																	.has("price") ? orderProductObject
																	.getString("price")
																	: null);
													orderProductsList
															.add(orderProduct);
												}
												order.setmOrderedProductsList(orderProductsList);
											}
										}
										order.setmOrderedProductsList(orderProductsList);

										customer.setOrder(order);
									}
								}

								customersList.add(customer);
							}
						}
						routeInfo.setCustomesList(customersList);
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routeInfo;
	}

}