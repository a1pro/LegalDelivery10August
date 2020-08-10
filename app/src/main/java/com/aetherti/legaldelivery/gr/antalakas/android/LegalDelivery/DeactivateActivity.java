package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aetherti.legaldelivery.R;

public class DeactivateActivity extends Activity {

	Button Loginbtn;
	EditText login_password_edit, login_username_edit;
	SharedPreferences sharedpref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deactivate_blue_screen);
		
		
		
		
		
		
		
		
		
		
		
		
	/*	BackHandler.removeEventListener('hardwareBackPress', this.handleBackButtonClick);
		
		
		handleBackButtonClick() {
			 
			 
			  
			    this.props.navigation.goBack(null);
			    return true;
		}*/
		
		
		
		/*
		 * 
		 * https://developer.android.com/training/basics/fragments/creating
		 * sharedpref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		if (sharedpref != null && !sharedpref.contains(Globals.RegisterPref)) {
			if (!sharedpref.getBoolean(Globals.DeviceRegisterPref, false)) {
				Toast.makeText(getApplicationContext(),"Not registerd to server!", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplication(),
						RegisterDeviceActivity.class));
			}
		}

		Loginbtn = (Button) findViewById(R.id.Loginbtn);
		login_password_edit = (EditText) findViewById(R.id.login_password_edit);
		login_username_edit = (EditText) findViewById(R.id.login_username_edit);

		Loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (login_username_edit.getText().toString().equals("ProcessS")
						&& login_password_edit.getText().toString().equals("welcome1!")) {
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(login_password_edit.getWindowToken(), 0);
					Toast.makeText(getApplication(), "Login Successful!",Toast.LENGTH_SHORT).show();

					startActivity(new Intent(getApplicationContext(),LegalDeliveryActivity.class));
					finish();
				} else
					Toast.makeText(getApplication(),"default username : ProcessS & password: welcome1!",Toast.LENGTH_SHORT).show();
			}
		});*/
	
	
	
	
	
	
	
	}
}
