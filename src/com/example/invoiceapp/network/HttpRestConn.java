package com.example.invoiceapp.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;
import android.os.Message;
import android.util.Log;

import com.example.invoiceapp.utils.Constants;

public final class HttpRestConn implements Runnable {
	private static AndroidHttpClient androidHttpClient = null;
	private InvoiceAppHandler askZiggyhandler;
	private static final String TAG = HttpRestConn.class.getSimpleName();
	private static final int CONNECTION_TIMEOUT_INTERVAL=20*1000;
	private static final int TIMEOUT_INTERVAL=20*1000;
	private String url;
	

	protected  String getStringfromInputStream(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public HttpRestConn(String urlString, InvoiceAppHandler handler) {
		this.url=urlString;
		this.askZiggyhandler=handler;
	}
	

	@Override
	public void run() {
		try {
			HttpGet httpGet = new HttpGet(url);
			androidHttpClient = AndroidHttpClient.newInstance("askziggy");
			HttpParams httpParams = androidHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT_INTERVAL);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_INTERVAL);
			HttpResponse response = androidHttpClient.execute(httpGet);
			int responseCode = response.getStatusLine().getStatusCode();
			
			if (responseCode == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				String responseObject = getStringfromInputStream(httpEntity);
				if (responseObject != null) {
					Log.i(TAG, "Response  -- " + responseObject.toString());
					Message.obtain(askZiggyhandler, Constants.SUCCESS, responseObject)
							.sendToTarget();
				} else {
					Message.obtain(askZiggyhandler, Constants.FAILURE,
							"Sorry Unable to Process your request")
							.sendToTarget();
				}
			} else {
				Message.obtain(askZiggyhandler, Constants.FAILURE,
						" Internal Server Error").sendToTarget();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Message.obtain(askZiggyhandler, Constants.FAILURE, e).sendToTarget();
		} catch (IOException e) {
			e.printStackTrace();
			Message.obtain(askZiggyhandler, Constants.FAILURE, e).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
			Message.obtain(askZiggyhandler, Constants.FAILURE, e).sendToTarget();
		} finally {
			if (androidHttpClient != null) {
				androidHttpClient.close();
				androidHttpClient = null;
			}
		}

	}
}