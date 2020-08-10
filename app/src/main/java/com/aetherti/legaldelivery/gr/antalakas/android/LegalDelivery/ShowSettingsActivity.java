package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.aetherti.legaldelivery.R;

public class ShowSettingsActivity extends Activity {
	TextView text_show_settings_daysSelected,
			text_show_settings_SaturdayDelivery,
			text_show_settings_AnyTimeDelivery, 
			text_show_setting_showConsole;
	SharedPreferences sharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_settings_layout);
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
		text_show_settings_daysSelected = (TextView) findViewById(R.id.text_show_settings_daysSelected);
		text_show_settings_SaturdayDelivery = (TextView) findViewById(R.id.text_show_settings_SaturdayDelivery);
		text_show_settings_AnyTimeDelivery = (TextView) findViewById(R.id.text_show_settings_AnyTimeDelivery);
		text_show_setting_showConsole = (TextView) findViewById(R.id.text_show_setting_showConsole);
		text_show_settings_daysSelected.setTypeface(tf);
		text_show_settings_SaturdayDelivery.setTypeface(tf);
		text_show_settings_AnyTimeDelivery.setTypeface(tf);
		text_show_setting_showConsole.setTypeface(tf);
		getValues();
	}

	public void getValues() {
		text_show_settings_daysSelected.setText(sharedPrefs.getString(
				"Selected_days", "[not set]"));// selected days
		text_show_settings_AnyTimeDelivery.setText(sharedPrefs.getString(
				"anytime_deliver", "[not set]") == "2" ? "Yes" : "No");// Saturday
																		// deliver?
		text_show_settings_SaturdayDelivery.setText(sharedPrefs.getBoolean(
				"date_config", false) ? "Yes" : "No");
		text_show_setting_showConsole.setText(sharedPrefs.getBoolean(
				"show_console", false) ? "Yes" : "No");
	}

}
