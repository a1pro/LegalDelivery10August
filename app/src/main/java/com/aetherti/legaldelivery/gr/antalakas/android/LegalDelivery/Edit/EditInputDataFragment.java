package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ManupulateFile;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LocationFrmAddress.CalculateTimeandDistance;

public class EditInputDataFragment extends Fragment {

	TextView jobNumTextView;
	TextView textView_ServerLicNum;
	TextView serviceTypeTextView;
	// TextView ltserviceTypeTextView;
	// TextView tvltserviceType;
	// TextView stndserviceTypeTextView;
	// TextView tvstndserviceType;
	TextView casepaperType;
	TextView fullnameTextView;
	TextView addressTextView;
	TextView aptTextView;
	TextView cityTextView;
	TextView stateTextView;
	TextView zipTextView;
	TextView textView_BusinessName;
	TextView BusinessName_TextView;
	LinearLayout linearLayout_BusinessName;
	LinearLayout linearLayout_fullName;
	TextView textView_fullname;
	TextView textView_STNDfullname;
	TextView edit_LicNum; 
	public Object textView_LicNum;
	private static String serviceType = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View V = inflater.inflate(R.layout.ld_edit_inputdata_fragment,
				container, false);

		jobNumTextView = (TextView) V.findViewById(R.id.edit_JOBNum);
		serviceTypeTextView = (TextView) V
				.findViewById(R.id.edit_ServiceType_TextView);
		// ltserviceTypeTextView =
		// (TextView)V.findViewById(R.id.edit_LTServiceType_TextView);
		// tvltserviceType =
		// (TextView)V.findViewById(R.id.TextView_LTServiceType);
		// stndserviceTypeTextView =
		// (TextView)V.findViewById(R.id.edit_STNDServiceType_TextView);
		// tvstndserviceType =
		// (TextView)V.findViewById(R.id.TextView_STNDServiceType);
		casepaperType = (TextView) V.findViewById(R.id.edit_CasePaperType_TextView);
		fullnameTextView = (TextView) V.findViewById(R.id.edit_FullName_TextView);
		addressTextView = (TextView) V.findViewById(R.id.edit_Address_TextView);
		aptTextView = (TextView) V.findViewById(R.id.edit_Apt_TextView);
		cityTextView = (TextView) V.findViewById(R.id.edit_City_TextView);
		stateTextView = (TextView) V.findViewById(R.id.edit_State_TextView);
		zipTextView = (TextView) V.findViewById(R.id.edit_Zip_TextView);

		// business name
		textView_BusinessName = (TextView) V.findViewById(R.id.textView_BusinessName);
		BusinessName_TextView = (TextView) V.findViewById(R.id.edit_BusinessName_TextView);
		linearLayout_BusinessName = (LinearLayout) V.findViewById(R.id.linearLayout_BusinessName);

		textView_fullname = (TextView) V.findViewById(R.id.textView_fullname);
		textView_STNDfullname = (TextView) V.findViewById(R.id.textView_STNDfullname);
		textView_ServerLicNum = (TextView) V.findViewById(R.id.edit_ServerLicNum);
		linearLayout_fullName = (LinearLayout) V.findViewById(R.id.linearLayout_fullName);
		edit_LicNum = (TextView) V.findViewById(R.id.edit_LicNum);
		populateFields();
		return V;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(!(LegalDeliveryActivity.getPreviousAttemptAddress().equalsIgnoreCase(""))){
			setTripTimeAddress();
		}
		
	}
	
	public  void setTripTimeAddress(){
		if(LegalDeliveryActivity.getPreviousAttemptAddress() == ""){
			LegalDeliveryActivity.setPreviousAttemptAddress(EditActivity.getCurrentAddress());
		}else{
			String dest = EditActivity.getCurrentAddress();
			if(dest.contains(" ")){
				dest = dest.replace(" ", "");
			}
			new CalcTimeRequiredTaskfromAddress().execute(dest);
		}
		String s0 =LegalDeliveryActivity.getPreviousAttemptAddress();
		System.out.print(s0);
		
		
	}
	
	class CalcTimeRequiredTaskfromAddress extends AsyncTask<String, String, String> {
		// This is point
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected String doInBackground(String... params) {
			final CalculateTimeandDistance calTD = new CalculateTimeandDistance();
			calTD.getDistanceInfoBySourceAddress(LegalDeliveryActivity.getPreviousAttemptAddress(),params[0]);
			return calTD.getTime();
		}

		@Override
		protected void onPostExecute(String result) {
			
			if(!(result.equalsIgnoreCase("0")))
			{
				EditCol0Fragment.col0editedittextview1.setText(result);
				EditCol0Fragment.col0editedittextview1.setFocusable(true);
			}
			
			String strGpsTime1 = LegalDeliveryActivity.getPreviousFirstAttemptTime();
			if(!(strGpsTime1.equalsIgnoreCase(""))){
				String requiredTime1 = calculateRequiredTime(strGpsTime1,result);
				EditCol0Fragment.suggestedFirstAttemptTime.setText("1st: "+requiredTime1);
			}
			String secondTime = LegalDeliveryActivity.getPreviousSecondAttemptTime();
			if(!(secondTime.equalsIgnoreCase(""))){
				String requiredTime2 = calculateRequiredTime(secondTime,result);
				EditCol0Fragment.suggestedSecondAttemptTime.setText("2nd: "+requiredTime2);
			}
			
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			// super.onProgressUpdate(values);
		}
	}
	
	public synchronized String calculateRequiredTime(String strGpsTime1,String result){
		
	String requiredTime ="";
	Calendar cal = Calendar.getInstance();
	int gpsSec = cal.get(Calendar.SECOND);
	int gpsMsec = cal.get(Calendar.MILLISECOND);
	
		if(strGpsTime1.length() > 0){
			if((strGpsTime1.equalsIgnoreCase("[Not Set]"))){
				strGpsTime1 = "";
			}
			int gpshour,gpsmin;
			if(strGpsTime1.length() > 0){
				gpshour= Integer.parseInt(strGpsTime1.substring(0, 2));
				gpsmin= Integer.parseInt(strGpsTime1.substring(3, 5));
			}else{ 
				String strGpsTime11 = LegalDeliveryActivity.getPreviousFirstAttemptTime();
				gpshour= Integer.parseInt(strGpsTime11.substring(0, 2));
				gpsmin= Integer.parseInt(strGpsTime11.substring(3, 5));
			}
			String TripTime;
			int EstimatedHour = 0,EstimatedMin=0;
			TripTime = result;
			if (TripTime.equals("wait...") || TripTime.equals("")||TripTime.length()<1){
				TripTime="";
			}
			if (TripTime != "" && TripTime.length() > 0) {
				EstimatedHour = Integer.parseInt(TripTime) / 60; //convert to hrs
				EstimatedMin = Integer.parseInt(TripTime) % 60;  //call this minutes
			}
			int mMin = gpsmin + EstimatedMin;
			while(mMin >= 60){
				mMin = (mMin % 60);
				EstimatedHour += 1;
			}
			requiredTime = String.format("%02d:%02d", gpshour + EstimatedHour, mMin);
			/*EditCol1Fragment editCol1Fragment=(EditCol1Fragment)getFragmentManager().findFragmentById(R.id.EditCol1FragmentID);
			if(EditActivity.getIsFirstAttemptCompleted() == false) {
				String EststrGpsTime1 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,gpsSec,gpsMsec);
				Calendar cal1 = Calendar.getInstance();
				int mobileSec = cal1.get(Calendar.SECOND);
				int mobileMsec = cal1.get(Calendar.MILLISECOND) + 20;
				String mobileTime1 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,mobileSec,mobileMsec);
				editCol1Fragment.SetGpsTime1(EststrGpsTime1);
				editCol1Fragment.setMobileTime1(mobileTime1);
			}else if(EditActivity.getIsSecondAttemptCompleted() == false){
				String EststrGpsTime2 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,gpsSec,gpsMsec );
				Calendar cal1 = Calendar.getInstance();
				int mobileSec = cal1.get(Calendar.SECOND);
				int mobileMsec = cal1.get(Calendar.MILLISECOND) + 30;
				String mobileTime2 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,mobileSec,mobileMsec);
				//editCol1Fragment.SetGpsTime2(EststrGpsTime2);
				//editCol1Fragment.setMobileTime2(mobileTime2);
			}else if(EditActivity.getIsThirdAttemptCompleted() == false){
				String EststrGpsTime3 = String.format("%02d:%02d:%2d:%3d", gpshour + EstimatedHour, mMin,gpsSec,gpsMsec);
				Calendar cal1 = Calendar.getInstance();
				int mobileSec = cal1.get(Calendar.SECOND);
				int mobileMsec = cal1.get(Calendar.MILLISECOND) + 25;
				String mobileTime3 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,mobileSec,mobileMsec);
				//editCol1Fragment.SetGpsTime3(EststrGpsTime3);
				//editCol1Fragment.setMobileTime3(mobileTime3);
			}*/
		}
		return requiredTime;
	}

	/**
	 * @return Returns the service type:  Standard | L&T Commercial | L&T Residential
	 */
	public String GetServiceType() {
		return serviceTypeTextView.getText().toString();
	}

	public String GetAddress() {
		return addressTextView.getText().toString();
	}

	public String GetCity() {
		return cityTextView.getText().toString();
	}

	public String GetZip() {
		return zipTextView.getText().toString();
	}
	
	public String getFullName(){
		return fullnameTextView.getText().toString();
	}
	
	public String getBusinessName(){
		return BusinessName_TextView.getText().toString();
	}

	public String getApt(){
		return aptTextView.getText().toString();
	}
	
	public String getState(){
		return stateTextView.getText().toString();
	}
	private void populateFields() {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String strJobNum = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_JobNo));
		String strServerLicNum = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServerLicenceNo));
		String strServiceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceType));
		setServiceType(strServiceType);
		String strLTServiceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTServiceType));
		String strFullname = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTFullname));
		String strBusinessname = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTBIZNAME));
		String strAddress = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTAddress));
		String strApt = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTApt));
		String strCity = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTCity));
		String strState = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTState));
		String strZip = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTZip));
		String StndstrFullname = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_FullName));
		String StndstrAddress = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_Address));
		String StndstrApt = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_Apt));
		String StndstrCity = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_City));
		String StndstrState = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_State));
		String StndstrZip = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_Zip));
		String StndServiceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_STND_ServiceType));
		// String strJobNo = cursor.getString(cursor
		// .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_JobNo));
		SharedPreferences Preference_LastService = getActivity().getSharedPreferences("LastServiceStatus", 0);
		Editor editor = Preference_LastService.edit(); // Used When we Want to
		editor.putString("LastDetailName", strFullname);
		editor.putString("LastDetaiAddress", " " + strAddress + " " + strCity + " " + strState);
		if (editor.commit())
			Log.i("SharedPrefChangedFrom:", getClass() + "Address Changed");
		try {
			if (strJobNum != null)
				jobNumTextView.setText(strJobNum);
			else
				jobNumTextView.setText("Not Available");

			if (serviceTypeTextView != null)
				serviceTypeTextView.setText(strServiceType);
			else
				serviceTypeTextView.setText("Not Available");

			if (textView_ServerLicNum != null)
				textView_ServerLicNum.setText(strServerLicNum);
			else
				textView_ServerLicNum.setText("Not Available");

			if (strServiceType != null) {
				if (strServiceType.equals("Standard")) {
					fullnameTextView.setText(StndstrFullname);

					BusinessName_TextView.setVisibility(View.GONE);
					textView_BusinessName.setVisibility(View.GONE);
					linearLayout_BusinessName.setVisibility(View.GONE);
					textView_fullname.setVisibility(View.GONE);
					textView_STNDfullname.setVisibility(View.VISIBLE);
					linearLayout_fullName.setVisibility(View.VISIBLE);

					casepaperType.setText(StndServiceType);
					// stndserviceTypeTextView.setText(StndServiceType);
					addressTextView.setText(StndstrAddress);
					aptTextView.setText(StndstrApt);
					cityTextView.setText(StndstrCity);
					stateTextView.setText(StndstrState);
					zipTextView.setText(StndstrZip);
				}

				else if (strServiceType.equals("L&T Commercial")) {
					// ltserviceTypeTextView.setText(strLTServiceType);
					casepaperType.setText(strLTServiceType);
					fullnameTextView.setText(strFullname);
					BusinessName_TextView.setText(strBusinessname);

					BusinessName_TextView.setVisibility(View.VISIBLE);
					textView_BusinessName.setVisibility(View.VISIBLE);
					linearLayout_BusinessName.setVisibility(View.VISIBLE);
					textView_fullname.setVisibility(View.GONE);
					fullnameTextView.setVisibility(View.GONE);
					linearLayout_fullName.setVisibility(View.GONE);
					textView_STNDfullname.setVisibility(View.GONE);

					addressTextView.setText(strAddress);
					aptTextView.setText(strApt);
					cityTextView.setText(strCity);
					stateTextView.setText(strState);
					zipTextView.setText(strZip);
				}

				else if (strServiceType.equals("L&T Residential")) {
					// ltserviceTypeTextView.setText(strLTServiceType);
					casepaperType.setText(strLTServiceType);
					fullnameTextView.setText(strFullname);

					BusinessName_TextView.setVisibility(View.GONE);
					textView_BusinessName.setVisibility(View.GONE);
					linearLayout_BusinessName.setVisibility(View.GONE);
					textView_fullname.setVisibility(View.VISIBLE);
					linearLayout_fullName.setVisibility(View.VISIBLE);
					textView_STNDfullname.setVisibility(View.GONE);

					addressTextView.setText(strAddress);
					aptTextView.setText(strApt);
					cityTextView.setText(strCity);
					stateTextView.setText(strState);
					zipTextView.setText(strZip);
				}
			} else {
				casepaperType.setText("Not Available");
				fullnameTextView.setText("Not Available");

				BusinessName_TextView.setVisibility(View.GONE);
				textView_BusinessName.setVisibility(View.GONE);
				linearLayout_BusinessName.setVisibility(View.GONE);
				//
				textView_fullname.setVisibility(View.GONE);
				linearLayout_fullName.setVisibility(View.GONE);
				textView_STNDfullname.setVisibility(View.GONE);

				addressTextView.setText("Not Available");
				aptTextView.setText("Not Available");
				cityTextView.setText("Not Available");
				stateTextView.setText("Not Available");
				zipTextView.setText("Not Available");
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			ManupulateFile manFile = new ManupulateFile();
			manFile.WriteToFile(st.toString(), getActivity());
			ErroReportingInBackground errreprt = new ErroReportingInBackground(
					getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!",
					Toast.LENGTH_SHORT).show();

		}
	}

	public static String getServiceType() {
		return serviceType;
	}

	public static void setServiceType(String serviceType) {
		EditInputDataFragment.serviceType = serviceType;
	}
	
	
}