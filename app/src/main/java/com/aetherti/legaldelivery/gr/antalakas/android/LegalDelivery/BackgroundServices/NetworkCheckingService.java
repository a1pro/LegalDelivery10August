package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.BackgroundServices;

import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class NetworkCheckingService extends Service{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("SERVICE STATUS::","Created!");
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("SERVICE STATUS::", "Runnimg...");
		new NetworkCheckTask().execute();
		return Service.START_NOT_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("SERVICE STATUS::", "Stopped!");
	}
	private class NetworkCheckTask extends AsyncTask<Void,Object,String>{

		@Override
		protected String doInBackground(Void... params) {
			 HttpClient httpclient = new DefaultHttpClient();
			 HttpPost httpPost = new HttpPost(Globals.Domain+Globals.DomainPathApplock);
			 try {
			     HttpResponse response = httpclient.execute(httpPost);
			 } catch (ClientProtocolException e) {
			     e.printStackTrace();
			          
			 } catch (IOException e) {
			     e.printStackTrace();
			 }
			return "SUCCESS";
		}
		@Override
		protected void onPostExecute(String result) {
			sendBroadcast(new Intent("SUCCESS"));
			
		}
	}
}
