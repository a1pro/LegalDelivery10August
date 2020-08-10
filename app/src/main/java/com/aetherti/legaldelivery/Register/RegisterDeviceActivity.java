package com.aetherti.legaldelivery.Register;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterDeviceActivity extends Activity {

    private static final String url = Globals.Domain + Globals.DomainPathRegisterDevice;
    static String CRLF = "\r\n";
    static String twoHyphens = "--";
    static String boundary = "*****mgd*****";
    String deviceID = null;
    EditText deviceCodeEdit = null;
    EditText deviceNameEdit = null;
    EditText deviceUserId = null;
    EditText deviceUserPwd = null;
    TextView resultTextView = null;
    SharedPreferences sharedpref;
    //////////////////////////////////////////////////////
    AuditLogReporter alreport;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ld_registerdevice);
        /////////////////////////////////////////////////////////////////////////////////////
        alreport = new AuditLogReporter();
        /////////////////////////////////////////////////////////////////////////////////////
        final Button registerButton = (Button) findViewById(R.id.registerButton);
        deviceCodeEdit = (EditText) findViewById(R.id.deviceCodeEdit);
        deviceNameEdit = (EditText) findViewById(R.id.deviceNameEdit);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        deviceUserId = (EditText) findViewById(R.id.register_username_edit);
        deviceUserPwd = (EditText) findViewById(R.id.register_pwd_edit);
        sharedpref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ///////////////////////////////////////////////////////
                alreport.reportAudit("$ Register Button Clicked! ", getApplication());
                ///////////////////////////////////////////////////////
                Toast.makeText(getApplicationContext(), "Wait for moment! Registration in progess...", Toast.LENGTH_LONG).show();

                if (checkPermission()) {

                    call_api_resgiter();

                    //Snackbar.make(view,"Permission already granted.",Snackbar.LENGTH_LONG).show();

                } else {
                    requestPermission();
                    //Snackbar.make(view,"Please request permission.",Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }

    private void call_api_resgiter() {

        if (!Globals.IsNetworkAvailable(getBaseContext())) {
            //////Changed By Sudheer...
            /////////////////////////////////////////////////////////
            RegisterDeviceActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Globals.ShowNoNetworkMessage(getBaseContext());//May cause Double msg-Sudheer...
                }
            });
            return;
        }
        deviceID = Globals.GetDeviceID(getBaseContext(), getContentResolver());

        //	Toast.makeText(getApplicationContext(),deviceID,Toast.LENGTH_LONG).show();
        final EditText deviceCodeEdit = (EditText) findViewById(R.id.deviceCodeEdit);
        final EditText deviceNameEdit = (EditText) findViewById(R.id.deviceNameEdit);

        String deviceCode = deviceCodeEdit.getText().toString();
        String deviceName = deviceNameEdit.getText().toString();
        String StrdeviceUserId = deviceUserId.getText().toString();
        String StrdeviceUserpwd = deviceUserPwd.getText().toString();
        resultTextView.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(deviceCodeEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(deviceNameEdit.getWindowToken(), 0);
            /*
            	RegistrationTask task = new RegistrationTask();
        		task.execute(new String[] { deviceID, deviceCode, deviceName, StrdeviceUserId, StrdeviceUserpwd});
        		*/
        Toast.makeText(getApplicationContext(), deviceID, Toast.LENGTH_LONG).show();
        //	System.out.println("ab");
        SendPostRequest sendtask = new SendPostRequest();
        sendtask.execute(new String[]{deviceID, deviceCode, deviceName, StrdeviceUserId, StrdeviceUserpwd});
    }

    private class RegistrationTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... deviceInfo) {
            String response = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                MultipartEntity entity = new MultipartEntity();
                entity.addPart("DeviceID", new StringBody(deviceInfo[0]));
                entity.addPart("DeviceCode", new StringBody(deviceInfo[1]));
                entity.addPart("DeviceName", new StringBody(deviceInfo[2]));
                entity.addPart("DeviceUserId", new StringBody(deviceInfo[3]));
                entity.addPart("DeviceUserPwd", new StringBody(deviceInfo[4]));
                httppost.setEntity(entity);

                HttpResponse httpResponse = httpclient.execute(httppost);
                InputStream content = httpResponse.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    if (s.contains("DOCTYPE"))
                        break;

                    response += s;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
		
		/*@Override
		protected void onProgressUpdate(String... values) {
			resultTextView.setText(values.toString());
			super.onProgressUpdate(values);
		}*/
		

		/*@Override
		protected void onPostExecute(String result) {
			Editor editor = sharedpref.edit();					
			editor.putBoolean(Globals.DeviceRegisterPref, true);
			resultTextView.setText(result);
		}*/
    }

    private class SendPostRequest extends AsyncTask<String, String, String> {
        HttpURLConnection conn = null;
        String Output;

        @SuppressWarnings("static-access")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... deviceInfo) {
            try {
                //String output;
                HttpClient httpclient = new DefaultHttpClient();

                com.aetherti.legaldelivery.Register.RegisterStructure ds = new RegisterStructure();
                ds.setDeviceID(deviceInfo[0]);
                ds.setDeviceCode(deviceInfo[1]);
                ds.setDeviceName(deviceInfo[2]);
                ds.setDeviceUserId(deviceInfo[3]);
                ds.setDeviceUserPwd(deviceInfo[4]);
                //ds.setServerId("");
                URL url = new URL("http://45.64.105.199/LDSWebService/api/AndroidSearch/Post");
                //	HttpPost httppost1 = new HttpPost();
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setRequestProperty("authorization", Globals.Authorization);
                conn.setRequestProperty("Thumbprint", Globals.Thumbprint);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(15000);
                Gson gson = new Gson();
                String JSONString = gson.toJson(ds);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                bw.write(JSONString);
                bw.flush();
                bw.close();
                conn.connect();
                int responseCode = conn.getResponseCode();
                System.out.println(responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    InputStreamReader isw = new InputStreamReader(in);
                    //HttpResponse httpResponse = httpclient.execute(httppost1);

                    //BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    BufferedReader reader = new BufferedReader(isw);
                    StringBuilder sb = new StringBuilder("");
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    in.close();

                    Output = sb.toString();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Output;

            //	return Output1;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            resultTextView.setText(values.toString());
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Editor editor = sharedpref.edit();                    //Assuming that every task done with complete registration.
            editor.putBoolean(Globals.DeviceRegisterPref, true);
            resultTextView.setText(result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


        }
    }
	
	/*protected void onProgressUpdate(String... values) {
		resultTextView.setText(values.toString());
		super.onProgressUpdate(values);
	}*/

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(RegisterDeviceActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterDeviceActivity.this, Manifest.permission.READ_PHONE_STATE)) {

          //  Toast.makeText(RegisterDeviceActivity.this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(RegisterDeviceActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }


    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //	Snackbar.make(view,"Permission Granted, Now you can access location data.",Snackbar.LENGTH_LONG).show();
                    call_api_resgiter();
                } else {

                    //Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }


}