package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import com.aetherti.legaldelivery.Map.LDLocation;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditCol0Fragment extends Fragment{
	AuditLogReporter alreport;
	LinearLayout ConsoleLayout;
	static TextView col0edittextview2;// Last service Status///might be 1st attempt/2nd attempt / completed!
	public static TextView col0editedittextview1;// Trip Time Calculation...Sudheer...
	Button firstbutton;
	LDLocation ldLocation = new LDLocation();
	SharedPreferences sharedPrefs;
	TextView previousFirstAttemptTime;
	TextView previousSecondAttemptTime;
	public static TextView suggestedFirstAttemptTime;
	public static TextView suggestedSecondAttemptTime;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.ld_edit_col0_fragment, container,false);
		alreport = new AuditLogReporter();
		ConsoleLayout = (LinearLayout) V.findViewById(R.id.ConsoleLayout);
		col0edittextview2 = (TextView) V.findViewById(R.id.col0edittextView2);
		col0editedittextview1 = (TextView) V.findViewById(R.id.col0editedittextview1);
		firstbutton = (Button) V.findViewById(R.id.firstadd);
		previousFirstAttemptTime = (TextView)V.findViewById(R.id.previousFirstAttemptTime);
		previousSecondAttemptTime = (TextView)V.findViewById(R.id.previousSecondAttemptTime);
		suggestedFirstAttemptTime = (TextView)V.findViewById(R.id.firstAttemptSuggestedTime);
		suggestedSecondAttemptTime = (TextView)V.findViewById(R.id.secondAttemptSuggestedTime);
		String previousFirstAttmptTime = LegalDeliveryActivity.getPreviousFirstAttemptTime();

		if(previousFirstAttmptTime.length() > 0){
			previousFirstAttemptTime.setText("1st: "+previousFirstAttmptTime.substring(0, 5));
			
		}
		String previousSecondAttmptTime = LegalDeliveryActivity.getPreviousSecondAttemptTime();
		
		if(previousSecondAttmptTime.length() > 0){
			previousSecondAttemptTime.setText("2nd: "+previousSecondAttmptTime.substring(0, 5));
		}
		
		/*firstbutton.setOnClickListener(this);*/
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String[] add = LegalDeliveryActivity.getPreviousAttemptAddress().split(",");
		if(add.length > 2){
			col0edittextview2.setText(add[0]+" ,"+add[1]);
		}else{
			col0edittextview2.setText("");
		}
		if (sharedPrefs.getBoolean("show_console", true)){
			alreport.reportAudit("Show Console? true", getActivity());
			boolean cursorview_flag = sharedPrefs.getBoolean("show_console",true);
			ShowConsoleView(cursorview_flag);
		} else {
			alreport.reportAudit("Show Console? false", getActivity());
			ShowConsoleView(false);
		}
		GetLastServiceDetails();// Used for demo //this method is under
		
		return V;
	}
	/**
	 * @param consoleview_flag
	 */
	private void ShowConsoleView(boolean consoleview_flag) {
		if (consoleview_flag){
			ConsoleLayout.setVisibility(View.VISIBLE);
		}else{
			ConsoleLayout.setVisibility(View.GONE);
		}
	}

	private void GetLastServiceDetails() {
		
	}
	
	public void SaveService() {
		try {
			ldLocation.getLocation(getActivity(), locationResultBeforeSave);
			Log.i("LDLOCATION:", ldLocation.locationResult.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LDLocation.LocationResult locationResultBeforeSave = new LDLocation.LocationResult() {
		@Override
		public void gotLocation(final Location location) {
			EditActivity editActivity = (EditActivity) getActivity();
			editActivity.SaveRecord(location);
		};
	};
	
	/*@SuppressWarnings("unused")
	@Override
	public void onClick(View arg0) {
		//EditCol2Fragment editCol2Fragment=(EditCol2Fragment)getFragmentManager().findFragmentById(R.id.EditCol2FragmentID);
		EditCol1Fragment editCol1Fragment=(EditCol1Fragment)getFragmentManager().findFragmentById(R.id.EditCol1FragmentID);
		EditCol0Fragment editCol0Fragment=(EditCol0Fragment)getFragmentManager().findFragmentById(R.id.EditCol0FragmentID);
			
		
				Calendar cal = Calendar.getInstance();
				int gpsYear = cal.get(Calendar.YEAR);
				int gpsMonth = cal.get(Calendar.MONTH) + 1;
				int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
				int gpsSec = cal.get(Calendar.SECOND);
				int gpsMsec = cal.get(Calendar.MILLISECOND);
				String strGpsTime1 = LegalDeliveryActivity.getPreviousFirstAttemptTime(); 
				if((strGpsTime1.equalsIgnoreCase("[Not Set]"))){
					strGpsTime1="";
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
				TripTime = editCol0Fragment.col0editedittextview1.getText().toString();
				if (TripTime.equals("wait...") || TripTime.equals("")||TripTime.length()<1){
					TripTime="";
				}
				if (TripTime != null && TripTime != "" && TripTime.length() > 0) {
					EstimatedHour = Integer.parseInt(TripTime) / 60; //convert to hrs
					EstimatedMin = Integer.parseInt(TripTime) % 60;  //call this minutes
				}
				int mMin = gpsmin + EstimatedMin;
				while(mMin >= 60){
					mMin = (mMin % 60);
					EstimatedHour += 1;
				}
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
					editCol1Fragment.SetGpsTime2(EststrGpsTime2);
					editCol1Fragment.setMobileTime2(mobileTime2);
				}else if(EditActivity.getIsThirdAttemptCompleted() == false){
					String EststrGpsTime3 = String.format("%02d:%02d:%2d:%3d", gpshour + EstimatedHour, mMin,gpsSec,gpsMsec);
					Calendar cal1 = Calendar.getInstance();
					int mobileSec = cal1.get(Calendar.SECOND);
					int mobileMsec = cal1.get(Calendar.MILLISECOND) + 25;
					String mobileTime3 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,mobileSec,mobileMsec);
					editCol1Fragment.SetGpsTime3(EststrGpsTime3);
					editCol1Fragment.setMobileTime3(mobileTime3);
				}

			if(editCol2Fragment.GetSecondAttempt() == true){
				Calendar cal = Calendar.getInstance();
				int gpsYear = cal.get(Calendar.YEAR);
				int gpsMonth = cal.get(Calendar.MONTH) + 1;
				int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
				int gpsNoon = cal.get(Calendar.AM_PM);
				int gpsSec=cal.get(Calendar.SECOND);
				int gpsMsec=cal.get(Calendar.MILLISECOND);
				String strGpsTime2 = LegalDeliveryActivity.getPreviousAttemptTime(); 
				int gpshour,gpsmin;
				if(strGpsTime2.equalsIgnoreCase("[Not Set]")){
					strGpsTime2 = "";
				}
				if(strGpsTime2.length() > 0){
					gpshour = Integer.parseInt(strGpsTime2.substring(0, 2));
					gpsmin = Integer.parseInt(strGpsTime2.substring(3, 5));
				}else{
					String strGpsTime12 = editCol0Fragment.col0secondtext.getText().toString();
					gpshour = Integer.parseInt(strGpsTime12.substring(0, 2));
					gpsmin = Integer.parseInt(strGpsTime12.substring(3, 5));
				}
				String TripTime = editCol0Fragment.secondeditattempt.getText().toString();
				int EstimatedHour = 0,EstimatedMin=0;
				if (TripTime.equals("wait...") || TripTime.equals("") || TripTime.length() < 1){
					TripTime = "";
				}
				if (TripTime != null && TripTime != "" && TripTime.length() > 0) {
					EstimatedHour = Integer.parseInt(TripTime) / 60; //convert to hrs
					EstimatedMin = Integer.parseInt(TripTime) % 60;  //call this minutes
				}
				int mMin = (gpsmin + EstimatedMin);
				while(mMin >= 60){
					mMin = (mMin % 60);
					EstimatedHour += 1;
				}
				String EststrGpsTime2 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,gpsSec,gpsMsec );
				Calendar cal1 = Calendar.getInstance();
				int mobileSec = cal1.get(Calendar.SECOND);
				int mobileMsec = cal1.get(Calendar.MILLISECOND) + 30;
				String mobileTime2 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,mobileSec,mobileMsec);
				editCol1Fragment.SetGpsTime2(EststrGpsTime2);
				editCol1Fragment.setMobileTime2(mobileTime2);
			}
			
			if(editCol2Fragment.GetThirdAttempt() == true){
				Calendar cal = Calendar.getInstance();
				int gpsYear = cal.get(Calendar.YEAR);
				int gpsMonth = cal.get(Calendar.MONTH) + 1;
				int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
				int gpsNoon = cal.get(Calendar.AM_PM);
				int gpsSec=cal.get(Calendar.SECOND);
				int gpsMsec=cal.get(Calendar.MILLISECOND);
				String strGpsTime3 = LegalDeliveryActivity.getPreviousAttemptTime();  
				int gpshour , gpsmin;
				if(strGpsTime3.equalsIgnoreCase("[Not Set]")){
					strGpsTime3 = "";
				}
				if(strGpsTime3.length()>0){
					gpshour = Integer.parseInt(strGpsTime3.substring(0, 2));
					gpsmin = Integer.parseInt(strGpsTime3.substring(3, 5));
				}else{
					String strGpsTime12 =editCol0Fragment.col0thirdtext.getText().toString();
					gpshour = Integer.parseInt(strGpsTime12.substring(0, 2));
					gpsmin = Integer.parseInt(strGpsTime12.substring(3, 5));
				}
				String TripTime = editCol0Fragment.thirdeditattempt.getText().toString();
				int EstimatedHour = 0,EstimatedMin=0;
				if (TripTime.equals("wait...") || TripTime.equals("") || TripTime.length() < 1){
					TripTime = "";
				}
				if (TripTime != null && TripTime != "" && TripTime.length() > 0) {
					EstimatedHour = Integer.parseInt(TripTime) / 60; //convert to hrs
					EstimatedMin = Integer.parseInt(TripTime) % 60;  //call this minutes
				}
				int mMin = (gpsmin + EstimatedMin);
				while(mMin >= 60){
					mMin = (mMin % 60);
					EstimatedHour += 1;
				}
				String EststrGpsTime3 = String.format("%02d:%02d:%2d:%3d", gpshour + EstimatedHour, mMin,gpsSec,gpsMsec);
				Calendar cal1 = Calendar.getInstance();
				int mobileSec = cal1.get(Calendar.SECOND);
				int mobileMsec = cal1.get(Calendar.MILLISECOND) + 25;
				String mobileTime3 = String.format("%02d:%02d:%02d:%03d", gpshour + EstimatedHour, mMin,mobileSec,mobileMsec);
				editCol1Fragment.SetGpsTime3(EststrGpsTime3);
				editCol1Fragment.setMobileTime3(mobileTime3);
			}
	}*/
}
