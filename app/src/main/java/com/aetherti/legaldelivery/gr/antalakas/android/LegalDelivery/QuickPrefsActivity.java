package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit.EditCol1Fragment;

public class QuickPrefsActivity extends PreferenceActivity {
	ListPreference lp;// = (ListPreference) findPreference("Selected_days");
	static EditCol1Fragment editCol1Fragment;
	boolean boolflag;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			android.content.SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", android.content.Context.MODE_PRIVATE);
		        Globals.ProductCode=pref.getString("product_code", null);
			if(Globals.ProductCode.equalsIgnoreCase("C")) {
				addPreferencesFromResource(R.xml.preferences);
			} else {
				addPreferencesFromResource(R.xml.preferencesb);
			}
			
			editCol1Fragment = (EditCol1Fragment) getFragmentManager().findFragmentById(R.id.EditCol1FragmentID);
			//CheckBoxPreference chkpref = (CheckBoxPreference) getPreferenceManager().findPreference("date_config");
			lp = (ListPreference) findPreference("Selected_days");
			///////////////////////////ListView Preferences//////////////////////////////
			lp.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference,
						Object newValue) {
					if (Check_Holiday_Or_Not(newValue.toString())) {
						return !boolflag;
					}
					else
						return true;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage("It seems one of the day is holiday!...")
				.setPositiveButton("Choose another days!",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								boolflag = false;
								}
						});
				

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return alertDialog;
	}

	@SuppressWarnings("deprecation")
	private boolean Check_Holiday_Or_Not(CharSequence entry) {

		String ar[] = String.valueOf(entry).split("-");
		String first_day = get1stDateofweekdays(ar[0].trim());
		//String second_day = get2ndDateofweekdays(ar[1].trim(),ar[0].trim());
		String second_day = get2ndDateofweekdays(ar[1].trim());
		for (String st : Globals.Holiday_date) {
			if (st.equals(first_day) || st.equals(second_day)) {
				showDialog(1);
				boolflag=true;
			//	Toast.makeText(this,"Encountered Holiday!",Toast.LENGTH_SHORT).show();
				System.out.println("before send: "+boolflag);
				return boolflag;
			}
		}
		//Toast.makeText(this, " Not encountered holiday!", Toast.LENGTH_SHORT).show();
		return boolflag;

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 0, 0, "Show current settings");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:	startActivity(new Intent(this, ShowSettingsActivity.class));
			return true;
		}
		return false;
	}
	
	public static String get1stDateofweekdays(String weekday) {

		Calendar caldemo = Calendar.getInstance();
		caldemo.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

		while (!sdf.format(caldemo.getTime()).equalsIgnoreCase(weekday)) {
			caldemo.add(Calendar.DAY_OF_MONTH, 1);
		}
		String resultdate = String.format("%02d", (caldemo.get(Calendar.MONTH) + 1))+"-"+
							String.format("%02d", caldemo.get(Calendar.DATE))+ "-"+
							caldemo.get(Calendar.YEAR);
		return resultdate;
	}

	public static String get2ndDateofweekdays(String weekday) {

		Calendar caldemo = Calendar.getInstance();
		caldemo.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		do {
			caldemo.add(Calendar.DAY_OF_MONTH, 1);
		} while (!sdf.format(caldemo.getTime()).equalsIgnoreCase(weekday));

		String resultdate = String.format("%02d", (caldemo.get(Calendar.MONTH) + 1))+"-"+
							String.format("%02d", caldemo.get(Calendar.DATE))+ "-"+
							caldemo.get(Calendar.YEAR);
		return resultdate;

	}
	
	public static String get3rdDateofweekdays(String weekday) {

		Calendar caldemo = Calendar.getInstance();
		caldemo.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		do {
			caldemo.add(Calendar.DAY_OF_MONTH, 2);
		} while (!sdf.format(caldemo.getTime()).equalsIgnoreCase(weekday));

		String resultdate = String.format("%02d", (caldemo.get(Calendar.MONTH) + 1))+"-"+
							String.format("%02d", caldemo.get(Calendar.DATE))+ "-"+
							caldemo.get(Calendar.YEAR);
		return resultdate;
	}
	
}
