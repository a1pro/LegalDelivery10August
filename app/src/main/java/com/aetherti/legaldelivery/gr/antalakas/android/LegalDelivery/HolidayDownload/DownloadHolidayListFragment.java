package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.HolidayDownload;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.aetherti.legaldelivery.R;
import com.google.gson.Gson;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.HDMessageAdapter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.HolidayMessage;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.HolidayMessages;

public class DownloadHolidayListFragment extends ListFragment {
	
	private static final String url = Globals.Domain + Globals.DomainHolidayPathDownload;
	private List<HolidayMessage> list;
	String strMessageCount = "";
	ProgressDialog mProgressDialog;
	String theYear = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater,
	ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.hd_download_list_fragment, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = new ArrayList<HolidayMessage>();
	}
	
	public void onListItemClick(ListView parent, View v,
		int position, long id){
	}
	public void SelectAll() {
		ListView listView = getListView();
		int count = 0;
		try {
			count = getListAdapter().getCount();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			ErroReportingInBackground errreprt = new ErroReportingInBackground(getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!",Toast.LENGTH_SHORT).show();
		}
		if (count == 0) {
			Toast.makeText(getActivity(), "Nothing To select .",Toast.LENGTH_SHORT).show();
		} else {
			for (int I = 0; I < count; I++) {
				listView.setItemChecked(I, true);
			}
		}
	}
	public void ClearAll() {
		ListView listView = getListView();

		int count = 0;
		try {
			count = getListAdapter().getCount();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));

			ErroReportingInBackground errreprt = new ErroReportingInBackground(
					getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!",Toast.LENGTH_SHORT).show();
		}
		if (count == 0) {
			Toast.makeText(getActivity(), "Nothing to Clear .",Toast.LENGTH_SHORT).show();
		} else {
			for (int I = 0; I < count; I++) {
				listView.setItemChecked(I, false);
			}
		}
	}
	public void Save()
	{
		ListView listView = getListView();
		SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
		if (checkedItems == null){
			Toast.makeText(getActivity(), "Nothing to Save .", Toast.LENGTH_SHORT).show();
		}
		else {
			LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(getActivity());
	        dbHelper.open();
	        
	  	  	try {	
	  	  		dbHelper.deleteHolidayYear(theYear);
	  	  		
			    for (int i=0; i<checkedItems.size(); i++) {
			    	
			        if (!checkedItems.valueAt(i)) 
			        	continue;
			        
		            HolidayMessage hdm = (HolidayMessage)listView.getAdapter().getItem(
		                                  checkedItems.keyAt(i));
		           
		            dbHelper.createHoliday(
		            		hdm.getHolidayID(), 						// _id
            	     		Integer.toString(hdm.getHolidayYear()),   	// Year
            	     		hdm.getHolidayDate(), 						// Date
            	     		hdm.getHolidayDescription() 				// Name
		            		);			  	  		
			            
			    }
	  	  	Toast.makeText(getActivity(), "Holidays Saved!", Toast.LENGTH_SHORT).show();
	  	  	}
	  	  	catch (Exception e) {
	  	  		e.printStackTrace();
	  		}
		    
		    dbHelper.close();
		}
	}
	
	public void Download(String sYear){
		if (!Globals.IsNetworkAvailable(getActivity().getBaseContext())){
			Globals.ShowNoNetworkMessage(getActivity().getBaseContext());
			return;
		}
		
		theYear = sYear;
		list.clear();
    	String deviceID = Globals.GetDeviceID(getActivity().getBaseContext(), getActivity().getContentResolver());
		
    	DownloadTask2 task = new DownloadTask2();
		task.execute(new String[] { deviceID, sYear });
	}
	
	private class DownloadTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage("Please wait while Downloading.....");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}
		@Override
		protected String doInBackground(String... deviceInfo) {
			
	  	  	HttpClient httpclient = new DefaultHttpClient();
	  	  	HttpPost httppost = new HttpPost(url);
			String returnstatement = "";
	  	  	try {
	  	  		MultipartEntity entity = new MultipartEntity();
	  	  		entity.addPart("DeviceID", new StringBody(deviceInfo[0]));
	  	  		entity.addPart("HolidayYear", new StringBody(deviceInfo[1]));
	  	  		httppost.setEntity(entity);
	  	  		HttpResponse response = httpclient.execute(httppost);
	  	  		HttpEntity resEntity = response.getEntity();
	  	  		if (resEntity != null) {
					try{
	  	  				String strDataLength = response.getFirstHeader("DataLength").getValue();
	  	  				Log.i("UplodActivityDataLngth:",strDataLength);
	  	  				int dataLength = Integer.parseInt(strDataLength);
	  	  				strMessageCount = response.getFirstHeader("MessageCount").getValue();
	  	  				int messageCount = Integer.parseInt(strMessageCount);
	  	  				ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  	  				resEntity.writeTo(bos);
	  	  				byte[] totalData = bos.toByteArray();	  			
	  	  				byte[] actualMessages = new byte[dataLength];
	  	  				System.arraycopy(totalData, 0, actualMessages, 0, dataLength);
	  	  				HolidayMessages hdms = HolidayMessages.parseFrom(actualMessages);
	  	  				for ( int I=0; I<messageCount; I++ ) {
	  	  					HolidayMessage hdm = (HolidayMessage)hdms.getHolidayMessage().get(I);	
	  	  					list.add(hdm);
	  	  				}
						returnstatement = "LegalDeliveryNoResponse";
					}catch (Exception e) {
						if(e.getMessage().equals(null))
							returnstatement ="Holidays not available for requested year! ";
							e.printStackTrace();
						}
	  	  			}
	  		} 
	  	  	catch (ClientProtocolException e) {
				returnstatement ="No Response Found From Server :- " +  e.getMessage();
	  	  		e.printStackTrace();
	  		} 
	  	  	catch (IOException e) {
				returnstatement ="No Response Found From Server :- " +  e.getMessage();
	  	  		e.printStackTrace();
	  		}			
	  	  	catch (Exception e) {
				returnstatement ="No Response Found From Server :- " +  e.getMessage();
	  	  		e.printStackTrace();
	  		}	 	
			return returnstatement;
		}
        @Override
		protected void onPostExecute(String result) {
			
			if(mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
			}
			if(result.equals("LegalDeliveryNoResponse")){
			setListAdapter(new HDMessageAdapter(getActivity(), list));
			ListView listView = getListView();
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			DownloadHolidayControlFragment downloadHolidayControl = (DownloadHolidayControlFragment) getFragmentManager()
					.findFragmentById(R.id.DownloadHolidayControlFragmentID);
			downloadHolidayControl.SetNumOfDownloadMessages(strMessageCount);
			
		}else{
				Globals.NoResponse_Data(getActivity().getBaseContext(),result);
			}
		}
	}
	
	private class DownloadTask2 extends AsyncTask<String, Void, String> {
    /*private final String HolidayID = null;
	private final String HolidayYear = null;
	private final String HolidayDate = null;
	private final String HolidayDescription = null;
	private final String HolidayMessage = null;
	*/
	HttpURLConnection conn = null;
	
	private String HolidayYear;
	
		@SuppressWarnings("static-access")
		
		/*protected void onPreExecute() {

			super.onPreExecute();
			alreport.reportAudit("Downloading Task initiated!", getActivity());
			pd = new ProgressDialog(getActivity());
			pd.setTitle("Downloading Services...");
			pd.setMessage("It might take few minutes for download!");
			pd.setProgressStyle(pd.STYLE_HORIZONTAL);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}*/
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage("Please wait while Downloading.....");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}
		@Override
		protected String doInBackground(String... deviceInfo) {
			
			try {
				HttpClient httpclient = new DefaultHttpClient();
				
			//HolidayMessage hm= new HolidayMessage();
			HolidayStructure hs= new HolidayStructure();
			
			hs.setDeviceID(deviceInfo[0]);
			hs.setHolidayYear(deviceInfo[1]);
	     	//hs.setHolidayYear(HolidayYear);
			
				
				URL url = new URL("http://45.64.105.199/LDSWebService/api/AndroidSearch/holiday");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				
				
				
				conn.setRequestProperty("authorization", Globals.Authorization );
				conn.setRequestProperty("Thumbprint", Globals.Thumbprint );
				
				
				
				
				conn.setRequestProperty("Content-Type", "application/json");
				 conn.setConnectTimeout(15000);
				Gson gson = new Gson();
				String JSONString = gson.toJson(hs);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
						(conn.getOutputStream(), "UTF-8"));
				bw.write(JSONString);
				bw.flush();
				bw.close();
				conn.connect();
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream in = new BufferedInputStream(conn.getInputStream());
					InputStreamReader isw = new InputStreamReader(in);
					BufferedReader reader = new BufferedReader(isw);
					StringBuilder sb = new StringBuilder("");
					String line = "";
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					in.close();
					final String output = sb.toString();	
					System.out.print(output);
					
					HolidayValue hV = gson.fromJson(output, HolidayValue.class);
					ArrayList<HolidayMessage> mData  = hV.$values;
					int abc = mData.size();
					System.out.print(abc);
					
					strMessageCount=Integer.toString(mData.size());
					for (int I = 0; I < mData.size(); I++) {
						   HolidayMessage.Builder builderLegalDeliveryMessage = HolidayMessage.newBuilder();
 						      builderLegalDeliveryMessage.setHolidayID(mData.get(I).getHolidayID());
 						      builderLegalDeliveryMessage.setHolidayYear(mData.get(I).getHolidayYear());
 						      builderLegalDeliveryMessage.setHolidayDate(mData.get(I).getHolidayDate());
						      builderLegalDeliveryMessage.setHolidayDescription(mData.get(I).getHolidayDescription());
						      
						      
						      HolidayMessage legalDeliveryMessage = new HolidayMessage
									(builderLegalDeliveryMessage);
						      list.add(legalDeliveryMessage);
						     
						  //    publishProgress(I);
								// pd.setProgress(I);
								//System.out.println(ldm.toString());
								Log.i("msg count:", String.valueOf(I));
						     /* String w=   (mData.get(I).getHolidayID());
						      int lic3=   (mData.get(I).getHolidayYear());
						      String lic=   (mData.get(I).getHolidayDescription());
							   
						      String lic4=   (mData.get(I).getHolidayDate());
						      */
					}	
					
					
					
			
				}
				}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		/*private void publishProgress(int i) {
			// TODO Auto-generated method stub
			
		}*/
		@Override
		protected void onPostExecute(String result) {
			
			if(mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
			}
			//if(result.equals("LegalDeliveryNoResponse")){
			setListAdapter(new HDMessageAdapter(getActivity(), list));
			
			ListView listView = getListView();
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			DownloadHolidayControlFragment downloadHolidayControl = (DownloadHolidayControlFragment) getFragmentManager()
					.findFragmentById(R.id.DownloadHolidayControlFragmentID);
	           	 
			downloadHolidayControl.SetNumOfDownloadMessages(strMessageCount);
			
		/*}else{
				Globals.NoResponse_Data(getActivity().getBaseContext(),result);
			}*/
		}
		
	}

	
	
	
	
}
