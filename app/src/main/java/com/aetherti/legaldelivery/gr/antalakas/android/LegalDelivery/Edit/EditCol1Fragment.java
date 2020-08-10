package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ManupulateFile;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.QuickPrefsActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class EditCol1Fragment extends Fragment implements OnClickListener {

    //////////////////////////////////////////////////////////////////////////////////////////////
    AuditLogReporter alreport;
    ////////////////////////////////////////////////////////////////////////////////////////////
    Spinner serviceresultSpinner;
    TextView dateofserviceStaticTextView;
    TextView dateofserviceTextView;
    TextView timeofserviceTextView;
    LinearLayout dateofserviceLinearLayout;
    LinearLayout serveDateInnerLayout;
    LinearLayout linearLayout1;
    EditCol2Fragment editCol2Fragment;
    LinearLayout firstAtmptDateInnerLayout;
    private LinearLayout secondAtmptDateInnerLayout;
    LinearLayout thirdAtmptDateInnerLayout;
    LinearLayout firstAtmptTimeInnerLayout;
    LinearLayout secondAtmptTimeInnerLayout;
    LinearLayout thirdAtmptTimeInnerLayout;
    TextView timeofserviceStaticTextView;
    LinearLayout timeofserviceLinearLayout;
    TextView gpsdate1StaticTextView;
    public TextView gpsdate1TextView;
    TextView gpstime1StaticTextView;
    TextView gpstime1TextView;
    LinearLayout gpsdate1linearLayout;
    LinearLayout gpstime1linearLayout;
    EditActivity editactivity;
    TextView gpsdate2StaticTextView;
    TextView gpsdate2TextView;
    EditInputDataFragment editInputDataFragment;
    LinearLayout gpsdate2linearLayout;
    TextView gpstime2StaticTextView;
    TextView gpstime2TextView;
    LinearLayout gpstime2linearLayout;
    TextView gpsdate3StaticTextView;
    TextView gpsdate3TextView;
    LinearLayout gpsdate3linearLayout;
    TextView gpstime3StaticTextView;
    TextView gpstime3TextView;
    TextView mobiletimeStaticTextView;
    TextView mobiletimeTextView;
    LinearLayout mobiletimeofserviceLinearLayout;
    LinearLayout mobileserveTimeInnerLayout;
    TextView mobiletimeoffirstattemptStaticTextView;
    TextView mobiletimeoffirstattempt;
    LinearLayout mobiletimeoffirstattemptserveTimeInnerLayout;
    LinearLayout mobiletimeoffirstattemptLinearLayout;
    TextView mobiletimeofsecondattemptStaticTextView;
    TextView mobiletimeofsecondattemptTextView;
    LinearLayout mobiletimeofsecondattemptserveTimeInnerLayout;
    LinearLayout mobiletimeofsecondattemptLinearLayout;
    TextView mobiletimeofthirdattemptStaticTextView;
    TextView mobiletimeofthirdattemptTextView;
    LinearLayout mobiletimeofthirdattemptLinearLayout;
    LinearLayout mobiletimeofthirdattemptserveTimeInnerLayout;
    CheckBox locationCheckBox;
    SharedPreferences sharedPrefs;
    Calendar cal = Calendar.getInstance();
    int mYear = cal.get(Calendar.YEAR);
    int mMonth = cal.get(Calendar.MONTH) + 1;
    int mDay = cal.get(Calendar.DAY_OF_MONTH);

    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
    SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm", Locale.getDefault());
    SimpleDateFormat sdatef = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
    String stime = sdf.format(d);
    String stimes = sdfs.format(d);
    String sdate = sdatef.format(d);
    int serviceTypeSelected = -1;
    int hour, minute;
    private static TextView _actualTimeSelectedTextView;
    public static TextView AMORPM;
    static String timeFormat = "__:__";
    String tomorrowAsString = "";
    String strGpsDate2, strGpsDate1;
    String strDateOfService;

    enum ServiceResultEnum {
        NA, Personal, PersonalPlus, Substitute, Conspicuous
    }

    public String strServiceType;
    EditActivity editActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.ld_edit_col1_fragment, container, false);
        alreport = new AuditLogReporter();
        editActivity = (EditActivity) getActivity();
        Cursor cursor = editActivity.GetCursor();
        // textView is the TextView view that should display it

        dateofserviceStaticTextView = (TextView) V.findViewById(R.id.dateofserviceStaticTextView);
        dateofserviceTextView = (TextView) V.findViewById(R.id.dateofserviceTextView);
        timeofserviceTextView = (TextView) V.findViewById(R.id.timeofserviceTextView);
        dateofserviceLinearLayout = (LinearLayout) V.findViewById(R.id.dateofserviceLinearLayout);
        serveDateInnerLayout = (LinearLayout) V.findViewById(R.id.serveDateInnerLayout);
        timeofserviceStaticTextView = (TextView) V.findViewById(R.id.timeofserviceStaticTextView);
        timeofserviceLinearLayout = (LinearLayout) V.findViewById(R.id.timeofserviceLinearLayout);
        linearLayout1 = (LinearLayout) V.findViewById(R.id.linearLayout1);
        linearLayout1.setVisibility(View.GONE);

        //mitesh
        mobiletimeTextView = (TextView) V.findViewById(R.id.mobiletimeTextView);
        mobiletimeStaticTextView = (TextView) V.findViewById(R.id.mobiletimeStaticTextView);
        mobiletimeofserviceLinearLayout = (LinearLayout) V.findViewById(R.id.mobiletimeofserviceLinearLayout);
        mobileserveTimeInnerLayout = (LinearLayout) V.findViewById(R.id.mobileserveTimeInnerLayout);
        //mitesh
        editCol2Fragment = (EditCol2Fragment) getFragmentManager().findFragmentById(R.id.EditCol2FragmentID);

        //1st date Attempt data///////////////////////////////////////////////////////////////////////////
        gpsdate1StaticTextView = (TextView) V.findViewById(R.id.gpsdate1StaticTextView);
        gpsdate1TextView = (TextView) V.findViewById(R.id.gpsdate1TextView);
        gpsdate1linearLayout = (LinearLayout) V.findViewById(R.id.gpsdate1linearLayout);
        firstAtmptDateInnerLayout = (LinearLayout) V.findViewById(R.id.firstAtmptDateInnerLayout);
        gpstime1StaticTextView = (TextView) V.findViewById(R.id.gpstime1StaticTextView);
        gpstime1TextView = (TextView) V.findViewById(R.id.gpstime1TextView);
        gpstime1linearLayout = (LinearLayout) V.findViewById(R.id.gpstime1linearLayout);
        gpstime1linearLayout.setVisibility(View.GONE);
        firstAtmptTimeInnerLayout = (LinearLayout) V.findViewById(R.id.firstAtmptTimeInnerLayout);
        //mitesh
        mobiletimeoffirstattempt = (TextView) V.findViewById(R.id.mobiletimeoffirstattempt);
        mobiletimeoffirstattemptStaticTextView = (TextView) V.findViewById(R.id.mobiletimeoffirstattemptStaticTextView);
        mobiletimeoffirstattemptLinearLayout = (LinearLayout) V.findViewById(R.id.mobiletimeoffirstattemptLinearLayout);
        mobiletimeoffirstattemptserveTimeInnerLayout = (LinearLayout) V.findViewById(R.id.mobiletimeoffirstattemptserveTimeInnerLayout);
        //mitesh

        gpsdate2StaticTextView = (TextView) V.findViewById(R.id.gpsdate2StaticTextView);
        gpsdate2TextView = (TextView) V.findViewById(R.id.gpsdate2TextView);
        gpsdate2linearLayout = (LinearLayout) V.findViewById(R.id.gpsdate2linearLayout);

        gpstime2StaticTextView = (TextView) V.findViewById(R.id.gpstime2StaticTextView);
        gpstime2TextView = (TextView) V.findViewById(R.id.gpstime2TextView);
        gpstime2linearLayout = (LinearLayout) V.findViewById(R.id.gpstime2linearLayout);
        secondAtmptDateInnerLayout = (LinearLayout) V.findViewById(R.id.secondAtmptDateInnerLayout);
        secondAtmptTimeInnerLayout = (LinearLayout) V.findViewById(R.id.secondAtmptTimeInnerLayout);

        //mitesh
        mobiletimeofsecondattemptTextView = (TextView) V.findViewById(R.id.mobiletimeofsecondattemptTextView);
        mobiletimeofsecondattemptStaticTextView = (TextView) V.findViewById(R.id.mobiletimeofsecondattemptStaticTextView);
        mobiletimeofsecondattemptLinearLayout = (LinearLayout) V.findViewById(R.id.mobiletimeofsecondattemptLinearLayout1);
        mobiletimeofsecondattemptserveTimeInnerLayout = (LinearLayout) V.findViewById(R.id.mobiletimeofsecondattemptserveTimeInnerLayout);
        //mitesh

        gpsdate3StaticTextView = (TextView) V.findViewById(R.id.gpsdate3StaticTextView);
        gpsdate3TextView = (TextView) V.findViewById(R.id.gpsdate3TextView);
        gpsdate3linearLayout = (LinearLayout) V.findViewById(R.id.gpsdate3linearLayout);

        gpstime3StaticTextView = (TextView) V.findViewById(R.id.gpstime3StaticTextView);
        gpstime3TextView = (TextView) V.findViewById(R.id.gpstime3TextView);
        thirdAtmptDateInnerLayout = (LinearLayout) V.findViewById(R.id.thirdAtmptDateInnerLayout);
        thirdAtmptTimeInnerLayout = (LinearLayout) V.findViewById(R.id.thirdAtmptTimeInnerLayout);

        //mitesh
        mobiletimeofthirdattemptTextView = (TextView) V.findViewById(R.id.mobiletimeofthirdattemptTextView);
        mobiletimeofthirdattemptStaticTextView = (TextView) V.findViewById(R.id.mobiletimeofthirdattemptStaticTextView);
        mobiletimeofthirdattemptLinearLayout = (LinearLayout) V.findViewById(R.id.mobiletimeofthirdattemptLinearLayout);
        mobiletimeofthirdattemptserveTimeInnerLayout = (LinearLayout) V.findViewById(R.id.mobiletimeofthirdattemptserveTimeInnerLayout);
        //mitesh
        editInputDataFragment = (EditInputDataFragment) getFragmentManager()
                .findFragmentById(R.id.EditInputDataFragment);
        serviceresultSpinner = (Spinner) V.findViewById(R.id.serviceresultSpinner);
        strServiceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceType));

        /////////////////////////////////////////////////////////////////////////////////////
        doChangesInServiceResultasServiceType(strServiceType);//Changes Result type values according to Service type

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", android.content.Context.MODE_PRIVATE);
        Globals.ProductCode = pref.getString("product_code", null);

        if (strDateOfService == null || strDateOfService.equalsIgnoreCase("")) {
            serviceresultSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    alreport.reportAudit("Selected Service Result:" + parentView.getSelectedItem().toString(), getActivity());
                    SetLayoutByServiceResult(parentView.getSelectedItem().toString());
                    if (LegalDeliveryActivity.getSelectedDayPair().equalsIgnoreCase("")) {
                        if (Globals.ProductCode.equalsIgnoreCase("C")) {
                            Toast.makeText(getActivity(), "No Day-Pair selected", Toast.LENGTH_LONG).show();
                        } else {
//						String ar[] = String.valueOf(LegalDeliveryActivity.getSelectedDayPair().toString()).split("-");
//						String first_day = QuickPrefsActivity.get1stDateofweekdays(ar[0].trim());
//						String second_day = QuickPrefsActivity.get2ndDateofweekdays(ar[1].trim());
						/*Calendar mcal = Calendar.getInstance();
						mcal.add(Calendar.DAY_OF_YEAR, 1);
						    Date tomorrow = mcal.getTime();
						    tomorrowAsString = sdatef.format(tomorrow);
						    if(Check_Tomarrow_isHoliday()) {
						    	mcal.add(Calendar.DAY_OF_YEAR, 1);
							    Date dayAtomorrow = mcal.getTime();
							    String dayAfterTommarrow = sdatef.format(dayAtomorrow);
							    gpsdate2TextView.setText(dayAfterTommarrow);
							} else {
								gpsdate2TextView.setText(tomorrowAsString);
							}*/
                            //	gpsdate1TextView.setText(sdate);

                            if (strGpsDate1 == null) {
                                gpsdate1TextView.setText("[Not Set]");
                            } else {
                                gpsdate1TextView.setText(strGpsDate1);
                            }
                            //1st attemt date set here
                            if (strGpsDate2 == null) {
                                gpsdate2TextView.setText("[Not Set]");
                            } else {
                                gpsdate2TextView.setText(strGpsDate2);
                            }
                            //2nd attemp
								/*if(gpstime1TextView.getText() == "[Not Set]") {
									gpstime1TextView.setText(stime);
									}
						if(gpstime2TextView.getText() == "[Not Set]") {
								gpstime2TextView.setText(stime);
						}*/
                        }
                    } else {
                        String[] ar = LegalDeliveryActivity.getSelectedDayPair().split("-");
                        String first_day = QuickPrefsActivity.get1stDateofweekdays(ar[0].trim());
                        String second_day = QuickPrefsActivity.get2ndDateofweekdays(ar[1].trim());
                        gpsdate1TextView.setText(first_day);
                        gpsdate2TextView.setText(second_day);
                    }
                }

                public void onNothingSelected(AdapterView<?> parentView) {
                    //Toast.makeText(getActivity(), "Service Result type is not selected", Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            serviceresultSpinner.setEnabled(false);

        }
        gpstime1TextView.setOnClickListener(this);
        gpstime2TextView.setOnClickListener(this);
        gpstime3TextView.setOnClickListener(this);
        dateofserviceTextView.setOnClickListener(this);
        populateFields();
        return V;
    }


    private void doChangesInServiceResultasServiceType(String strServiceType2) {
        ArrayAdapter<String> adapter;
        alreport.reportAudit("Changing view according to service Type:" + strServiceType2, getActivity());
        String[] mValues = {"n/a", "Personal", "Personal Plus", "Substitute", "Conspicuous", "Corporation"};
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList(mValues));
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);

        if (strServiceType2.equals("n/a")) {
            // do nothing//
        } else if (strServiceType2.equals("Standard")) {
            adapter.remove(adapter.getItem(2));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serviceresultSpinner.setAdapter(adapter);
        } else if (strServiceType2.equals("L&T Residential")) {
            adapter.remove(adapter.getItem(5));
            String recordName = editInputDataFragment.getFullName();
            if (recordName.contains(",")) {
                adapter.remove(adapter.getItem(1));
            } else {
                adapter.remove(adapter.getItem(2));
            }
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serviceresultSpinner.setAdapter(adapter);
        } else if (strServiceType2.equalsIgnoreCase("L&T Commercial")) {
            adapter.remove(adapter.getItem(5));
            String recordName = editInputDataFragment.getBusinessName();
            if (recordName.contains(",")) {
                adapter.remove(adapter.getItem(1));
            } else {
                adapter.remove(adapter.getItem(2));
            }
			/*adapter.remove(adapter.getItem(1));
			adapter.remove(adapter.getItem(1));
			adapter.remove(adapter.getItem(3));*/
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serviceresultSpinner.setAdapter(adapter);
        }
    }

    /**
     * @param selecteditem type of service result type selected!
     */
    private void SetLayoutByServiceResult(String selecteditem) {
        ////////////////////////////////////////////////////////////////////////////
        alreport.reportAudit("Setting Layout editcol1 By service result: " + selecteditem, getActivity());
        ////////////////////////////////////////////////////////////////////////////
        if (selecteditem.equals("n/a")) {
            ((EditActivity) getActivity()).InitializeLayout();
        } else if (selecteditem.equals("Personal")) {
            ((EditActivity) getActivity()).InitializePersonalLayout();

        } else if (selecteditem.equals("Personal Plus")) {
            ((EditActivity) getActivity()).InitializePersonalPlusLayout();

        } else if (selecteditem.equals("Substitute")) {
            ((EditActivity) getActivity()).InitializeSubstituteLayout();

        } else if (selecteditem.equals("Conspicuous")) {
            ((EditActivity) getActivity()).InitializeConspicuousLayout();
            editCol2Fragment = (EditCol2Fragment) getFragmentManager().findFragmentById(R.id.EditCol2FragmentID);
            if (editCol2Fragment.saveButton.isEnabled()) {
                Toast.makeText(getActivity(), "Wait for a moment", Toast.LENGTH_LONG).show();
            }
        } else if (selecteditem.equals("Corporation")) {

            if (strServiceType.equals("L&T Residential") || strServiceType.equals("L&T Commercial")) {
                Toast.makeText(editActivity.getBaseContext(), "Corporation is not allowed for L&T service", Toast.LENGTH_LONG).show();
            }
            ((EditActivity) getActivity()).InitializeCorporationLayout();
        }
		/*String st3 = editActivity.get();
		String st4 = editActivity.get();
		String st5 = editActivity.get();
		String st6 = editActivity.get();
		String st7 = editActivity.get();
		String st8 = editActivity.get();
		
		*/

        DemoHideEditCol1EditCol2Items();
    }

    public void DemoHideEditCol1EditCol2Items() {
        this.ShowFirstAttempt(true);
        this.ShowSecondAttempt(true);
        this.ShowThirdAttempt(true);

        EditCol2Fragment editCol2Fragment = (EditCol2Fragment) getFragmentManager().findFragmentById(R.id.EditCol2FragmentID);

        editCol2Fragment.ShowFirstAttemtGPS(true);
        editCol2Fragment.ShowSecondAttemtGPS(true);
        editCol2Fragment.ShowThirdAttemtGPS(true);

        EditActivity editActivity = (EditActivity) getActivity();
        Cursor cursor = editActivity.GetCursor();
        String strServiceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceType));
        if (strServiceType.equals("L&T Residential") || strServiceType.equals("L&T Commercial")) {
            this.ShowThirdAttempt(false);
            editCol2Fragment.ShowThirdAttemtGPS(false);
        }
        boolean blatlon1 = false;
        String lon1 = ((String) editCol2Fragment.lon1TextView.getText());
        if (lon1.trim().toLowerCase().equalsIgnoreCase("[not set]")) {
            String latlon1 = "00.00000000";
            editCol2Fragment.lon1TextView.setText(latlon1);
        }
        String s1;
        if (editCol2Fragment.lon1TextView.getText().toString().isEmpty()) {
            s1 = "00.00000000";
        }else {
            s1=editCol2Fragment.lon1TextView.getText().toString();
        }
        double lonDouble1 = Double.parseDouble(s1);
        if (lon1.trim().toLowerCase().equalsIgnoreCase("[not set]") || lonDouble1 == 0) {
            lon1 = "";
        } else {
            blatlon1 = true;
        }

        boolean blatlon2 = false;
        String lon2 = ((String) editCol2Fragment.lon2TextView.getText());
        if (lon2.trim().toLowerCase().equals("[not set]")) {
            String latlon2 = "00.00000000";
            editCol2Fragment.lon2TextView.setText(latlon2);
        }
        String s2;
        if(editCol2Fragment.lon2TextView.getText().toString().isEmpty()){
            s2 = "00.00000000";
        }else {
            s2=editCol2Fragment.lon2TextView.getText().toString();
        }
        double lonDouble2 = Double.parseDouble(s2);
       if (lon2.trim().toLowerCase().equalsIgnoreCase("[not set]") || lonDouble2 == 0) {
            lon2 = "";
        } else {
            blatlon2 = true;
        }

        boolean blatlon3 = false;
        String lon3 = ((String) editCol2Fragment.lon3TextView.getText());
        if (lon3.trim().toLowerCase().equals("[not set]")) {
            String latlon3 = "00.00000000";
            editCol2Fragment.lon3TextView.setText(latlon3);
        }
        String s3;
        if(editCol2Fragment.lon3TextView.getText().toString().isEmpty()){
            s3 = "00.00000000";
        }else {
            s3=editCol2Fragment.lon3TextView.getText().toString();
        }
        double lonDouble3 = Double.parseDouble(s3);

        if (lon3.trim().toLowerCase().equalsIgnoreCase("[not set]") || lonDouble3 == 0) {
            lon3 = "";
        } else {
            blatlon3 = true;
        }

        if (lonDouble1 == 0) {
            editCol2Fragment.lat1TextView.setText("[Not Set]");
            editCol2Fragment.lon1TextView.setText("[Not Set]");
        }
        if (lonDouble2 == 0) {
            editCol2Fragment.lat2TextView.setText("[Not Set]");
            editCol2Fragment.lon2TextView.setText("[Not Set]");
        }
        if (lonDouble3 == 0) {
            editCol2Fragment.lat3TextView.setText("[Not Set]");
            editCol2Fragment.lon3TextView.setText("[Not Set]");
        }
        if (blatlon1) {
            editCol2Fragment.SetFirstAttempt(true);
        }
        if (blatlon2) {
            editCol2Fragment.SetSecondAttempt(true);
        }
        if (blatlon3) {
            editCol2Fragment.SetThirdAttempt(true);
        }
    }

    public void HideEditCol1EditCol2Items() {

        String timeOfService = this.GetTimeOfService().trim();
        if (timeOfService.trim().toLowerCase().equalsIgnoreCase("[not set]")) {
            timeOfService = "";
        }
        String time1 = (String) this.gpstime1TextView.getText();
        if (time1.trim().toLowerCase().equalsIgnoreCase("[not set]")) {
            time1 = "";
        }
        String time2 = (String) this.gpstime2TextView.getText();
        if (time2.trim().toLowerCase().equalsIgnoreCase("[not set]")) {
            time2 = "";
        }
        String time3 = ((String) this.gpstime3TextView.getText());
        if (time3.trim().toLowerCase().equalsIgnoreCase("[not set]")) {
            time3 = "";
        }
        String strServiceResult = this.GetServiceResult();
        boolean DisplayUnconditional = false;
        if (!strServiceResult.equals("n/a")) {
            //disable t3

            this.ShowThirdAttempt(false);
            if (time3.trim().length() != 0) {
                if (DisplayUnconditional) {
                    //enable t3
                    this.ShowThirdAttempt(true);
                } else {
                    if (!time3.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase())) {
                        //enable t3
                        this.ShowThirdAttempt(true);
                        DisplayUnconditional = true;
                    }
                }
            }
            //disable t2
            this.ShowSecondAttempt(false);
            if (time2.trim().length() != 0) {
                if (DisplayUnconditional) {
                    //enable t2
                    this.ShowSecondAttempt(true);
                } else {
                    if (!time2.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase())) {
                        //enable t2
                        this.ShowSecondAttempt(true);
                        DisplayUnconditional = true;
                    }
                }
            }
            //disable t1
            this.ShowFirstAttempt(false);
            if (time1.trim().length() != 0) {
                if (DisplayUnconditional) {
                    //enable t1
                    this.ShowFirstAttempt(true);
                } else {
                    if (!time1.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase())) {
                        //enable t1
                        this.ShowFirstAttempt(true);
                        DisplayUnconditional = true;
                    } else if (time1.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase()) && time2.trim().length() == 0 && time3.trim().length() == 0) {
                        this.ShowFirstAttempt(false);
                        DisplayUnconditional = true;
                    }
                }
            }
            if (!DisplayUnconditional) {
                if (time1.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase())) {
                    this.ShowFirstAttempt(true);
                    DisplayUnconditional = true;
                }
                if (time2.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase()) && !DisplayUnconditional) {
                    this.ShowSecondAttempt(true);
                    DisplayUnconditional = true;
                }
                if (time3.trim().toLowerCase().equalsIgnoreCase(timeOfService.trim().toLowerCase()) && !DisplayUnconditional) {
                    this.ShowThirdAttempt(true);
                }
            }
        } else {
            if (time1.trim().length() == 0 ||
                    time1.trim().equals(((String) this.timeofserviceTextView.getText()).trim())) {
                this.ShowFirstAttempt(false);
            }
            if (time2.trim().length() == 0 ||
                    time2.trim().equals(((String) this.timeofserviceTextView.getText()).trim())) {
                this.ShowSecondAttempt(false);
            }
            if (time3.trim().length() == 0 ||
                    time3.trim().equals(((String) this.timeofserviceTextView.getText()).trim())) {
                this.ShowThirdAttempt(false);
            }
        }
        if (timeOfService.length() == 0 && time1.length() == 0
                && time2.length() == 0 && time3.length() == 0 || strServiceResult.equals("n/a")) {
            if (time1.length() == 0) {
                this.ShowFirstAttempt(true);
            }
            if (time2.length() == 0) {
                this.ShowSecondAttempt(true);
            }
            if (time3.length() == 0) {
                this.ShowThirdAttempt(true);
            }
        }
        EditActivity editActivity = (EditActivity) getActivity();
        Cursor cursor = editActivity.GetCursor();
        String strServiceType = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceType));
        if (strServiceType.equals("L&T Residential") || strServiceType.equals("L&T Commercial")) {
            this.ShowThirdAttempt(false);
        }

        if (!strServiceResult.equalsIgnoreCase("conspicuous") && !strServiceResult.equalsIgnoreCase("n/a") && !this.dateofserviceTextView.getText().equals("[Not Set]")) {
            this.ShowFirstAttempt(false);
            this.ShowSecondAttempt(false);
            this.ShowThirdAttempt(false);
        } else if (strServiceResult.equalsIgnoreCase("conspicuous") && !this.dateofserviceTextView.getText().equals("[Not Set]")) {
            this.ShowFirstAttempt(true);
            if (strServiceType.equals("Standard")) {
                this.ShowSecondAttempt(true);
            } else {
                this.ShowSecondAttempt(false);
            }
            this.ShowThirdAttempt(false);
        }
    }

    /**
     * @return Spinner Selected Service Result
     */
    String GetServiceResult() {
        return serviceresultSpinner.getSelectedItem().toString();
    }

    /**
     * @param strServiceResult this string sets service Result selection;
     */
    void SetServiceResult(String strServiceResult) {

        int itemCount = serviceresultSpinner.getCount();
        for (int I = 0; I < itemCount; I++) {
            String strItem = serviceresultSpinner.getItemAtPosition(I).toString();
            if (!strItem.equalsIgnoreCase(strServiceResult))
                continue;

            serviceresultSpinner.setSelection(I);
            break;
        }
    }

    public void SetDateOfService(String strDT) {
        dateofserviceTextView.setText(strDT);
    }

    public String GetDateOfService() {
        return dateofserviceTextView.getText().toString();
    }

    public void SetTimeOfService(String strDT) {
        timeofserviceTextView.setText(strDT);
    }

    public String GetTimeOfService() {
        return timeofserviceTextView.getText().toString();
    }

    public void SetGpsDate1(String strDT) {
        gpsdate1TextView.setText(strDT);
    }

    public String GetGpsDate1() {
        return gpsdate1TextView.getText().toString();
    }

    public void SetGpsTime1(String strDT) {
        gpstime1TextView.setText(strDT);
    }

    public String GetGpsTime1() {
        return gpstime1TextView.getText().toString();
    }

    /**
     * @param strDT Date String that to be set to Date of 2nd Attempt
     */
    public void SetGpsDate2(String strDT) {
        gpsdate2TextView.setText(strDT);
    }

    /**
     * @return Second attempts date
     */
    public String GetGpsDate2() {
        return gpsdate2TextView.getText().toString();
    }

    public void SetGpsTime2(String strDT) {
        gpstime2TextView.setText(strDT);
    }

    public String GetGpsTime2() {
        return gpstime2TextView.getText().toString();
    }

    public void SetGpsDate3(String strDT) {
        gpsdate3TextView.setText(strDT);
    }

    public String GetGpsDate3() {
        return gpsdate3TextView.getText().toString();
    }

    public void SetGpsTime3(String strDT) {
        gpstime3TextView.setText(strDT);
    }

    public String GetGpsTime3() {
        return gpstime3TextView.getText().toString();
    }

    //mitesh
    public void setMobileTime(String str) {
        mobiletimeTextView.setText(str);
    }

    public String getMobileTime() {
        return mobiletimeTextView.getText().toString();
    }

    public void setMobileTime1(String str) {
        mobiletimeoffirstattempt.setText(str);
    }

    public String getMobileTime1() {
        return mobiletimeoffirstattempt.getText().toString();
    }

    public void setMobileTime2(String str) {
        mobiletimeofsecondattemptTextView.setText(str);
    }

    public String getMobileTime2() {
        return mobiletimeofsecondattemptTextView.getText().toString();
    }

    public void setMobileTime3(String str) {
        mobiletimeofthirdattemptTextView.setText(str);
    }

    public String getMobileTime3() {
        return mobiletimeofthirdattemptTextView.getText().toString();
    }
    //mitesh

    public void SetLocationCheckBox(boolean bVal) {
        locationCheckBox.setChecked(bVal);
    }

    @SuppressLint("SimpleDateFormat")
    private void populateFields() {
        EditActivity editActivity = (EditActivity) getActivity();
        Cursor cursor = editActivity.GetCursor();
        String strServiceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
        if (strServiceResult == null) {
            strServiceResult = "";
        }
        //all activity editing done here...
        SetServiceResult(strServiceResult);
        strDateOfService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_DateOfService));
        String strTimeOfService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_TimeOfService));
        strGpsDate1 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSDate1));
        String strGpsTime1 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSTime1));
        strGpsDate2 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSDate2));
        String strGpsTime2 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSTime2));
        String strGpsDate3 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSDate3));
        String strGpsTime3 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSTime3));
        String mobTime = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GpsTimeOfService));
        String mobTime1 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GpsTimeOfFirstAttempt));
        String mobTime2 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GpsTimeOfSecondAttempt));
        String mobTime3 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GpsTimeOfThirdAttempt));


        Log.e("first_attemt", strGpsDate1 + " " + strGpsDate2 + " " + strDateOfService);


        try {
            //mitesh
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

//			cal.add(Calendar.DAY_OF_YEAR, 1);
//		    Date tomorrow = cal.getTime();
//		    String tomorrowAsString = sdatef.format(tomorrow);
//			gpsdate1TextView.setText(sdate);

            if ((sharedPrefs.getBoolean("show_complexview", true))) {
                if (strDateOfService == null || strDateOfService.equalsIgnoreCase("")) {
                    dateofserviceTextView.setText("[Not Set]");
                    gpstime3TextView.setText("[Not Set]");
                    timeofserviceTextView.setText("[Not Set]");
                    gpstime1TextView.setText("[Not Set]");
                    dateofserviceTextView.setClickable(true);
                    timeofserviceTextView.setClickable(true);

                } else {
                    dateofserviceTextView.setText(strDateOfService);
                    dateofserviceTextView.setClickable(false);
                    serviceresultSpinner.setEnabled(false);
                    gpstime3TextView.setClickable(false);
                    timeofserviceTextView.setClickable(false);
                    gpstime1TextView.setClickable(false);
                    //       editCol2Fragment.serviceFirstAttemptCheckBox.setEnabled(false);
                    //     editCol2Fragment.serviceSecondAttemptCheckBox.setEnabled(false);
                    //  editCol2Fragment.serviceCompletedCheckBox.setEnabled(false);

                }
                if (strTimeOfService == null) {
                    timeofserviceTextView.setText("[Not Set]");
                    timeofserviceTextView.setClickable(false);
                } else {
                    timeofserviceTextView.setText(strTimeOfService);
                    timeofserviceTextView.setClickable(false);
                }
                if (strGpsDate1 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        gpsdate1TextView.setText(sdate);
                    } else {
                        gpsdate1TextView.setText("[Not Set]");
                    }
                    gpsdate1TextView.setClickable(false);
                } else {
                    gpsdate1TextView.setText(strGpsDate1);
                    gpsdate1TextView.setClickable(false);
                }

                if (strGpsTime1 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        if (checkIsTimeValid(stime)) {
                            gpstime1TextView.setText(stime);
                        } else {
                            gpstime1TextView.setText("[Not Set]");
                        }
                        gpstime1TextView.setClickable(false);
                    } else {
                        gpstime1TextView.setText("[Not Set]");
//						 gpstime1TextView.setClickable(false);
                    }
                } else {
                    gpstime1TextView.setText(strGpsTime1);
                    //gpstime1TextView.setClickable(false);
                }

                if (strGpsDate2 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        gpsdate2TextView.setText(sdate);
                    } else {
                        gpsdate2TextView.setText("[Not Set]");
                    }
                    gpsdate2TextView.setClickable(false);
                } else {
                    gpsdate2TextView.setText(strGpsDate2);
                    gpsdate2TextView.setClickable(false);
                }

                if (strGpsTime2 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        if (checkIsTimeValid(stime)) {
                            gpstime2TextView.setText(stime);
                        } else {
                            gpstime2TextView.setText("[Not Set]");
                        }
                        gpstime2TextView.setClickable(false);
                    } else {
                        gpstime2TextView.setText("[Not Set]");
//						 gpstime1TextView.setClickable(false);
                    }
                } else {
                    gpstime2TextView.setText(strGpsTime2);
                    // gpstime2TextView.setClickable(false);
                }

                if (strGpsDate3 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        gpsdate3TextView.setText(sdate);
                    } else {
                        gpsdate3TextView.setText("[Not Set]");
                    }
                    gpsdate3TextView.setClickable(false);
                } else {
                    gpsdate3TextView.setText(strGpsDate3);
                    gpsdate3TextView.setClickable(false);
                }

                if (strGpsTime3 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        if (checkIsTimeValid(stime)) {
                            gpstime3TextView.setText(stime);
                        } else {
                            gpstime3TextView.setText("[Not Set]");
                        }
                        gpstime3TextView.setClickable(false);
                    } else {
                        gpstime3TextView.setText("[Not Set]");
//						 gpstime1TextView.setClickable(false);
                    }
                } else {
                    gpstime3TextView.setText(strGpsTime3);
                    //gpstime3TextView.setClickable(false);
                }

                if (mobTime == null) {
                    mobiletimeTextView.setText("[Not Set]");
                } else {
                    mobiletimeTextView.setText(mobTime);
                }

                if (mobTime1 == null) {
                    mobiletimeoffirstattempt.setText("[Not Set]");
                } else {
                    mobiletimeoffirstattempt.setText(mobTime1);
                }

                if (mobTime2 == null) {
                    mobiletimeofsecondattemptTextView.setText("[Not Set]");
                } else {
                    mobiletimeofsecondattemptTextView.setText(mobTime2);
                }

                if (mobTime3 == null) {
                    mobiletimeofthirdattemptTextView.setText("[Not Set]");
                } else {
                    mobiletimeofthirdattemptTextView.setText(mobTime3);
                }
            } else {
                mobiletimeStaticTextView.setText("");
                mobiletimeoffirstattemptStaticTextView.setText("");
                mobiletimeofsecondattemptStaticTextView.setText("");
                mobiletimeofthirdattemptStaticTextView.setText("");
                linearLayout1.setVisibility(View.GONE);
                gpsdate3linearLayout.setVisibility(View.GONE);
                gpstime1linearLayout.setVisibility(View.GONE);
                gpsdate2linearLayout.setVisibility(View.GONE);
                gpstime2linearLayout.setVisibility(View.GONE);
                gpsdate1linearLayout.setVisibility(View.GONE);
                mobiletimeofserviceLinearLayout.setVisibility(View.GONE);
                mobiletimeofsecondattemptLinearLayout.setVisibility(View.GONE);
                mobiletimeoffirstattemptLinearLayout.setVisibility(View.GONE);

                if (strDateOfService == null) {
                    dateofserviceTextView.setText("[Not Set]");
                    dateofserviceTextView.setClickable(false);
                } else {
                    dateofserviceTextView.setText(strDateOfService);
                    dateofserviceTextView.setClickable(false);
                }
               /* if (strDateOfService == null || strDateOfService.equalsIgnoreCase("")) {
                    dateofserviceTextView.setText("[Not Set]");
                    dateofserviceTextView.setClickable(false);
                } else {
                    dateofserviceTextView.setText(strDateOfService);
                    dateofserviceTextView.setClickable(false);
                    serviceresultSpinner.setEnabled(false);
                    gpstime3TextView.setClickable(false);
                    timeofserviceTextView.setClickable(false);
                    gpstime1TextView.setClickable(false);


                    gpstime3TextView.setText("[Not Set]");
                    timeofserviceTextView.setText("[Not Set]");
                    gpstime1TextView.setText("[Not Set]");
                }*/
                if (strTimeOfService == null) {
                    timeofserviceTextView.setText("[Not Set]");
                    timeofserviceTextView.setClickable(false);
                } else {
                    timeofserviceTextView.setText(strTimeOfService);
                    timeofserviceTextView.setClickable(false);
                }
                if (strGpsDate1 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        gpsdate1TextView.setText(sdate);
                    } else {
                        gpsdate1TextView.setText("[Not Set]");
                    }
                    gpsdate1TextView.setClickable(false);
                } else {
                    gpsdate1TextView.setText(strGpsDate1);
                    gpsdate1TextView.setClickable(false);
                }

                if (strGpsTime1 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        if (checkIsTimeValid(stime)) {
                            gpstime1TextView.setText(stime);
                        } else {
                            gpstime1TextView.setText("[Not Set]");
                        }
                        gpstime1TextView.setClickable(false);
                    } else {
                        gpstime1TextView.setText("[Not Set]");
//						 gpstime1TextView.setClickable(false);
                    }

                } else {
                    gpstime1TextView.setText(strGpsTime1.substring(0, 5));
                    //gpstime1TextView.setClickable(false);
                }

                if (strGpsDate2 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        gpsdate2TextView.setText(sdate);
                    } else {
                        gpsdate2TextView.setText("[Not Set]");
                    }
                    gpsdate2TextView.setClickable(false);
                } else {
                    gpsdate2TextView.setText(strGpsDate2);
                    gpsdate2TextView.setClickable(false);
                }

                if (strGpsTime2 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        if (checkIsTimeValid(stime)) {
                            gpstime2TextView.setText(stime);
                        } else {
                            gpstime2TextView.setText("[Not Set]");
                        }
                        gpstime2TextView.setClickable(false);
                    }
                    gpstime2TextView.setText("[Not Set]");
                    // gpstime2TextView.setClickable(false);
                } else {
                    gpstime2TextView.setText(strGpsTime2.substring(0, 5));
                    //gpstime2TextView.setClickable(false);
                }

                if (strGpsDate3 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        gpsdate3TextView.setText(sdate);
                    } else {
                        gpsdate3TextView.setText("[Not Set]");
                    }
                    gpsdate3TextView.setClickable(false);
                } else {
                    gpsdate3TextView.setText(strGpsDate3);
                    gpsdate3TextView.setClickable(false);
                }

                if (strGpsTime3 == null) {
                    if (Globals.ProductCode.equalsIgnoreCase("B")) {
                        if (checkIsTimeValid(stime)) {
                            gpstime3TextView.setText(stime);
                        } else {
                            gpstime3TextView.setText("[Not Set]");
                        }
                        gpstime3TextView.setClickable(false);
                    }
                    gpstime3TextView.setText("[Not Set]");
                    // gpstime3TextView.setClickable(false);
                } else {
                    gpstime3TextView.setText(strGpsTime3.substring(0, 5));
                    // gpstime3TextView.setClickable(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            StringWriter st = new StringWriter();
            e.printStackTrace(new PrintWriter(st));
            ManupulateFile manFile = new ManupulateFile();
            manFile.WriteToFile(st.toString(), getActivity());
            ErroReportingInBackground errreprt = new ErroReportingInBackground(getActivity());
            errreprt.execute(st.toString());
            Toast.makeText(getActivity(), "Error reported through mail!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Error: " + e.getMessage() + " caused by: " + e.getCause(), Toast.LENGTH_LONG).show();

        }
    }
//	void method(String strDate) {
//		
//	}

    private int idOfDateTimeControlSelected = -1;

    public int GetIDOfDateTimeControlSelected() {
        return idOfDateTimeControlSelected;
    }
    //This is dead code because onClickListeners for the view is disabled!...

    @SuppressLint("SimpleDateFormat")
    public void onClick(View v) {

		/*switch (v.getId()) { 
			case R.id.gpstime1TextView: editActivity.showDialog(0); break; 
			case R.id.gpstime2TextView: editActivity.showDialog(1); break; 
			case R.id.gpstime3TextView: editActivity.showDialog(2); break; 
		}*/

        switch (v.getId()) {
            case R.id.gpstime1TextView:
                doTimeOperations(1);
                break;
            case R.id.gpstime2TextView:
                doTimeOperations(2);
                break;
            case R.id.gpstime3TextView:
                doTimeOperations(3);
                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public synchronized void doTimeOperations(final int pWhichAttemptTime) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            View xmlView = View.inflate(getActivity(), R.layout.ld_hour_list, null);
            alertDialog.setView(xmlView);
            GridView gridview = (GridView) xmlView.findViewById(R.id.gridview);
            ArrayList<Integer> hours = new ArrayList<Integer>();
            for (int i = 0; i < 10; i++) {
                hours.add(i);
            }
            gridview.setAdapter(new HourAdapter(getActivity(), hours));
            Button AMButton = (Button) xmlView.findViewById(R.id.AM);
            Button PMButton = (Button) xmlView.findViewById(R.id.PM);
            Button Reset = (Button) xmlView.findViewById(R.id.Reset);
            _actualTimeSelectedTextView = (TextView) xmlView.findViewById(R.id.actualTimeTextView);
            AMORPM = (TextView) xmlView.findViewById(R.id.whichTime);
            alertDialog.setCancelable(false);
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            timeFormat = "__:__";
                            dialog.cancel();
                        }
                    });
            alertDialog.setPositiveButton("Set",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                String getTime = _actualTimeSelectedTextView.getText().toString();
                                String[] splitTime = getTime.split(":");
                                String hour = splitTime[0];
                                String min = splitTime[1];
                                int actHour = Integer.parseInt(hour);
                                int actMin = Integer.parseInt(min);
                                if (((actHour >= 0) && (actHour < 13)) && ((actMin >= 0) && (actMin < 61))) {
                                    String AMOrPM = AMORPM.getText().toString();
                                    if (getTime.equalsIgnoreCase("") || AMOrPM.equalsIgnoreCase("")) {
                                        Toast.makeText(getActivity(), "Invalid Time", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                                    SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                                    Date date = parseFormat.parse(getTime + " " + AMOrPM);
                                    String finalTime = displayFormat.format(date);
                                    if (EditInputDataFragment.getServiceType().equalsIgnoreCase("L&T Residential")) {
                                        editCol2Fragment = (EditCol2Fragment) getFragmentManager().findFragmentById(R.id.EditCol2FragmentID);
                                        String timeRule1start = "06:00", timeRule1end = "07:59",
                                                timeRule2start = "18:01", timeRule2end = "22:29",
                                                timeRule3start = "09:00", timeRule3end = "16:59";

                                        Date timeRule1Start = new SimpleDateFormat("HH:mm").parse(timeRule1start);
                                        Date timeRule1End = new SimpleDateFormat("HH:mm").parse(timeRule1end);
                                        Date timeRule2Start = new SimpleDateFormat("HH:mm").parse(timeRule2start);
                                        Date timeRule2End = new SimpleDateFormat("HH:mm").parse(timeRule2end);
                                        Date timeRule3Start = new SimpleDateFormat("HH:mm").parse(timeRule3start);
                                        Date timeRule3End = new SimpleDateFormat("HH:mm").parse(timeRule3end);

                                        boolean isTimeValid = checkIsTimeValid(finalTime);
                                        if (isTimeValid) {
                                            switch (pWhichAttemptTime) {
                                                case 1:
                                                    SetGpsTime1(finalTime);
                                                    break;
                                                case 2:
                                                    SetGpsTime2(finalTime);
                                                    String m = (String) gpstime1TextView.getText();
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                                                    SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss:SSS");
                                                    Date datem1 = sdf3.parse(m);
                                                    if (datem1.after(timeRule3Start) && (datem1.before(timeRule3End))) {
                                                        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                                    }
                                                    Date date1 = sdf.parse("1970-01-01 " + sdf3.format(datem1));
                                                    if (((date1.after(timeRule3Start)) && (date1.before(timeRule3End))) && ((date.after(timeRule3Start)) && (date.before(timeRule3End)))) {
                                                        editCol2Fragment.saveButton.setEnabled(true);
                                                        Toast.makeText(getActivity(), "Invalid Time, Please try again another time", Toast.LENGTH_LONG).show();
                                                    } else if (((date1.after(timeRule1Start)) && (date1.before(timeRule1End)) || ((date1.after(timeRule2Start)) && (date1.before(timeRule2End)))) &&
                                                            (((date.after(timeRule1Start)) && (date.before(timeRule1End))) || ((date.after(timeRule2Start)) && (date.before(timeRule2End))))) {
                                                        editCol2Fragment.saveButton.setEnabled(false);
                                                        Toast.makeText(getActivity(), "Invalid Time, Please try again another time", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        editCol2Fragment.saveButton.setEnabled(true);
                                                    }
                                                    break;
                                                case 3:
                                                    SetGpsTime3(finalTime);
                                                    break;
                                            }

                                        } else {
                                            Toast.makeText(getActivity(), "Invalid delivery time", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        switch (pWhichAttemptTime) {
                                            case 1:
                                                SetGpsTime1(finalTime);
                                                break;
                                            case 2:
                                                SetGpsTime2(finalTime);
                                                break;
                                            case 3:
                                                SetGpsTime3(finalTime);
                                                break;
                                        }

                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Invalid time, please check time by considering AM/PM", Toast.LENGTH_LONG).show();
                                }
                                timeFormat = "__:__";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            AMButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String actualTimeFirstChar = timeFormat.substring(0, 1);
                    if (actualTimeFirstChar.equalsIgnoreCase("_")) {
                        timeFormat = replaceCharAt(timeFormat, 0, '0');
                        _actualTimeSelectedTextView.setText(timeFormat);
                    }
                    AMORPM.setText(arg0.getTag().toString());
                }
            });
            PMButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String actualTimeFirstChar = timeFormat.substring(0, 1);
                    if (actualTimeFirstChar.equalsIgnoreCase("_")) {
                        timeFormat = replaceCharAt(timeFormat, 0, '0');
                        _actualTimeSelectedTextView.setText(timeFormat);
                    }
                    AMORPM.setText(arg0.getTag().toString());
                }
            });
            Reset.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    AMORPM.setText("");
                    _actualTimeSelectedTextView.setText("");
                    timeFormat = "__:__";
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public boolean checkIsTimeValid(String inputTime) {
        boolean result = false;
        String strdate = (String) gpsdate1TextView.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(strdate);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

            if (!(sdf.format(convertedDate).equalsIgnoreCase("Saturday"))) {
                if (!(strServiceType.equals("L&T Commercial") || strServiceType.equals("Standard"))) {

                    String timeRule1start = "06:00", timeRule1end = "07:59",
                            timeRule2start = "18:01", timeRule2end = "22:29",
                            timeRule3start = "09:00", timeRule3end = "16:59";
                    try {

//		    			  if(!(EditCol2Fragment.ischecked)) {
//		    				  String m = (String) gpstime1TextView.getText();
//		    			  }
                        Date actualInputTime = new SimpleDateFormat("HH:mm").parse(inputTime);
                        Date timeRule1Start = new SimpleDateFormat("HH:mm").parse(timeRule1start);
                        Date timeRule1End = new SimpleDateFormat("HH:mm").parse(timeRule1end);
                        Date timeRule2Start = new SimpleDateFormat("HH:mm").parse(timeRule2start);
                        Date timeRule2End = new SimpleDateFormat("HH:mm").parse(timeRule2end);
                        Date timeRule3Start = new SimpleDateFormat("HH:mm").parse(timeRule3start);
                        Date timeRule3End = new SimpleDateFormat("HH:mm").parse(timeRule3end);
                        if (((actualInputTime.after(timeRule1Start)) && (actualInputTime.before(timeRule1End))) ||
                                ((actualInputTime.after(timeRule2Start)) && (actualInputTime.before(timeRule2End))) ||
                                ((actualInputTime.after(timeRule3Start)) && (actualInputTime.before(timeRule3End)))) {
                            result = true;
                        } else {
                            result = false;
                        }
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

    public void DisplayDateTimeOfService() {
        dateofserviceStaticTextView.setVisibility(View.VISIBLE);
        dateofserviceTextView.setVisibility(View.VISIBLE);
        timeofserviceTextView.setVisibility(View.VISIBLE);
        serveDateInnerLayout.setVisibility(View.VISIBLE);
        dateofserviceLinearLayout.setVisibility(View.VISIBLE);
        // dateofserviceTextViewLinearLayout.setVisibility(View.VISIBLE);
        timeofserviceStaticTextView.setVisibility(View.VISIBLE);
        timeofserviceLinearLayout.setVisibility(View.VISIBLE);

        if (sharedPrefs.getBoolean("show_complexview", true)) {
            mobiletimeTextView.setVisibility(View.VISIBLE);
        } else {
            mobiletimeTextView.setVisibility(View.GONE);
        }
        mobiletimeStaticTextView.setVisibility(View.VISIBLE);
        mobileserveTimeInnerLayout.setVisibility(View.VISIBLE);
    }

    public void HideDateTimeOfService() {
        dateofserviceStaticTextView.setVisibility(View.GONE);
        dateofserviceTextView.setVisibility(View.GONE);
        timeofserviceTextView.setVisibility(View.GONE);
        serveDateInnerLayout.setVisibility(View.GONE);
        dateofserviceLinearLayout.setVisibility(View.GONE);
        // dateofserviceTextViewLinearLayout.setVisibility(View.GONE);
        timeofserviceStaticTextView.setVisibility(View.GONE);
        timeofserviceLinearLayout.setVisibility(View.GONE);
        mobiletimeTextView.setVisibility(View.GONE);
        mobiletimeStaticTextView.setVisibility(View.GONE);
        mobileserveTimeInnerLayout.setVisibility(View.GONE);
    }

    public void DisplayFirstAtempt() {
        gpsdate1StaticTextView.setVisibility(View.VISIBLE);
        gpsdate1TextView.setVisibility(View.VISIBLE);
        gpsdate1linearLayout.setVisibility(View.VISIBLE);
        gpstime1StaticTextView.setVisibility(View.VISIBLE);
        gpstime1TextView.setVisibility(View.VISIBLE);
        firstAtmptDateInnerLayout.setVisibility(View.VISIBLE);
        mobiletimeoffirstattempt.setVisibility(View.VISIBLE);
        mobiletimeoffirstattemptStaticTextView.setVisibility(View.VISIBLE);
        mobiletimeoffirstattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
    }

    public void HideFirstAtempt() {
        gpsdate1StaticTextView.setVisibility(View.GONE);
        gpsdate1TextView.setVisibility(View.GONE);
        gpsdate1linearLayout.setVisibility(View.GONE);
        gpstime1StaticTextView.setVisibility(View.GONE);
        gpstime1TextView.setVisibility(View.GONE);
        firstAtmptDateInnerLayout.setVisibility(View.GONE);
        mobiletimeoffirstattempt.setVisibility(View.GONE);
        mobiletimeoffirstattemptStaticTextView.setVisibility(View.GONE);
        mobiletimeoffirstattemptserveTimeInnerLayout.setVisibility(View.GONE);
    }

    public void DisplaySecondAtempt() {
        gpsdate2StaticTextView.setVisibility(View.VISIBLE);
        gpsdate2TextView.setVisibility(View.VISIBLE);
        gpsdate2linearLayout.setVisibility(View.VISIBLE);
        gpstime2StaticTextView.setVisibility(View.VISIBLE);
        gpstime2TextView.setVisibility(View.VISIBLE);
        secondAtmptDateInnerLayout.setVisibility(View.VISIBLE);
        gpstime2linearLayout.setVisibility(View.VISIBLE);
        mobiletimeofsecondattemptStaticTextView.setVisibility(View.VISIBLE);
        mobiletimeofsecondattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
        mobiletimeofsecondattemptTextView.setVisibility(View.VISIBLE);
    }

    public void HideSecondAtempt() {
        gpsdate2StaticTextView.setVisibility(View.GONE);
        gpsdate2TextView.setVisibility(View.GONE);
        gpsdate2linearLayout.setVisibility(View.GONE);
        gpstime2StaticTextView.setVisibility(View.GONE);
        gpstime2TextView.setVisibility(View.GONE);
        secondAtmptDateInnerLayout.setVisibility(View.GONE);
        gpstime2linearLayout.setVisibility(View.GONE);
        mobiletimeofsecondattemptTextView.setVisibility(View.GONE);
        mobiletimeofsecondattemptStaticTextView.setVisibility(View.GONE);
        mobiletimeofsecondattemptserveTimeInnerLayout.setVisibility(View.GONE);
    }

    public void DisplayThirdAtempt() {
        gpsdate3StaticTextView.setVisibility(View.VISIBLE);
        gpsdate3TextView.setVisibility(View.VISIBLE);
        gpsdate3linearLayout.setVisibility(View.VISIBLE);
        gpstime3StaticTextView.setVisibility(View.VISIBLE);
        gpstime3TextView.setVisibility(View.VISIBLE);
        thirdAtmptDateInnerLayout.setVisibility(View.VISIBLE);
        thirdAtmptTimeInnerLayout.setVisibility(View.VISIBLE);
        mobiletimeofthirdattemptStaticTextView.setVisibility(View.VISIBLE);
        mobiletimeofthirdattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
        mobiletimeofthirdattemptTextView.setVisibility(View.VISIBLE);
    }

    public void HideThirdAtempt() {
        gpsdate3StaticTextView.setVisibility(View.GONE);
        gpsdate3TextView.setVisibility(View.GONE);
        gpsdate3linearLayout.setVisibility(View.GONE);
        thirdAtmptDateInnerLayout.setVisibility(View.GONE);
        gpstime3StaticTextView.setVisibility(View.GONE);
        gpstime3TextView.setVisibility(View.GONE);
        thirdAtmptTimeInnerLayout.setVisibility(View.GONE);
        mobiletimeofthirdattemptTextView.setVisibility(View.GONE);
        mobiletimeofthirdattemptStaticTextView.setVisibility(View.GONE);
        mobiletimeofthirdattemptserveTimeInnerLayout.setVisibility(View.GONE);
    }

    public void ShowFirstAttempt(boolean value) {
        if (!value) {
            gpsdate1StaticTextView.setVisibility(View.GONE);
            firstAtmptDateInnerLayout.setVisibility(View.GONE);
            gpsdate1TextView.setVisibility(View.GONE);
            gpstime1TextView.setVisibility(View.GONE);
            gpsdate1linearLayout.setVisibility(View.GONE);
            // gpstime1linearLayout.setVisibility(View.GONE);
            gpstime1StaticTextView.setVisibility(View.GONE);
            firstAtmptTimeInnerLayout.setVisibility(View.GONE);
            mobiletimeoffirstattempt.setVisibility(View.GONE);
            mobiletimeoffirstattemptStaticTextView.setVisibility(View.GONE);
            mobiletimeoffirstattemptserveTimeInnerLayout.setVisibility(View.GONE);
            // linearLayout1.setVisibility(View.GONE);
        } else {
            gpsdate1StaticTextView.setVisibility(View.VISIBLE);
            firstAtmptDateInnerLayout.setVisibility(View.VISIBLE);
            gpsdate1TextView.setVisibility(View.VISIBLE);
            gpstime1TextView.setVisibility(View.VISIBLE);
            //	gpstime1linearLayout.setVisibility(View.VISIBLE);
            gpsdate1linearLayout.setVisibility(View.VISIBLE);
            gpstime1StaticTextView.setVisibility(View.VISIBLE);
            firstAtmptTimeInnerLayout.setVisibility(View.VISIBLE);
            //linearLayout1.setVisibility(View.VISIBLE);
            if (sharedPrefs.getBoolean("show_complexview", true)) {
                mobiletimeoffirstattempt.setVisibility(View.VISIBLE);
            } else {
                mobiletimeoffirstattempt.setVisibility(View.GONE);
            }
            mobiletimeoffirstattemptStaticTextView.setVisibility(View.VISIBLE);
            mobiletimeoffirstattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
        }
    }

    public void ShowSecondAttempt(boolean value) {
        // TODO Auto-generated method stub
        if (!value) {
            gpsdate2StaticTextView.setVisibility(View.GONE);
            secondAtmptDateInnerLayout.setVisibility(View.GONE);
            gpsdate2TextView.setVisibility(View.GONE);
            gpstime2TextView.setVisibility(View.GONE);
            gpsdate2linearLayout.setVisibility(View.GONE);
            gpstime2linearLayout.setVisibility(View.GONE);

            gpstime2StaticTextView.setVisibility(View.GONE);
            secondAtmptTimeInnerLayout.setVisibility(View.GONE);
            gpstime2linearLayout.setVisibility(View.GONE);

            mobiletimeofsecondattemptTextView.setVisibility(View.GONE);
            mobiletimeofsecondattemptStaticTextView.setVisibility(View.GONE);
            mobiletimeofsecondattemptserveTimeInnerLayout.setVisibility(View.GONE);
        } else {
            gpsdate2StaticTextView.setVisibility(View.VISIBLE);
            secondAtmptDateInnerLayout.setVisibility(View.VISIBLE);
            gpsdate2TextView.setVisibility(View.VISIBLE);
            gpstime2TextView.setVisibility(View.VISIBLE);
            gpstime2linearLayout.setVisibility(View.VISIBLE);
            gpsdate2linearLayout.setVisibility(View.VISIBLE);
            gpstime2StaticTextView.setVisibility(View.VISIBLE);
            secondAtmptTimeInnerLayout.setVisibility(View.VISIBLE);
            gpstime2linearLayout.setVisibility(View.VISIBLE);
            if (sharedPrefs.getBoolean("show_complexview", true)) {
                mobiletimeofsecondattemptTextView.setVisibility(View.VISIBLE);
            } else {
                mobiletimeofsecondattemptTextView.setVisibility(View.GONE);
            }
            mobiletimeofsecondattemptStaticTextView.setVisibility(View.VISIBLE);
            mobiletimeofsecondattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param value if True : All data for third Attempt i.e date,Time of third attempt made visible
     *              and if False: All data for third Attempt i.e date,Time of third attempt made invisible
     */
    public void ShowThirdAttempt(boolean value) {
        // TODO Auto-generated method stub
        if (!value) {
            gpsdate3StaticTextView.setVisibility(View.GONE);
            thirdAtmptDateInnerLayout.setVisibility(View.GONE);
            gpsdate3TextView.setVisibility(View.GONE);
            gpstime3TextView.setVisibility(View.GONE);
            gpsdate3linearLayout.setVisibility(View.GONE);
            gpstime3StaticTextView.setVisibility(View.GONE);
            thirdAtmptTimeInnerLayout.setVisibility(View.GONE);
            gpstime1linearLayout.setVisibility(View.GONE);
            mobiletimeofthirdattemptTextView.setVisibility(View.GONE);
            mobiletimeofthirdattemptStaticTextView.setVisibility(View.GONE);
            mobiletimeofthirdattemptserveTimeInnerLayout.setVisibility(View.GONE);
        } else {
            gpsdate3StaticTextView.setVisibility(View.VISIBLE);
            thirdAtmptDateInnerLayout.setVisibility(View.VISIBLE);
            gpsdate3TextView.setVisibility(View.VISIBLE);
            gpstime3TextView.setVisibility(View.VISIBLE);
            gpsdate3linearLayout.setVisibility(View.VISIBLE);
            gpstime3StaticTextView.setVisibility(View.VISIBLE);
            thirdAtmptTimeInnerLayout.setVisibility(View.VISIBLE);
            gpstime1linearLayout.setVisibility(View.VISIBLE);
            if (sharedPrefs.getBoolean("show_complexview", true)) {
                mobiletimeofthirdattemptTextView.setVisibility(View.VISIBLE);
            } else {
                mobiletimeofthirdattemptTextView.setVisibility(View.GONE);
            }
            mobiletimeofthirdattemptStaticTextView.setVisibility(View.VISIBLE);
            mobiletimeofthirdattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
        }
    }

    public void ShowThirdAttemptwithlatlon(boolean value) {
        // TODO Auto-generated method stub
        if (!value) {
            gpsdate3StaticTextView.setVisibility(View.GONE);
            thirdAtmptDateInnerLayout.setVisibility(View.GONE);
            gpsdate3TextView.setVisibility(View.GONE);
            gpstime3TextView.setVisibility(View.GONE);
            gpsdate3linearLayout.setVisibility(View.GONE);
            gpstime3StaticTextView.setVisibility(View.GONE);
            thirdAtmptTimeInnerLayout.setVisibility(View.GONE);
            gpstime1linearLayout.setVisibility(View.GONE);
            mobiletimeofthirdattemptTextView.setVisibility(View.GONE);
            mobiletimeofthirdattemptStaticTextView.setVisibility(View.GONE);
            mobiletimeofthirdattemptserveTimeInnerLayout.setVisibility(View.GONE);
        } else {
            gpsdate3StaticTextView.setVisibility(View.VISIBLE);
            thirdAtmptDateInnerLayout.setVisibility(View.VISIBLE);
            gpsdate3TextView.setVisibility(View.VISIBLE);
            gpstime3TextView.setVisibility(View.VISIBLE);
            gpsdate3linearLayout.setVisibility(View.VISIBLE);
            gpstime3StaticTextView.setVisibility(View.VISIBLE);
            thirdAtmptTimeInnerLayout.setVisibility(View.VISIBLE);
            gpstime1linearLayout.setVisibility(View.VISIBLE);
            mobiletimeofthirdattemptTextView.setVisibility(View.VISIBLE);
            mobiletimeofthirdattemptStaticTextView.setVisibility(View.VISIBLE);
            mobiletimeofthirdattemptserveTimeInnerLayout.setVisibility(View.VISIBLE);
        }
    }

    public synchronized static String get_actualTimeSelectedTextView() {
        return _actualTimeSelectedTextView.getText().toString();
    }

    public synchronized static void set_actualTimeSelectedTextViewText(String _actualTimeSelectedTextView) {
        timeFormat = timeFormat + _actualTimeSelectedTextView;
        int actualLengthOfData = timeFormat.length();
        timeFormat = timeFormat.substring(1, actualLengthOfData);
        timeFormat = replaceCharAt(timeFormat, 1, timeFormat.charAt(2));
        timeFormat = replaceCharAt(timeFormat, 2, ':');
        EditCol1Fragment._actualTimeSelectedTextView.setText(timeFormat);
    }

    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    public boolean Check_Tomarrow_isHoliday() {

        cal.add(Calendar.DAY_OF_YEAR, 1);
        int _year = cal.get(Calendar.YEAR);
        int _month = cal.get(Calendar.MONTH) + 1;
        int _day = cal.get(Calendar.DAY_OF_MONTH);

//		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//	    Date convertedDate = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//        String dayOfTheWeek = sdf.format(convertedDate);
//        if((sdf.format(convertedDate).equalsIgnoreCase("Sunday"))){
//        	return false;
//        }
        if (Globals.Holiday_date.isEmpty()) {
            return false;
        } else {
            String todaysDate = "";
            todaysDate = String.valueOf(_year) + "-";
            String month = String.valueOf(_month);
            String day = String.valueOf(_day);
            if (month.length() < 2) {
                todaysDate = todaysDate + "0" + month + "-";
            } else {
                todaysDate = todaysDate + month + "-";
            }
            if (day.length() < 2) {
                todaysDate = todaysDate + "0" + day;
            } else {
                todaysDate = todaysDate + day;
            }

            for (String sdate : Globals.Holiday_date) {
                if (sdate.equals(todaysDate)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean Check_Today_isHoliday() {
        if (Globals.Holiday_date.isEmpty()) {
            return false;
        } else {
            String todaysDate = "";
            todaysDate = String.valueOf(mYear) + "-";
            String month = String.valueOf(mMonth);
            String day = String.valueOf(mDay);
            if (month.length() < 2) {
                todaysDate = todaysDate + "0" + month + "-";
            } else {
                todaysDate = todaysDate + month + "-";
            }
            if (day.length() < 2) {
                todaysDate = todaysDate + "0" + day;
            } else {
                todaysDate = todaysDate + day;
            }

            for (String sdate : Globals.Holiday_date) {
                if (sdate.equals(todaysDate)) {
                    return true;
                }
            }
        }
        return false;
    }
}