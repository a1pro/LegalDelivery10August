package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ErroReportingInBackground extends AsyncTask<String, String, Boolean> {
	Context context;

	public ErroReportingInBackground(Context con) {
		context = con;
	}
	Exception e;
	@Override
	protected void onPreExecute() {
		Toast.makeText(context, "Error Captured! Sending to Programmer!",Toast.LENGTH_LONG).show();
		super.onPreExecute();
	}
	@Override
	protected void onProgressUpdate(String... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	protected Boolean doInBackground(String... params) {
		String strfeedback = params[0];
		String accname = null;
		boolean result=false;
		try {
			AccountManager mgr = AccountManager.get(context);
			Account[] accts = mgr.getAccounts();
			accname = accts[0].name;
		} catch (Exception e) {
			Log.e("SendMail", e.getMessage(), e);
		}
		GMailSender sender = new GMailSender("texnofeedback@gmail.com",
				"backfeedtexno");

		//String recipients = "sudheer.patil@texnovate.com";
		String recipients = "mitesh.shaha@aetherti.com";
		try {
			sender.sendMail("User feedback", "User E-mail :- " + accname + "\n"
					+ "\n" + "User Feedback:-  " + strfeedback,
					"texnofeedback@gmail.com", recipients);
			result=true;
		} catch (Exception e) {
			this.e = e;
			e.printStackTrace();
		}
		return result;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		if(result)
			Toast.makeText(context, "Error mailed!", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(context, "Error not reported!", Toast.LENGTH_SHORT).show();
		super.onPostExecute(result);
	}
}
