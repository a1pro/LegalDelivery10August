package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.Register.RegisterDeviceActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Download.LDDownloadActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReportigInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.HolidayDownload.HolidayDownloadActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload.LDUploadActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

@SuppressLint("SdCardPath")
public class LegalDeliveryActivity extends Activity {

	// Logic for audit log//
	@SuppressLint("SdCardPath")
	File myFile = new File("/sdcard/auditLog.txt");
	FileOutputStream fout;
	OutputStreamWriter foutWriter;
	
	public TextView lastDateView = null;
	LinearLayout weekDays_layout;
	Calendar cal = Calendar.getInstance();
	LDDatabaseAdapter dbadapter;
	public int mYear = cal.get(Calendar.YEAR);
	public int mMonth = cal.get(Calendar.MONTH) + 1;
	public int mDay = cal.get(Calendar.DAY_OF_MONTH);
	Spinner weekdaySpinner;
	AuditLogReporter alreport;
	
	//////////Ashish//////////////
		private static String previousAttemptAddress = "";
		private static String previousFirstAttemptTime = "";
		private static String previousSecondAttemptTime = "";
		private static int selectedDayPairID = 0;
		private static String selectedDayPair = "";

	private static final int PERMISSION_REQUEST_CODE = 1;
	private static final int PERMISSION_REQUEST_CODE2 = 2;
		
	////////////////////////////
	//ServiceRepeatingAlarm SRA;
	//private DataUpdateReceiver dataUpdateReceiver;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		checkPermission();
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		alreport = new AuditLogReporter();
		alreport.clearFile();
		alreport.reportAudit("Starting Application:", this);
		if (Globals.IsNetworkAvailable(this)) {
			//Globals.ShowNotification(this, "Nerwork Available!");
			alreport.reportAudit("Network checking network ? available", this);
		} else {
			//Globals.ShowNotification(this,"Network Not available ! Please Check internet Connection!");
			alreport.reportAudit("Network checking network ? not availble!",
					this);
		}



		dbadapter = new LDDatabaseAdapter(this);

		if (dbadapter != null) {
			Make_array_of_Holidays(mYear, mMonth, mDay);
			Check_need_to_download();
			alreport.reportAudit("Connecting to database? Holidays counted",
					this);
		}
		
		// final Button searchButton = (Button) findViewById(R.id.btnSearch);
		// Here is my Stuff Sudheer...
		// Finding out Search Criteria selected:

		/*
		 * searchButton.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View v) { try { Cursor c =
		 * dbHelper.fetchAllLDsAllColumns();
		 * 
		 * LegalDeliveryMessage ldm = LegalDeliveryMessage.newBuilder()
		 * .setLegalDeliveryID(c.getString(0)) .setJobNo(c.getString(1))
		 * .setClient(c.getString(2)) .setServiceType(c.getString(3))
		 * 
		 * .build();
		 * 
		 * 
		 * // If row exists in local database, do not enter it again if
		 * (c.getCount() != 0) continue; } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		/*
		 * 
		 * "Client TEXT, " + "ServiceType TEXT, " + "LTServiceType TEXT, " +
		 * "CaseNo TEXT, " + "LTFullname TEXT, " + "LTBizname TEXT, " +
		 * "CorpRecipient TEXT, " + "CorpRecipientTitle TEXT, " +
		 * "LTAddress TEXT, " + "LTApt TEXT, " + "LTCity TEXT, " +
		 * "LTState TEXT, " + "LTZip TEXT, " + "GPSDate TEXT, " +
		 * "GPSTime TEXT, " + "GPSLat  REAL, " + "GPSLon  REAL, " +
		 * "ServiceResult TEXT, " + "InputDate TEXT, " + "DateOfService TEXT, "
		 * + "TimeOfService TEXT, " + "Pod TEXT, " + "DoorLock INTEGER, " +
		 * "PServ TEXT, " + "CServ TEXT, " +
		 * "IsOnDeviceEdited INTEGER NOT NULL DEFAULT 0)";
		 * 
		 * 
		 * 
		 * 
		 * } });
		 */

		/*
		 * FragmentManager fragmentManager = getFragmentManager();
		 * FragmentTransaction fragmentTransaction =
		 * fragmentManager.beginTransaction();
		 * 
		 * //---get the current display info--- WindowManager wm =
		 * getWindowManager(); Display d = wm.getDefaultDisplay();
		 * 
		 * if (d.getWidth() > d.getHeight()) { //---landscape mode---
		 * FilterFragment filterFragment = new FilterFragment(); //
		 * android.R.id.content refers to the content // view of the activity
		 * 
		 * fragmentTransaction.replace( R.id.mainLinearHorizontal,
		 * filterFragment);
		 * 
		 * FilterResultFragment filterResultFragment = new
		 * FilterResultFragment(); fragmentTransaction.add(
		 * R.id.mainLinearHorizontal, filterResultFragment);
		 * 
		 * fragmentTransaction.setTransition(FragmentTransaction.
		 * TRANSIT_FRAGMENT_OPEN); fragmentTransaction.addToBackStack(null);
		 * 
		 * } else { //---portrait mode--- FilterResultFragment
		 * filterResultFragment = new FilterResultFragment();
		 * fragmentTransaction.replace( R.id.mainLinearHorizontal,
		 * filterResultFragment);
		 * 
		 * fragmentTransaction.setTransition(FragmentTransaction.
		 * TRANSIT_FRAGMENT_OPEN); fragmentTransaction.addToBackStack(null); }
		 * 
		 * fragmentTransaction.commit();
		 */
		/*SimpleDateFormat sdfm = new SimpleDateFormat("EEEE");
		Log.i("getting system date:", sdfm.format(new Date()));
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		if (sharedPrefs.getString("Selected_days", "NONE").equals("NONE")) {
			Toast.makeText(this,
					"weekdays not set! please set before updating records!",
					Toast.LENGTH_LONG).show();
		}
*/
		/*if (!sdfm.format(new Date()).equals(
				sharedPrefs.getString("Selected_days", "NONE").split("-")[0])) {
			Log.i("Selected weekday pref:",
					sharedPrefs.getString("Selected_days", "NONE"));
			show_edit_pref_mistmatch_alert(sharedPrefs.getString(
					"Selected_days", "NONE"));

		}*/
		/*
		 * Map nmap=new HashMap<String, Object>();
		 * nmap.putAll(sharedPrefs.getAll());
		 * System.out.println(nmap.toString());
		 */
		
		
		//This is code for live status of service
			/*SRA=new ServiceRepeatingAlarm();
			dataUpdateReceiver=new DataUpdateReceiver();*/
		/*	Calendar cal1=Calendar.getInstance();
		int gpsHour = cal1.get(Calendar.HOUR);
		int gpsMinute = cal1.get(Calendar.MINUTE);
		int gpsNoon = cal1.get(Calendar.AM_PM);
		String noon="";
		if (gpsNoon == 0)
			noon = "AM";
		else if (gpsNoon == 1)
			noon = "PM";
		String strGpsTime=String.format("%02d:%02d %s",gpsHour, gpsMinute, noon);
		Log.i("System Time calculated ", strGpsTime);
		Log.i("In Milisecods:",String.valueOf(cal1.getTimeInMillis()));
		Calendar cal2=Calendar.getInstance();
		GPSTracker gpstracker =  new GPSTracker(getApplicationContext());
		
		if (gpstracker.canGetLocation()) {
			Location location = gpstracker.getLocation();
			//cal2.setTimeInMillis(location.getTime());
			Log.i("In Milisecods:",String.valueOf(cal2.getTimeInMillis()));
			int gpsHourG = cal2.get(Calendar.HOUR);
			int gpsMinuteG = cal2.get(Calendar.MINUTE);
			int gpsNoonG = cal2.get(Calendar.AM_PM);
			noon = "";
			if (gpsNoonG == 0)
				noon = "AM";
			else if (gpsNoonG == 1)
				noon = "PM";
			String strGpsTimeG = String.format("%02d:%02d %s", gpsHourG,
					gpsMinuteG, noon);
			Log.i("Real Gps Time calculated from Gps sensors!", strGpsTimeG);
		}*/
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	/*if (dataUpdateReceiver == null) 
			dataUpdateReceiver = new DataUpdateReceiver();
		IntentFilter intentFilter = new IntentFilter("SUCCESS");
		registerReceiver(dataUpdateReceiver, intentFilter);
		if(SRA!=null)
		{
			SRA.setAlarm(getApplicationContext());
		}*/
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		/*if (dataUpdateReceiver != null)
			unregisterReceiver(dataUpdateReceiver);
*/
	}

	/**
	 * Checking whether database is empty if yes transfer to download page!
	 */
	private void Check_need_to_download() {
		try {
			dbadapter.open_readable();

			if (dbadapter.count_no_of_Records() <= 0)
				show_db_null_alert();
			dbadapter.close();

		} catch (Exception e) {

		} finally {

		}

	}
	private void checkPermission() {
		Dexter.withActivity(this)
				.withPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						if (report.areAllPermissionsGranted()) {

						}
					}

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
						token.continuePermissionRequest();
					}
				}).check();

	}


	private void requestPermission() {
		if (ActivityCompat.shouldShowRequestPermissionRationale(LegalDeliveryActivity.this, Manifest.permission.READ_PHONE_STATE)) {
		} else {

			ActivityCompat.requestPermissions(LegalDeliveryActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
		}

		if(ActivityCompat.shouldShowRequestPermissionRationale(LegalDeliveryActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
		}else {
			ActivityCompat.requestPermissions(LegalDeliveryActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE2);

		}
	}

	private void Make_array_of_Holidays(int year, int month, int day) {
		try {
			dbadapter.open_readable();
			Cursor hcursor = dbadapter.fetchHolidays("2012");// String.valueOf(year));
																// //2012 is for
																// test delete
																// after test

			// /////////////////////////////////////////////////////
			alreport.reportAudit("Fetching DB of Holidays ? of year::2012",
					this);
			if (hcursor != null) {

				hcursor.moveToFirst();
				while (hcursor.moveToNext()) {
					String Holiday = hcursor.getString(hcursor
							.getColumnIndexOrThrow(dbadapter.KEY_HolidayDate));
					String HdDescription = hcursor
							.getString(hcursor
									.getColumnIndexOrThrow(dbadapter.KEY_HolidayDescription));
					Globals.Holiday_date.add(Holiday.split(" ")[0]);
					Globals.Holiday_Description.add(HdDescription);
				}

			} else {
				Toast.makeText(this,
						"Please download Holidays,for validation! ",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception etexception) {
			etexception.printStackTrace();
		} finally {
			dbadapter.close();
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST_CODE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				} else {

				}
			case PERMISSION_REQUEST_CODE2:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				} else {

				}
				break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int menuID = item.getItemId();
		try {
			switch (menuID) {
			case (R.id.download):
				Intent ldDownload = new Intent(this, LDDownloadActivity.class);
				startActivityForResult(ldDownload, 0);
				return true;
			case (R.id.sync):
				Intent ldUpload = new Intent(this, LDUploadActivity.class);
				startActivityForResult(ldUpload, 0);
				return true;
			case (R.id.register):
				Intent registerIntentRegister = new Intent(this,RegisterDeviceActivity.class);
				startActivityForResult(registerIntentRegister, 0);
				return true;
			case (R.id.downloadHolidays):
				Intent hdDownloadHolidays = new Intent(this,HolidayDownloadActivity.class);
				startActivityForResult(hdDownloadHolidays, 0);
				return true;
			case (R.id.editPreferences):
				Intent quickPrefsActivity = new Intent(this,QuickPrefsActivity.class);
				startActivityForResult(quickPrefsActivity, 0);
				return true;
			case (R.id.exit):
				Intent i = new Intent();
				i.putExtra("Ok", "0");
				setResult(0, i);
				finish();
			default:
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Globals.DATE_DIALOG_ID:

			// /////////////////////////////////////////////////////
			alreport.reportAudit("Creating Dialog   " + mYear + ":" + mMonth
					+ ":" + mDay, this);
			// /////////////////////////////////////////////////////

			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);

		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

			if (lastDateView != null) {
				lastDateView.setText(String.format("%02d-%02d-%04d",monthOfYear + 1, dayOfMonth, year));
				lastDateView = null;
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		alreport.reportAudit("Exiting Application!", this);
		try {
			setPreviousAttemptAddress("");
			setPreviousFirstAttemptTime("");
			setPreviousSecondAttemptTime("");
			AuditLogReportigInBackground auditlogreportingbkprocess = new AuditLogReportigInBackground(this);
			auditlogreportingbkprocess.execute("/sdcard/auditlog.txt");
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "Audit Log not reported!", Toast.LENGTH_LONG).show();
		}
		@SuppressWarnings("static-access")
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(this.NOTIFICATION_SERVICE);
		notificationManager.cancel(Globals.NOTY_ID);
		 
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent();
			i.putExtra("Ok", "0");
			setResult(0, i);
			show_exit_alert();
			// finish();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		alreport.reportAudit("Going to previous activity", this);
		show_exit_alert();
	}

	private void show_exit_alert() {
		AlertDialog.Builder alert_for_exit = new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_action_warning)
				.setTitle("Exit Confirmation")
				.setMessage("Do you really want to leave?")
				.setCancelable(false)
				.setNegativeButton("No!", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).setPositiveButton("Sure !", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						setPreviousAttemptAddress("");
						setPreviousFirstAttemptTime("");
						finish();
					}
				});
		AlertDialog alertexit = alert_for_exit.create();
		alertexit.show();
	}

	/*private void show_edit_pref_mistmatch_alert(String day_pref) {
		AlertDialog.Builder alertDialogBuilderforpref = new AlertDialog.Builder(
				this);
		alertDialogBuilderforpref
				.setMessage("Current day not match with selected weekday preferences-"
						+ day_pref);
		alertDialogBuilderforpref.setPositiveButton(
				"Goto EditPreference page.",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent positveActivity = new Intent(
								getApplicationContext(),
								gr.antalakas.android.LegalDelivery.QuickPrefsActivity.class);
						startActivity(positveActivity);

					}
				});
		alertDialogBuilderforpref.setNegativeButton("SKIP", null);

		AlertDialog alertDialogforpref = alertDialogBuilderforpref.create();
		alertDialogforpref.show();
	}*/

	private void show_db_null_alert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("No record found in local database!");
		alertDialogBuilder.setPositiveButton("Goto download page.",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent positveActivity = new Intent(
								getApplicationContext(),
								com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Download.LDDownloadActivity.class);
						startActivity(positveActivity);

					}
				});
		alertDialogBuilder.setNegativeButton("SKIP", null);

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public synchronized static String getPreviousAttemptAddress() {
		return previousAttemptAddress;
	}

	public synchronized static void setPreviousAttemptAddress(String previousAttemptAddress) {
		if(previousAttemptAddress.contains(" ")){
			previousAttemptAddress = previousAttemptAddress.replace(" ", "");
		}
		LegalDeliveryActivity.previousAttemptAddress = previousAttemptAddress;
	}

	public static String getPreviousFirstAttemptTime() {
		return previousFirstAttemptTime;
	}

	public static void setPreviousFirstAttemptTime(String previousAttemptTime) {
		LegalDeliveryActivity.previousFirstAttemptTime = previousAttemptTime;
	}

	public static int getSelectedDayPairID() {
		return selectedDayPairID;
	}

	public static void setSelectedDayPairID(int i) {
		LegalDeliveryActivity.selectedDayPairID = i;
	}

	public static String getSelectedDayPair() {
		return selectedDayPair;
	}

	public static void setSelectedDayPair(String selectedDayPair) {
		LegalDeliveryActivity.selectedDayPair = selectedDayPair;
	}

	public static String getPreviousSecondAttemptTime() {
		return previousSecondAttemptTime;
	}

	public static void setPreviousSecondAttemptTime(String previousSecondAttemptTime) {
		LegalDeliveryActivity.previousSecondAttemptTime = previousSecondAttemptTime;
	}
}