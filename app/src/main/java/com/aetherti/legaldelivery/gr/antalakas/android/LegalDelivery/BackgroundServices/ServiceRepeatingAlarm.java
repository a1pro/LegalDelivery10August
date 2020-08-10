package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.BackgroundServices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ServiceRepeatingAlarm extends BroadcastReceiver {
	final public static String ONE_TIME = "onetime";
	Intent networkConnector;
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		StringBuilder msgData = new StringBuilder();
		if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
			// Make sure this intent has been sent by the one-time timer button.
			msgData.append("One time Timer : ");
		}
		Toast.makeText(context, "This is data:" + msgData, Toast.LENGTH_LONG)
				.show();
		networkConnector=new Intent(context, NetworkCheckingService.class);
		//networkConnector.putExtra("URL", "www.google.com");
		context.startService(networkConnector);
	}

	public void setAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ServiceRepeatingAlarm.class);
		intent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		// After after 5 seconds
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				1000 * 5, pi);
	}

	public void cancelAlarm(Context context) {
		Intent intent = new Intent(context, ServiceRepeatingAlarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		context.stopService(new Intent(context,NetworkCheckingService.class));
	}
}
