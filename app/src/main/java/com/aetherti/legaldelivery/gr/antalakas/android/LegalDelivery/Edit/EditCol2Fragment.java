package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.Map.LDLocation;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ManupulateFile;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LocationFrmAddress.CalculateTimeandDistance;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class EditCol2Fragment extends Fragment {
    //public String strPersonSeenPersonalEditText;
    public String pserv1;

    Activity _myParent = null;
    AuditLogReporter alreport;
    TextView personSeenSubstituteNoSeenTextView;
    Spinner personSeenSubstituteSpinner;
    LinearLayout personSeenSubstituteNoSeenlinearLayout;
    TextView personSeenSubstituteTextView;
    EditText personSeenSubstituteEditText;
    LinearLayout personSeenSubstitutelinearLayout;
    EditCol1Fragment editCol1Fragment;
    TextView personSeenPersonalTextView;
    EditText personSeenPersonalEditText;
    LinearLayout personSeenPersonallinearLayout;
    EditActivity editactivity;
    Spinner personSeenPersonalSpinner;
    TextView personNotSeenPersonalPlusTextView;
    EditText personNotSeenPersonalPlusEditText;
    LinearLayout personNotSeenPersonalPluslinearLayout;
    TextView corprecTextView;
    EditInputDataFragment editInputDataFragment;
    EditText corprecEditText;
    LinearLayout corprecLinearLayout;
    SharedPreferences sharedPrefs;
    TextView corprectitleTextView;
    EditText corprectitleEditText;
    LinearLayout corprectitleLinearLayout;
    CheckBox serviceFirstAttemptCheckBox;
    CheckBox serviceSecondAttemptCheckBox;
    CheckBox serviceThirdAttemptCheckBox;
    ProgressDialog mProgressDialog;
    CheckBox serviceCompletedCheckBox;
    LinearLayout firstAtmptLayout;
    TextView lat1TextView;
    TextView lon1TextView;
    LinearLayout LatLon1Layout;
    LinearLayout secondAtmptLayout;
    TextView lat2TextView;
    TextView lon2TextView;
    LinearLayout LatLon2Layout;
    LinearLayout thirdAtmptLayout;
    TextView lat3TextView;
    TextView lon3TextView;
    EditInputDataFragment editinputfragment;
    LinearLayout LatLon3Layout;
    LinearLayout nonamegiven;
    LinearLayout namegiven;
    public Button saveButton;
    List<String> personNotseenlist;
    LDLocation ldLocation = new LDLocation();
    ProgressDialog progressdialog;
    Location currentUserLocation;
    double currentLocationLatitute;
    double currentLocationLongitude;
    double lat1 = 0, lon1 = 0;
    double lat2 = 0, lon2 = 0;
    double lat3 = 0, lon3 = 0;
    Calendar cal = Calendar.getInstance();
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String stime = sdf.format(d);
    static boolean ischecked;
    WebView myWebView;
    int personSeenSubstituteSelected = -1;
    Button showOnMapButton;

    public void setParentActivity(Activity pMyParent) {
        _myParent = pMyParent;
    }

    @SuppressWarnings("unused")
    private String personNotSeenPersonalText = "";

    double mLat1 = -1000, mLon1 = -1000, mLat2 = -1000, mLon2 = -1000, mLat3 = -1000, mLon3 = -1000;


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 0:
                try {
                    String Keyboard = data.getExtras().getString("Value");
                    String Ok = data.getExtras().getString("Ok");
                    String Cancel = data.getExtras().getString("Cancel");

                    if (Ok.equals("1")) {
                        personSeenSubstituteEditText.setEnabled(true);
                        personSeenSubstituteEditText.setText(Keyboard);
                    }
                    if (Cancel.equals("1")) {
                        personSeenSubstituteEditText.setEnabled(true);
                        personSeenSubstituteEditText.setText(personSeenSubstituteEditText.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        alreport = new AuditLogReporter();
        if (alreport != null) alreport.reportAudit("In EditCol2Fragment...", getActivity());
        View V = inflater.inflate(R.layout.ld_edit_col2_fragment, container, false);
        editinputfragment = (EditInputDataFragment) getFragmentManager()
                .findFragmentById(R.id.EditInputDataFragment);
        personSeenSubstituteNoSeenTextView = V.findViewById(R.id.personSeenSubstituteNoSeenTextView);
        personSeenSubstituteSpinner = V.findViewById(R.id.personSeenSubstituteSpinner);
        personSeenSubstituteSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                selectedItem = selectedItem.replace("\"", "");
                if (selectedItem.equals("n/a")) {
                    if (personSeenSubstituteEditText.getText().toString().equalsIgnoreCase("")) {
                        personSeenSubstituteEditText.setText("");
                    } else if (personSeenSubstituteEditText.getText().toString().equalsIgnoreCase("John Doe")) {
                        personSeenSubstituteEditText.setText("");
                    } else if (personSeenSubstituteEditText.getText().toString().equalsIgnoreCase("Jane Doe")) {
                        personSeenSubstituteEditText.setText("");
                    }
                    personSeenSubstituteEditText.setEnabled(true);
                } else {
                    personSeenSubstituteEditText.setText(selectedItem);
                    personSeenSubstituteSpinner.setSelection(position);
                }

                personSeenSubstituteSelected = position;
                alreport.reportAudit("clicked Person seen Substitute spinner " + selectedItemView.toString(), getActivity());
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        personSeenSubstituteNoSeenlinearLayout = V.findViewById(R.id.personSeenSubstituteNoSeenlinearLayout);
        personSeenSubstituteTextView = V.findViewById(R.id.personSeenSubstituteTextView);
        personSeenSubstituteEditText = V.findViewById(R.id.personSeenSubstituteEditText);
        personSeenSubstitutelinearLayout = V.findViewById(R.id.personSeenSubstitutelinearLayout);
        personSeenPersonalTextView = V.findViewById(R.id.personSeenPersonalTextView);
        personSeenPersonalEditText = V.findViewById(R.id.personSeenPersonalEditText);
        personSeenPersonallinearLayout = V.findViewById(R.id.personSeenPersonallinearLayout);
        personSeenPersonalSpinner = V.findViewById(R.id.personSeenPersonalSpinner);
        personNotSeenPersonalPlusTextView = V.findViewById(R.id.personNotSeenPersonalPlusTextView);
        personNotSeenPersonalPlusEditText = V.findViewById(R.id.personNotSeenPersonalPlusEditText);
        personNotSeenPersonalPluslinearLayout = V.findViewById(R.id.personNotSeenPersonalPluslinearLayout);
        corprecTextView = V.findViewById(R.id.corprecTextView);
        corprecEditText = V.findViewById(R.id.corprecEditText);
        corprecLinearLayout = V.findViewById(R.id.corpreclinearLayout);
        corprectitleTextView = V.findViewById(R.id.corprectitleTextView);
        corprectitleEditText = V.findViewById(R.id.corprectitleEditText);
        corprectitleLinearLayout = V.findViewById(R.id.corprectitlelinearLayout);
        serviceFirstAttemptCheckBox = V.findViewById(R.id.serviceFirstAttempt);
        serviceSecondAttemptCheckBox = V.findViewById(R.id.serviceSecondAttempt);
        serviceThirdAttemptCheckBox = V.findViewById(R.id.serviceThirdAttempt);
        serviceCompletedCheckBox = V.findViewById(R.id.serviceCompletedCheckBox);
        saveButton = V.findViewById(R.id.saveButton);
        nonamegiven = V.findViewById(R.id.nonamegivenlayout);
        namegiven = V.findViewById(R.id.namegivenlayout);
        editCol1Fragment = (EditCol1Fragment) getFragmentManager().findFragmentById(R.id.EditCol1FragmentID);
//		EditActivity edit = new EditActivity();
        showOnMapButton = V.findViewById(R.id.showOnMapButton);
        String DOS = editCol1Fragment.GetDateOfService();
        Log.e("hello", DOS);
        if (DOS.isEmpty() || DOS.equalsIgnoreCase("[Not Set]")) {
            serviceCompletedCheckBox.setEnabled(true);
            /*personSeenPersonalSpinner.setEnabled(true);
            personSeenSubstituteSpinner.setEnabled(true);
            editCol1Fragment.dateofserviceTextView.setClickable(true);
            editCol1Fragment.timeofserviceTextView.setClickable(true);*/
         //   editCol1Fragment.timeofserviceTextView.setClickable(true);
        } else {
            serviceFirstAttemptCheckBox.setEnabled(false);
            serviceSecondAttemptCheckBox.setEnabled(false);
            serviceCompletedCheckBox.setEnabled(false);
            personSeenPersonalSpinner.setEnabled(false);
            personSeenSubstituteSpinner.setEnabled(false);
            showOnMapButton.setClickable(false);
            saveButton.setEnabled(false);
        }

        serviceFirstAttemptCheckBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
//				boolean isGPSEnabled = GPSEnabled();
				/*if(isGPSEnabled){
					showAlertForGps(); 
					if(serviceFirstAttemptCheckBox.isChecked()){
						serviceFirstAttemptCheckBox.setChecked(false);
					}
				}else{*/
                if (serviceFirstAttemptCheckBox.isChecked()) {
                    lat1TextView.setText("Calculating");
                    lon1TextView.setText("Calculating");
//						if(LegalDeliveryActivity.getPreviousAttemptAddress().equalsIgnoreCase("")){
//							SimpleDateFormat format1 = new SimpleDateFormat("HH:mm"); 
//							//editCol1Fragment.gpstime1TextView.setText(format1.format(ca.getTime()));
//						}
                    if (lat1TextView.getText().toString().equals("Calculating")) {
                        try {
                            Toast.makeText(getActivity(), "Wait for a moment", Toast.LENGTH_LONG).show();
                            ldLocation.getLocation(getActivity(), locationResultFirstAttempt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    editCol1Fragment.gpsdate1TextView.setText("[Not Set]");
                    editCol1Fragment.gpstime1TextView.setText("[Not Set]");
                    editCol1Fragment.gpstime2TextView.setText("[Not Set]");
                    lat1TextView.setText("[Not Set]");
                    lon1TextView.setText("[Not Set]");
                }
                //}
            }
        });

        serviceSecondAttemptCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				/*boolean isGPSEnabled = GPSEnabled();
				if(isGPSEnabled){
					showAlertForGps(); 
					if(serviceSecondAttemptCheckBox.isChecked()){
						serviceSecondAttemptCheckBox.setChecked(false);
					}
				}else{*/
                if (serviceSecondAttemptCheckBox.isChecked()) {
                    lat2TextView.setText("Calculating");
                    lon2TextView.setText("Calculating");
//						if(LegalDeliveryActivity.getPreviousAttemptAddress().equalsIgnoreCase("")){
//							Calendar ca = Calendar.getInstance(); 
//							SimpleDateFormat  format1 = new SimpleDateFormat("HH:mm"); 
//							//editCol1Fragment.gpstime2TextView.setText(format1.format(ca.getTime()));
//						}
                    if (lat2TextView.getText().toString().equals("Calculating")) {
                        try {
                            Toast.makeText(getActivity(), "Wait for a moment", Toast.LENGTH_LONG).show();
                            ldLocation.getLocation(getActivity(), locationResultSecondAttempt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    editCol1Fragment.gpsdate2TextView.setText("[Not Set]");
                    editCol1Fragment.gpstime2TextView.setText("[Not Set]");
                    lat2TextView.setText("[Not Set]");
                    lon2TextView.setText("[Not Set]");
                }
                //}

            }
        });

        serviceThirdAttemptCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				/*boolean isGPSEnabled = GPSEnabled();
				if(isGPSEnabled){
					showAlertForGps(); 
					if(serviceThirdAttemptCheckBox.isChecked()){
						serviceThirdAttemptCheckBox.setChecked(false);
					}
				}else{*/
                if (serviceThirdAttemptCheckBox.isChecked()) {
                    lat3TextView.setText("Calculating");
                    lon3TextView.setText("Calculating");
//						if(LegalDeliveryActivity.getPreviousAttemptAddress().equalsIgnoreCase("")){
//							Calendar ca = Calendar.getInstance(); 
//							SimpleDateFormat  format1 = new SimpleDateFormat("HH:mm"); 
//							//editCol1Fragment.gpstime3TextView.setText(format1.format(ca.getTime()));
//						}
                    if (lat3TextView.getText().toString().equalsIgnoreCase("Calculating")) {
                        try {
                            Toast.makeText(getActivity(), "Wait for a moment", Toast.LENGTH_LONG).show();
                            ldLocation.getLocation(getActivity(), locationResultThirdAttempt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    editCol1Fragment.gpsdate2TextView.setText("[Not Set]");
                    editCol1Fragment.gpstime2TextView.setText("[Not Set]");
                    lat3TextView.setText("[Not Set]");
                    lon3TextView.setText("[Not Set]");
                }
                //}
            }
        });

        serviceCompletedCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (serviceFirstAttemptCheckBox.isChecked() &&
                        (editCol1Fragment.serviceresultSpinner.getSelectedItem().toString().equals("n/a"))) {
                    serviceCompletedCheckBox.setChecked(false);
                    serviceCompletedCheckBox.setClickable(false);
                } else {
                    serviceCompletedCheckBox.setClickable(true);
                }
            }
        });
        myWebView = V.findViewById(R.id.gpsloc);

        myWebView.setVisibility(View.GONE);

        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mapMethod();
            }
        });
        final Button saveButton = V.findViewById(R.id.saveButton);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Globals.ProductCode = pref.getString("product_code", null);

//		  SharedPreferences pref = android.preference.PreferenceManager.getDefaultSharedPreferences(getActivity());
//		              Globals.ProductCode=pref.getString("product_code", "");

        assert Globals.ProductCode != null;
        if (Globals.ProductCode.equalsIgnoreCase("B"))
            if (!(checkIsTimeValid(stime))) {
             //   Toast.makeText(getActivity(), "Invalid delivery time.", Toast.LENGTH_SHORT).show();
              //  saveButton.setEnabled(true);
            }
        //sohan remind this thing

        saveButton.setOnClickListener(new View.OnClickListener() {
            void thread_for_save() {
                final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Saving Record ...", true);
                ringProgressDialog.setCancelable(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(13000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();
            }

            public void onClick(View v) {
                try {
                    if (Globals.ProductCode.equalsIgnoreCase("B") && editCol1Fragment.Check_Today_isHoliday()) {
                        Toast.makeText(getActivity(), "Today is Holiday service can not be delivered.", Toast.LENGTH_SHORT).show();
                    } else {
                        alreport.reportAudit("Save Button Clicked...", getActivity());
//						EditInputDataFragment editInputDataFragment = (EditInputDataFragment) getFragmentManager().findFragmentById(R.id.EditInputDataFragment);
//						String serviceType = editInputDataFragment.GetServiceType();
                        String serviceResult = editCol1Fragment.GetServiceResult();
                        EditCol1Fragment editCol1Fragment = (EditCol1Fragment) getFragmentManager().findFragmentById(R.id.EditCol1FragmentID);
                        String s1 = editCol1Fragment.GetGpsTime1();
                        String s2 = editCol1Fragment.GetGpsTime2();
                        String s3 = editCol1Fragment.GetGpsTime3();

                        if (serviceFirstAttemptCheckBox.isChecked()) {
                            if (s1.equals("[Not Set]")) {
                                AlertDialog.Builder builder_not_sunday = new AlertDialog.Builder(getActivity())
                                        .setIcon(R.drawable.ic_action_warning).setTitle("Warning !")
                                        .setMessage("Enter Time of First Attempt!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity();
                                            }
                                        });
                                AlertDialog alert1 = builder_not_sunday.create();
                                alert1.show();
                            }
                        }
                        if (serviceSecondAttemptCheckBox.isChecked()) {
                            if (s2.equals("[Not Set]")) {
                                AlertDialog.Builder builder_not_sunday = new AlertDialog.Builder(getActivity())
                                        .setIcon(R.drawable.ic_action_warning).setTitle("Warning !")
                                        .setMessage("Enter Time of Second Attempt!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity();
                                            }
                                        });
                                AlertDialog alert2 = builder_not_sunday.create();
                                alert2.show();
                            }
                        }
                        if (serviceThirdAttemptCheckBox.isChecked()) {

                            if (s3.equals("[Not Set]")) {
                                AlertDialog.Builder builder_not_sunday = new AlertDialog.Builder(getActivity())
                                        .setIcon(R.drawable.ic_action_warning).setTitle("Warning !")
                                        .setMessage("Enter Time of Third Attempt!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity();
                                            }
                                        });
                                AlertDialog alert3 = builder_not_sunday.create();
                                alert3.show();
                            }
                        }

                        //SaveService();
                        //if (serviceType.equals("L&T Residential") || serviceType.equals("L&T Commercial")){

                        //if(serviceFirstAttemptCheckBox.isChecked()){
                        //if(!s1.equals("[Not Set]")){
                        boolean validationResult = validateLTService(serviceResult);
                        String isDayPairSelected = LegalDeliveryActivity.getSelectedDayPair();
                        if ((validationResult) && Globals.ProductCode.equalsIgnoreCase("C")) {
                            if (!(isDayPairSelected.equalsIgnoreCase(""))) {
                                thread_for_save();
                                SaveService();
                                Thread.interrupted();
                            }
                        } else if (validationResult && Globals.ProductCode.equalsIgnoreCase("B")) {
                            thread_for_save();
                            SaveService();
                            Thread.interrupted();
                        } else {
                            //Toast.makeText(getActivity(), "Please select Day-pair", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        validationResult = false;
                        //}
                        //}
							/*if(serviceSecondAttemptCheckBox.isChecked()){
								boolean validationResult = false;
								if((!s1.equals("[Not Set]"))&&(!s2.equals("[Not Set]"))){
									validationResult = validateLTService(serviceResult);
									String isDayPairSelected = LegalDeliveryActivity.getSelectedDayPair();
									if((validationResult) && (!isDayPairSelected.equalsIgnoreCase(""))){
										thread_for_save();
										SaveService();
										Thread.interrupted();
									}else{
										Toast.makeText(getActivity(), "Please select Day-pair", Toast.LENGTH_SHORT).show();
										return;
									}
								}
							}*/
                        //}
                        //some changes is remaining in standard services
						/*else{
							boolean validationResult = false;
								if(serviceThirdAttemptCheckBox.isChecked()){
									if((!s1.equals("[Not Set]"))&&(!s2.equals("[Not Set]"))&&(!s3.equals("[Not Set]"))){
										validationResult = validateStdService(serviceResult);
										String isDayPairSelected = LegalDeliveryActivity.getSelectedDayPair();
										if((validationResult) && (!isDayPairSelected.equalsIgnoreCase(""))){
											thread_for_save();
											SaveService();
											Thread.interrupted();
										}else{
											Toast.makeText(getActivity(), "Please select Day-pair", Toast.LENGTH_SHORT).show();
											return;
										}
									}
								}
							}*/
                    }
                    StringWriter st = new StringWriter();

                    ManupulateFile manFile = new ManupulateFile();
                    manFile.WriteToFile(st.toString(), getActivity());
                    ErroReportingInBackground errreprt = new ErroReportingInBackground(getActivity());
                    errreprt.execute(st.toString());
                    Toast.makeText(getActivity(), "Error reported through mail!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        personSeenPersonalSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getItemAtPosition(position).toString();
                String strpersonNotseenlist = personNotseenlist.toString();
                System.out.println("person not seen list" + personNotseenlist.toString());
                alreport.reportAudit("person not seen :" + strpersonNotseenlist, getActivity());
                int end = strpersonNotseenlist.length();
                strpersonNotseenlist = strpersonNotseenlist.substring(1, end - 1);//
                String[] strpersonNotseenArr = strpersonNotseenlist.split(",");
                List<String> personNotSeen = new ArrayList<String>();
                for (int j = 0; j < strpersonNotseenArr.length; j++) {
                    personNotSeen.add(strpersonNotseenArr[j].trim());
                }
                if (selectedItem.equals("Select Person Seen")) {
					/*personNotSeenPersonalPlusEditText.setText("");
					personSeenPersonalEditText.setText("");*/
                } else {
                    if (personNotSeen.contains(selectedItem)) {
                        personNotSeen.remove(selectedItem);
                        String strPerson = personNotSeen.toString();
                        strPerson = strPerson.substring(1, (strPerson.length() - 1));
                        personNotSeenPersonalPlusEditText.setText(strPerson);
                        personSeenPersonalEditText.setText(selectedItem);
                        personSeenPersonalSpinner.setSelection(0);
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        firstAtmptLayout = V.findViewById(R.id.firstAtmptLayout);
        lat1TextView = V.findViewById(R.id.lat1TextView);
        lon1TextView = V.findViewById(R.id.lon1TextView);
        LatLon1Layout = V.findViewById(R.id.latlonText1);
        secondAtmptLayout = V.findViewById(R.id.secondAtmptLayout);
        lat2TextView = V.findViewById(R.id.lat2TextView);
        lon2TextView = V.findViewById(R.id.lon2TextView);
        LatLon2Layout = V.findViewById(R.id.latlonText2);
        thirdAtmptLayout = V.findViewById(R.id.thirdAtmptLayout);
        lat3TextView = V.findViewById(R.id.lat3TextView);
        lon3TextView = V.findViewById(R.id.lon3TextView);
        LatLon3Layout = V.findViewById(R.id.latlonText3);
		/*serviceFirstAttemptCheckBox.setOnCheckedChangeListener(this);
		serviceSecondAttemptCheckBox.setOnCheckedChangeListener(this);
		serviceThirdAttemptCheckBox.setOnCheckedChangeListener(this);
		serviceCompletedCheckBox.setOnCheckedChangeListener(this);*/
        populateFields();
        return V;
    }

    private void mapMethod() {
        String API_KEY = null;
        try {
            ApplicationInfo appinfo = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
            API_KEY = new Bundle(appinfo.metaData).getString("com.google.android.maps.v2.API_KEY");
        } catch (NameNotFoundException e) {
            Toast.makeText(getActivity(), "Error in reading API key from manifest file!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        String GPSLat = "" + lat3TextView.getText().toString();
        String GPSLon = "" + lon3TextView.getText().toString();

        if (GPSLat.equals("[Not Set]") || GPSLat.equals("00.00000000")) {
            GPSLat = "" + lat2TextView.getText().toString();
            GPSLon = "" + lon2TextView.getText().toString();
        }

        if (GPSLat.equals("[Not Set]") || GPSLat.equals("00.00000000")) {
            GPSLat = "" + lat1TextView.getText().toString();
            GPSLon = "" + lon1TextView.getText().toString();
        }

        if (!GPSLat.equals("[Not Set]") && !GPSLat.equals("[Not Set]")) {
            String uri = "http://maps.googleapis.com/maps/api/staticmap?center="
                    + GPSLat
                    + ","
                    + GPSLon
                    + "&zoom=15&size=500x350&markers=color:red%7Clabel:S%7C"
                    + GPSLat
                    + ","
                    + GPSLon
                    + "&sensor=false&key=" + API_KEY;
            myWebView.loadUrl(uri);
            myWebView.setVisibility(View.VISIBLE);
        } else if (GPSLat == "00.00000000" && GPSLon == "00.00000000")
            Toast.makeText(getActivity(), "Invalid Location !", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getActivity(), "No Latitute and Longitute Found for current Service", Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkIsTimeValid(String inputTime) {
        boolean result = false;
        String strdate = (String) editCol1Fragment.gpsdate1TextView.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(strdate);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            if (!(sdf.format(convertedDate).equalsIgnoreCase("Saturday"))) {
                if (!(editCol1Fragment.strServiceType.equals("L&T Commercial") || editCol1Fragment.strServiceType.equals("Standard"))) {
                    String timeRule1start = "06:00", timeRule1end = "07:59",
                            timeRule2start = "18:01", timeRule2end = "22:29",
                            timeRule3start = "09:00", timeRule3end = "16:59";
                    try {
                        Date actualInputTime = new SimpleDateFormat("HH:mm").parse(inputTime);
                        Date timeRule1Start = new SimpleDateFormat("HH:mm").parse(timeRule1start);
                        Date timeRule1End = new SimpleDateFormat("HH:mm").parse(timeRule1end);
                        Date timeRule2Start = new SimpleDateFormat("HH:mm").parse(timeRule2start);
                        Date timeRule2End = new SimpleDateFormat("HH:mm").parse(timeRule2end);
                        Date timeRule3Start = new SimpleDateFormat("HH:mm").parse(timeRule3start);
                        Date timeRule3End = new SimpleDateFormat("HH:mm").parse(timeRule3end);
                        result = ((actualInputTime.after(timeRule1Start)) && (actualInputTime.before(timeRule1End))) ||
                                ((actualInputTime.after(timeRule2Start)) && (actualInputTime.before(timeRule2End))) ||
                                ((actualInputTime.after(timeRule3Start)) && (actualInputTime.before(timeRule3End)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    result = true;
                }
            } else {
                result = true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return result;
    }

    public void setCurrentLocationLatitute(double latitude, double longitude) {
        currentLocationLatitute = latitude;
        currentLocationLongitude = longitude;
    }

    @Override
    public void onResume() {
        super.onResume();
        showOnMapButton.performClick();
    }

    public double getCurrentAddressLatitude() {
        return currentLocationLatitute;
    }

    public double getcurrentAddressLogitude() {
        return currentLocationLongitude;
    }

    /**
     * @param lat Setting Latitude to lat1.TextView with decimal format 00.000000000
     * @param lon Setting Latitude to lon1.TextView with decimal format 00.000000000
     */
    public void SetLatLon1(double lat, double lon) {
        DecimalFormat deciformat = new DecimalFormat("00.00000000");
        deciformat.setRoundingMode(RoundingMode.DOWN);
        lat1TextView.setText(deciformat.format(lat));
        lon1TextView.setText(deciformat.format(lon));
    }

    /**
     * @param lat Setting Latitude to lat2.TextView with decimal format 00.000000000
     * @param lon Setting Latitude to lon2.TextView with decimal format 00.000000000
     */
    public void SetLatLon2(double lat, double lon) {
        DecimalFormat deciformat = new DecimalFormat("00.00000000");
        deciformat.setRoundingMode(RoundingMode.DOWN);
        lat2TextView.setText(deciformat.format(lat));
        lon2TextView.setText(deciformat.format(lon));
    }

    /**
     * @param lat Setting Latitude to lat3.TextView with decimal format 00.000000000
     * @param lon Setting Latitude to lon3.TextView with decimal format 00.000000000
     */
    public void SetLatLon3(double lat, double lon) {
        DecimalFormat deciformat = new DecimalFormat("00.00000000");
        deciformat.setRoundingMode(RoundingMode.DOWN);
        lat3TextView.setText(deciformat.format(lat));
        lon3TextView.setText(deciformat.format(lon));
    }

    private boolean validateLTService(String pServiceResult) {

        boolean result = true;

        if (((GetFirstAttempt() == false) && (GetSecondAttempt() == false))) {
            Toast.makeText(getActivity(), "Select at least one attempt", Toast.LENGTH_LONG).show();
            result = false;
            return result;
        }
        if (((GetFirstAttempt() == false) && (GetSecondAttempt() == true))) {
            Toast.makeText(getActivity(), "Lower attempts should be selected", Toast.LENGTH_LONG).show();
            result = false;
            return result;
        }
        if ((GetSecondAttempt() == true)) {
            if (pServiceResult.equals("n/a")) {
                Toast.makeText(getActivity(), "Service Result Required !", Toast.LENGTH_LONG).show();
                result = false;
                return result;
            }
        }
	 /* if (!pServiceResult.equals("n/a")) {
			result = validateResult(pServiceResult);
		}*/

        return result;
    }

    private boolean validateResult(String pServiceResult) {
        boolean result = false;
        if (pServiceResult.equals("Conspicuous")) {
            result = validateConspResult();
        } else if (pServiceResult.equals("Personal")) {
            result = validateOtherResult(pServiceResult);
        } else if (pServiceResult.equals("Personal Plus")) {
            result = validateOtherResult(pServiceResult);
        } else if (pServiceResult.equals("Substitute")) {
            result = validateOtherResult(pServiceResult);
        }
        return result;
    }

    private boolean validateOtherResult(String pServiceResult) {
        String personSeenPersonal = "";
        String personNotSeenPersonal = "";
        String personSeenSubstitute = "";
        EditSexFragment editsexFragment = (EditSexFragment) getFragmentManager()
                .findFragmentById(R.id.EditSexFragmentID);

        EditSkinFragment editskinFragment = (EditSkinFragment) getFragmentManager()
                .findFragmentById(R.id.EditSkinFragmentID);

        EditHairFragment edithairFragment = (EditHairFragment) getFragmentManager()
                .findFragmentById(R.id.EditHairFragmentID);

        EditAgeFragment editageFragment = (EditAgeFragment) getFragmentManager()
                .findFragmentById(R.id.EditAgeFragmentID);

        EditHeightFragment editheightFragment = (EditHeightFragment) getFragmentManager()
                .findFragmentById(R.id.EditHeightFragmentID);

        EditWeightFragment editweightFragment = (EditWeightFragment) getFragmentManager()
                .findFragmentById(R.id.EditWeightFragmentID);

        // ----------------------------------------------------------------------------------------

        Boolean editsexIsAtLeastOneSelected = editsexFragment
                .IsAtLeastOneSelected();
        Boolean editskinIsAtLeastOneSelected = editskinFragment
                .IsAtLeastOneSelected();
        Boolean edithairIsAtLeastOneSelected = edithairFragment
                .IsAtLeast1AndMax3Selected();
        Boolean editageIsAtLeastOneSelected = editageFragment
                .IsAtLeastOneSelected();
        Boolean editheightIsAtLeastOneSelected = editheightFragment
                .IsAtLeastOneSelected();
        Boolean editweightIsAtLeastOneSelected = editweightFragment
                .IsAtLeastOneSelected();

        if (pServiceResult.equals("Personal")) {
            personSeenPersonal = personSeenPersonalEditText.getText().toString();
            if (!editsexIsAtLeastOneSelected || !editskinIsAtLeastOneSelected
                    || !edithairIsAtLeastOneSelected
                    || !editageIsAtLeastOneSelected
                    || !editheightIsAtLeastOneSelected
                    || !editweightIsAtLeastOneSelected || (personSeenPersonal.equalsIgnoreCase(""))) {
                AlertDialog.Builder cServiceAlertDialog = new AlertDialog.Builder(getActivity());
                cServiceAlertDialog.setTitle("Warning");
                cServiceAlertDialog.setMessage("Select at least one value from each category of \"Personal/Plus/Substitute\" service tab or enter person seen");
                cServiceAlertDialog.setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
                cServiceAlertDialog.show();
                return false;
            }
        } else if (pServiceResult.equals("Personal Plus")) {
            personSeenPersonal = personSeenPersonalEditText.getText().toString();
            personNotSeenPersonal = personNotSeenPersonalPlusEditText.getText().toString();
            if (!editsexIsAtLeastOneSelected || !editskinIsAtLeastOneSelected
                    || !edithairIsAtLeastOneSelected
                    || !editageIsAtLeastOneSelected
                    || !editheightIsAtLeastOneSelected
                    || !editweightIsAtLeastOneSelected || personSeenPersonal.equalsIgnoreCase("") || personNotSeenPersonal.equalsIgnoreCase("")) {
                AlertDialog.Builder cServiceAlertDialog = new AlertDialog.Builder(getActivity());
                cServiceAlertDialog.setTitle("Warning");
                cServiceAlertDialog.setMessage("Select at least one value from each category of \"Personal/Plus/Substitute\" service tab or enter person seen or enter person not seen");
                cServiceAlertDialog.setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });
                cServiceAlertDialog.show();
                return false;
            }
        } else if (pServiceResult.equals("Substitute")) {
            personSeenSubstitute = personSeenSubstituteEditText.getText().toString();
            if (!editsexIsAtLeastOneSelected || !editskinIsAtLeastOneSelected
                    || !edithairIsAtLeastOneSelected
                    || !editageIsAtLeastOneSelected
                    || !editheightIsAtLeastOneSelected
                    || !editweightIsAtLeastOneSelected || personSeenSubstitute.equalsIgnoreCase("")) {
                AlertDialog.Builder cServiceAlertDialog = new AlertDialog.Builder(getActivity());
                cServiceAlertDialog.setTitle("Warning");
                cServiceAlertDialog.setMessage("Select at least one value from each category of \"Personal/Plus/Substitute\" service tab or enter person seen substitute");
                cServiceAlertDialog.setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });
                cServiceAlertDialog.show();
                return false;
            }
        }
        return true;
    }

//	private boolean validateStdService(String pServiceResult) {
//
//		boolean result = true;
//		if ((((GetFirstAttempt() == false) && (GetSecondAttempt() == false)) && (GetThirdAttempt() == false))) {
//			Toast.makeText(getActivity(),"Select at least one attempt!",Toast.LENGTH_LONG).show();
//			result = false;
//			return result;
//		}
//		if (((GetFirstAttempt() == false) || (GetSecondAttempt() == false))
//				&& (GetThirdAttempt() == true)) {
//			Toast.makeText(getActivity(),"Lower attempts should be selected!",Toast.LENGTH_LONG).show();
//			result = false;
//			return result;
//		}
//		if ((GetFirstAttempt() == false)
//				&& (GetSecondAttempt() == true)) {
//			Toast.makeText(getActivity(),"Lower attempts should be selected!",Toast.LENGTH_LONG).show();
//			result = false;
//			return result;
//		}
//		if (GetThirdAttempt() == true) {
//			if (pServiceResult.equals("n/a")) {
//				Toast.makeText(getActivity(),"Service Result Required!",Toast.LENGTH_LONG).show();
//				result = false;
//				return result;
//			}
//		}
//		
//		if (!pServiceResult.equals("n/a")) {
//			result = validateResult(pServiceResult);
//		}
//		
//		return result;
//	}

    private boolean validateConspResult() {

        EditEntryFragment editentryFragment = (EditEntryFragment) getFragmentManager().findFragmentById(R.id.EditEntryFragmentID);
        EditWallFragment editwallFragment = (EditWallFragment) getFragmentManager().findFragmentById(R.id.EditWallFragmentID);
        EditFloorFragment editfloorFragment = (EditFloorFragment) getFragmentManager().findFragmentById(R.id.EditFloorFragmentID);
        EditDoorFragment editdoorFragment = (EditDoorFragment) getFragmentManager().findFragmentById(R.id.EditDoorFragmentID);
        EditLockFragment editlockFragment = (EditLockFragment) getFragmentManager().findFragmentById(R.id.EditLockFragmentID);
        // --------------------------------------------------------------------------------------------------------------------------------------------

        boolean editentryIsAtLeastOneSelected = editentryFragment.IsAtLeastOneSelected();
        boolean editwallIsAtLeastOneSelected = editwallFragment.IsAtLeast1AndMax3Selected();
        boolean editfloorIsAtLeastOneSelected = editfloorFragment.IsAtLeast1AndMax3Selected();
        boolean editdoorIsAtLeastOneSelected = editdoorFragment.IsAtLeast1AndMax3Selected();
        boolean editlockIsAtLeastOneSelected = editlockFragment.IsAtLeast1AndMax3Selected();

        if (!editentryIsAtLeastOneSelected || !editwallIsAtLeastOneSelected
                || !editfloorIsAtLeastOneSelected || !editdoorIsAtLeastOneSelected || !editlockIsAtLeastOneSelected) {
            AlertDialog.Builder cServiceAlertDialog = new AlertDialog.Builder(getActivity());
            cServiceAlertDialog.setTitle("Warning");
            cServiceAlertDialog.setMessage("Select at least one & maximum three properties from each category of \"C\" services");
            cServiceAlertDialog.setPositiveButton("OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            cServiceAlertDialog.show();
            return false;
        }
        return true;
    }

    public void SaveService() {
        try {
            alreport.reportAudit("In Saving Process...", getActivity());
            @SuppressWarnings("unused")
            EditActivity editActivity = (EditActivity) getActivity();
            String attempt_no = "";
            Log.i("Last Attempt was::", attempt_no);
            alreport.reportAudit("Saving this service for console view", getActivity());
            ldLocation.getLocation(getActivity(), locationResultBeforesave);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Can't Save,Due to:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public LDLocation.LocationResult locationResultBeforesave = new LDLocation.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            EditActivity editActivity = (EditActivity) getActivity();
            editActivity.SaveRecord(location);
        }
    };

    public LDLocation.LocationResult currentLocation = new LDLocation.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            Address latlon = getLocationFromAddress(location);
            if (latlon != null) {
                double lat1 = 0, lon1 = 0;
                lat1 = latlon.getLatitude();
                lon1 = latlon.getLongitude();
                setCurrentLocationLatitute(lat1, lon1);
            }
        }
    };

    public LDLocation.LocationResult locationResultFirstAttempt = new LDLocation.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            Address latlon = getLocationFromAddress(location);
            if (latlon != null) {
                if (editCol1Fragment.serviceresultSpinner.getSelectedItem().toString().equals("Conspicuous")) {

                    lat1 = latlon.getLatitude();
                    lon1 = latlon.getLongitude();
                    SetLatLon1(lat1, lon1);
                    Random r = new Random();
                    double x = r.nextInt(1000);
                    x = x / 10000000;
                    double lat2 = (lat1 + x);
                    double lon2 = (lon1 - x);
                    SetLatLon2(lat2, lon2);
                    double y = r.nextInt(1000);
                    y = y / 10000000;
                    double lat3 = (lat1 + y);
                    double lon3 = (lon1 - y);
                    SetLatLon3(lat3, lon3);
                } else {
                    lat1 = latlon.getLatitude();
                    lon1 = latlon.getLongitude();
                    SetLatLon1(lat1, lon1);
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Lat Lon Can't be Generated", Toast.LENGTH_LONG).show();
            }
        }

    };

    public LDLocation.LocationResult locationResultSecondAttempt = new LDLocation.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            Address latlon = getLocationFromAddress(location);
            if (latlon != null) {
                lat2 = latlon.getLatitude();
                lon2 = latlon.getLongitude();
                Random r = new Random();
                double x = r.nextInt(1000);
                x = x / 10000000;
                lat2 = (lat2 + x);
                lon2 = (lon2 - x);
                SetLatLon2(lat2, lon2);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Lat Lon Can't be Generated", Toast.LENGTH_LONG).show();
            }
        }
    };

    public LDLocation.LocationResult locationResultThirdAttempt = new LDLocation.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            Address latlon = getLocationFromAddress(location);
            if (latlon != null) {
                lat3 = latlon.getLatitude();
                lon3 = latlon.getLongitude();
                Random r = new Random();
                double y = r.nextInt(1000);
                y = y / 10000000;

                lat3 = (lat3 + y);
                lon3 = (lon3 - y);
                SetLatLon3(lat3, lon3);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Lat Lon Can't be Generated", Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * @param strFullname it is<B> LT Fullname</B>
     * @param resultType  ServiceResultType
     * @return List<String> of persons by Splitting(',')
     */
    public List<String> getAllPersons(String strFullname, String resultType) {
        List<String> persons = new ArrayList<String>();
        personNotseenlist = new ArrayList<String>();
        EditActivity editActivity = (EditActivity) getActivity();
        Cursor cursor = editActivity.GetRelatedPersonCursor();

        if (!resultType.equalsIgnoreCase("personal")) {
            // Add first item
            persons.add("Select Person Seen");
            // Add second item
            if (strFullname != null) {
                String[] personNames = strFullname.split(",");
                for (String personName : personNames) {
                    persons.add(personName.trim());
                    personNotseenlist.add(personName.trim());
                    Log.i("Persons seen:", personName);
                }
            }
        }
        if (cursor.moveToFirst()) {
            do {
                Log.i("RelatedDatabasContain:", cursor.getString(0));
                persons.add(cursor.getString(0));
                personNotseenlist.add(cursor.getString(0));
            } while (cursor.moveToNext());
        } else
            Log.i("empty!", "Related data base is empty! and cursor.moveToFirst says" + cursor.moveToFirst());

        cursor.close();
        return persons;
    }

    public List<String> getAllPersonsNotSeen() {
        for (String s : personNotseenlist)
            Log.i("Person Not Seen List:", s);
        return personNotseenlist;
    }

    /**
     * @param strFullname it is <b><i>LTFullName </i></b>if serviceResultType!=Conspicuous and LTFullName!=null from database
     * @param resultType
     */
    private void LoadPersonSeenPersonalSpinnerData(String strFullname, String resultType) {

        List<String> persons = getAllPersons(strFullname, resultType);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, persons);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        personSeenPersonalSpinner.setAdapter(dataAdapter);
    }

    private void populateFields() {
        ////////////////////////////////////////////////////////////////////////////////
        alreport.reportAudit("EditCol2 populating Fields:", getActivity());
        ////////////////////////////////////////////////////////////////////////////////
        EditActivity editActivity = (EditActivity) getActivity();
        Cursor cursor = editActivity.GetCursor();
        String serviceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceType));
        if (serviceType == null) {
            serviceType = "";
        }
        String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
        if (serviceResult == null) {
            serviceResult = "";
        }
        //-----------------------This Conditions for SUBSTITUTE service Result type----------------------------------------------------///
        String strPersonSeenSubstitute = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PersonSeenDoeSubstitute));

        if (strPersonSeenSubstitute != null) {
            SetPersonSeenSubstitute(strPersonSeenSubstitute);
        }

        //String strPersonSeenSubstituteEditText = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PPSERVED1));

		/*if (strPersonSeenSubstituteEditText != null) {
			if (strPersonSeenSubstituteEditText.trim().equalsIgnoreCase("john doe")|| strPersonSeenSubstituteEditText.trim().equalsIgnoreCase("jane doe")){///Hard Coded!!!!!!!!
				SetPersonSeenSubstitute(strPersonSeenSubstituteEditText);
				strPersonSeenSubstituteEditText = "";
			}
		} else {
			if (strPersonSeenSubstituteEditText == null) {
				strPersonSeenSubstituteEditText = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PersonSeenSubstitute));
				if (strPersonSeenSubstituteEditText == null)
				{
					strPersonSeenSubstituteEditText = "";
				}
				else
				{
					personSeenPersonalSpinner.setClickable(false);
				}
			}
			
		}*/
        if (serviceType.equalsIgnoreCase("standard") && serviceResult.equalsIgnoreCase("substitute")) {
            if (strPersonSeenSubstitute == null) {
                SetPersonSeenSubstitute("John Doe");///Hard Coded!!!!!!!!
            }
        }
        //------------------This is condition for Person seen Personal-----------------------------------------------------------------------//

        String strPersonSeenPersonalEditText = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PersonSeenPersonal));

        String PPServed1 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTFullname));

        //This is my stuff Sudheer...
        String PPServedBiz = "";
        if (PPServed1 == null) {
            PPServedBiz = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTBIZNAME));
        }

        //This is My Logic... Sudheer...
        if (serviceType != null && serviceType.equalsIgnoreCase("L&T Commercial"))
            LoadPersonSeenPersonalSpinnerData(PPServedBiz, serviceResult);
        else
            LoadPersonSeenPersonalSpinnerData(PPServed1, serviceResult);
        ///////////////////////////////////////////////////////////////////////
        if (strPersonSeenPersonalEditText == null)
            personSeenPersonalSpinner.setSelection(0);
        else {
            for (int I = 0; I < personSeenPersonalSpinner.getCount(); I++) {
                String person = personSeenPersonalSpinner.getItemAtPosition(I).toString();

                if (strPersonSeenPersonalEditText.equals(person)) {
                    personSeenPersonalSpinner.setSelection(I);
                    break;
                }
            }
        }
        ///Up to Person Seen /////////
        String personNotSeenText = "";
        String strPersonNotSeenPersonalPlusEditText = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PersonsNotSeenPersonal));// KEY_PersonsNotSeenPersonal));
        if (strPersonNotSeenPersonalPlusEditText == null) {
            personNotSeenPersonalPlusEditText.setText("");
        } else {

            if (PPServed1 != null) {
                String splitString = ",";
                if (strPersonNotSeenPersonalPlusEditText.contains("&")) {
                    splitString = "&";
                }
                String[] personNotSeenList = strPersonNotSeenPersonalPlusEditText.trim().split(splitString);
                String[] ppservedList = PPServed1.trim().split(",");
                for (String string : personNotSeenList) {
                    for (String ppservedName : ppservedList) {
                        if (personNotSeenText.length() > 0) {
                            personNotSeenText += ",";
                        }
                        if (!string.trim().equals(ppservedName.trim())) {
                            personNotSeenText += string.trim();
                        }
                    }
                }
            } else {
                personNotSeenText = strPersonNotSeenPersonalPlusEditText;
            }
            // }
            personNotSeenPersonalText = personNotSeenText;
            personNotSeenPersonalPlusEditText.setText(personNotSeenText);
        }

        String strCorpRecipient = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_CorpRecipient));
        String strCorpRecipientTitle = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_CorpRecipientTitle));
        int intServiceFirstAttempt = cursor.getInt(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_FirstAttempt));
        int intServiceSecondAttempt = cursor.getInt(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_SecondAttempt));
        int intServiceThirdAttempt = cursor.getInt(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ThirdAttempt));
        int intServiceCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceCompleted));
        // Bind UI Controls
        personSeenSubstituteEditText.setText(strPersonSeenSubstitute);

        personSeenPersonalEditText.setText(strPersonSeenPersonalEditText);

        // personNotSeenPersonalPlusEditText.setText(strPersonNotSeenPersonalPlusEditText);
        corprecEditText.setText(strCorpRecipient);
        corprectitleEditText.setText(strCorpRecipientTitle);
        serviceFirstAttemptCheckBox.setChecked(intServiceFirstAttempt == 1);
        serviceSecondAttemptCheckBox.setChecked(intServiceSecondAttempt == 1);
        serviceThirdAttemptCheckBox.setChecked(intServiceThirdAttempt == 1);
        serviceCompletedCheckBox.setChecked(intServiceCompleted == 1);
        mLat1 = cursor.getDouble(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLat1));
        mLon1 = cursor.getDouble(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLon1));
        mLat2 = cursor.getDouble(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLat2));
        mLon2 = cursor.getDouble(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLon2));
        mLat3 = cursor.getDouble(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLat3));
        mLon3 = cursor.getDouble(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLon3));
    }

    String GetPersonSeenSubstitute() {
        return personSeenSubstituteSpinner.getSelectedItem().toString();
    }

    void SetPersonSeenSubstitute(String strPersonSeenSubstitute) {

        int itemCount = personSeenSubstituteSpinner.getCount();

        for (int I = 0; I < itemCount; I++) {
            String strItem = personSeenSubstituteSpinner.getItemAtPosition(I)
                    .toString();

            if (!strItem.trim().toLowerCase()
                    .equals(strPersonSeenSubstitute.trim().toLowerCase()))
                continue;
            personSeenSubstituteSpinner.setSelection(I);
            break;
        }
        if (strPersonSeenSubstitute != null) {
            personSeenSubstituteEditText.setText(strPersonSeenSubstitute);
        }

    }

    public void SetFirstAttempt(boolean bVal) {
        serviceFirstAttemptCheckBox.setChecked(bVal);
    }

    /**
     * @return is 1st attempt checkbox is checked?true:false
     */
    public boolean GetFirstAttempt() {
        return serviceFirstAttemptCheckBox.isChecked();
    }

    /**
     * @param bVal if true sets checked else uncheck!
     */
    public void SetSecondAttempt(boolean bVal) {
        serviceSecondAttemptCheckBox.setChecked(bVal);
    }

    /**
     * @return is 2nd Attempts checkbox is checked?true:false?
     */
    public boolean GetSecondAttempt() {
        return serviceSecondAttemptCheckBox.isChecked();
    }

    public void SetThirdAttempt(boolean bVal) {
        serviceThirdAttemptCheckBox.setChecked(bVal);
    }

    /**
     * @return isthird attempt check box is checked or not?!
     */
    public boolean GetThirdAttempt() {
        return serviceThirdAttemptCheckBox.isChecked();
    }

    public void SetServiceCompleted(boolean bVal) {
        serviceCompletedCheckBox.setChecked(bVal);
    }

    public boolean GetServiceCompleted() {
        return serviceCompletedCheckBox.isChecked();
    }

    public void DisplayPersonSeenSubstituteNoSeen() {
        personSeenSubstituteNoSeenTextView.setVisibility(View.VISIBLE);
        personSeenSubstituteSpinner.setVisibility(View.VISIBLE);
        personSeenSubstituteNoSeenlinearLayout.setVisibility(View.VISIBLE);
    }

    public void HidePersonSeenSubstituteNoSeen() {
        personSeenSubstituteNoSeenTextView.setVisibility(View.GONE);
        personSeenSubstituteSpinner.setVisibility(View.GONE);
        personSeenSubstituteNoSeenlinearLayout.setVisibility(View.GONE);
    }

    public void DisplayPersonSeenSubstitute() {
        personSeenSubstituteTextView.setVisibility(View.VISIBLE);
        personSeenSubstituteEditText.setVisibility(View.VISIBLE);
        personSeenSubstitutelinearLayout.setVisibility(View.VISIBLE);
        nonamegiven.setVisibility(View.VISIBLE);
        namegiven.setVisibility(View.VISIBLE);
    }

    public Address getLocationFromAddress(Location strAddress) {
        Geocoder coder = new Geocoder(_myParent);
        List<Address> address;
        Address location = null;
        try {
            address = coder.getFromLocation(strAddress.getLatitude(), strAddress.getLongitude(), 5);

            if (address.size() > 0) {
                location = address.get(0);
                location.getLatitude();
                location.getLongitude();
            } else {
                Toast.makeText(_myParent, "Can't Find Address by Google Map Services", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return location;
    }

    /**
     * @param value boolean for showing or hiding views as lat1textview,lontextview,latlonlaytout,firstattemptlayot
     */
    public void ShowFirstAttemtGPS(boolean value) {
        if (!value) {
            lat1TextView.setVisibility(View.GONE);
            lon1TextView.setVisibility(View.GONE);
            LatLon1Layout.setVisibility(View.GONE);
            firstAtmptLayout.setVisibility(View.GONE);

        } else {
            lat1TextView.setVisibility(View.VISIBLE);
            lon1TextView.setVisibility(View.VISIBLE);
            LatLon1Layout.setVisibility(View.VISIBLE);
            firstAtmptLayout.setVisibility(View.VISIBLE);
        }
    }

    public void ShowFirstAttemtGPS(boolean value1, boolean value2) {

        if (value1) {
            serviceFirstAttemptCheckBox.setEnabled(false);
        } else {
            lat1TextView.setText("[Not Set]");
            lon1TextView.setText("[Not Set]");
            serviceFirstAttemptCheckBox.setEnabled(true);
        }
    }

    public void ShowSecondAttemtGPS(boolean value) {
        if (!value) {
            lat2TextView.setVisibility(View.GONE);
            lon2TextView.setVisibility(View.GONE);
            LatLon2Layout.setVisibility(View.GONE);
            secondAtmptLayout.setVisibility(View.GONE);

        } else {
            lat2TextView.setVisibility(View.VISIBLE);
            lon2TextView.setVisibility(View.VISIBLE);
            LatLon2Layout.setVisibility(View.VISIBLE);
            secondAtmptLayout.setVisibility(View.VISIBLE);
        }
    }

    public void ShowSecondAttemtGPS(boolean value1, boolean value2) {

        if (value1) {
            serviceSecondAttemptCheckBox.setEnabled(false);
        } else {
            lat2TextView.setText("[Not Set]");
            lon2TextView.setText("[Not Set]");
            serviceSecondAttemptCheckBox.setEnabled(true);
        }
    }

    /**
     * @param value Boolean if false Hide Third Attempts
     *              lat3TextView.setVisibility(View.GONE);
     *              lon3TextView.setVisibility(View.GONE);
     *              LatLon3Layout.setVisibility(View.GONE);
     *              thirdAtmptLayout.setVisibility(View.GONE);
     */
    public void ShowThirdAttemtGPS(boolean value) {
        if (!value) {
            lat3TextView.setVisibility(View.GONE);
            lon3TextView.setVisibility(View.GONE);
            LatLon3Layout.setVisibility(View.GONE);
            thirdAtmptLayout.setVisibility(View.GONE);
            // serviceSecondAttemptCheckBox.setVisibility(View.GONE);
        } else {
            lat3TextView.setVisibility(View.VISIBLE);
            lon3TextView.setVisibility(View.VISIBLE);
            LatLon3Layout.setVisibility(View.VISIBLE);
            thirdAtmptLayout.setVisibility(View.VISIBLE);
            // serviceSecondAttemptCheckBox.setVisibility(View.VISIBLE);
        }
    }

    public void ShowThirdAttemtGPS(boolean value1, boolean value2) {

        if (value1) {
            serviceThirdAttemptCheckBox.setEnabled(false);
        } else {
            lat3TextView.setText("[Not Set]");
            lon3TextView.setText("[Not Set]");
            serviceThirdAttemptCheckBox.setEnabled(true);
        }
    }

    public void HidePersonSeenSubstitute() {
        personSeenSubstituteTextView.setVisibility(View.GONE);
        personSeenSubstituteEditText.setVisibility(View.GONE);
        personSeenSubstitutelinearLayout.setVisibility(View.GONE);
        nonamegiven.setVisibility(View.GONE);
        namegiven.setVisibility(View.GONE);
    }

    public void DisplayPersonSeenPersonal() {
        personSeenPersonalTextView.setVisibility(View.VISIBLE);
        personSeenPersonalEditText.setVisibility(View.VISIBLE);
        personSeenPersonalEditText.setFocusable(false);
        personSeenPersonalSpinner.setVisibility(View.VISIBLE);
        personSeenPersonallinearLayout.setVisibility(View.VISIBLE);
    }

    public void HidePersonSeenPersonal() {
        personSeenPersonalTextView.setVisibility(View.GONE);
        personSeenPersonalEditText.setVisibility(View.GONE);
        personSeenPersonalSpinner.setVisibility(View.GONE);
        personSeenPersonallinearLayout.setVisibility(View.GONE);
    }

    public void DisplayPersonNotSeenPersonalPlus() {
        personNotSeenPersonalPlusTextView.setVisibility(View.VISIBLE);
        personNotSeenPersonalPlusEditText.setVisibility(View.VISIBLE);
        personNotSeenPersonalPluslinearLayout.setVisibility(View.VISIBLE);
    }

    public void HidePersonNotSeenPersonalPlus() {
        personNotSeenPersonalPlusTextView.setVisibility(View.GONE);
        personNotSeenPersonalPlusEditText.setVisibility(View.GONE);
        personNotSeenPersonalPluslinearLayout.setVisibility(View.GONE);
    }

    public void DisplayCorpRec() {
        corprecTextView.setVisibility(View.VISIBLE);
        corprecEditText.setVisibility(View.VISIBLE);
        corprecLinearLayout.setVisibility(View.VISIBLE);
    }

    public void HideCorpRec() {
        corprecTextView.setVisibility(View.GONE);
        corprecEditText.setVisibility(View.GONE);
        corprecLinearLayout.setVisibility(View.GONE);
    }

    public void DisplayCorpRecTitle() {
        corprectitleTextView.setVisibility(View.VISIBLE);
        corprectitleEditText.setVisibility(View.VISIBLE);
        corprectitleLinearLayout.setVisibility(View.VISIBLE);
    }

    public void HideCorpRecTitle() {
        corprectitleTextView.setVisibility(View.GONE);
        corprectitleEditText.setVisibility(View.GONE);
        corprectitleLinearLayout.setVisibility(View.GONE);
    }

    ///modified by meeee
    ///mitesh
	/*@SuppressWarnings("static-access")
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		EditActivity edit = new EditActivity();
		ldLocation.getLocation(getActivity(), currentLocation);
		if(isChecked)
		{
				if(GetFirstAttempt() == true)
				{
					if(!(edit.address.isEmpty()))
					{  
						String value="";
						@SuppressWarnings("rawtypes")
						Iterator i = edit.address.entrySet().iterator();
						while(i.hasNext())
						{   
							//String k=i.next().toString();
							@SuppressWarnings("rawtypes")
							Entry e = (Entry) i.next();

							@SuppressWarnings("unused")
							String k = e.getKey().toString(); 
							value=e.getValue().toString();
							if(value.equalsIgnoreCase(EditCol0Fragment.col0edittextview3.getText().toString())){
								value = "";
							}
						}
						EditCol0Fragment.col0edittextview2.setText(value);
					}else{
						EditCol0Fragment.col0edittextview2.setText("");
					}

					if(!(edit.firstTime.isEmpty()))
					{
						String value="";
						@SuppressWarnings("rawtypes")
						Iterator i=edit.firstTime.entrySet().iterator();
						while(i.hasNext())
						{   
							@SuppressWarnings("rawtypes")
							Entry e=(Entry) i.next();
							@SuppressWarnings("unused")
							String k=e.getKey().toString(); 
							value=e.getValue().toString();

						}
						EditCol0Fragment.col0edittextView4.setText(value);
					}else{
						EditCol0Fragment.col0edittextView4.setText("");
					}
					
					//new CalcTimeRequiredTask().execute(dest);
				}

				if(GetSecondAttempt() == true)
				{
					if(!(EditActivity.address2.isEmpty()))
					{   
						String value="";
						Entry<Object, Object> e=(Entry<Object, Object>)EditActivity.address2.lastEntry();
						@SuppressWarnings("unused")
						String k = e.getKey().toString(); 
						value = e.getValue().toString();
						if(value.equalsIgnoreCase(EditCol0Fragment.secondtextaddress.getText().toString())){
							value="";
						}
						EditCol0Fragment.secondtextaddress.setText(value);
						//new CalcTimeRequiredTask2().execute(dest);
					}

					if(!(EditActivity.secondTime.isEmpty())){
						String value="";
						@SuppressWarnings("rawtypes")
						Iterator i=EditActivity.secondTime.entrySet().iterator();
						while(i.hasNext()){   
							@SuppressWarnings("rawtypes")
							Entry e=(Entry) i.next();
							@SuppressWarnings("unused")
							String k=e.getKey().toString(); 
							value=e.getValue().toString();
						}
						EditCol0Fragment.col0secondtext.setText(value);
					}else{
						EditCol0Fragment.col0secondtext.setText("");
					}
				}
				if(GetThirdAttempt()==true){
					if(!(EditActivity.address3.isEmpty())){  
						String value="";
						Entry<Object, Object> e=(Entry<Object, Object>)EditActivity.address3.lastEntry();
						@SuppressWarnings("unused")
						String k= e.getKey().toString(); 
						value=e.getValue().toString();
						if(value.equalsIgnoreCase(EditCol0Fragment.thirdtextaddress.getText().toString())){
							value="";
						}
						EditCol0Fragment.thirdtextaddress.setText(value);
						//new CalcTimeRequiredTask3().execute(dest);
					}

					if(!(EditActivity.thirdTime.isEmpty())){
						String value="";
						@SuppressWarnings("rawtypes")
						Iterator i=EditActivity.thirdTime.entrySet().iterator();
						while(i.hasNext()){   
							@SuppressWarnings("rawtypes")
							Entry e=(Entry) i.next();
							@SuppressWarnings("unused")
							String k=e.getKey().toString(); 
							value=e.getValue().toString();
						}
						EditCol0Fragment.col0thirdtext.setText(value);
					}else{
						EditCol0Fragment.col0thirdtext .setText("");
					}
				}
		}
	}*/

    public class CalcTimeRequiredTask extends AsyncTask<String, String, String> {
        // This is point
        EditActivity edit = new EditActivity();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            final CalculateTimeandDistance calTD = new CalculateTimeandDistance();

            if ((lat1 > 0)) {
                calTD.getDistanceInfo(lat1, lon1, params[0]);
            }
            return calTD.getTime();
        }

        @Override
        protected void onPostExecute(String result) {
            EditCol0Fragment.col0editedittextview1.setText(result);
            EditCol0Fragment.col0editedittextview1.setFocusable(true);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // super.onProgressUpdate(values);
        }
    }

	/*public class CalcTimeRequiredTask2 extends AsyncTask<String, String, String> {
		// This is point
		EditActivity edit = new EditActivity();
		EditCol0Fragment  editCol0Fragment = (EditCol0Fragment) getFragmentManager().findFragmentById(R.id.EditCol0FragmentID);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			final CalculateTimeandDistance calTD = new CalculateTimeandDistance();
			if ((lat2 > 0)){
				calTD.getDistanceInfo(lat2,lon2, params[0]);
			}
			return calTD.getTime();
		}

		@Override
		protected void onPostExecute(String result) {
			EditCol0Fragment.secondeditattempt.setText(result);
			edit.secondtrip = result;
			EditCol0Fragment.col0editedittextview1
			.setSelection(EditCol0Fragment.col0editedittextview1.getText().length());
			EditCol0Fragment.col0editedittextview1.setFocusable(true);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// super.onProgressUpdate(values);
		}
	}

	public class CalcTimeRequiredTask3 extends AsyncTask<String, String, String> {
		EditActivity edit = new EditActivity();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			final CalculateTimeandDistance calTD = new CalculateTimeandDistance();
			if ((lat3 > 0)){
				calTD.getDistanceInfo(lat3,lon3, params[0]);
			}
			return calTD.getTime();
		}

		@Override
		protected void onPostExecute(String result) {
			EditCol0Fragment.thirdeditattempt.setText(result);
			edit.thirdtrip = result;
			EditCol0Fragment.col0editedittextview1
			.setSelection(EditCol0Fragment.col0editedittextview1.getText().length());
			EditCol0Fragment.col0editedittextview1.setFocusable(true);
		}
		@Override
		protected void onProgressUpdate(String... values) {
			// super.onProgressUpdate(values);
		}
	}*/

    public boolean GPSEnabled() {
        boolean gps_enabled = false;
        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps_enabled == false;
    }

    public void showAlertForGps() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Opss");
        alert.setMessage("GPS is disabled!!!");
        alert.setPositiveButton("Enable", new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        alert.show();
    }

}
