package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Download.LDDownloadActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ManupulateFile;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.GPSLocationProvider.GPSTracker;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessage;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessages;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessages.Builder;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload.InputStreamKnownSizeBody;

@SuppressLint({"DefaultLocale", "SimpleDateFormat"})
public class EditActivity extends FragmentActivity {
    View V;
    public String ProductCode, uploadflag;
    public String pserv1;
    public static String inputdate;
    public static String input;
    public int servicecomplete = 0;
    AuditLogReporter alreport;
    Cursor cursor;
    double lat1, lon1;
    private Location lastKnownLocation;
    private static final String url = Globals.Domain + Globals.DomainPathUpload;
    private LDDatabaseAdapter dbHelper;
    private static boolean isFirstAttemptCompleted = false;
    private static boolean isSecondAttemptCompleted = false;
    private static boolean isThirdAttemptCompleted = false;
    public static int dayPairTabSelectionOneTimeFlag = 0;
    private String rowID;
    // Golbal data for another Activity use:
    String Addressis, city, State, aptmt, first_day, second_day,
            By_Whom = "Not available !", Last_attempt = "nothing was",
            selected_weekdays_pref;
    StringBuilder FullAddr = new StringBuilder("not available !");
    String firsttrip = "", secondtrip = "", thirdtrip = "";
    String destAddr;

    /*public static TreeMap<Object, Object> address = new TreeMap<Object, Object>();
    public static TreeMap<Object, Object> address2 = new TreeMap<Object, Object>();
    public static TreeMap<Object, Object> address3 = new TreeMap<Object, Object>();
    public static TreeMap<Object, Object> firstTime = new TreeMap<Object, Object>();
    public static TreeMap<Object, Object> secondTime = new TreeMap<Object, Object>();
    public static TreeMap<Object, Object> thirdTime = new TreeMap<Object, Object>();
    public static int key = 0;
    public static int keyS = 0;
    public static int keyT = 0;
    public static int key1 = 0;
    public static int key2 = 0;
    public static int key3 = 0;*/
    boolean first = false;
    boolean second = false;
    boolean third = false;
    CheckBox check;
    GPSTracker gpstGlobal;
    public int mYear = 0;
    public int mMonth = 0;
    public int mDay = 0;
    public int mHour = 0;
    public int mMinute = 0;
    public int mNoon = 0;
    int EstimatedHour = 0, EstimatedMin = 0;
    LegalDeliveryMessages legalDeliveryMessages;
    Calendar cal = Calendar.getInstance();
    public int Day_Of_Week = cal.get(Calendar.DAY_OF_WEEK);
    public TextView lastDateView = null;
    static EditInputDataFragment editInputDataFragment;
    EditCol1Fragment editCol1Fragment;
    EditCol2Fragment editCol2Fragment;
    //EditCol0Fragment editCol0Fragment;
    EditInputDataFragment editinputfragment;
    //EditEntryFragment editentryfragment;
    //EditSexFragment editsexfragment;
    String tempString2 = null;
    final Set<LocalDate> _holidays = new HashSet<LocalDate>();
    Boolean canDeliverOnSaturday = true;
    EditText otherIdentifyingFeaturesP;
    EditText otherIdentifyingFeaturesC;
    TabHost editTabHost;
    TabHost.TabSpec spec;

    ProgressDialog mProgressDialog;
    Address latlong;
    Handler ToastMessageHandler;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    int hour, minute;

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        alreport = new AuditLogReporter();
        alreport.reportAudit("In Edit Activity!", this);
        // --------------All Date and Time initialization is done
        // here!------------------------------------------------//
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH) + 1;
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR);
        mMinute = cal.get(Calendar.MINUTE);
        mNoon = cal.get(Calendar.AM_PM);
        // -------------------------------------------------------------------------------------------------------------//
        // need of database is here to check holidays!
        try {
            // ----------Database initialization-----------//
            dbHelper = new LDDatabaseAdapter(this);
            dbHelper.open();
            alreport.reportAudit("Connected to database!", this);
            Bundle extras = getIntent().getExtras();

            // This code is to transform the view to
            if (extras != null) {
                rowID = extras.getString(LDDatabaseAdapter.KEY_ROWID);// getting
                // information
                // of
                // record
                // will
                // edited...
            }
            alreport.reportAudit("Getting Data frm previous activity:" + rowID, this);

            // ----------Getting Shared Preferences
            // here-------------------------//
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            editor = sharedPrefs.edit();
            // Fetch Data from database
            cursor = dbHelper.fetchLDAllColumns(rowID); // Fetching all Rows
            // from database//
            setContentView(R.layout.ld_edit);


            // //// functinality is supported > 3.O OS VERSION
			
			 /* Configuration config = getResources().getConfiguration();
			  if(config.smallestScreenWidthDp >= 600) {
				  setContentView(R.layout.ld_edit); 
			  } else {
				  setContentView(R.layout.ld_edit1);
			  }*/

            // All Fragment Initialization..//////////////////////////////
            editInputDataFragment = (EditInputDataFragment) getFragmentManager()
                    .findFragmentById(R.id.EditInputDataFragment);
            editCol1Fragment = (EditCol1Fragment) getFragmentManager()
                    .findFragmentById(R.id.EditCol1FragmentID);
            editCol2Fragment = (EditCol2Fragment) getFragmentManager()
                    .findFragmentById(R.id.EditCol2FragmentID);
            editCol2Fragment.setParentActivity(this);
			/*editCol0Fragment = (EditCol0Fragment) getFragmentManager()
					.findFragmentById(R.id.EditCol0FragmentID);*/
			/*editentryfragment = (EditEntryFragment)getFragmentManager()
					.findFragmentById(R.id.editentry);*/
			
			/*editsexfragment = (EditSexFragment)getFragmentManager()
					.findFragmentById(R.id.editsex);*/

            uploadflag = cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceUploadFlag));


            String ProductCode = cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_producttype));


            String inputdate = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_InputDate));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date11 = dateFormat.parse("29-02-2016");
            Date date22 = dateFormat.parse(inputdate);
            if (date11.before(date22)) {

                editInputDataFragment.edit_LicNum.setText("2033461");

            } else {
                editInputDataFragment.edit_LicNum.setText("1420662");

            }

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", android.content.Context.MODE_PRIVATE);
            Globals.ProductCode = pref.getString("product_code", null);

            if (EditInputDataFragment.getServiceType().equalsIgnoreCase("L&T Residential") || EditInputDataFragment.getServiceType().equalsIgnoreCase("L&T Commercial")) {
                if (!(getIsSecondAttemptCompleted()) && Globals.ProductCode.equalsIgnoreCase("B")) {
                    String m = (String) editCol1Fragment.gpstime1TextView.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss:SSS");
//			        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date date1;
                    Date date2;
                    try {
//						String stime = simpleDateFormatArrivals.format(date);
                        String timeRule1start = "06:00", timeRule1end = "07:59",
                                timeRule2start = "18:01", timeRule2end = "22:29",
                                timeRule3start = "09:00", timeRule3end = "16:59";

                        Date timeRule1Start = new SimpleDateFormat("HH:mm").parse(timeRule1start);
                        Date timeRule1End = new SimpleDateFormat("HH:mm").parse(timeRule1end);
                        Date timeRule2Start = new SimpleDateFormat("HH:mm").parse(timeRule2start);
                        Date timeRule2End = new SimpleDateFormat("HH:mm").parse(timeRule2end);
                        Date timeRule3Start = new SimpleDateFormat("HH:mm").parse(timeRule3start);
                        Date timeRule3End = new SimpleDateFormat("HH:mm").parse(timeRule3end);

                        Date datem1 = sdf3.parse(m);
                        if (datem1.after(timeRule3Start) && (datem1.before(timeRule3End))) {
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                        }
                        date1 = sdf.parse("1970-01-01 " + sdf3.format(datem1));

                        String n = (String) editCol1Fragment.gpstime2TextView.getText();
                       // String n = "13:23:42:174";
                        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                        Date date = displayFormat.parse(n);

                        date2 = sdf2.parse("1970-01-01 " + displayFormat.format(date));
                        if (((date1.after(timeRule3Start)) && (date1.before(timeRule3End))) && ((date2.after(timeRule3Start)) && (date2.before(timeRule3End)))) {
                        //    editCol2Fragment.saveButton.setEnabled(false);
                            //Toast.makeText(this, "Invalid Time, Please try again another time", Toast.LENGTH_LONG).show();
                        } else if (((date1.after(timeRule1Start)) && (date1.before(timeRule1End)) || ((date1.after(timeRule2Start)) && (date1.before(timeRule2End)))) &&
                                (((date2.after(timeRule1Start)) && (date2.before(timeRule1End))) || ((date2.after(timeRule2Start)) && (date2.before(timeRule2End))))) {
                            editCol2Fragment.saveButton.setEnabled(false);
                           // Toast.makeText(this, "Invalid Time, Please try again another time", Toast.LENGTH_LONG).show();
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            otherIdentifyingFeaturesP = (EditText) findViewById(R.id.otherIdentifyingFeaturesPEditText);
            otherIdentifyingFeaturesC = (EditText) findViewById(R.id.otherIdentifyingFeaturesCEditText);

            EditText editsex = (EditText) findViewById(R.id.editsex);
            EditText editSkin = (EditText) findViewById(R.id.editskin);
            EditText editHair = (EditText) findViewById(R.id.edithair);
            EditText editAge = (EditText) findViewById(R.id.editage);
            EditText editHeight = (EditText) findViewById(R.id.editheight);
            EditText editWeight = (EditText) findViewById(R.id.editweight);
            EditText editEntry = (EditText) findViewById(R.id.editentry);
            EditText editWall = (EditText) findViewById(R.id.editwall);
            EditText editFloor = (EditText) findViewById(R.id.editfloor);
            EditText editLock = (EditText) findViewById(R.id.editlock);
            EditText editDoo = (EditText) findViewById(R.id.editdoo);
            EditText editNoLock = (EditText) findViewById(R.id.editNoloc);
            String strPService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PServ));
            String strCService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_CServ));
            String[] mPServiceData = null;
            String[] mCServiceData = null;
            if (strPService != null) {
                mPServiceData = strPService.split(";");
            }
            if (strCService != null) {
                mCServiceData = strCService.split(";");
            }

            String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
            if (serviceResult != null) {
                if (serviceResult.equals("Personal") || serviceResult.equals("Personal Plus")
                        || serviceResult.equals("Substitute")) {

                    if (mPServiceData[0] == null) {
                        editsex.setText(" ");
                    } else {
                        editsex.setText(mPServiceData[0]);
                    }
                    if (mPServiceData[1] == null) {
                        editSkin.setText(" ");
                    } else {
                        editSkin.setText(mPServiceData[1]);
                    }
                    if (mPServiceData[2] == null) {
                        editHair.setText(" ");
                    } else {
                        editHair.setText(mPServiceData[2]);
                    }
                    if (mPServiceData[3] == null) {
                        editAge.setText(" ");
                    } else {
                        editAge.setText(mPServiceData[3]);
                    }
                    if (mPServiceData[4] == null) {
                        editHeight.setText(" ");
                    } else {
                        editHeight.setText(mPServiceData[4]);
                    }

                    //editsex.setText(mPServiceData[0]);
                    //editSkin.setText(mPServiceData[1]);
                    //editHair.setText(mPServiceData[2]);
                    //editAge.setText(mPServiceData[3]);
                    //editHeight.setText(mPServiceData[4]);
                    if (mPServiceData[5] == null) {
                        editWeight.setText(" ");
                    } else {
                        editWeight.setText(mPServiceData[5]);
                    }

                } else {
                    if (mCServiceData[0] == null) {
                        editEntry.setText(" ");
                    } else {
                        editEntry.setText(mCServiceData[0]);
                    }

                    if (mCServiceData[1] == null) {
                        editWall.setText(" ");
                    } else {
                        editWall.setText(mCServiceData[1]);
                    }

                    if (mCServiceData[2] == null) {
                        editFloor.setText(" ");
                    } else {
                        editFloor.setText(mCServiceData[2]);
                    }

                    if (mCServiceData[3] == null) {
                        editLock.setText(" ");
                    } else {
                        editLock.setText(mCServiceData[3]);
                    }

                    if (mCServiceData[4] == null) {
                        editDoo.setText(" ");
                    } else {
                        editDoo.setText(mCServiceData[4]);
                    }

                    if (mCServiceData[5] == null) {
                        editNoLock.setText(" ");
                    } else {
                        editNoLock.setText(mCServiceData[5]);
                    }

                    //editEntry.setText(mCServiceData[0]);
                    //editWall.setText(mCServiceData[1]);
                    //editFloor.setText(mCServiceData[2]);
                    //editLock.setText(mCServiceData[3]);
                    //	editDoo.setText(mCServiceData[4]);
                    //editNoLock.setText(mCServiceData[5]);
                }
                //	String st1= editsex.getText();

                String getTextsex = editsex.getText().toString();
                //editsex.setText(getTextsex);

                String getTextSkin = editSkin.getText().toString();
                //	editsex.setText(getTextSkin);

                String getTexthair = editHair.getText().toString();
                //editsex.setText(getTexthair);

                String getTextage = editAge.getText().toString();
                //editsex.setText(getTextage);

                String getTextheight = editHeight.getText().toString();
                //editsex.setText(getTextheight);

                String getTextweight = editWeight.getText().toString();
                //editsex.setText(getTextweight);

                String mPersonDesc = getTextsex + ";" + getTextSkin + ";" + getTexthair + ";" +
                        getTextage + ";" + getTextheight + ";" + getTextweight;
                //Builder legalDeliveryMessage;
                //	legalDeliveryMessage.setPServ(mPersonDesc);
		      
		
			/*String getTextentry = editEntry.getText().toString();
			//String mentry=getC_Entry() != null ? getC_Entry() : "";
			
			editsex.setText(getTextentry);
			
			String getTextwall = editWall.getText().toString();
			editsex.setText(getTextwall);
			
			String getTextfloor = editFloor.getText().toString();
			editsex.setText(getTexthair);
			
			String getTextlock = editLock.getText().toString();
			editsex.setText(getTextlock);
			
			String getTextdoo = editDoo.getText().toString();
			editsex.setText(getTextdoo);
			
			String getTextnolock = editNoLock.getText().toString();
			editsex.setText(getTextnolock);
			
			String mDesc= getTextentry + ";" +  getTextwall  + ";" + getTextfloor  + ";" +
					getTextlock   + ";" +  getTextdoo   + ";" +  getTextnolock; 
			 */
                //legalDeliveryMessage.setPServ(mDesc);
		     
			
		/*	
			String mC_Entry = mData.get(I).getC_Entry() != null ? mData.get(I).getC_Entry() : "";
		      String mC_Wall = mData.get(I).getC_Wall() != null ? mData.get(I).getC_Wall() : "";
		      String mC_Door = mData.get(I).getC_Door() != null ? mData.get(I).getC_Door() : "";
		      String mC_Floor = mData.get(I).getC_Floor() != null ? mData.get(I).getC_Floor() : "";
		      String mC_Lock = mData.get(I).getC_Lock() != null ? mData.get(I).getC_Lock() : "";
		     
		      String mDoorLock = mData.get(I).getDoorLock() != null ? mData.get(I).getDoorLock() : "";
			     
		      String mPersonDescrption =  mGender + ";" + mP_Skincolor + ";" +
		    		  mP_Hair + ";" +mP_Age + ";" + mP_Height + ";" + mP_Weight;
		      builderLegalDeliveryMessage.setPServ(mPersonDescrption);*/

            }
            if (Globals.ProductCode.equalsIgnoreCase("B")) {
                getFragmentManager()
                        .findFragmentById(R.id.Ed).getView().setVisibility(View.GONE);
            }            // Tab hosting or Tab manipulating//
            editTabHost = (TabHost) findViewById(R.id.tabhost);
            editTabHost.setup();
            spec = editTabHost.newTabSpec("tag1");
            // tab 1
            spec.setContent(R.id.tab1);
            spec.setIndicator("Update Delivery Info");
            editTabHost.addTab(spec);
            // tab 2
            spec = editTabHost.newTabSpec("tag2");
            spec.setContent(R.id.pserviceTab);
            spec.setIndicator("Personal/Plus/Substitute Service");
            editTabHost.addTab(spec);
            // tab 3
            spec = editTabHost.newTabSpec("tag2");
            spec.setContent(R.id.cserviceTab);
            spec.setIndicator("\"C\" Service");
            editTabHost.addTab(spec);
            populateFields();

//			  TabHost tabHost;

            TabHost host = (TabHost) findViewById(R.id.tabHost);
            host.setup();

            //Tab 1
            TabHost.TabSpec spec = host.newTabSpec("Tab One");
            spec.setContent(R.id.tab11);
            spec.setIndicator("Tab One");
            host.addTab(spec);

            //Tab 2
            spec = host.newTabSpec("Tab Two");
            spec.setContent(R.id.tab22);
            spec.setIndicator("Tab Two");
            host.addTab(spec);

            //Tab 3
		      /*  spec = host.newTabSpec("Tab Three");
		        spec.setContent(R.id.tab33);
		        spec.setIndicator("Tab Three");
		        host.addTab(spec);*/


            checkIfResultIsCreated();
            // -------------is Today is Sunday?-------------------------------//
            if (Globals.isToday_is_Sunday(this)) {
                show_Holiday_Details("Sunday");// Show Sunday Dialog//
            } else if (Check_Today_isHoliday()) {
                show_Holiday_Details("Holiday");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("date_exception", e.getMessage());
            StringWriter st = new StringWriter();
            e.printStackTrace(new PrintWriter(st));
            ManupulateFile manFile = new ManupulateFile();
            manFile.WriteToFile(st.toString(), this);
            ErroReportingInBackground errreprt = new ErroReportingInBackground(this);
            errreprt.execute(st.toString());
            Toast.makeText(this, "Error reported through mail!", Toast.LENGTH_SHORT).show();
        }
        ToastMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("message");
                if (message != null)
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void checkIfResultIsCreated() {
        if (editCol2Fragment.GetServiceCompleted()) {
            //editentryfragment.saveButton3.setClickable(false);
            //editentryfragment.saveButton3.setEnabled(false);
            //	editsexfragment.saveButton.setClickable(false);
            //editsexfragment.saveButton.setEnabled(false);
            editCol2Fragment.saveButton.setClickable(false);
        } else {
            // editCol2Fragment.saveButton.setEnabled(true);
            editCol1Fragment.mobiletimeTextView.setClickable(true);
            editCol1Fragment.gpstime1TextView.setClickable(true);
            editCol1Fragment.gpstime2TextView.setClickable(true);
            editCol1Fragment.dateofserviceTextView.setClickable(true);
            editCol1Fragment.mobiletimeoffirstattemptStaticTextView.setClickable(true);
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new TimePickerDialog(this, mTimeSetListener1, hour, minute, false);
            case 1:
                return new TimePickerDialog(this, mTimeSetListener2, hour, minute, false);
            case 2:
                return new TimePickerDialog(this, mTimeSetListener3, hour, minute, false);
            default:
                break;
        }
        return null;
    }

    ;

    private TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String min = Integer.toString(minute);
            if (minute < 10) {
                min = "0" + min;
            }
            String hour = Integer.toString(hourOfDay);
            if (hourOfDay < 10) {
                hour = "0" + hour;
            }
            String inputTime = hour + ":" + min;
            if (EditInputDataFragment.getServiceType().equalsIgnoreCase("L&T Residential")) {
                boolean isTimeValid = checkIsTimeValid(inputTime);
                if (isTimeValid) {
                    editCol1Fragment.SetGpsTime1(inputTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid time, please select again!", Toast.LENGTH_LONG).show();
                }
            } else {
                editCol1Fragment.SetGpsTime1(inputTime);
            }
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String min = Integer.toString(minute);
            if (minute < 10) {
                min = "0" + min;
            }
            String hour = Integer.toString(hourOfDay);
            if (hourOfDay < 10) {
                hour = "0" + hour;
            }
            editCol1Fragment.SetGpsTime2(hour + ":" + min);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener3 = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String min = Integer.toString(minute);
            if (minute < 10) {
                min = "0" + min;
            }
            String hour = Integer.toString(hourOfDay);
            if (hourOfDay < 10) {
                hour = "0" + hour;
            }

            editCol1Fragment.SetGpsTime3(hour + ":" + min);
        }
    };

    public static boolean checkIsTimeValid(String inputTime) {
        boolean result = false;
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
            if (((actualInputTime.after(timeRule1Start)) && (actualInputTime.before(timeRule1End))) ||
                    ((actualInputTime.after(timeRule2Start)) && (actualInputTime.before(timeRule2End))) ||
                    ((actualInputTime.after(timeRule3Start)) && (actualInputTime.before(timeRule3End)))) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    // to update console
    boolean Check_Today_isHoliday() {
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

    /**
     * Shows alert Dialog if its Sunday with OK Button if You Click OK it will
     * finish your Activity.
     */
    private void show_Holiday_Details(String msg) {
		/*AlertDialog.Builder builder_not_sunday = new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_action_warning).setTitle("Warning !")
				.setMessage("Today is "+msg+" service can not be delivered.")
				.setCancelable(false)
				.setPositiveButton("ok", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {*/

        getIntent();
        editCol1Fragment.dateofserviceTextView
                .setClickable(false);
        editCol1Fragment.timeofserviceTextView
                .setClickable(false);
        editCol1Fragment.mobiletimeoffirstattempt
                .setClickable(false);
        editCol1Fragment.dateofserviceTextView.setClickable(false);
        editCol1Fragment.dateofserviceTextView.setClickable(false);
        editCol1Fragment.mobiletimeofsecondattemptTextView
                .setClickable(false);
        editCol1Fragment.serviceresultSpinner.setClickable(false);
        editCol1Fragment.serviceresultSpinner.setEnabled(false);
        editCol1Fragment.gpstime2TextView.setClickable(false);
        editCol1Fragment.gpsdate2TextView.setClickable(false);
        editCol1Fragment.gpsdate3TextView.setClickable(false);
        editCol1Fragment.gpstime3TextView.setClickable(false);
        editCol1Fragment.gpsdate1TextView.setClickable(false);
        editCol1Fragment.gpstime1TextView.setClickable(false);
        editCol1Fragment.mobiletimeofsecondattemptTextView
                .setClickable(false);
        editCol2Fragment.serviceFirstAttemptCheckBox
                .setClickable(false);
        editCol2Fragment.personSeenPersonalEditText.setEnabled(false);
        editCol2Fragment.personSeenSubstituteEditText.setEnabled(false);
        editCol2Fragment.personSeenSubstituteTextView.setClickable(false);
        editCol2Fragment.personNotSeenPersonalPlusEditText.setEnabled(false);
        editCol2Fragment.personNotSeenPersonalPlusTextView.setClickable(false);
        editCol2Fragment.serviceSecondAttemptCheckBox
                .setClickable(false);
        editCol2Fragment.serviceThirdAttemptCheckBox
                .setClickable(false);
        editCol2Fragment.serviceCompletedCheckBox
                .setClickable(false);
        editCol2Fragment.saveButton.setClickable(false);
        editCol2Fragment.saveButton.setEnabled(false);
        editCol2Fragment.personSeenPersonalEditText
                .setClickable(false);
        editCol2Fragment.personSeenSubstituteEditText
                .setClickable(false);
        editCol2Fragment.personNotSeenPersonalPlusEditText
                .setClickable(false);
        editCol2Fragment.personSeenPersonalTextView
                .setClickable(false);
        //editentryfragment.saveButton3.setClickable(false);
        //	editentryfragment.saveButton3.setEnabled(false);
        //editsexfragment.saveButton.setClickable(false);
        //editsexfragment.saveButton.setEnabled(false);
        Toast.makeText(this, "Today is " + msg + " service can't be delivered.", Toast.LENGTH_LONG).show();
        //}
				/*});
		AlertDialog alert_not_sunday = builder_not_sunday.create();
		alreport.reportAudit("Holiday Details:" + msg, this);
		alert_not_sunday.show();*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public void onBackPressed() {
        if (!(editTabHost == null))// this is mediate sudheer...
            if (editTabHost.getCurrentTab() == 0)
                super.onBackPressed();
            else
                editTabHost.setCurrentTab(0);
    }

    /**
     * @return cursor of all Fetched All data Columns;
     */
    public Cursor GetCursor() {
        return cursor;
    }

    /**
     * @return Return cursor of Related person database.
     */
    public Cursor GetRelatedPersonCursor() {
        String legalDeliveryID = cursor.getString(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ROWID));
        return dbHelper.fetchRelatedPersons(legalDeliveryID);
    }

    public String GetServiceResult() {
        return editCol1Fragment.GetServiceResult();
    }

    public Address getLocation(Location baseLocation) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Address location = null;
        try {
            address = coder.getFromLocation(baseLocation.getLatitude(), baseLocation.getLongitude(), 5);
            if (address == null) {
                return null;
            }
            location = address.get(0);
            location.getLatitude();
            location.getLongitude();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return location;
    }

    private boolean SaveFirstAttempt(Location location) {
        boolean bRet = false;
        try {
            alreport.reportAudit("Saving First Attempt...", this);
            Calendar cal = Calendar.getInstance();
            int gpsYear = cal.get(Calendar.YEAR);
            int gpsMonth = cal.get(Calendar.MONTH) + 1;
            int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
            Random rand = new Random();
            Integer gpsSec = rand.nextInt(58) + 1;
            Integer gpsMsec = rand.nextInt(850) + 1;
			/*address.put(key, FullAddr.toString());
			key++;*/

            lat1 = Double.parseDouble(editCol2Fragment.lat1TextView.getText().toString());
            lon1 = Double.parseDouble(editCol2Fragment.lon1TextView.getText().toString());

            String strGpsDate1 = String.format("%02d-%02d-%04d", gpsMonth, gpsDay, gpsYear);
            int gpshour = 0, gpsmin = 0;
            int mobSec;
            if (gpsSec < 49) {
                mobSec = gpsSec + rand.nextInt(10) + 1;
            } else {
                mobSec = gpsSec + 2;
            }
            int mobMsec = rand.nextInt(850) + 1;

            String EststrGpsTime1 = editCol1Fragment.GetGpsTime1();
            if (EststrGpsTime1.length() > 0) {
                gpshour = Integer.parseInt(EststrGpsTime1.substring(0, 2));
                gpsmin = Integer.parseInt(EststrGpsTime1.substring(3, 5));
            }

            EststrGpsTime1 = String.format("%02d:%02d:%02d:%03d", gpshour, gpsmin, gpsSec, gpsMsec);
            String mobTime = String.format("%02d:%02d:%02d:%03d", gpshour, gpsmin, mobSec, mobMsec);

            editCol1Fragment.SetGpsTime1(EststrGpsTime1);
            editCol1Fragment.setMobileTime1(mobTime);
            //}

            //EststrGpsTime1 = editCol1Fragment.GetGpsTime1();
            //String mobileTime1 = editCol1Fragment.getMobileTime1();
            String serviceType = editInputDataFragment.GetServiceType();

            String ActualDate;
            if (!editCol2Fragment.serviceCompletedCheckBox.isChecked()
                    && editCol2Fragment.serviceFirstAttemptCheckBox.isChecked()
                    && editCol2Fragment.serviceSecondAttemptCheckBox.isChecked()) {
                editCol2Fragment.SetServiceCompleted(true);
                servicecomplete = 1;
            }
            if (editCol2Fragment.GetServiceCompleted()) {
                servicecomplete = 1;
            }
            if (serviceType.equalsIgnoreCase("L&T Residential") || serviceType.equalsIgnoreCase("L&T Commercial")) {
                ActualDate = editCol1Fragment.gpsdate1TextView.getText().toString();
            } else {
                ActualDate = strGpsDate1;
            }
            if (lat1 > 0) {
                String saveCurrentAddress = getCurrentAddress();
                if (saveCurrentAddress.length() > 0) {
                    String EststrGpsTime2 = editCol1Fragment.GetGpsTime2();
                    LegalDeliveryActivity.setPreviousAttemptAddress(saveCurrentAddress);
                    LegalDeliveryActivity.setPreviousFirstAttemptTime(EststrGpsTime1);
                    if (!(EststrGpsTime2.equalsIgnoreCase("[Not Set]"))) {
                        LegalDeliveryActivity.setPreviousSecondAttemptTime(EststrGpsTime2);
                    } else {
                        LegalDeliveryActivity.setPreviousSecondAttemptTime("");
                    }
                }
                //String szz=LegalDeliveryActivity.setPreviousAttemptAddress(saveCurrentAddress);
            }

            bRet = dbHelper.updateLD_FirstAttempt(rowID, ActualDate,
                    EststrGpsTime1, lat1, lon1, mobTime, servicecomplete);
            if (bRet) {
                editCol1Fragment.SetGpsTime1(EststrGpsTime1);
                editCol1Fragment.setMobileTime1(mobTime);
                //firstTime.put(key1, EststrGpsTime1);
                editCol1Fragment.SetGpsDate1(ActualDate);
                editCol2Fragment.SetFirstAttempt(true);
                //key1++;
                alreport.reportAudit("first attempt added in collection", this);
                ToastMsg("First attempt updated!");
            } else {
                ToastMsg("Error in saving first attempt.");
            }
            alreport.reportAudit("Result for saving 1st Attempt? " + bRet, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bRet;
    }

    private boolean SaveSecondAttempt(Location location) {
        alreport.reportAudit("Saving 2nd attmpt...", this);
        //address2.put(keyS, FullAddr.toString());
        //keyS++;
        Calendar cal = Calendar.getInstance();
        EstimatedHour = 0;
        EstimatedMin = 0;
        int gpshour = 0, gpsmin = 0;
        int gpsYear = cal.get(Calendar.YEAR);
        int gpsMonth = cal.get(Calendar.MONTH) + 1;
        int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
        Random rand = new Random();
        Integer gpsSec = rand.nextInt(58) + 1;
        Integer gpsMsec = rand.nextInt(850) + 1;
        double lat2, lon2;
        lat2 = Double.parseDouble(editCol2Fragment.lat2TextView.getText().toString());
        lon2 = Double.parseDouble(editCol2Fragment.lon2TextView.getText().toString());

        int mobSec;
        if (gpsSec < 49) {
            mobSec = gpsSec + rand.nextInt(10) + 1;
        } else {
            mobSec = gpsSec + 2;
        }
        int mobMsec = rand.nextInt(850) + 1;
        String EststrGpsTime2 = editCol1Fragment.GetGpsTime2();

        if (EststrGpsTime2.length() > 0) {
            gpshour = Integer.parseInt(EststrGpsTime2.substring(0, 2));
            gpsmin = Integer.parseInt(EststrGpsTime2.substring(3, 5));
        }
        EststrGpsTime2 = String.format("%02d:%02d:%02d:%03d", gpshour, gpsmin, gpsSec, gpsMsec);
        String mobTime2 = String.format("%02d:%02d:%02d:%03d", gpshour, gpsmin, mobSec, mobMsec);
        editCol1Fragment.SetGpsTime2(EststrGpsTime2);
        editCol1Fragment.setMobileTime2(mobTime2);
        //}
        String Actualdate;
        String strGpsDate2 = String.format("%02d-%02d-%04d", gpsMonth, gpsDay, gpsYear);
        EststrGpsTime2 = editCol1Fragment.GetGpsTime2();
        String mobileTime2 = editCol1Fragment.getMobileTime2();
        boolean bRet;
        String serviceType = editInputDataFragment.GetServiceType();
        if (serviceType.equalsIgnoreCase("L&T Residential") || serviceType.equalsIgnoreCase("L&T Commercial")) {
            Actualdate = editCol1Fragment.gpsdate2TextView.getText().toString();
        } else {
            Actualdate = strGpsDate2;
        }
        if (editCol2Fragment.serviceCompletedCheckBox.isChecked()) {
            servicecomplete = 1;
        }
        if (lat2 > 0) {
            String saveCurrentAddress = getCurrentAddress();
            if (saveCurrentAddress.length() > 0) {
                String EststrGpsTime1 = editCol1Fragment.GetGpsTime1();
                LegalDeliveryActivity.setPreviousAttemptAddress(saveCurrentAddress);
                LegalDeliveryActivity.setPreviousSecondAttemptTime(EststrGpsTime2);
                LegalDeliveryActivity.setPreviousFirstAttemptTime(EststrGpsTime1);
            }
        }
        bRet = dbHelper.updateLD_SecondAttempt(rowID, Actualdate,
                EststrGpsTime2, lat2, lon2, mobileTime2, servicecomplete);

        if (bRet) {
            editCol1Fragment.SetGpsTime2(EststrGpsTime2);
            editCol1Fragment.setMobileTime2(mobileTime2);
            // change is here as saving in first attempt!
            editCol1Fragment.SetGpsDate2(Actualdate);
            editCol2Fragment.SetSecondAttempt(true);
            //secondTime.put(key2, EststrGpsTime2);
            //key2++;setPreviousAttemptAddress
            alreport.reportAudit("Second attempt added in the collection", this);
            ToastMsg("Second attempt Updated!");
        } else {
            ToastMsg("Error in saving second attempt.");
        }
        alreport.reportAudit("Result for saving 2nd Attmpt?" + bRet, this);
        return bRet;
    }

    /**
     * @param location provides lats & longs
     * @return if Date & Time updated successfully to database then returns
     * true.
     */
    private boolean SaveThirdAttempt(Location location) {

        alreport.reportAudit("Saving 3rd Attempt...", this);
        //address3.put(keyT, FullAddr.toString());
        //keyT++;
        Calendar cal = Calendar.getInstance();
        Calendar cGPS = Calendar.getInstance();
        cGPS.setTimeInMillis(location.getTime());
        EstimatedHour = 0;
        EstimatedMin = 0;
        int gpsYear = cal.get(Calendar.YEAR);
        int gpsMonth = cal.get(Calendar.MONTH) + 1;
        int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
        Random rand = new Random();
        Integer gpsSec = rand.nextInt(58) + 1;
        Integer gpsMsec = rand.nextInt(850) + 1;
        double lat3, lon3;
        lat3 = Double.parseDouble(editCol2Fragment.lat3TextView.getText().toString());
        lon3 = Double.parseDouble(editCol2Fragment.lon3TextView.getText().toString());


        String strGpsDate3 = String.format("%02d-%02d-%04d", gpsMonth, gpsDay, gpsYear);

        int gpshour = 0, gpsmin = 0;
        int mobSec;
        if (gpsSec < 49) {
            mobSec = gpsSec + rand.nextInt(10) + 1;
        } else {
            mobSec = gpsSec + 2;
        }
        int mobMsec = rand.nextInt(850) + 1;

        String EststrGpsTime3 = editCol1Fragment.GetGpsTime1();

        if (EststrGpsTime3.length() > 0) {
            gpshour = Integer.parseInt(EststrGpsTime3.substring(0, 2));
            gpsmin = Integer.parseInt(EststrGpsTime3.substring(3, 5));
        }

        EststrGpsTime3 = String.format("%02d:%02d:%02d:%03d", gpshour, gpsmin, gpsSec, gpsMsec);
        String mobTime3 = String.format("%02d:%02d:%02d:%03d", gpshour, gpsmin, mobSec, mobMsec);

        editCol1Fragment.SetGpsTime3(EststrGpsTime3);
        editCol1Fragment.setMobileTime3(mobTime3);
        //}
        EststrGpsTime3 = editCol1Fragment.GetGpsTime3();
        String mobileTime3 = editCol1Fragment.getMobileTime3();
        if (editCol2Fragment.serviceCompletedCheckBox.isChecked()) {
            servicecomplete = 1;
        }
        if (lat3 > 0) {
            String saveCurrentAddress = getCurrentAddress();
            if (saveCurrentAddress.length() > 0) {
                LegalDeliveryActivity.setPreviousAttemptAddress(saveCurrentAddress);
                LegalDeliveryActivity.setPreviousFirstAttemptTime(EststrGpsTime3);
            }
        }
        boolean bRet = dbHelper.updateLD_ThirdAttempt(rowID, strGpsDate3,
                EststrGpsTime3, lat3, lon3, mobileTime3, servicecomplete);

        if (bRet) {
            editCol1Fragment.SetGpsTime3(EststrGpsTime3);
            editCol1Fragment.setMobileTime3(mobileTime3);
            editCol1Fragment.SetGpsDate3(strGpsDate3);
            editCol2Fragment.SetThirdAttempt(true);
            //thirdTime.put(key3, EststrGpsTime3);
            //key3++;
            alreport.reportAudit("Third attempts are added in collection", this);
            ToastMsg("Third Attempt Updated!");

        } else {
            ToastMsg("Error in saving third attempt.");
        }
        alreport.reportAudit("Result for saving 3rd attempt" + bRet, this);
        return bRet;
    }


    public void SaveRecord(Location location) {
        try {
            alreport.reportAudit("In Saving Process Starts Here!...", this);
            boolean validationResult = false;
            String strServiceResult = editCol1Fragment.GetServiceResult();
            String strServiceType = editInputDataFragment.GetServiceType();

            if (strServiceType.equals("L&T Residential")
                    || strServiceType.equals("L&T Commercial")) {
                validationResult = saveLTService(strServiceResult, location);
            } else {
                validationResult = saveStdService(strServiceResult, location);
            }
            if (validationResult) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Saved Successfully!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean saveLTService(String pServiceResult, Location location) {
        alreport.reportAudit("Saving LT Record with Service result:" + pServiceResult, this);
        // mitesh
        boolean result = true;
        boolean serviceUpdated = false;
        int iAttempt = 1;

        if (editCol2Fragment.GetFirstAttempt() == true) {
            result = SaveFirstAttempt(location);
            serviceUpdated = result;
            iAttempt = 1;
        }
        if (!result) {
            return result;
        }
        if (editCol2Fragment.GetSecondAttempt() == true) {
            if (location != null) {
                result = SaveSecondAttempt(location);
                serviceUpdated = result;
                iAttempt = 2;
            }
        }
        if (!result) {
            return result;
        }
        switch (pServiceResult) {
            case "Conspicuous":
                result = SaveConspResult(iAttempt, pServiceResult);
                break;
            case "Personal":
                result = SaveOtherResult(iAttempt, pServiceResult);
                break;
            case "Personal Plus":
                result = SaveOtherResult(iAttempt, pServiceResult);
                break;
            case "Substitute":
                result = SaveOtherResult(iAttempt, pServiceResult);
                break;
            default:
                if (!serviceUpdated) {
                    ToastMsg("Service Not Updated! Error in Saving!");
                }
                break;
        }
        alreport.reportAudit("Result for saving LT record is:" + result, this);
        if (result) {
            uploadToServer();
        }
        return result;
    }

    private boolean saveStdService(String pServiceResult, Location location) {
        boolean result = true;
        int iAttempt = 1;

        //address.put(key, FullAddr.toString());
        //key++;


        boolean serviceUpdated = false;

        if (editCol2Fragment.GetFirstAttempt() == true) {
            result = SaveFirstAttempt(location);
            serviceUpdated = result;
            iAttempt = 1;
        }
        if (!result) {
            return result;
        }

        if (editCol2Fragment.GetSecondAttempt() == true) {
            result = SaveSecondAttempt(location);
            serviceUpdated = result;
            iAttempt = 2;
        }
        if (!result) {
            return result;
        }

        if (editCol2Fragment.GetThirdAttempt() == true) {
            result = SaveThirdAttempt(location);
            serviceUpdated = result;
            iAttempt = 3;
        }
        if (!result) {
            return result;
        }
        if (pServiceResult.equals("Conspicuous")) {
            result = SaveConspResult(iAttempt, pServiceResult);
        } else if (pServiceResult.equals("Personal")) {
            result = SaveOtherResult(iAttempt, pServiceResult);
        } else if (pServiceResult.equals("Personal Plus")) {
            result = SaveOtherResult(iAttempt, pServiceResult);
        } else if (pServiceResult.equals("Substitute")) {
            result = SaveOtherResult(iAttempt, pServiceResult);
        } else if (pServiceResult.equals("Corporation")) {
            result = SaveOtherResult(iAttempt, pServiceResult);
        } else {
            if (!serviceUpdated) {
                ToastMsg("Service Not Updated!");
            }

        }
        if (result) {
            uploadToServer();
        }
        return result;
    }


    public void uploadToServer() {
        Thread uploadDataToServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = dbHelper.fetchAllCompletedServicesAllColumns();

                for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
                    String rowId = cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ROWID));
                    if (rowId.equalsIgnoreCase(rowID)) {
                        Cursor cursor1 = dbHelper.fetchLD_UploadFlag(rowID);
                        String updateStatusFlag = cursor1.getString(0);
                        if (updateStatusFlag.equals("0")) {
                            LegalDeliveryMessage legalDeliveryMessage = LegalDeliveryMessage.newBuilder()
                                    .setLegalDeliveryID(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ROWID)))
                                    .setJobNo(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_JobNo)))
                                    .setClient(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_Client)))
                                    .setServiceType(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceType)))
                                    .setLTServiceType(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTServiceType)))
                                    .setCaseNo(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CaseNo)))
                                    .setLTFullname(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTFullname)))
                                    .setSTND_FULLNAME(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_FullName)))
                                    .setPersonSeenSubstitute(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenSubstitute)) == null ? cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenDoeSubstitute)) : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenSubstitute)))
                                    .setPersonSeenDoeSubstitute(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenDoeSubstitute)))
                                    .setPersonSeenPersonal(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonSeenPersonal)))
                                    .setPersonsNotSeenPersonal(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PersonsNotSeenPersonal)))
                                    .setCorpRecipient(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CorpRecipient)))
                                    .setCorpRecipientTitle(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CorpRecipientTitle)))
                                    .setLTAddress(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTAddress)))
                                    .setSTND_ADDRESS(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Address)))
                                    .setLTApt(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTApt)))
                                    .setSTND_APT(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Apt)))
                                    .setLTCity(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTCity)))
                                    .setSTND_CITY(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_City)))
                                    .setLTState(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTState)))
                                    .setSTND_STATE(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_State)))
                                    .setLTZip(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTZip)))
                                    .setSTND_ZIP(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Zip)))
                                    .setDateOfService((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DateOfService))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DateOfService)))
                                    .setTimeOfService((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_TimeOfService))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_TimeOfService)))
                                    .setGpsTimeOfService((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfService))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfService)))
                                    .setGPSDate1((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate1))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate1)))
                                    .setGPSTime1((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime1))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime1)))
                                    .setGpsTimeOfFirstAttempt((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfFirstAttempt))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfFirstAttempt)))

                                    .setGPSLat1(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLat1)))
                                    .setGPSLon1(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLon1)))
                                    .setGPSDate2((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate2))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate2)))
                                    .setGPSTime2((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime2))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime2)))
                                    .setGpsTimeOfSecondAttempt((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfSecondAttempt))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfSecondAttempt)))

                                    .setGPSLat2(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLat2)))
                                    .setGPSLon2(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLon2)))
                                    .setGPSDate3((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate3))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSDate3)))
                                    .setGPSTime3((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime3))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSTime3)))
                                    .setGpsTimeOfThirdAttempt((cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfThirdAttempt))) == null ? "[Not Set]" : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GpsTimeOfThirdAttempt)))

                                    .setGPSLat3(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLat3)))
                                    .setGPSLon3(cursor.getDouble(cursor.getColumnIndex(LDDatabaseAdapter.KEY_GPSLon3)))
                                    .setServiceResult(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceResult)))
                                    .setInputDate(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_InputDate)))
                                    .setDoorLock(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_DoorLock)))
                                    .setPServ(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PServ)))
                                    .setCServ(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_CServ)))//The below code(WTF! code) is just becoz of .net devloper modified to single OtherIdentifier Field! so I changed this code!
                                    .setOtherCommentsP(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsP)) == null ? cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsC)) : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsP)))
                                    .setOtherCommentsC(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsC)) == null ? cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsP)) : cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_OtherCommentsC)))
                                    .setFirstAttempt(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_FirstAttempt)))

                                    .setSecondAttempt(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_SecondAttempt)))
                                    .setThirdAttempt(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ThirdAttempt)))
                                    .setServiceCompleted(cursor.getInt(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceCompleted)))
                                    .setSTND_ServiceType(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_ServiceType)))
                                    .setLTBIZNAME(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTBIZNAME)))
                                    .setPPSERVED1(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_PPSERVED1)))
                                    .setServerLicenceNo(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServerLicenceNo)))

                                    .setproducttype(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_producttype)))
                                    .build();

                            int pt = (cursor.getColumnIndex(LDDatabaseAdapter.KEY_producttype));
                            LegalDeliveryMessages.Builder ldmBuilder = LegalDeliveryMessages.newBuilder();
                            ldmBuilder.addElementLegalDeliveryMessage(legalDeliveryMessage);
                            legalDeliveryMessages = ldmBuilder.build();
                            String deviceID = Globals.GetDeviceID(getBaseContext(), getContentResolver());
                            HttpClient httpclient = new DefaultHttpClient();//getting device information
                            HttpPost httppost = new HttpPost(url);//get server IP as well as url.
                            String resultValue = "NotSucceed";
                            HttpEntity resEntity;
                            try {
                                MultipartEntity entity = new MultipartEntity();
                                entity.addPart("DeviceID", new StringBody(deviceID));
                                byte[] binMessage = legalDeliveryMessages.toByteArray();
                                entity.addPart("message", new InputStreamKnownSizeBody(new ByteArrayInputStream(binMessage), binMessage.length, "application/octet-stream"));
                                httppost.setEntity(entity);
                                HttpResponse response = httpclient.execute(httppost);
                                if (response != null) {
                                    resEntity = response.getEntity();
                                }
                                resultValue = response.getFirstHeader("Result").getValue();//EXception,problem ask to sachin

                            } catch (ClientProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (NullPointerException npe) {
                                npe.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        });
        uploadDataToServerThread.start();
    }

    private boolean SaveConspResult(int pAttempt, String pServiceResult) {

        Calendar cal = Calendar.getInstance();
//		int gpsYear = cal.get(Calendar.YEAR);
//		int gpsMonth = cal.get(Calendar.MONTH) + 1;
//		int gpsDay = cal.get(Calendar.DAY_OF_MONTH);
//		int gpsHour = cal.get(Calendar.HOUR);
//		int gpsMinute = cal.get(Calendar.MINUTE);
        int gpsNoon = cal.get(Calendar.AM_PM);

        EditText editEntry = (EditText) findViewById(R.id.editentry);
        EditText editWall = (EditText) findViewById(R.id.editwall);
        EditText editFloor = (EditText) findViewById(R.id.editfloor);
        EditText editLock = (EditText) findViewById(R.id.editlock);
        EditText editDoo = (EditText) findViewById(R.id.editdoo);
        EditText editNoLock = (EditText) findViewById(R.id.editNoloc);


        String strEntry = editEntry.getText().toString();
        String strWall = editWall.getText().toString();
        String strFloor = editFloor.getText().toString();
        String strLocks = editLock.getText().toString();
        String strDoor = editDoo.getText().toString();
        String strDoorLocks = editNoLock.getText().toString();


        //String cService = strEntry + ";" + strWall + ";" + strFloor + ";"+ strDoor + ";" + strLocks + ";" + strDoorLocks;

        String cService = strEntry + ";" + strWall + ";" + strFloor + ";"
                + strLocks + ";" + strDoor + ";" + strDoorLocks;


        String strOtherIdentifyingFeaturesC = GetOtherIdentifyingFeaturesC();
//		String strGpsDate = String.format("%02d-%02d-%04d", gpsMonth, gpsDay, gpsYear);
        String noon = "";

        if (gpsNoon == 0)
            noon = "AM";
        else if (gpsNoon == 1)
            noon = "PM";

//		String strGpsTime = String.format("%02d:%02d %s", gpsHour + EstimatedHour, gpsMinute + EstimatedMin, noon);
        String strDateOfService = "", strTimeOfService = "", mobileTime = "";


        editCol1Fragment.SetDateOfService(strDateOfService = editCol1Fragment.GetGpsDate2());
        editCol1Fragment.SetTimeOfService(strTimeOfService = editCol1Fragment.GetGpsTime2());
        editCol1Fragment.setMobileTime(mobileTime = editCol1Fragment.getMobileTime2());
        boolean bRet = dbHelper.updateLD_Conspicuous(rowID, strDateOfService,
                strTimeOfService, pServiceResult, strDoorLocks
                , cService, strOtherIdentifyingFeaturesC, pAttempt, mobileTime);
        if (bRet) {
            ToastMsg("Saved Successfully!");
        }
        return bRet;
    }

    /**
     * @param pAttempt         Attempt should be saved in database
     * @param strServiceResult Type of service result
     * @return <strong>True </strong> if database is updated with following
     * values: PersonSeen,PersonNotSeen,Substitute
     * DiliveredTo,Corporation Recipient,etc. <strong> Date & Time of
     * Service<strong>
     */
    private boolean SaveOtherResult(int pAttempt, String strServiceResult) {
        boolean bRet = false;

        EditText editsex = (EditText) findViewById(R.id.editsex);
        EditText editSkin = (EditText) findViewById(R.id.editskin);
        EditText editHair = (EditText) findViewById(R.id.edithair);
        EditText editAge = (EditText) findViewById(R.id.editage);
        EditText editHeight = (EditText) findViewById(R.id.editheight);
        EditText editWeight = (EditText) findViewById(R.id.editweight);

        try {

            String strSex = editsex.getText().toString();
            //editsex.setText(getTextsex);

            String strSkin = editSkin.getText().toString();
            //	editsex.setText(getTextSkin);

            String strHair = editHair.getText().toString();
            //editsex.setText(getTexthair);

            String strAge = editAge.getText().toString();
            //editsex.setText(getTextage);

            String strHeight = editHeight.getText().toString();
            //editsex.setText(getTextheight);

            String strWeight = editWeight.getText().toString();

            String pService = strSex + ";" + strSkin + ";" + strHair + ";"
                    + strAge + ";" + strHeight + ";" + strWeight;


            if (strSex == "") {
                System.out.print("strsex is full");
            } else if (strSkin == "") {
                System.out.print("strSkin is full");
            } else if (strHair == "") {
                System.out.print("strHair is full");
            } else if (strAge == "") {
                System.out.print("strAge is full");
            } else if (strHeight == "") {
                System.out.print("strHeight is full");
            } else if (strWeight == "") {
                System.out.print("strWeight is full");
            }


            String strOtherIdentifyingFeaturesP = GetOtherIdentifyingFeaturesP();
            String strPersonSeenSubstitute = editCol2Fragment.personSeenSubstituteEditText.getText().toString().trim();
            if (strPersonSeenSubstitute.length() != 0) {
                // Sachin's Madhale's Logic as by LDS windows application...
                if (editCol2Fragment.personSeenSubstituteEditText.getText().toString().equalsIgnoreCase("Doe")) {
                    if (!editCol2Fragment.personSeenSubstituteEditText.getText().toString().equals("'John Doe'")
                            && !editCol2Fragment.personSeenSubstituteEditText.getText().toString().equals("'Jane Doe'")) {
                        if (!editCol2Fragment.personSeenSubstituteEditText
                                .getText().toString().equals("'Doe'")
                                || !editCol2Fragment.personSeenSubstituteEditText
                                .getText().toString().equals("'doe'"))
                            label:{
                                String tempString = "";
                                if (tempString2 == tempString)
                                    break label;
                                tempString = editCol2Fragment.personSeenSubstituteEditText
                                        .getText().toString();
                                tempString2 = tempString.replace("\'Doe\'", "Doe");
                                if (tempString.contains("\"Doe\""))
                                    tempString2 = tempString.replace("\"Doe\"",
                                            "Doe");
                                if (tempString.contains("Doe"))
                                    tempString2 = tempString
                                            .replace("Doe", "Doe");
                                if (tempString.contains("\'doe\'"))
                                    tempString2 = tempString.replace("\'doe\'",
                                            "doe");
                                if (tempString.contains("\"doe\""))
                                    tempString2 = tempString.replace("\"doe\"",
                                            "doe");
                                if (tempString.contains("doe"))
                                    tempString2 = tempString
                                            .replace("doe", "doe");
                                editCol2Fragment.personSeenSubstituteEditText
                                        .setText(tempString2);
                                // tempString2;//=editCol2Fragment.personSeenSubstituteEditText.getText().toString();
                                System.out.println(tempString2);
                            }
                    }
                } else {
                    boolean space = false;
                    for (int i = 0; i < strPersonSeenSubstitute.length(); i++) {
                        if (strPersonSeenSubstitute.charAt(i) == ' ') {
                            space = true;
                        }
                    }
                    if (!space) {
                        strPersonSeenSubstitute = strPersonSeenSubstitute + " Doe";
                        editCol2Fragment.personSeenSubstituteEditText.setText(strPersonSeenSubstitute);
                    }

                }
            }
            // ///Sudheer...11-July-2014

            String strPersonSeenPersonal = editCol2Fragment.personSeenPersonalEditText.getText().toString();

            String strPersonNotSeenPersonalPlus = editCol2Fragment.personNotSeenPersonalPlusEditText
                    .getText().toString();

            if (strPersonNotSeenPersonalPlus != null && strPersonNotSeenPersonalPlus.equals("Select Person Seen"))
                strPersonNotSeenPersonalPlus = "";

            String strCorpRecipient = editCol2Fragment.corprecEditText.getText().toString();

            String strCorpRecipientTitle = editCol2Fragment.corprectitleEditText.getText().toString();

//			String noon = "";

//			if (gpsNoon == 0)
//				noon = "AM";
//			else if (gpsNoon == 1)
//				noon = "PM";

            String strDateOfService = "";//
            // mitesh
            String strTimeOfService = "";
            String mobileTime = "";

            if (editCol2Fragment.GetThirdAttempt() && editCol2Fragment.GetSecondAttempt() && editCol2Fragment.GetFirstAttempt()) {
                editCol1Fragment
                        .SetDateOfService(strDateOfService = editCol1Fragment
                                .GetGpsDate3());
                // Time of service == Time of 2nd Attempt.
                editCol1Fragment
                        .SetTimeOfService(strTimeOfService = editCol1Fragment
                                .GetGpsTime3());
                editCol1Fragment.setMobileTime(mobileTime = editCol1Fragment
                        .getMobileTime3());

            } else if (editCol2Fragment.GetSecondAttempt() && editCol2Fragment.GetFirstAttempt()) {
                // Date of service == Date of 2nd Attempt.
                editCol1Fragment
                        .SetDateOfService(strDateOfService = editCol1Fragment
                                .GetGpsDate2());
                // Time of service == Time of 2nd Attempt.
                editCol1Fragment
                        .SetTimeOfService(strTimeOfService = editCol1Fragment
                                .GetGpsTime2());
                editCol1Fragment.setMobileTime(mobileTime = editCol1Fragment
                        .getMobileTime2());
            } else {
                if (editCol2Fragment.GetFirstAttempt()) {
                    editCol1Fragment
                            .SetDateOfService(strDateOfService = editCol1Fragment
                                    .GetGpsDate1());
                    editCol1Fragment
                            .SetTimeOfService(strTimeOfService = editCol1Fragment
                                    .GetGpsTime1());
                    editCol1Fragment
                            .setMobileTime(mobileTime = editCol1Fragment
                                    .getMobileTime1());
                }
            }

            String serviceType = editInputDataFragment.GetServiceType();//if remove then app stopped
            if (serviceType.equalsIgnoreCase("Standard")) {
                if (editCol2Fragment.GetFirstAttempt()
                        && editCol2Fragment.GetSecondAttempt()
                        && editCol2Fragment.GetThirdAttempt()) {
                    editCol2Fragment.SetServiceCompleted(true);
                }
            } else {
                if (editCol2Fragment.GetFirstAttempt()
                        && editCol2Fragment.GetSecondAttempt()) {
                    editCol2Fragment.SetServiceCompleted(true);
                }
            }
            // mitesh
            bRet = dbHelper.updateLD_PersonalPlus_Substitute(rowID,
                    strPersonSeenSubstitute, strPersonSeenSubstitute,
                    strPersonSeenPersonal, strPersonNotSeenPersonalPlus,
                    strCorpRecipient, strCorpRecipientTitle, strDateOfService,
                    strTimeOfService, strServiceResult, pService,
                    strOtherIdentifyingFeaturesP, pAttempt, mobileTime, servicecomplete);

            if (bRet) {
                ToastMsg("Saved Successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            StringWriter st = new StringWriter();
            e.printStackTrace(new PrintWriter(st));
            ManupulateFile manFile = new ManupulateFile();
            manFile.WriteToFile(st.toString(), this);
            ErroReportingInBackground errreprt = new ErroReportingInBackground(
                    this);
            errreprt.execute(st.toString());
            Toast.makeText(this, "Error reported through mail!",
                    Toast.LENGTH_SHORT).show();
        }
        return bRet;
    }

    /**
     * This Populates Fields by your database
     */
    private void populateFields() {
        // Get String that OtherIdentifying Features!
        alreport.reportAudit("In PopulatingFields()", this);
        String strOtherIdentifyingFeaturesP = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_OtherCommentsP));

        String strOtherIdentifyingFeaturesC = cursor.getString(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_OtherCommentsC));
        String strServiceResult = editCol1Fragment.GetServiceResult();
        SetOtherIdentifyingFeaturesP(strOtherIdentifyingFeaturesP);
        if (strOtherIdentifyingFeaturesC != null && strServiceResult.equalsIgnoreCase("Conspicuous")) {
            SetOtherIdentifyingFeaturesC(strOtherIdentifyingFeaturesC);
        } else if (strServiceResult.equalsIgnoreCase("Conspicuous")) {
            SetOtherIdentifyingFeaturesC(strOtherIdentifyingFeaturesP);
        }

        double mLat1 = cursor.getDouble(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLat1));
        double mLon1 = cursor.getDouble(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLon1));
        double mLat2 = cursor.getDouble(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLat2));
        double mLon2 = cursor.getDouble(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLon2));
        double mLat3 = cursor.getDouble(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLat3));
        double mLon3 = cursor.getDouble(cursor
                .getColumnIndexOrThrow(LDDatabaseAdapter.KEY_GPSLon3));

        String firstAttempt = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_FirstAttempt));
        String secondAttempt = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_SecondAttempt));
        String thirdAttempt = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ThirdAttempt));
        if (firstAttempt.equalsIgnoreCase("1")) {
            setIsFirstAttemptCompleted(true);
        } else {
            setIsFirstAttemptCompleted(false);
        }
        if (secondAttempt.equalsIgnoreCase("1")) {
            setIsSecondAttemptCompleted(true);
        } else {
            setIsSecondAttemptCompleted(false);
        }
        if (thirdAttempt.equalsIgnoreCase("1")) {
            setIsThirdAttemptCompleted(true);
        } else {
            setIsThirdAttemptCompleted(false);
        }
        editCol2Fragment.SetLatLon1(mLat1, mLon1);
        editCol2Fragment.SetLatLon2(mLat2, mLon2);
        editCol2Fragment.SetLatLon3(mLat3, mLon3);
        gpstGlobal = new GPSTracker(this);
        String FullNameSTND = cursor.getString(cursor
                .getColumnIndex(LDDatabaseAdapter.KEY_STND_FullName));
        String FullNameLT = cursor.getString(cursor
                .getColumnIndex(LDDatabaseAdapter.KEY_LTFullname));

        if (FullNameLT == null) {
            FullNameLT = cursor.getString(cursor
                    .getColumnIndex(LDDatabaseAdapter.KEY_LTBIZNAME));
        }

        if (FullNameSTND != null) {
            By_Whom = FullNameSTND;
        } else {
            By_Whom = "" + FullNameLT;
        }
    }
    // TODO Auto-generated method stub

    /**
     * Hiding all gpstimeTexview(1,2,3) if they are not set like [not set] and
     * in Addition if ServiceResult==n/a
     */
    @SuppressLint("DefaultLocale")
    public void HideEditCol1EditCol2Items() {
        // Amit changes for lat and Lon display
        String time1 = (String) editCol1Fragment.gpstime1TextView.getText();
        String time2 = (String) editCol1Fragment.gpstime2TextView.getText();
        String time3 = (String) editCol1Fragment.gpstime3TextView.getText();
        String time0 = (String) editCol1Fragment.timeofserviceTextView
                .getText();

        if (time1.trim().toLowerCase().equals("[not set]"))
            time1 = "";
        if (time2.trim().toLowerCase().equals("[not set]"))
            time2 = "";
        if (time3.trim().toLowerCase().equals("[not set]"))
            time3 = "";
        if (time0.trim().toLowerCase().equals("[not set]"))
            time0 = "";
        boolean DisplayUnconditional = false;
        String strServiceResult = editCol1Fragment.GetServiceResult();
        alreport.reportAudit("Dispatch conditional:?" + DisplayUnconditional,
                this);

        if (!strServiceResult.equals("n/a")) {
            // disable t3
            alreport.reportAudit("not n/a Result type", this);
            // mitesh
            editCol2Fragment.ShowThirdAttemtGPS(true);
            editCol1Fragment.ShowThirdAttempt(true);
            alreport.reportAudit("Show third attempt?" + false, this);

            if (time3.trim().length() != 0) {
                if (DisplayUnconditional) {
                    alreport.reportAudit(
                            "time3!=0; DisplayUnconditiaonal=true; showing third attempt" + true,
                            this);
                    editCol2Fragment.ShowThirdAttemtGPS(true);
                    editCol1Fragment.ShowThirdAttempt(true);
                } else {
                    // following Condition for if time of service not equals to
                    // third attempt service then show third attempt
                    alreport.reportAudit("DisplayUnconditional=false", this);

                    if (!time3.trim().toLowerCase()
                            .equals(time0.trim().toLowerCase())) {
                        alreport.reportAudit(
                                "Time of Third attempt==Time of service", this);
                        editCol1Fragment.ShowThirdAttempt(true);
                        DisplayUnconditional = true;
                        alreport.reportAudit(
                                "Showing Third attempt Time: and DisplayUnconditional" + true,
                                this);
                    }
                    editCol2Fragment.ShowThirdAttemtGPS(true);
                    alreport.reportAudit(
                            "Showing third attempt Checkbox=" + true, this);

                }
            }

            // mitesh // disable t2
            editCol2Fragment.ShowSecondAttemtGPS(true);
            editCol1Fragment.ShowSecondAttempt(true);
            alreport.reportAudit("Second Attempt?" + false, this);

            if (time2.trim().length() != 0) {
                if (DisplayUnconditional) {
                    // enable t2
                    alreport.reportAudit("DisplayUnconditional="
                            + DisplayUnconditional, this);
                    editCol2Fragment.ShowSecondAttemtGPS(true);
                    editCol1Fragment.ShowSecondAttempt(true);
                    alreport.reportAudit("Showing second attempt" + true, this);

                } else {
                    if (!time2.trim().toLowerCase()
                            .equals(time0.trim().toLowerCase())) {
                        // enable t2
                        // editCol2Fragment.ShowSecondAttemtGPS(true);
                        editCol1Fragment.ShowSecondAttempt(true);
                        DisplayUnconditional = true;
                        alreport.reportAudit(
                                "Time of 2nd attempt not equal to Time of service and Showing 2nd Time?" + true,
                                this);
                    }
                    editCol2Fragment.ShowSecondAttemtGPS(true);
                    alreport.reportAudit("Showing second ?" + true, this);
                }
            }

            // mitesh // disable t1
            editCol2Fragment.ShowFirstAttemtGPS(true);
            editCol1Fragment.ShowFirstAttempt(true);
            if (time1.trim().length() != 0) {
                if (DisplayUnconditional) {
                    // enable t1
                    editCol2Fragment.ShowFirstAttemtGPS(true);
                    editCol1Fragment.ShowFirstAttempt(true);
                    alreport.reportAudit("Showing First Attempt?" + true, this);

                    alreport.reportAudit("DisplayUnconditional"
                            + DisplayUnconditional, this);

                } else {
                    if (!time1.trim().toLowerCase()
                            .equals(time0.trim().toLowerCase())) {// enable t1
                        // editCol2Fragment.ShowFirstAttemtGPS(true);
                        editCol1Fragment.ShowFirstAttempt(true);
                        DisplayUnconditional = true;
                        alreport.reportAudit(
                                "Time of First attempt not equal to time of service!",
                                this);
                    } else if (time1.trim().toLowerCase()
                            .equals(time0.trim().toLowerCase())
                            && time2.trim().length() == 0
                            && time3.trim().length() == 0) {
                        alreport.reportAudit(
                                "Time of First attempt=Time of service and time of second=Time of third=0",
                                this);
                        editCol2Fragment.serviceFirstAttemptCheckBox
                                .setVisibility(View.GONE);
                        alreport.reportAudit("First attempt Checkbax ? GONE",
                                this);
                        editCol1Fragment.ShowFirstAttempt(false);
                        alreport.reportAudit(
                                "Showing First attempt time ?" + false, this);
                        DisplayUnconditional = true;
                    }
                    editCol2Fragment.ShowFirstAttemtGPS(true);
                    alreport.reportAudit(
                            "Showing First Attempt CheckBox?" + true, this);
                }
            }
            if (!DisplayUnconditional) {
                if (time1.trim().toLowerCase()
                        .equals(time0.trim().toLowerCase())) {
                    editCol2Fragment.ShowFirstAttemtGPS(true);
                    editCol1Fragment.ShowFirstAttempt(true);
                    DisplayUnconditional = true;
                    alreport.reportAudit(
                            "1st attempt time=Time of service,Showing First attempt?=DisplayUnconditiaonal?" + true,
                            this);
                }
                if (time2.trim().toLowerCase()
                        .equals(time0.trim().toLowerCase())
                        && !DisplayUnconditional) {
                    editCol2Fragment.ShowSecondAttemtGPS(true);
                    editCol1Fragment.ShowSecondAttempt(true);
                    DisplayUnconditional = true;
                    alreport.reportAudit(
                            "2nd attempt time=TimeofService And DisplayUnconditional=false; Showing 2nd attmpt?" + true,
                            this);
                }
                if (time3.trim().toLowerCase()
                        .equals(time0.trim().toLowerCase())
                        && !DisplayUnconditional) {
                    alreport.reportAudit(
                            "3rd Attempt=TimeofService and DisplayConditional=False; Showing Third attempt=" + true,
                            this);
                    editCol2Fragment.ShowThirdAttemtGPS(true);
                    editCol1Fragment.ShowThirdAttempt(true);
                    DisplayUnconditional = true;
                }
                if (time1.trim().length() == 0) {
                    alreport.reportAudit("time1=0", this);
                    editCol2Fragment.ShowFirstAttemtGPS(true);
                    editCol2Fragment.serviceFirstAttemptCheckBox
                            .setVisibility(View.GONE);
                    editCol1Fragment.ShowFirstAttempt(false);
                    alreport.reportAudit(
                            "1st Attmpt checkbx=true;Show fist attempt time =false",
                            this);
                }
            }
        } else {

            if (time1.trim().length() == 0) {
                alreport.reportAudit("First attempt time=0", this);

                editCol2Fragment.ShowFirstAttemtGPS(true);
                editCol2Fragment.serviceFirstAttemptCheckBox.setVisibility(View.GONE);
                editCol1Fragment.ShowFirstAttempt(false);
            } else if (time1.trim().equals(
                    ((String) editCol1Fragment.timeofserviceTextView.getText())
                            .trim())) {

                editCol1Fragment.ShowFirstAttempt(false);

            } else {
                editCol2Fragment.serviceFirstAttemptCheckBox.setVisibility(View.GONE);
            }
            if (time2.trim().length() == 0) {
                editCol2Fragment.ShowSecondAttemtGPS(false);
                editCol1Fragment.ShowSecondAttempt(false);
            } else if (time2.trim().equals(
                    ((String) editCol1Fragment.timeofserviceTextView.getText())
                            .trim())) {
                editCol1Fragment.ShowSecondAttempt(false);
                editCol2Fragment.serviceFirstAttemptCheckBox
                        .setVisibility(View.VISIBLE);
            } else {
                editCol2Fragment.serviceFirstAttemptCheckBox
                        .setVisibility(View.VISIBLE);

            }
            if (time3.trim().length() == 0) {
                editCol2Fragment.ShowThirdAttemtGPS(false);
                editCol1Fragment.ShowThirdAttempt(false);

            } else if (time3.trim().equals(
                    ((String) editCol1Fragment.timeofserviceTextView.getText())
                            .trim())) {
                editCol1Fragment.ShowThirdAttempt(false);
                editCol2Fragment.serviceFirstAttemptCheckBox
                        .setVisibility(View.VISIBLE);

            } else {
                editCol2Fragment.serviceFirstAttemptCheckBox
                        .setVisibility(View.VISIBLE);
            }
        }

        boolean latlon1 = false;
        String lon1 = ((String) editCol2Fragment.lon1TextView.getText());
        double lonDouble1 = Double.parseDouble(lon1);
        if (lon1.trim().toLowerCase().equals("[not set]") || lonDouble1 == 0) {
            lon1 = "";
        } else {
            latlon1 = true;
        }

        boolean latlon2 = false;
        String lon2 = ((String) editCol2Fragment.lon2TextView.getText());
        double lonDouble2 = Double.parseDouble(lon2);
        if (lon2.trim().toLowerCase().equalsIgnoreCase("[not set]") || lonDouble2 == 0) {
            lon2 = "";
        } else {
            latlon2 = true;
        }
        boolean latlon3 = false;
        String lon3 = ((String) editCol2Fragment.lon3TextView.getText());
        double lonDouble3 = Double.parseDouble(lon3);
        if (lon3.trim().toLowerCase().equalsIgnoreCase("[not set]") || lonDouble3 == 0) {
            lon3 = "";
        } else {
            latlon3 = true;
        }

        if (latlon1) {
            editCol2Fragment.serviceFirstAttemptCheckBox.setChecked(true);
        }
        if (latlon2) {
            editCol2Fragment.serviceSecondAttemptCheckBox.setChecked(true);
        }
        if (latlon3) {
            editCol2Fragment.serviceThirdAttemptCheckBox.setChecked(true);
        }
        if (latlon1 && latlon2 && latlon3) {

            editCol2Fragment.serviceCompletedCheckBox.setChecked(true);
        }

        if (lonDouble1 == 0 && lonDouble2 == 0 && lonDouble3 == 0
                || strServiceResult.equals("n/a")) {
            editCol2Fragment.ShowFirstAttemtGPS(true);
            editCol1Fragment.ShowFirstAttempt(true);
            editCol2Fragment.ShowSecondAttemtGPS(true);
            editCol1Fragment.ShowSecondAttempt(true);
            editCol2Fragment.ShowThirdAttemtGPS(true);
            editCol1Fragment.ShowThirdAttempt(true);

            editCol2Fragment.serviceFirstAttemptCheckBox
                    .setVisibility(View.VISIBLE);
            editCol2Fragment.serviceSecondAttemptCheckBox
                    .setVisibility(View.VISIBLE);
            editCol2Fragment.serviceThirdAttemptCheckBox
                    .setVisibility(View.VISIBLE);

            if (lonDouble1 == 0) {
                editCol2Fragment.lat1TextView.setText("[Not Set]");
                editCol2Fragment.lon1TextView.setText("[Not Set]");
                alreport.reportAudit(
                        "Longi1 ==0? latitude 1=[Not Set] and Longitude1=[Not Set]",
                        this);
            }
            if (lonDouble2 == 0) {
                editCol2Fragment.lat2TextView.setText("[Not Set]");
                editCol2Fragment.lon2TextView.setText("[Not Set]");
                alreport.reportAudit(
                        "Longi2 ==0? latitude2 =[Not Set] and Longitud2=[Not Set]",
                        this);
            }
            if (lonDouble3 == 0) {
                editCol2Fragment.lat3TextView.setText("[Not Set]");
                editCol2Fragment.lon3TextView.setText("[Not Set]");
                alreport.reportAudit(
                        "Longi3 ==0? latitude 3=[Not Set] and Longitude3=[Not Set]",
                        this);
            }

            String strServiceType = editInputDataFragment.GetServiceType();
            if (strServiceType.equals("L&T Residential")
                    || strServiceType.equals("L&T Commercial")) {
                alreport.reportAudit(
                        "ServiceType?LT Showing 3rd attempt? " + false, this);
                editCol2Fragment.ShowThirdAttemtGPS(false);
                editCol1Fragment.ShowThirdAttempt(false);
            }

            if (!strServiceResult.equalsIgnoreCase("conspicuous")
                    && !strServiceResult.equalsIgnoreCase("n/a")) {
                alreport.reportAudit("Result Type? NOT Cospicuous nor n/a ",
                        this);
                alreport.reportAudit("initalizing lat and lon={Not set}", this);
                String lat = "[Not Set]";
                String lon = "[Not Set]";

                if (!editCol2Fragment.lat3TextView.getText()
                        .equals("[Not Set]")
                        && !editCol2Fragment.lat3TextView.getText().equals(
                        "00.00000000")) {
                    alreport.reportAudit(
                            "lat and lon = previously set lat3 and lon3 but not 0.000000 & [Not Set]",
                            this);
                    lat = (String) editCol2Fragment.lat3TextView.getText();
                    lon = (String) editCol2Fragment.lon3TextView.getText();

                } else if (!editCol2Fragment.lat2TextView.getText().equals(
                        "[Not Set]")
                        && !editCol2Fragment.lat2TextView.getText().equals(
                        "00.00000000")) {
                    alreport.reportAudit(
                            "lat and lon = previously set lat2 and lon2 but not 0.000000 & [Not Set]",
                            this);
                    lat = (String) editCol2Fragment.lat2TextView.getText();
                    lon = (String) editCol2Fragment.lon2TextView.getText();
                } else if (!editCol2Fragment.lat1TextView.getText().equals(
                        "[Not Set]")
                        && !editCol2Fragment.lat1TextView.getText().equals(
                        "00.00000000")) {
                    alreport.reportAudit(
                            "lat and lon = previously set lat3 and lon3 but not 0.000000 & [Not Set]",
                            this);
                    lat = (String) editCol2Fragment.lat1TextView.getText();
                    lon = (String) editCol2Fragment.lon1TextView.getText();
                }
                alreport.reportAudit("showing lat1 and lon2 to lat and lon",
                        this);
                editCol2Fragment.lat1TextView.setText(lat);
                editCol2Fragment.lon1TextView.setText(lon);

                alreport.reportAudit("Showing 1st attempt GPS? " + true, this);
                editCol2Fragment.ShowFirstAttemtGPS(true);
                alreport.reportAudit(
                        "1st attempt check bx? GONE ,show 1st,2nd and 2rd attempt time" + false,
                        this);
                alreport.reportAudit("Showing 2nd and 3rd attempt GPS? false",
                        this);
                editCol2Fragment.serviceFirstAttemptCheckBox
                        .setVisibility(View.GONE);

            } else if (strServiceResult.equalsIgnoreCase("conspicuous")) {
                alreport.reportAudit("Result Service Type? Conspicuous", this);
                editCol2Fragment.ShowFirstAttemtGPS(true);
                editCol2Fragment.ShowSecondAttemtGPS(true);
                editCol1Fragment.ShowFirstAttempt(true);
                alreport.reportAudit(
                        "Show 1st,2nd attempt GPS? true and show first attempt time=true",
                        this);
                if (strServiceType.equals("Standard")) {
                    alreport.reportAudit(
                            "Result=conspicuous&&ServiceType=Stnd? Showing Showing 3rd attempt GPS = 2nd attempt time=true",
                            this);
                    editCol2Fragment.ShowThirdAttemtGPS(true);
                    editCol1Fragment.ShowSecondAttempt(true);
                } else {
                    alreport.reportAudit(
                            "Result=Conspicuous&& ServiceType! =Stnd? Show 3rd attempt GPS=show 3rd attempt time=false",
                            this);
                    editCol2Fragment.ShowThirdAttemtGPS(false);
                    editCol1Fragment.ShowSecondAttempt(false);
                }
                alreport.reportAudit("Showing 3rd attempt? false", this);
                editCol1Fragment.ShowThirdAttempt(false);
            }
        }
    }

    /**
     * @return (String) otherIdetifyingFraturesP.getText()
     */
    private String GetOtherIdentifyingFeaturesP() {
        String strFeatures = otherIdentifyingFeaturesP.getText().toString();
        return strFeatures;
    }

    /**
     * @param: strFeatures Accepts OtherIdentifying Features for
     * Personal/Personal Plus Service Result
     */
    private void SetOtherIdentifyingFeaturesP(String strFeatures) {
        otherIdentifyingFeaturesP.setText(strFeatures);
    }

    private String GetOtherIdentifyingFeaturesC() {
        String strFeatures = otherIdentifyingFeaturesC.getText().toString();
        return strFeatures;
    }

    /**
     * @param: strFeatures Accepts OtherIdentifying Features for Conspiscuous
     * Service Result
     */
    private void SetOtherIdentifyingFeaturesC(String strFeatures) {
        otherIdentifyingFeaturesC.setText(strFeatures);
    }

    public Location GetLastKnownLocation() {
        return lastKnownLocation;
    }

    public LDDatabaseAdapter GetLDDBAdapter() {
        return dbHelper;
    }

	
	/*@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if (id == TIME_DIALOG_ID) {
			((TimePickerDialog) dialog).updateTime(mHour, mMinute);
		} else if (id == DATE_DIALOG_ID) {
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
		}
	}*/

    boolean IsTimeInRange(LocalTime selectedTime, LocalTime fromTime,
                          LocalTime toTime) {

        int from = selectedTime.compareTo(fromTime);// in short selected
        int to = selectedTime.compareTo(toTime);
        if ((from <= 0) && (to >= 0))// is Selected Time is greater than
            // From-time && selected Time is less
            // than To-Time then return true
            return true;
        else
            return false;
    }

    // mitesh
    boolean IsTimeInRange1(LocalTime selectedTime, LocalTime fromTime,
                           LocalTime toTime) {
        int from = selectedTime.compareTo(fromTime);// in short selected
        // time-fromtime
        int to = selectedTime.compareTo(toTime);

        if ((from >= 0) && (to <= 0))// is Selected Time is greater than
            return true;
        else
            return false;
    }


    private void HandleDateAttempt(View v) {

        String strDate = "";
        if (editCol1Fragment.dateofserviceTextView.getText().toString() == "[Not Set]") {
            strDate = String.format("%02d-%02d-%04d",
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.YEAR));
        } else
            strDate = editCol1Fragment.dateofserviceTextView.getText().toString();

       /* try {
            cal.setTime(Globals.sdf.parse(strDate + " 0:00 AM"));
            (((LDDownloadActivity) getActivity()).mYear) = cal
                    .get(Calendar.YEAR);
            (((LDDownloadActivity) getActivity()).mMonth) = cal
                    .get(Calendar.MONTH);
            (((LDDownloadActivity) getActivity()).mDay) = cal
                    .get(Calendar.DAY_OF_MONTH);
            getActivity().showDialog(Globals.DATE_DIALOG_ID);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    // mitesh

    enum ConspicuousTimeResult {
        OK, HasToBeInRange1, HasToBeInRange2
    }

    enum ConspicuousTimeSet {
        serviceTime, gpsTime1, gpsTime2, gpsTime3
    }

    public void InitializeLayout() {
        alreport.reportAudit("Initalize Layout by service result: n/a", this);
        editCol1Fragment.HideDateTimeOfService();
        editCol1Fragment.HideDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();// editCol1Fragment.HideFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();// editCol1Fragment.HideSecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    public void InitializePersonalLayout() {
        String strServiceType = editInputDataFragment.GetServiceType();

        editCol2Fragment.personSeenPersonalSpinner.setVisibility(View.GONE);
        String pserv1 = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_LTFullname));

        editCol2Fragment.personSeenPersonalEditText.setText(pserv1);

        if (strServiceType.equals("L&T Residential")) {
            if (!(editCol2Fragment.GetFirstAttempt())) {
                resetall();
            }
            ArrangeLayoutFor_Residential_Personal();
            return;
        }

        if (strServiceType.equals("L&T Commercial")) {
            if (!(editCol2Fragment.GetFirstAttempt())) {
                resetall();
            }
            ArrangeLayoutFor_Commercial_Personal();
            return;
        }

        if (strServiceType.equals("Standard")) {
            if (!(editCol2Fragment.GetFirstAttempt()) && (!(editCol2Fragment.GetSecondAttempt()))) {
                resetall();
            }
            ArrangeLayoutFor_Standard_Personal();
            return;
        }

        InitializeLayout();
    }

    public void InitializePersonalPlusLayout() {
        String strServiceType = editInputDataFragment.GetServiceType();
        if (strServiceType.equals("L&T Residential")) {
            if (!(editCol2Fragment.GetFirstAttempt())) {
                resetall();
            }
            ArrangeLayoutFor_Residential_PersonalPlus();
            return;
        }

        if (strServiceType.equals("L&T Commercial")) {
            if (!(editCol2Fragment.GetFirstAttempt())) {
                resetall();
            }
            ArrangeLayoutFor_Commercial_PersonalPlus();
            return;
        }
        Toast.makeText(this,
                "Personal Plus Result is not valid for Standard Service Type.",
                Toast.LENGTH_LONG).show();

        InitializeLayout();
    }

    public void InitializeSubstituteLayout() {
        String strServiceType = editInputDataFragment.GetServiceType();
        if (strServiceType.equals("L&T Residential")) {
            if (!(editCol2Fragment.GetFirstAttempt())) {
                resetall();
            }
            ArrangeLayoutFor_Residential_Substitute();
            return;
        }

        if (strServiceType.equals("L&T Commercial")) {
            if (!(editCol2Fragment.GetFirstAttempt())) {
                resetall();
            }
            ArrangeLayoutFor_Commercial_Substitute();
            return;
        }

        if (strServiceType.equals("Standard")) {
            if (!(editCol2Fragment.GetFirstAttempt()) && (!(editCol2Fragment.GetSecondAttempt()))) {
                resetall();
            }

            ArrangeLayoutFor_Standard_Substitute();
            return;
        }

        InitializeLayout();
    }

    public void InitializeConspicuousLayout() {
        String strServiceType = editInputDataFragment.GetServiceType();
        //Calendar ca = Calendar.getInstance();
        //SimpleDateFormat  format = new SimpleDateFormat("MM-dd-yyyy");
        // SimpleDateFormat  format1 = new SimpleDateFormat("HH:mm");
        String time1 = editCol1Fragment.gpstime2TextView.getText().toString();
        String time2 = editCol1Fragment.gpstime1TextView.getText().toString();
        if (Globals.ProductCode.equalsIgnoreCase("C")) {
            if (time1.equals("[Not Set]") || time2.equals("[Not Set]")) {
                editCol2Fragment.SetFirstAttempt(true);
                editCol2Fragment.SetSecondAttempt(true);

                if (editCol2Fragment.lat1TextView.getText().toString().equals("[Not Set]")) {
                    try {
                        editCol2Fragment.ldLocation.getLocation(this, editCol2Fragment.locationResultFirstAttempt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
		/*	 editCol1Fragment.gpsdate1TextView.setText(format.format(ca.getTime()));
			 editCol1Fragment.gpstime1TextView.setText(format1.format(ca.getTime()));
			 editCol1Fragment.gpsdate2TextView.setText(format.format(ca.getTime()));
			 editCol1Fragment.gpstime2TextView.setText(format1.format(ca.getTime()));
			 editCol1Fragment.gpsdate3TextView.setText(format.format(ca.getTime()));
			 editCol1Fragment.gpstime3TextView.setText(format1.format(ca.getTime()));*/
                editCol2Fragment.serviceFirstAttemptCheckBox.setClickable(false);
                editCol2Fragment.serviceSecondAttemptCheckBox.setClickable(false);
                editCol2Fragment.serviceThirdAttemptCheckBox.setClickable(false);
                editCol2Fragment.serviceCompletedCheckBox.setClickable(false);
                editCol2Fragment.serviceCompletedCheckBox.setChecked(true);
            } else {
                editCol2Fragment.serviceFirstAttemptCheckBox.setClickable(false);
                editCol2Fragment.serviceSecondAttemptCheckBox.setClickable(false);
                editCol2Fragment.serviceThirdAttemptCheckBox.setClickable(false);
                editCol2Fragment.serviceCompletedCheckBox.setClickable(false);

            }
        } else {
            editCol2Fragment.SetFirstAttempt(true);
            editCol2Fragment.SetSecondAttempt(true);
            if (editCol2Fragment.lat1TextView.getText().toString().equals("[Not Set]")) {
                try {
                    editCol2Fragment.ldLocation.getLocation(this, editCol2Fragment.locationResultFirstAttempt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
	/*	 editCol1Fragment.gpsdate1TextView.setText(format.format(ca.getTime()));
		 editCol1Fragment.gpstime1TextView.setText(format1.format(ca.getTime()));
		 editCol1Fragment.gpsdate2TextView.setText(format.format(ca.getTime()));
		 editCol1Fragment.gpstime2TextView.setText(format1.format(ca.getTime()));
		 editCol1Fragment.gpsdate3TextView.setText(format.format(ca.getTime()));
		 editCol1Fragment.gpstime3TextView.setText(format1.format(ca.getTime()));*/
            editCol2Fragment.serviceFirstAttemptCheckBox.setClickable(false);
            editCol2Fragment.serviceSecondAttemptCheckBox.setClickable(false);
            editCol2Fragment.serviceThirdAttemptCheckBox.setClickable(false);
            editCol2Fragment.serviceCompletedCheckBox.setClickable(false);
            editCol2Fragment.serviceCompletedCheckBox.setChecked(true);
        }

        if (strServiceType.equals("L&T Residential")) {
            ArrangeLayoutFor_Residential_Conspicuous();
            return;
        }

        if (strServiceType.equals("L&T Commercial")) {
            ArrangeLayoutFor_Commercial_Conspicuous();
            return;
        }

        if (strServiceType.equals("Standard")) {
            editCol2Fragment.SetThirdAttempt(true);
            ArrangeLayoutFor_Standard_Conspicuous();
            return;
        }

        InitializeLayout();
    }

    public void InitializeCorporationLayout() {
        String strServiceType = editInputDataFragment.GetServiceType();

        if (strServiceType.equals("Standard")) {
            ArrangeLayoutFor_Standard_Corporation();
            return;
        }

        InitializeLayout();

        Toast.makeText(this,
                "Corporation Result is only valid for Standard Service Type.",
                Toast.LENGTH_LONG).show();
    }

    public void resetall() {
        String Timeofservice = editCol1Fragment.timeofserviceTextView.getText().toString();
        String Dateofservice = editCol1Fragment.dateofserviceTextView.getText().toString();
        if ((Timeofservice.equals("[Not Set]")) && (Dateofservice.equals("[Not Set]"))) {
            editCol2Fragment.serviceFirstAttemptCheckBox.setClickable(true);
            editCol2Fragment.serviceSecondAttemptCheckBox.setClickable(true);
            editCol2Fragment.serviceThirdAttemptCheckBox.setClickable(true);
            editCol2Fragment.serviceCompletedCheckBox.setClickable(true);
            editCol2Fragment.serviceFirstAttemptCheckBox.setChecked(false);
            editCol2Fragment.serviceSecondAttemptCheckBox.setChecked(false);
            editCol2Fragment.serviceThirdAttemptCheckBox.setChecked(false);
            editCol2Fragment.serviceCompletedCheckBox.setChecked(false);
            editCol1Fragment.gpsdate1TextView.setText("[Not Set]");
            editCol1Fragment.gpstime1TextView.setText("[Not Set]");
            editCol1Fragment.gpsdate2TextView.setText("[Not Set]");
            editCol1Fragment.gpstime2TextView.setText("[Not Set]");
            editCol1Fragment.gpsdate3TextView.setText("[Not Set]");
            editCol1Fragment.gpstime3TextView.setText("[Not Set]");
        }
    }

    // Residential
    // =========================================================
    private void ArrangeLayoutFor_Residential_Personal() {
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();// editCol1Fragment.HideFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();// editCol1Fragment.HideSecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.DisplayPersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Residential_PersonalPlus() {
        alreport.reportAudit(
                "Arranging Layout for LT and service result : personal plus",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt(); // editCol1Fragment.HideFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();// editCol1Fragment.HideSecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.DisplayPersonNotSeenPersonalPlus();
        editCol2Fragment.DisplayPersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Residential_Substitute() {
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();
        editCol2Fragment.DisplayPersonSeenSubstituteNoSeen();
        editCol2Fragment.DisplayPersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Residential_Conspicuous() {
        alreport.reportAudit(
                "Arranging Layout for LT Residential and service result : conspic",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
        if (editCol1Fragment.GetServiceResult() != null)
            if (editCol1Fragment.GetServiceResult().equals("Conspicuous")) {
                //Setting date of service == 2nd attempt date!
                editCol1Fragment.SetDateOfService(editCol1Fragment.GetGpsDate2());
            }

    }

    // Commercial
    // =========================================================
    private void ArrangeLayoutFor_Commercial_Personal() {
        alreport.reportAudit(
                "Arranging Layout for LT Commercial and service result : personal",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();// editCol1Fragment.HideFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();// editCol1Fragment.HideSecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.DisplayPersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Commercial_PersonalPlus() {
        alreport.reportAudit(
                "Arranging Layout for LT and service result : personal plus",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();// editCol1Fragment.HideFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();// editCol1Fragment.HideSecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.DisplayPersonNotSeenPersonalPlus();
        editCol2Fragment.DisplayPersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Commercial_Substitute() {
        alreport.reportAudit(
                "Arranging Layout for LT commercial and service result : substitute",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();
        editCol2Fragment.DisplayPersonSeenSubstituteNoSeen();
        editCol2Fragment.DisplayPersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Commercial_Conspicuous() {
        alreport.reportAudit(
                "Arranging Layout for LT Commerc and service result : Conspi",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    // Standard
    // =========================================================
    private void ArrangeLayoutFor_Standard_Personal() {
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();

        editCol1Fragment.DisplayFirstAtempt();// editCol1Fragment.HideFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();// editCol1Fragment.HideSecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();// editCol1Fragment.HideThirdAtempt();

        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.DisplayPersonSeenPersonal();

        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Standard_Substitute() {
        alreport.reportAudit(
                "Arranging Layout for Standard and service result : substitute",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();
        editCol2Fragment.DisplayPersonSeenSubstituteNoSeen();
        editCol2Fragment.DisplayPersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Standard_Conspicuous() {
        alreport.reportAudit(
                "Arranging Layout for STND and service result : Conspic", this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.HideCorpRec();
        editCol2Fragment.HideCorpRecTitle();
    }

    private void ArrangeLayoutFor_Standard_Corporation() {
        alreport.reportAudit(
                "Arranging Layout for Stand and service result :Corporation",
                this);
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayDateTimeOfService();
        editCol1Fragment.DisplayFirstAtempt();
        editCol1Fragment.DisplaySecondAtempt();
        editCol1Fragment.DisplayThirdAtempt();
        editCol2Fragment.HidePersonSeenSubstituteNoSeen();
        editCol2Fragment.HidePersonSeenSubstitute();
        editCol2Fragment.HidePersonNotSeenPersonalPlus();
        editCol2Fragment.HidePersonSeenPersonal();
        editCol2Fragment.DisplayCorpRec();
        editCol2Fragment.DisplayCorpRecTitle();
    }

    public static String getCurrentAddress() {
        String Apt = editInputDataFragment.getApt();
        String Address = editInputDataFragment.GetAddress();
        String City = editInputDataFragment.GetCity();
        String State = editInputDataFragment.getState();
        String finalAddress = "";
        if (Apt != null) {
            if (finalAddress == "") {
                finalAddress = finalAddress + Apt;
            }
        }
        if (Address != null) {
            if (finalAddress == "") {
                finalAddress = finalAddress + Address;
            } else {
                finalAddress = finalAddress + "," + Address;
            }
        }
        if (City != null) {
            if (finalAddress == "") {
                finalAddress = finalAddress + City;
            } else {
                finalAddress = finalAddress + "," + City;
            }
        }
        if (State != null) {
            if (finalAddress == "") {
                finalAddress = finalAddress + State;
            } else {
                finalAddress = finalAddress + "," + State;
            }
        }
        return finalAddress;
    }


    private void ToastMsg(String msg) {
        if (!msg.equals("")) {
            Message msgObj = ToastMessageHandler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("message", msg);
            msgObj.setData(b);
            ToastMessageHandler.sendMessage(msgObj);
        }
    }

    public static boolean getIsFirstAttemptCompleted() {
        return isFirstAttemptCompleted;
    }

    public static void setIsFirstAttemptCompleted(boolean isFirstAttempt) {
        isFirstAttemptCompleted = isFirstAttempt;
    }

    public static boolean getIsSecondAttemptCompleted() {
        return isSecondAttemptCompleted;
    }

    public static void setIsSecondAttemptCompleted(boolean isSecondAttempt) {
        isSecondAttemptCompleted = isSecondAttempt;
    }

    public static boolean getIsThirdAttemptCompleted() {
        return isThirdAttemptCompleted;
    }

    public static void setIsThirdAttemptCompleted(boolean isThirdAttempt) {
        isThirdAttemptCompleted = isThirdAttempt;
    }
}
