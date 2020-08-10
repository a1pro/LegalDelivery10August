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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CalculateTimeandDistance {
	Double distancedouble = 0.0;
	String Time = "0";
	Handler ToastMessageHandler;
	
	public void getDistanceInfo(double lat1, double lng1,
			String destinationAddress) {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			destinationAddress = destinationAddress.replaceAll(" ", "%20");
			String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ lat1
					+ ","
					+ lng1
					+ "&destination="
					+ destinationAddress
					+ "&mode=driving&sensor=false";

			HttpPost httppost = new HttpPost(url);

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

		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());

			JSONArray array = jsonObject.getJSONArray("routes");

			JSONObject routes = array.getJSONObject(0);

			JSONArray legs = routes.getJSONArray("legs");

			JSONObject steps = legs.getJSONObject(0);

			JSONObject distance = steps.getJSONObject("distance");

			JSONObject duration = steps.getJSONObject("duration");

			this.distancedouble = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]", ""));

			this.Time = String.valueOf(duration.getString("text").replaceAll("[^\\.0123456789]", ""));
			if(Time.length() > 2){
				int length = (Time.length()-2);
				int hours = Integer.parseInt(Time.substring(0,length));
				int len = Time.length();
				int min = Integer.parseInt(Time.substring((len-2),len));
				int hoursToMinute = (int) Math.round(hours * 60);
				int totalTime = hoursToMinute + min;
				this.Time = String.valueOf(totalTime);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//mitesh

	public void getDistanceInfoBySourceAddress(String sourceAddress,String destinationAddress) {
		
		StringBuilder stringBuilder = new StringBuilder();

		try {
			if(sourceAddress == null || destinationAddress == null){
				ToastMsg("The address is null");
				return;
			}

			String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ sourceAddress
					+ "&destination="
					+ destinationAddress
					+ "&mode=driving&sensor=false";

			HttpPost httppost = new HttpPost(url);

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
			Log.i("What info get:", stringBuilder.toString());

		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject = new JSONObject(stringBuilder.toString());

			JSONArray array = jsonObject.getJSONArray("routes");

			JSONObject routes = array.getJSONObject(0);

			JSONArray legs = routes.getJSONArray("legs");

			JSONObject steps = legs.getJSONObject(0);

			JSONObject distance = steps.getJSONObject("distance");

			JSONObject duration = steps.getJSONObject("duration");

			this.Time = String.valueOf(duration.getString("text").replaceAll("[^\\.0123456789]", ""));
			if(Time.length() > 2){
				int length = (Time.length()-2);
				int hours = Integer.parseInt(Time.substring(0,length));
				int len = Time.length();
				int min = Integer.parseInt(Time.substring((len-2),len));
				int hoursToMinute = (int) Math.round(hours * 60);
				int totalTime = hoursToMinute + min;
				this.Time = String.valueOf(totalTime);
			}
			if(Time == "0"){
				ToastMsg("Address is wrong");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void ToastMsg(String msg) {

		if (!msg.equals(null) && !msg.equals("")) {
			Message msgObj = ToastMessageHandler.obtainMessage();
			Bundle b = new Bundle();
			b.putString("message", msg);
			msgObj.setData(b);
			ToastMessageHandler.sendMessage(msgObj);
		}
	}
	//mitesh

	public Double getDistance() {
		return this.distancedouble;
	}

	public String getTime() {
		return this.Time;
	}
}
