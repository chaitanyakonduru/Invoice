package com.example.invoiceapp.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.invoiceapp.models.Driver;

public class JsonResponseParser {

	private static final String TAG_MESSAGE = "drivers";
	private static final String TAG_DRIVER_ID = "driver_id";
	private static final String TAG_DRIVER_NAME = "driver_name";
	private static final String TAG_DRIVER_PASSWORD = "password";

	public static List<Driver> parseFeedbackResponse(String string) {
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
}