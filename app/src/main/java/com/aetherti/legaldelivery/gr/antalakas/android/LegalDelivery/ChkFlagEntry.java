package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.ServerStatusMessage;

public class ChkFlagEntry extends Activity {
	
	private static final String url = Globals.Domain + Globals.DomainPathDownloadServerStatus;
	String strMessageCount = "";
	AlertDialog alertDialog;

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			try {
				String Ok = data.getExtras().getString("Ok");
				if (Ok.equals("0")) {
					finish();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!Globals.IsNetworkAvailable(getBaseContext())) {
			alertDialog = new AlertDialog.Builder(ChkFlagEntry.this).create();
			alertDialog.setTitle("LegalDelivery 1.0.17");
			alertDialog.setMessage("Cannot connect to the service, network is not available. \nPlease check network settings.");
			alertDialog.setIcon(R.drawable.icon_alert);
			alertDialog.setButton("Setting",
					new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface dialog, final int which) {
							final Intent setting = new Intent(
									Settings.ACTION_WIFI_SETTINGS);
							startActivity(setting);
						}
					});
			alertDialog.show();
		} else {
			String serverFlag = chkServerFlag().toString();
			if (serverFlag.equalsIgnoreCase("y")) {
				Intent i = new Intent(this, LegalDeliveryActivity.class);
				startActivityForResult(i, 0);
			} else if (serverFlag.equalsIgnoreCase("n")) {
				terminate();
				Intent i = new Intent(this, BlueScreenDesign.class);
				startActivityForResult(i, 0);
			}
		}
	}

	private String chkServerFlag() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		String deviceID = Globals.GetDeviceID(getBaseContext(), getContentResolver());
		String Server_Status = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("DeviceID", new StringBody(deviceID));
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				try{
					String strDataLength = response.getFirstHeader("DataLength").getValue();
					int dataLength = Integer.parseInt(strDataLength);
					strMessageCount = response.getFirstHeader("MessageCount").getValue();

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					resEntity.writeTo(bos);

					byte[] totalData = bos.toByteArray();	  			
					byte[] actualMessages = new byte[dataLength];

					System.arraycopy(totalData, 0, actualMessages, 0, dataLength);

					ServerStatusMessage ServerSM = ServerStatusMessage.parseFrom(actualMessages);
					Server_Status = ServerSM.getServerStatus();

				}catch (Exception e) {
					e.printStackTrace(); 
					Server_Status = "Error while Cheching Server Status "+e.getMessage();
				}
			}
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
			Server_Status = "Error while Checking Server Status "+e.getMessage();
		} 
		catch (IOException e) {
			e.printStackTrace();
			Server_Status = "Error while Checking Server Status "+e.getMessage();
		}			
		catch (Exception e) {
			e.printStackTrace();
			Server_Status = "Error while Checking Server Status"+e.getMessage();
		}	
		Log.i("Server Status:",Server_Status);//Delete after test: Sudheer..
		return Server_Status;
	}

	void terminate() {
		LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(this);
		dbHelper.open();
		dbHelper.deleteLD();
		dbHelper.deleteHoliday();
		dbHelper.deleteRelatedP();
		dbHelper.deleteRepository();
	}
}
