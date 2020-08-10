package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
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

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;
import com.google.gson.Gson;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LDMessageAdapter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessage;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessages;

public class UploadListFragment extends ListFragment {

	private static final String url = Globals.Domain + Globals.DomainPathUpload;
	public static List<LegalDeliveryMessage> list;
	public ArrayList<LegalDeliveryMessage> LegalDelivery = new ArrayList<LegalDeliveryMessage>();
	LegalDeliveryMessages legalDeliveryMessages;
	String strMessageCount = "";
	private LDDatabaseAdapter dbHelper;
	ProgressDialog mProgressDialog;
	LinearLayout upload_LinearLayout;
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	AuditLogReporter alreport;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View V = inflater.inflate(R.layout.ld_upload_list_fragment, container,false);
		Drawable d_bk = null;
		try {
			d_bk = Drawable.createFromStream(getResources().getAssets().open("ic_Sync_bk.png"), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			ErroReportingInBackground errreprt = new ErroReportingInBackground(getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!",Toast.LENGTH_SHORT).show();
		}
		
		alreport=new AuditLogReporter();
		alreport.reportAudit("Setting Background img to upload List", getActivity());	
		upload_LinearLayout = (LinearLayout) V.findViewById(R.id.upload_LinearLayout);
		upload_LinearLayout.setBackgroundDrawable(d_bk);
		return V;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new LDDatabaseAdapter(getActivity());
		dbHelper.open();
		list = new ArrayList<LegalDeliveryMessage>();
	}

	@Override
	public void onDestroyView() {
		dbHelper.close();
		super.onDestroyView();
	}
	public void onListItemClick(ListView parent, View v, int position, long id) {
	}
	public void Search() {
		try {
			alreport.reportAudit("Searching Records for upload!", getActivity());
			list.clear();
			Cursor cursor = dbHelper.fetchAllCompletedServicesAllColumns();
			boolean hasItemIList = false;
			if (!cursor.moveToFirst()) {
				Toast.makeText(getActivity(), "No Data Found.",Toast.LENGTH_SHORT).show();
				hasItemIList = true;
			}

			for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
				String rowId = cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ROWID));
				Cursor cursor1 = dbHelper.fetchLD_UploadFlag(rowId);
				String updateStatusFlag = cursor1.getString(0);
				if (updateStatusFlag.equals("0")) {
					hasItemIList = true;
					
					String mpserv= (cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PServ)));
					String mGender = ""; 
					String mP_Skincolor = ""; 
					String mP_Hair = ""; 
					String mP_Age = ""; 
					String mP_Height = ""; 
					String mP_Weight = "";
					
					if(mpserv != null){
						String[] parts = mpserv.split(";");
						 mGender = parts[0]; 
						 mP_Skincolor = parts[1]; 
						 mP_Hair = parts[2]; 
						 mP_Age = parts[3]; 
						 mP_Height = parts[4];
						 mP_Weight = parts[5]; 
					}
					
					
					String mcserv= (cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CServ)));
					String mC_Entry = ""; 
					String mC_Wall = ""; 
					String mC_Floor = ""; 
					String mC_Lock = ""; 
					String mC_Door = ""; 
					String mDoorLock = "";
					
					if(mcserv != null){
						String[] part = mcserv.split(";");
						mC_Entry = part[0]; 
						mC_Wall = part[1]; 
						mC_Floor = part[2]; 
						mC_Lock = part[3]; 
						mC_Door = part[4]; 
						mDoorLock = part[5]; 
					}
					
					LegalDeliveryMessage legalDeliveryMessage = LegalDeliveryMessage.newBuilder()
							.setLegalDeliveryID(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ROWID)))
							
//							.setLegalDeliveryID(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DeviceID)))
					    	.setJobNo(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_JobNo)))
							.setClient(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_Client)))
							.setServiceType(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceType)))
							.setLTServiceType(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTServiceType)))
							.setCaseNo(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CaseNo)))
							.setLTFullname(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTFullname)))
							.setSTND_FULLNAME(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_FullName)))
							.setPersonSeenSubstitute(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenSubstitute))==null? cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenDoeSubstitute)):cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenSubstitute)))
							.setPersonSeenDoeSubstitute(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenDoeSubstitute)))
							.setPersonSeenPersonal(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenPersonal)))
							.setPersonsNotSeenPersonal(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonsNotSeenPersonal)))
							.setCorpRecipient(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CorpRecipient)))
							.setCorpRecipientTitle(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CorpRecipientTitle)))
							.setLTAddress(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTAddress)))
							.setSTND_ADDRESS(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Address)))
							.setLTApt(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTApt)))
							.setSTND_APT(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Apt)))
							.setLTCity(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTCity)))
							.setSTND_CITY(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_City)))
							.setLTState(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTState)))
							.setSTND_STATE(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_State)))
							.setLTZip(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTZip)))
							.setSTND_ZIP(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Zip)))
							.setDateOfService((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DateOfService)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DateOfService)))
							.setTimeOfService((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_TimeOfService)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_TimeOfService)))
							.setGpsTimeOfService((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfService)))==null ? "[Not Set]":cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfService)))
							.setGPSDate1((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate1)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate1)))
							.setGPSTime1((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime1)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime1)))
							.setGpsTimeOfFirstAttempt((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfFirstAttempt)))==null ? "[Not Set]":cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfFirstAttempt)))
							.setGPSLat1(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLat1)))
							.setGPSLon1(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLon1)))
							.setGPSDate2((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate2)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate2)))
							.setGPSTime2((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime2)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime2)))	
							.setGpsTimeOfSecondAttempt((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfSecondAttempt)))==null ? "[Not Set]" :cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfSecondAttempt)))
							.setGPSLat2(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLat2)))
							.setGPSLon2(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLon2)))
							.setGPSDate3((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate3)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate3)))
 							.setGPSTime3((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime3)))==null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime3)))
							.setGpsTimeOfThirdAttempt((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfThirdAttempt)))==null ? "[Not Set]" :cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfThirdAttempt)))
 							.setGPSLat3(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLat3)))
							.setGPSLon3(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLon3)))
							.setServiceResult(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceResult)))
							.setInputDate(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_InputDate)))
							//.setDoorLock(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DoorLock)))
							//.setPServ(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PServ)))
							//.setCServ(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CServ)))//The below code(WTF! code) is just becoz of .net devloper modified to single OtherIdentifier Field! so I changed this code!
							.setOtherCommentsP(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsP))
									==null?cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsC))
											:cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsP)))
							.setOtherCommentsC(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsC))==null?cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsP)):cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsC)))
							.setFirstAttempt(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_FirstAttempt)))
							.setSecondAttempt(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_SecondAttempt)))
							.setThirdAttempt(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ThirdAttempt)))
							.setServiceCompleted(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceCompleted)))
							.setSTND_ServiceType(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_ServiceType)))
							.setLTBIZNAME(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTBIZNAME)))
							.setPPSERVED1(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PPSERVED1)))
							.setServerLicenceNo(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServerLicenceNo)))
							.setproducttype(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_producttype)))
							
							.setP_Gender(mGender)
							.setP_Skincolor(mP_Skincolor)
							.setP_Hair(mP_Hair)
							.setP_Age(mP_Age)
					        .setP_Height(mP_Height)
							.setP_Weight(mP_Weight)
							
							.setC_Entry(mC_Entry)
							.setC_Wall(mC_Wall)
							.setC_Floor(mC_Floor)
							.setC_Lock(mC_Lock)
							.setC_Door(mC_Door)
							.setDoorLock(mDoorLock)
							.setDeviceId(Globals.GetDeviceID(getActivity().getBaseContext(),
									getActivity().getContentResolver()))
							.build();

		//	String job=(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PriductType)));

			String jlic=(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServerLicenceNo)));
								
					list.add(legalDeliveryMessage);
					System.out.println(legalDeliveryMessage.toString());//just checking what is sending to server... delete it afere use...Sudheer...
				}
			}

			if (!hasItemIList) {
				Toast.makeText(getActivity(), "No Data Found.",Toast.LENGTH_SHORT).show();
			}
			setListAdapter(new LDMessageAdapter(getActivity(), list));
			ListView listView = getListView();
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));

			ErroReportingInBackground errreprt = new ErroReportingInBackground(
					getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	@SuppressWarnings("unused")
	public void Upload() {
		
		 
		if (!Globals.IsNetworkAvailable(getActivity().getBaseContext())) {
			Globals.ShowNoNetworkMessage(getActivity().getBaseContext());
			return;    
		     
		}
		alreport.reportAudit("IN Process of Upload!", getActivity());
		
		ListView listView = getListView();
		SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
		LegalDeliveryMessages.Builder ldmBuilder = LegalDeliveryMessages.newBuilder();
		if (listView.getAdapter() == null) {
			Toast.makeText(getActivity(), "Nothing to Upload .",Toast.LENGTH_SHORT).show();
			return;
		} else if (listView.getAdapter().getCount() > 0 && checkedItems.size() == 0) {
			Toast.makeText(getActivity(), "select at least 1 Service record! ",Toast.LENGTH_SHORT).show();
			return;
		}else if(listView==null){		
			Toast.makeText(getActivity(), "Nothing to Upload !", Toast.LENGTH_SHORT).show();
			return;	
		}	
		for (int i = 0; i < checkedItems.size(); i++) {
			if (checkedItems.valueAt(i)) {
				LegalDeliveryMessage ldm = (LegalDeliveryMessage) listView.getAdapter().getItem(checkedItems.keyAt(i));
				LegalDelivery.add(ldm);
				ldmBuilder.addElementLegalDeliveryMessage(ldm);
			}
		}
		legalDeliveryMessages = ldmBuilder.build();
		if (legalDeliveryMessages.computeSize() > 0) {
			String deviceID = Globals.GetDeviceID(getActivity().getBaseContext(), getActivity().getContentResolver());
			
			/*UploadTask task = new UploadTask();
			task.execute(new String[] { deviceID });*/
			
			SendPostRequest sendpost = new SendPostRequest();
			sendpost.execute(new String[] { deviceID });
			
			
		}
	}
	
	public void SelectAll() {
		ListView listView = getListView();
		int count = 0;
		try {
			count = getListAdapter().getCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0) {
			Toast.makeText(getActivity(), "Nothing To Select",Toast.LENGTH_SHORT).show();
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
		}
		if (count == 0) {
			Toast.makeText(getActivity(), "Nothing to Clear",Toast.LENGTH_SHORT).show();
		} else {
			for (int I = 0; I < count; I++) {
				listView.setItemChecked(I, false);
			}
			/////////////////////////////////////////////////////////////////////////////////
			alreport.reportAudit("Cleared All!", getActivity());
			/////////////////////////////////////////////////////////////////////////////////
		}
	}
	public class UploadTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage("Please wait while Uploading.....");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			/////////////////////////////////////////////////////////////////////////////////
			//alreport.reportAudit("Upload Task initiated!", getActivity());
			/////////////////////////////////////////////////////////////////////////////////
		}
		@Override
		protected String doInBackground(String... deviceInfo) {
			HttpClient httpclient = new DefaultHttpClient();//getting device information
			HttpPost httppost = new HttpPost(url);//get server IP as well as url.
			String resultValue = "NotSucceed";
			HttpEntity resEntity;
			try {
				MultipartEntity entity = new MultipartEntity();
				entity.addPart("DeviceID", new StringBody(deviceInfo[0]));
				byte[] binMessage = legalDeliveryMessages.toByteArray();
				entity.addPart("message", new InputStreamKnownSizeBody(new ByteArrayInputStream(binMessage)
						,binMessage.length, "application/octet-stream"));
				httppost.setEntity(entity);
				HttpResponse response = httpclient.execute(httppost);
				if (response != null) {
					resEntity = response.getEntity();
				}
				resultValue = response.getFirstHeader("Result").getValue();//EXception,problem ask to sachin
			}
			catch (ClientProtocolException e) {
				e.printStackTrace();
				return "Upload Error ClientProtocolException";
			} catch (IOException e) {
				e.printStackTrace();
				return "Upload Error IOException";
			}catch(NullPointerException npe)
			{	npe.printStackTrace();
				return "Error in Uploading! No responce from Server!";
			}catch (Exception e) {
				e.printStackTrace();
				return "Upload Error Exception";
			}
			if (resultValue == "NotSucceed")
				return "failed to upload!";
			else if (resultValue == "DeviceNotFound")
				return "Device not recognised ! Register Device!";
			else if (resultValue == "NoFiletoSync")
				return "File not found! File may be Deleted!";
			else if (resultValue == "Succeed")
				return "Upload Succeded";
			return "Upload Succeded";
		}
		@Override
		protected void onPostExecute(String result) {
			if (result == "Upload Succeded") {
				ListView listView = getListView();
				SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

				for (int i = 0; i < checkedItems.size(); i++) {
					if (checkedItems.valueAt(i)) {
						LegalDeliveryMessage ldm = (LegalDeliveryMessage) listView.getAdapter().getItem(checkedItems.keyAt(i));
						System.out.println(ldm);
						String rowId = ldm.getLegalDeliveryID();
						dbHelper.updateLD_UploadFlag(rowId, 1);
					}
				}
			}     
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}
	}
	private class SendPostRequest extends AsyncTask<String, Integer, String> {
		HttpURLConnection conn = null;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage("Please wait while Uploading.....");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			
		}
		@Override
		protected String doInBackground(String... arg0) {
			String output = "";
			int responseCode = 0;
			try {
				String deviceID = Globals.GetDeviceID(getActivity().getBaseContext(),
						getActivity().getContentResolver());
				System.out.println(deviceID);
				UploadStructure ds = new UploadStructure();
				ds.setLegalDelivery(LegalDelivery);
				URL url = new URL("http://45.64.105.199/LDSWebService/api/AndroidSearch/Put");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("PUT");
				
				conn.setRequestProperty("authorization", Globals.Authorization );
				conn.setRequestProperty("Thumbprint", Globals.Thumbprint );
				
				
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setConnectTimeout(15000);
				Gson gson = new Gson();
				String JSONString = gson.toJson(LegalDelivery);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
				bw.write(JSONString);
				bw.flush();
				bw.close();
				conn.connect();
				responseCode = conn.getResponseCode();
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
					output = sb.toString();		
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return Integer.toString(responseCode);
		}
	
		@Override
		protected void onPostExecute(String result) {
			
			if (result.equalsIgnoreCase("200") ) {
				ListView listView = getListView();
				SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

				for (int i = 0; i < checkedItems.size(); i++) {
					if (checkedItems.valueAt(i)) 
					{
						LegalDeliveryMessage ldm = (LegalDeliveryMessage)
								listView.getAdapter().getItem(checkedItems.keyAt(i));
						System.out.println(ldm);
						String rowId = ldm.getLegalDeliveryID();
						dbHelper.updateLD_UploadFlag(rowId, 1);
					}
				}
				 Toast.makeText(getActivity(), "Uploaded data successfully.", Toast.LENGTH_LONG).show();		
				
			
			} else
			{ 
				ListView listView = getListView();
			    SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

			   for (int i = 0; i < checkedItems.size(); i++) {
				 if (checkedItems.valueAt(i)) 
				{
					LegalDeliveryMessage ldm = (LegalDeliveryMessage)
							listView.getAdapter().getItem(checkedItems.keyAt(i));
					System.out.println(ldm);
					String rowId = ldm.getLegalDeliveryID();
					dbHelper.updateLD_UploadFlag(rowId, 0);
				
				}
			}
				
				Toast.makeText(getActivity(), "Failed to upload data, Please try after some time.", Toast.LENGTH_LONG).show();
				
				
			}
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			
		}
		
		
		
		/*@Override
		protected void onPostExecute(String result) {
			
			if (result.equalsIgnoreCase("200") ) {
				ListView listView = getListView();
				SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

				for (int i = 0; i < checkedItems.size(); i++) {
					if (checkedItems.valueAt(i)) 
					{
						LegalDeliveryMessage ldm = (LegalDeliveryMessage)
								listView.getAdapter().getItem(checkedItems.keyAt(i));
						System.out.println(ldm);
						String rowId = ldm.getLegalDeliveryID();
						dbHelper.updateLD_UploadFlag(rowId, 1);
					}
				}
				Toast.makeText(getActivity(), "Uploaded data successfully.", Toast.LENGTH_LONG).show();		
				
				
			
			} else
			{
				
				Toast.makeText(getActivity(), "Failed to upload data, Please try after some time.", Toast.LENGTH_LONG).show();
				
				
			}
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
			
		}*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
					
	}
	
}
