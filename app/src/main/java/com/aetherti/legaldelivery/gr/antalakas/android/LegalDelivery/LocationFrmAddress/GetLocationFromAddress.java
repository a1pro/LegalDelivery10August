package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LocationFrmAddress;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
////////////////////////////////////////////////////////
/////This is Class which takes Address
///// And returns the Latitude and Longitudes.... 
////////////////////////////////////////////////////////

public class GetLocationFromAddress {

	private double lat, lon;
	private JSONObject JObject;

	public boolean isLocationAvailable(String Addr) {

		try {
			getLocationInfo(Addr);
			this.lon = ((JSONArray) JObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lng");
			
			this.lat = ((JSONArray) JObject.get("results")).getJSONObject(0)
					.getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");
			
			Log.d("Rturn type", "True");
			return true;
		} catch (JSONException e) {
			Log.d("Return Type", "false");
			return false;

		}
	}

	public JSONObject getLocationInfo(String address) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			address = address.replaceAll(" ", "%20");
			Log.d("Changed Address:", address);
			HttpPost httppost = new HttpPost(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();
			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
			Log.d("What we get", stringBuilder.toString());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d("Location from Address Class", e.getMessage());
		} catch (IOException e) {
			Log.d("Location from Address Class", e.getMessage());
			e.printStackTrace();
		}
		JObject = new JSONObject();
		try {
			JObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JObject;
	}

	public double getLatitude() {
		return lat;
	}

	public double getLaongitude() {
		return lon;
	}
}
