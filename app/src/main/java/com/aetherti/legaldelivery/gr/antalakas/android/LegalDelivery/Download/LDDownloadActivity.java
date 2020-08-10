package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Download;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.aetherti.legaldelivery.R;

import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;

import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
public class LDDownloadActivity extends Activity {

	public TextView lastDateView = null;
	boolean isSaved=false;
	Calendar cal = Calendar.getInstance();
	public int mYear = cal.get(Calendar.YEAR);
	public int mMonth = cal.get(Calendar.MONTH)+1;
	public int mDay = cal.get(Calendar.DAY_OF_MONTH);
	AuditLogReporter alreport;	
   
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ld_download);
        //replace by layout   ld_download       =  blue_screen
        alreport=new AuditLogReporter();
    }
    
    
    
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press is disabled",      
         Toast.LENGTH_LONG).show();

        return false;
           // Disable back button..............
    }
    */
    
    
    
	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == Globals.DATE_DIALOG_ID) {
			alreport.reportAudit("Creating Dialog:"+mDay+":"+mMonth+":"+mYear,this);
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			alreport.reportAudit("Touched DateDialog(Changed) "+dayOfMonth+":"+monthOfYear+":"+year,getApplication());
			//view.getParent()
			if (lastDateView != null) {
				lastDateView.setText(String.format("%02d-%02d-%04d",monthOfYear + 1, dayOfMonth, year));
				lastDateView = null;//BIG QUERY why should it kept null???
			}
		}
	};
	
	@Override
	public void onBackPressed() {
			super.onBackPressed();
	}
	private void show_exit_alert(String msg) {
		AlertDialog.Builder alert_for_exit = new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_action_warning)
				.setTitle("Confirmation")
				.setMessage("Dont want to "+ msg+" service records?")
				.setCancelable(false)
				.setNegativeButton("Oops!", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Nothing Just finish that dialog....Sudheer...
					}
				}).setPositiveButton("Continue without "+msg+"ing !", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		AlertDialog alertexit = alert_for_exit.create();
		alertexit.show();
	}
	public  void setsaved(boolean result){
		this.isSaved=result;
	}
}
