package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.aetherti.legaldelivery.R;

import static android.content.ContentValues.TAG;


/**
 * @author android
 *
 */
public final class Globals {

	public static String Authorization = "1.0.11.170102";
	public static String Thumbprint = "0CB20800EDDB26CC9AFC16C5B128F80347040830";
	public static int ErrorCount = 0;
	public static final int TIME_DIALOG_ID = 0;
	public static final int DATE_DIALOG_ID = 1;
	public static String last_attempt_no;

	public static final String RegisterPref = "RegisteredDevice";
	public static final String DeviceRegisterPref = "DeviceRegisterd";
	//public  final String Holiday_date[]=new String [100];
	//public  final String Holiday_Description[];
	public static final ArrayList<String> Holiday_date = new ArrayList<String>();
	public static final ArrayList<String> Holiday_Description = new ArrayList<String>();
	// Client Side Data
	//Old server ip
	//public final static String Domain = "http://198.144.36.241/LDSWeb";
	//server 2//
	public final static String Domain = "http://45.64.105.198/LDSWeb";
	//Vpn jeff server
	//public final static String Domain = "http://192.168.3.241/LDSWeb";
	//server 1//
	//public final static String Domain = "http://45.64.105.198/LDSWeb";
	//public final static String Domain = "http://192.168.101.211/LDSWeb";

	public final static String DomainPathDownload = "/DownloadServices2.aspx";
	public final static String DomainPathRegisterDevice = "/RegisterDevice.aspx";
	public final static String DomainPathUpload = "/SynchronizeServices.aspx";
	public final static String DomainHolidayPathDownload = "/DownloadHolidays.aspx";
	public final static String DomainPathDownloadServerStatus = "/DownloadServerStatus.aspx";
	//new data
	public final static String DomainPathApplock = "/AppLock.aspx";
	public final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy h:mm ");
	final static SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS");



	public static final int NOTY_ID = 10008;

	private static NotificationManager NTMngr = null;

	public static String ProductCode;

	//public static String ProductCode = "B";
	public static String GetDeviceID(Context context, ContentResolver contentResolver) {
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;


		if (tm != null) {
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = ""+ android.provider.Settings.Secure.getString(contentResolver,android.provider.Settings.Secure.ANDROID_ID);
			UUID deviceUuid = new UUID(androidId.hashCode(),((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
			return deviceUuid.toString();
		}
		return "";
	}

	public static boolean IsNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		Toast.makeText(context, "Please Switch-on Data Connection!",Toast.LENGTH_SHORT).show();
		return false;
	}

	public static void ShowNoNetworkMessage(Context context) {
		Toast.makeText(context,"Cannot connect to the service, network is not available. Please check network settings.",
				Toast.LENGTH_LONG).show();
	}

	// Method for network response called in DownloadListFragment date 6/8/2012
	public static void NoResponse_Data(Context context, String result) {
		try {
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ShowDialog(String message, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
		.setPositiveButton("OK", null);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	public static String GetSetting(String preference, Context context) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPrefs.getString(preference, "-1");
	}

	public static void ShowSettings(final Context mContext) {
		AlertDialog.Builder alert_settings = new AlertDialog.Builder(mContext);
		alert_settings.setTitle("Check your Internet connection !");
		alert_settings.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
			}
		});
		alert_settings.show();
	}

	public static void ShowNotification(Context mcontext, String msg) {
		PendingIntent pintent;// =PendingIntent.getActivity(mcontext, 0, new
		// Intent(mcontext, 
		// LegalDeliveryActivity.class), 0);
		NTMngr = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
		
		if (msg.contains("not")) {
			NTMngr.cancel(NOTY_ID);
			NTMngr.cancelAll();
			Notification mNotification = new Notification(
					R.drawable.icon_24x24_blue,
					"Legal Delivery: Notification!", System.currentTimeMillis());
			pintent = PendingIntent.getActivity(mcontext, 0, new Intent(
					Settings.ACTION_SETTINGS), 0);
			try {
				Method deprecatedMethod = mNotification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
				deprecatedMethod.invoke(mNotification, mcontext, "Network Status:", msg, pintent);
			} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				Log.i(TAG, "Method not found", e);
			}
			NTMngr.notify(NOTY_ID, mNotification);
		} else {
			NTMngr.cancel(NOTY_ID);
			NTMngr.cancelAll();
			Notification mNotification = new Notification(R.drawable.icon_32x32_blue,"Legal Delivery: Notification!", System.currentTimeMillis());
			
			pintent = PendingIntent.getActivity(mcontext, 0, new Intent(
					mcontext, LegalDeliveryActivity.class), 0);
			try {
				Method deprecatedMethod = mNotification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
				deprecatedMethod.invoke(mNotification, mcontext, "Network Status:", msg, pintent);
			} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				Log.i(TAG, "Method not found", e);
			}
			NTMngr.notify(NOTY_ID, mNotification);
		}

		
	}

	public static void DissmissErrNotification(Context errdismissContext){
		NTMngr.cancel(1024);
	}
	public static boolean isToday_is_Friday(Context fContext){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE",Locale.US);
		String today = dayFormat.format(calendar.getTime());
		if(today.equals("Friday")||today.equals("friday"))
			return true;
		else
			return false;
	}

	/**
	 * @return true if Today is Sunday else return false.
	 */
	public static boolean isToday_is_Sunday(Context sContext) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
		String weekDay = dayFormat.format(calendar.getTime());
		if (weekDay.equals("Sunday")||weekDay.equals("sunday"))
			return true;
		else
			return false;
	}

}
