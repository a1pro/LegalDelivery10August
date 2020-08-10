package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Download;

import android.Manifest;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.Register.RegisterDeviceActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessage;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.BlueScreenDesign;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Download.FiltStructure;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LDMessageAdapter;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LegalDeliveryMessages;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.NewMessage;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.NewValues;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.RelatedPersonMessage;

import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;

public class DownloadListFragment extends ListFragment {
    // public String  authorization,thumbprint ;
    private static final String url = Globals.Domain + Globals.DomainPathDownload;
    private List<LegalDeliveryMessage> services;
    String strMessageCount = "";
    Boolean isInvalidUser = false;
    public Context context;
    Exception excptn;
    ProgressDialog mProgressDialog;
    ProgressDialog pd;
    View V;
    private static final int PERMISSION_REQUEST_CODE = 1;
    SharedPreferences pref;
    String deviceID;

    // ////////////////////////////////////////////////////////////////////////////////
    AuditLogReporter alreport = new AuditLogReporter();

    // //////////////   Ashvini  26 0ct//////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        V = inflater.inflate(R.layout.ld_download_list_fragment, container,
                false);
		/*return inflater.inflate(R.layout.ld_download_list_fragment, container,
				false);
		*/

        LinearLayout linear = (LinearLayout) V.findViewById(R.id.linear);
        HorizontalScrollView hs = (HorizontalScrollView) V.findViewById(R.id.scroll);
        LinearLayout ll = (LinearLayout) V.findViewById(R.id.downloadLinearInner);
        //linear.addView(ll);

        return V;
        //        ld_download_list_fragment...............           blue_screen

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        services = new ArrayList<LegalDeliveryMessage>();
        if (checkPermission()) {
            deviceID = Globals.GetDeviceID(getActivity().getBaseContext(), getActivity().getContentResolver());
        } else {
            requestPermission();
            //Snackbar.make(view,"Please request permission.",Snackbar.LENGTH_LONG).show();
        }

        // setListAdapter(new ArrayAdapter<String>(getActivity(),
        // android.R.layout.simple_list_item_1, testData));
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;

        }
    }


    public void onListItemClick(ListView parent, View v, int position, long id) {
        // Toast.makeText(getActivity(),
        // "You have selected " + testData[position],
        // Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("static-access")
    public void Save() {
        // Code is disable for download...Sudheer...
        /*
         * mProgressDialog = new ProgressDialog(getActivity());
         * mProgressDialog.setIndeterminate(true);
         * mProgressDialog.setMessage("Please wait while Saving.....");
         * mProgressDialog.setCancelable(false); mProgressDialog.show();
         */
        // ///////////////////////////////////////////////////////////////////////////////
        alreport.reportAudit("Saving Records!", getActivity());
        // ///////////////////////////////////////////////////////////////////////////////

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Saving Services");
        pd.setMessage("Save in Progress...");
        pd.setProgressStyle(pd.STYLE_HORIZONTAL);
        pd.setProgress(0);
        pd.setCancelable(false);
        pd.setMax(0);
        pd.show();
        ListView listView = getListView();
        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
        LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(getActivity());
        dbHelper.open();

        boolean hasValue = false;

        if (checkedItems == null) {
            Toast.makeText(getActivity(), "Nothing to Save .", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    hasValue = true;
                    NewValues nv = new NewValues();

                    LegalDeliveryMessage ldm = (LegalDeliveryMessage) listView
                            .getAdapter().getItem(checkedItems.keyAt(i));
                    try {
                        Cursor c = dbHelper.fetchLD(ldm.getLegalDeliveryID());

                        // If row exists in local database, do not enter it
                        // again
                        if (c.getCount() != 0)
                            continue;
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringWriter st = new StringWriter();
                        e.printStackTrace(new PrintWriter(st));
                        ErroReportingInBackground errreprt = new ErroReportingInBackground(
                                getActivity());
                        errreprt.execute(st.toString());
                        Toast.makeText(getActivity(),
                                "Error reported through mail!",
                                Toast.LENGTH_SHORT).show();
                    }

                    try {
                        dbHelper.createLD(ldm.getLegalDeliveryID(), // _id
                                ldm.getJobNo(), // JobNo
                                ldm.getClient(), // Client,
                                ldm.getServiceType(), // ServiceType,
                                ldm.getLTServiceType(), // LTServiceType,
                                ldm.getCaseNo(), // CaseNo,
                                ldm.getLTFullname(), // LTFullname,
                                ldm.getLTAddress(), // LTAddress,
                                ldm.getLTApt(), // LTApt,
                                ldm.getLTCity(), // LTCity,
                                ldm.getLTState(), // LTState,
                                ldm.getLTZip(), // LTZip,
                                ldm.getInputDate(),
                                ldm.getproducttype()
                                //nv.getproducttype()

                                // InputDate,
                        );

                        int messageCount = ldm.getRelatedPersons()
                                .getRelatedPersonMessage().size();
                        Log.i("Related Person Msg Cnt:",
                                String.valueOf(messageCount));
                        pd.setMax(messageCount);
                        for (int I = 0; I < messageCount; I++) {
                            pd.setProgress(I);
                            RelatedPersonMessage rpm = (RelatedPersonMessage) ldm
                                    .getRelatedPersons()
                                    .getRelatedPersonMessage().get(I);
                            if (dbHelper
                                    .createRelatedPerson(
                                            rpm.getRelatedPersonID(),
                                            rpm.getLegalDeliveryID(),
                                            rpm.getFullname()) != -1)
                                Log.i("RelatdPersnMSg:"
                                                + getClass().getName() + " Succes",
                                        String.valueOf(dbHelper.createRelatedPerson(
                                                rpm.getRelatedPersonID(),
                                                rpm.getLegalDeliveryID(),
                                                rpm.getFullname())));
                            else
                                Log.i("Error" + getClass().getName(),
                                        "Error in Fetching RelatdPerson NOT FETCHED!!!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringWriter st = new StringWriter();
                        e.printStackTrace(new PrintWriter(st));
                        ErroReportingInBackground errreprt = new ErroReportingInBackground(
                                getActivity());
                        errreprt.execute(st.toString());
                        Toast.makeText(getActivity(),
                                "Error reported through mail!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

        dbHelper.close();
        /*
         * //Code disbled for development...sudheer..
         * if(mProgressDialog.isShowing()){
         *
         * mProgressDialog.dismiss(); }
         */

        if (hasValue) {
            pd.dismiss();
            Toast.makeText(this.getActivity(), "Saved!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Nothing to Save .", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isListEmpty() {
        ListView listView = getListView();
        if (listView.getCount() <= 0)
            return true;
        else
            return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    deviceID = Globals.GetDeviceID(getActivity().getBaseContext(), getActivity().getContentResolver());

                } else {

                    //Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    @SuppressWarnings("static-access")
    public void SaveWithResults() {
        NewValues nv = new NewValues();


        // Code disabled for
        // development...Sudheer...
        /*
         * mProgressDialog = new ProgressDialog(getActivity());
         * mProgressDialog.setIndeterminate(true);
         * mProgressDialog.setMessage("Please wait while Saving.....");
         * mProgressDialog.setCancelable(false); mProgressDialog.show();
         */
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Saving Services");
        pd.setMessage("Saving in Progress...");
        pd.setProgressStyle(pd.STYLE_HORIZONTAL);
        pd.setProgress(0);
        pd.show();
        ListView listView = getListView();

        boolean hasValue = false;

        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();

        LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(getActivity());
        dbHelper.open();

        if (checkedItems == null) {
            Toast.makeText(getActivity(), "Nothing to Save .", Toast.LENGTH_SHORT).show();
        } else {
            pd.setMax(checkedItems.size());
            dbHelper.deleteLD();
            for (int i = 0; i < checkedItems.size(); i++) {

                if (checkedItems.valueAt(i)) {
                    hasValue = true;
                    LegalDeliveryMessage ldm = (LegalDeliveryMessage) listView.getAdapter().getItem(checkedItems.keyAt(i));

                    try {

                        Cursor c = dbHelper.fetchLD(ldm.getLegalDeliveryID());

                        // If row exists in local database, do not enter it
                        // again
                        if (c.getCount() != 0)
                            continue;
                    } catch (Exception e) {
                        StringWriter st = new StringWriter();
                        e.printStackTrace(new PrintWriter(st));

                        ErroReportingInBackground errreprt = new ErroReportingInBackground(getActivity());
                        errreprt.execute(st.toString());
                        Toast.makeText(getActivity(), "Error reported through mail!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    try {
                        pd.setProgress(i);
                        dbHelper.createLD_Full(
                                ldm.getLegalDeliveryID(), // _id
                                ldm.getJobNo(), // JobNo
                                ldm.getClient(), // Client,
                                ldm.getServiceType(), // ServiceType,
                                ldm.getLTServiceType(), // LTServiceType,
                                ldm.getLTBIZNAME(), // LT BIZNESS NAME
                                ldm.getPPSERVED1(), // PPServed1
                                ldm.getServerLicenceNo(), // ServerLicenceNo
                                ldm.getCaseNo(), // CaseNo,
                                ldm.getLTFullname(), // LTFullname,
                                ldm.getLTAddress(), // LTAddress,
                                ldm.getLTApt(), // LTApt,
                                ldm.getLTCity(), // LTCity,
                                ldm.getLTState(), // LTState,
                                ldm.getLTZip(), // LTZip,
                                ldm.getInputDate(), // InputDate,
                                // stnd
                                ldm.getSTND_FULLNAME(),
                                ldm.getSTND_ADDRESS(),
                                ldm.getSTND_APT(),
                                ldm.getSTND_CITY(),
                                ldm.getSTND_STATE(),
                                ldm.getSTND_ZIP(),
                                ldm.getSTND_ServiceType(),
                                // Results
                                ldm.getPersonSeenSubstitute(),
                                ldm.getPersonSeenDoeSubstitute(),
                                ldm.getPersonSeenPersonal(),
                                ldm.getPersonsNotSeenPersonal(),
                                ldm.getCorpRecipient(),
                                ldm.getCorpRecipientTitle(),
                                ldm.getDateOfService(), ldm.getTimeOfService(),
                                ldm.getGPSDate1(), ldm.getGPSTime1(),
                                ldm.getGPSLat1(), ldm.getGPSLon1(),
                                ldm.getGPSDate2(), ldm.getGPSTime2(),
                                ldm.getGPSLat2(), ldm.getGPSLon2(),
                                ldm.getGPSDate3(), ldm.getGPSTime3(),
                                ldm.getGPSLat3(), ldm.getGPSLon3(),
                                ldm.getServiceResult(),
                                ldm.getDoorLock(),
                                ldm.getPServ(), ldm.getCServ(),
                                ldm.getOtherCommentsP(),
                                ldm.getOtherCommentsC(),
                                ldm.getFirstAttempt(), ldm.getSecondAttempt(),
                                ldm.getThirdAttempt(),
                                ldm.getServiceCompleted(),

                                ldm.getGpsTimeOfService(),
                                ldm.getGpsTimeOfFirstAttempt(),
                                ldm.getGpsTimeOfSecondAttempt(),
                                ldm.getGpsTimeOfThirdAttempt(),
                                0,// upload status
                                ldm.getproducttype()

                                //nv.getPriductType()
                                // flag
                        );

						/*int messageCount = ldm.getRelatedPersons().getRelatedPersonMessage().size();

						for (int I = 0; I < messageCount; I++) {

							RelatedPersonMessage rpm = (RelatedPersonMessage) ldm
									.getRelatedPersons()
									.getRelatedPersonMessage().get(I);
							dbHelper.createRelatedPerson(
									rpm.getRelatedPersonID(),
									rpm.getLegalDeliveryID(), rpm.getFullname());
						}*/

                        /*
                         * String buzzname = ldm.getLTBIZNAME();
                         * if(buzzname.equals("")){
                         *
                         * }
                         */
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringWriter st = new StringWriter();
                        e.printStackTrace(new PrintWriter(st));
                        ErroReportingInBackground errreprt = new ErroReportingInBackground(
                                getActivity());
                        errreprt.execute(st.toString());
                        Toast.makeText(getActivity(),
                                "Error reported through mail!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        dbHelper.close();

        /*
         * //Code Disabled for development ...Sudheer...
         * if(mProgressDialog.isShowing()){
         *
         * mProgressDialog.dismiss(); }
         */
        if (hasValue) {
            pd.dismiss();
            Toast.makeText(this.getActivity(), "Saved!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Nothing to Save .", Toast.LENGTH_SHORT).show();
        }
    }

    public void SelectAll() {
        ListView listView = getListView();
        int count = 0;
        // ///////////////////////////////////////////////////////////////////////////////
        alreport.reportAudit("Selecting all record!", getActivity());
        // ///////////////////////////////////////////////////////////////////////////////
        try {
            count = getListAdapter().getCount();
        } catch (Exception e) {
            e.printStackTrace();
            StringWriter st = new StringWriter();
            e.printStackTrace(new PrintWriter(st));
            ErroReportingInBackground errreprt = new ErroReportingInBackground(getActivity());
            errreprt.execute(st.toString());
            Toast.makeText(getActivity(), "Error reported through mail!", Toast.LENGTH_SHORT).show();
        }
        if (count == 0) {
            Toast.makeText(getActivity(), "Nothing To select .", Toast.LENGTH_SHORT).show();
        } else {
            for (int I = 0; I < count; I++) {
                listView.setItemChecked(I, true);
            }
        }
    }

    public void ClearAll() {
        ListView listView = getListView();
        // ///////////////////////////////////////////////////////////////////////////////
        alreport.reportAudit("Clearing All!", getActivity());
        // ///////////////////////////////////////////////////////////////////////////////
        int count = 0;
        try {
            count = getListAdapter().getCount();
        } catch (Exception e) {
            e.printStackTrace();
            StringWriter st = new StringWriter();
            e.printStackTrace(new PrintWriter(st));

            ErroReportingInBackground errreprt = new ErroReportingInBackground(
                    getActivity());
            errreprt.execute(st.toString());
            Toast.makeText(getActivity(), "Error reported through mail!",
                    Toast.LENGTH_SHORT).show();
        }
        if (count == 0) {
            Toast.makeText(getActivity(), "Nothing to Clear .",
                    Toast.LENGTH_SHORT).show();
        } else {
            for (int I = 0; I < count; I++) {
                listView.setItemChecked(I, false);
            }
        }
    }

    public void Download() {
        if (!Globals.IsNetworkAvailable(getActivity().getBaseContext())) {
            Globals.ShowNoNetworkMessage(getActivity().getBaseContext());
            return;
        }
        services.clear();
        LegalDeliveryActivity.setPreviousAttemptAddress("");
        //String sz= LegalDeliveryActivity.setPreviousAttemptAddress();
        LegalDeliveryActivity.setPreviousFirstAttemptTime("");
        LegalDeliveryActivity.setPreviousSecondAttemptTime("");


        alreport.reportAudit("Executing Download Task!", getActivity());
		/*DownloadTask task = new DownloadTask();

		task.execute(new String[] { deviceID });*/
		/*SendPostRequest sendPostRequest = new SendPostRequest();
		sendPostRequest.execute();*/

        SendPostRequest sendPostRequest = new SendPostRequest();
        sendPostRequest.execute();
    }

    //mitesh
    public void DownloadFiltered(int fromDay, int fromMonth, int fromYear,
                                 int toDay, int toMonth, int toYear) {
        if (!Globals.IsNetworkAvailable(getActivity().getBaseContext())) {
            Globals.ShowNoNetworkMessage(getActivity().getBaseContext());
            return;
        }

        services.clear();
        LegalDeliveryActivity.setPreviousAttemptAddress("");

        LegalDeliveryActivity.setPreviousFirstAttemptTime("");
        LegalDeliveryActivity.setPreviousSecondAttemptTime("");
        //String deviceID = GetDeviceID();


        DownloadTaskFiltered task = new DownloadTaskFiltered();

        task.execute(new String[]{deviceID, Integer.toString(fromDay), Integer.toString(fromMonth),
                Integer.toString(fromYear),
                Integer.toString(toDay), Integer.toString(toMonth), Integer.toString(toYear)});
    }

    /*downloadLi`	0
     * s
     *t.DownloadFiltered2(dtFrom.getDayOfMonth(),
            dtFrom.getMonthOfYear(), dtFrom.getYear(),
            dtTo.getDayOfMonth(), dtTo.getMonthOfYear(),
            dtTo.getYear());
    */
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }

    public void DownloadFiltered2(String dtFrom, String dtTo) {
        if (!Globals.IsNetworkAvailable(getActivity().getBaseContext())) {
            Globals.ShowNoNetworkMessage(getActivity().getBaseContext());
            return;
        }

        services.clear();
        LegalDeliveryActivity.setPreviousAttemptAddress("");

        LegalDeliveryActivity.setPreviousFirstAttemptTime("");
        LegalDeliveryActivity.setPreviousSecondAttemptTime("");


        DownloadTaskFiltered2 task = new DownloadTaskFiltered2();

        task.execute(new String[]{deviceID, dtFrom, dtTo});
    }


    //mitesh
	/* public void DownloadFiltered(int fromDay, int fromMonth, int fromYear,
	  int toDay, int toMonth, int toYear) { if
	 (!Globals.IsNetworkAvailable(getActivity().getBaseContext())) {
	  Globals.ShowNoNetworkMessage(getActivity().getBaseContext()); return; }

	 services.clear();

	 // String deviceID = GetDeviceID(); String deviceID =
	 Globals.GetDeviceID(getActivity().getBaseContext(),
     getActivity().getContentResolver());

	  DownloadTaskFiltered task = new DownloadTaskFiltered(); task.execute(new
	  String[] { deviceID, Integer.toString(fromDay),
	 Integer.toString(fromMonth), Integer.toString(fromYear),
	  Integer.toString(toDay), Integer.toString(toMonth),
	  Integer.toString(toYear) }); }*/


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        @SuppressWarnings("static-access")
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // ///////////////////////////////////////////////////////////////////////////////
            alreport.reportAudit("Downloading Task initiated!", getActivity());

            pd = new ProgressDialog(getActivity());
            pd.setTitle("Downloading Services...");
            pd.setMessage("It might take few minutes for download!");
            pd.setProgressStyle(pd.STYLE_HORIZONTAL);
            // pd.setProgress(0);
            pd.setIndeterminate(true);
            // pd.setMax(0);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... deviceInfo) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String returnstatement = "";
            try {
                MultipartEntity entity = new MultipartEntity();
                entity.addPart("DeviceID", new StringBody(deviceInfo[0]));
                Log.i("Device infp[0]", deviceInfo[0]);// Delete after test
                // Sudheer...
                httppost.setEntity(entity);

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    try {
                        String strDataLength = response.getFirstHeader("DataLength").getValue();
                        // String strDataLength =
                        // response.getFirstHeader("Content-Length").getValue();
                        int dataLength = Integer.parseInt(strDataLength);
                        Log.i("String data Length:", String.valueOf(dataLength));// Delete
                        // after
                        // test/..sudheer...
                        strMessageCount = response.getFirstHeader("MessageCount").getValue();
                        int messageCount = Integer.parseInt(strMessageCount);
                        // setting max value in progress dailog...
                        pd.setMax(messageCount);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        resEntity.writeTo(bos);
                        byte[] totalData = bos.toByteArray();
                        byte[] actualMessages = new byte[dataLength];
                        // src //dst
                        System.arraycopy(totalData, 0, actualMessages, 0,
                                dataLength);
                        LegalDeliveryMessages ldms = LegalDeliveryMessages
                                .parseFrom(actualMessages);
                        List<LegalDeliveryMessage> services1 = new ArrayList<LegalDeliveryMessage>();
                        for (int I = 0; I < messageCount; I++) {
                            LegalDeliveryMessage ldm = (LegalDeliveryMessage) ldms
                                    .getLegalDeliveryMessage().get(I);
                            services1.add(ldm);
                            publishProgress(I);
                        }
                        Collections.sort(services1, new Comparator<LegalDeliveryMessage>() {
                            @Override
                            public int compare(LegalDeliveryMessage o1, LegalDeliveryMessage o2) {
                                return (o2.getJobNo()).compareTo(o1.getJobNo());
                            }
                        });
                        services.addAll(services1);
                        returnstatement = "LegalDeliveryNoResponse";
                    } catch (final HttpHostConnectException httpconnectionException) {
                        getActivity().runOnUiThread(new Runnable() {

                            public void run() {
                                Toast.makeText(getActivity(), "Error:" + httpconnectionException
                                                .getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (NullPointerException nullxption) {
                        getActivity().runOnUiThread(new Runnable() {

                            public void run() {
                                Toast.makeText(getActivity(),
                                        "No data For Download!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                        nullxption.printStackTrace();

                    } catch (Exception e) {
                        returnstatement = "No data for download  "
                                + e.getMessage();

                        e.printStackTrace();
                        StringWriter st = new StringWriter();
                        e.printStackTrace(new PrintWriter(st));

                        ErroReportingInBackground errreprt = new ErroReportingInBackground(
                                getActivity());
                        errreprt.execute(st.toString());
                        Toast.makeText(getActivity(),
                                "Error reported through mail!",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (ClientProtocolException e) {
                returnstatement = "No Response Found From Server :- "
                        + e.getMessage();
                e.printStackTrace();

            } catch (IOException e) {
                returnstatement = "No Response Found From Server :- "
                        + e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                returnstatement = "No Response Found From Server :- "
                        + e.getMessage();
                e.printStackTrace();
            }
            pd.dismiss();
            return returnstatement;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (pd.isIndeterminate()) {
                pd.setIndeterminate(false);
            }
            pd.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // Disable for development..result.Sudheer...
            /*
             * if(mProgressDialog.isShowing()){
             *
             * mProgressDialog.dismiss(); }
             */


            // ///////////////////////////////////////////////////////////////////////////////
            alreport.reportAudit("Downloading task finished!", getActivity());
            // ///////////////////////////////////////////////////////////////////////////////

            if (result.equals("LegalDeliveryNoResponse")) {
                setListAdapter(new LDMessageAdapter(getActivity(), services));

                ListView listView = getListView();
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                DownloadControlFragment downloadControl = (DownloadControlFragment) getFragmentManager()
                        .findFragmentById(R.id.DownloadControlFragmentID);

                downloadControl.SetNumOfDownloadMessages(strMessageCount);
            } else {
                if (result.equals(null))
                    Globals.NoResponse_Data(getActivity().getBaseContext(),
                            result);
            }

        }
    }

    private class SendPostRequest extends AsyncTask<String, Integer, String> {

        HttpURLConnection conn = null;

        @SuppressWarnings("static-access")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alreport.reportAudit("Downloading Task initiated!", getActivity());
            pd = new ProgressDialog(getActivity());
            pd.setTitle("Downloading Services...");
            pd.setMessage("It might take few minutes for download!");
            pd.setProgressStyle(pd.STYLE_HORIZONTAL);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            //String producttype="";

            String authorization = "";
            String thumbprint = "";
            try {

                System.out.println(deviceID);
                DownloadStructure ds = new DownloadStructure();
                ds.setDeviceId(deviceID);

                URL url = new URL("http://45.64.105.199/LDSWebService/api/AndroidSearch");
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                //set headers
				/*String authorization="1.0.11.170102";
				String thumbprint="AAF8CE958E71370E6426E75C8B46B14D4CDD8C35";

				conn.setRequestProperty("authorization", "1.0.11.170102" );
				conn.setRequestProperty("Thumbprint", "AAF8CE958E71370E6426E75C8B46B14D4CDD8C35" );
				ds.setAuthorization(authorization);
				ds.setThumbprint(thumbprint);
				*/


				/*authorization=Globals.Authorization;
				thumbprint=Globals.Thumbprint;
				ds.setAuthorization(authorization);
				ds.setThumbprint(thumbprint);
				System.out.print(authorization);
				*/

                conn.setRequestProperty("authorization", Globals.Authorization);
                conn.setRequestProperty("Thumbprint", Globals.Thumbprint);


                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(15000);
                Gson gson = new Gson();
                String JSONString = gson.toJson(ds);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                        (conn.getOutputStream(), "UTF-8"));
                bw.write(JSONString);
                bw.flush();
                bw.close();
                conn.connect();
                int responseCode = conn.getResponseCode();
//				conn.getHeaderFields()

                if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    isInvalidUser = true;
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    InputStreamReader isw = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(isw);
                    StringBuilder sb = new StringBuilder("");
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    in.close();
                    final String output = sb.toString();

                    ///////////////////deactivated user

                    // get header by key

                    Map<String, List<String>> map = conn.getHeaderFields();
                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                        System.out.println("Key : " + entry.getKey() +
                                " ,Value : " + entry.getValue());

                    }
                    String ptype = conn.getHeaderField("producttype");
                    String ptype2 = ptype.replaceAll("^[\"']+|[\"']+$", "");
                    System.out.println(ptype2);
                    //conn.setRequestProperty("producttype");
                    ///////////////////////////
                    NewValues newValues = gson.fromJson(output, NewValues.class);
                    ArrayList<NewMessage> mData = newValues.$values;
                    int abc = mData.size();
                    System.out.print(abc);

                    strMessageCount = Integer.toString(mData.size());

                    for (int I = 0; I < mData.size(); I++) {
                        LegalDeliveryMessage.Builder builderLegalDeliveryMessage = LegalDeliveryMessage.
                                newBuilder();
                        builderLegalDeliveryMessage.setLegalDeliveryID(mData.get(I).getLegalDeliveryID());
                        builderLegalDeliveryMessage.setJobNo(mData.get(I).getJobNo());
                        builderLegalDeliveryMessage.setClient(mData.get(I).getClient());
                        builderLegalDeliveryMessage.setCaseNo(mData.get(I).getCaseNo());
                        builderLegalDeliveryMessage.setInputDate(mData.get(I).getInputDate());

                        builderLegalDeliveryMessage.setLTFullname(mData.get(I).getLTFullname());
                        if (mData.get(I).getGPSLat1() != null) {
                            builderLegalDeliveryMessage.setGPSLat1(Double.parseDouble(mData.get(I).getGPSLat1()));
                        }
                        if (mData.get(I).getGPSLon1() != null) {
                            builderLegalDeliveryMessage.setGPSLon1(Double.parseDouble(mData.get(I).getGPSLon1()));
                        }
                        if (mData.get(I).getGPSLat2() != null) {
                            builderLegalDeliveryMessage.setGPSLat2(Double.parseDouble(mData.get(I).getGPSLat2()));

                        }
                        if (mData.get(I).getGPSLon2() != null) {
                            builderLegalDeliveryMessage.setGPSLon2(Double.parseDouble(mData.get(I).getGPSLon2()));
                        }

                        if (mData.get(I).getGPSLat3() != null) {

                            builderLegalDeliveryMessage.setGPSLat3(Double.parseDouble(mData.get(I).getGPSLat3()));
                        }
                        if (mData.get(I).getGPSLon3() != null) {
                            builderLegalDeliveryMessage.setGPSLon3(Double.parseDouble(mData.get(I).getGPSLon3()));
                        }
                        builderLegalDeliveryMessage.setLTBIZNAME(mData.get(I).getLTBIZNAME());
                        builderLegalDeliveryMessage.setDateOfService(mData.get(I).getDateOfService());
                        builderLegalDeliveryMessage.setTimeOfService(mData.get(I).getDateOfService());
                        builderLegalDeliveryMessage.setPersonSeenSubstitute(mData.get(I).getPersonSeenSubstitute());
                        builderLegalDeliveryMessage.setPersonSeenDoeSubstitute(mData.get(I).getPersonSeenDoeSubstitute());
                        builderLegalDeliveryMessage.setPersonSeenPersonal(mData.get(I).getPersonSeenPersonal());
                        builderLegalDeliveryMessage.setPersonsNotSeenPersonal(mData.get(I).getPersonsNotSeenPersonal());
                        builderLegalDeliveryMessage.setOtherCommentsC(mData.get(I).getOtherCommentsC());
                        builderLegalDeliveryMessage.setOtherCommentsP(mData.get(I).getOtherCommentsP());
                        // if(mData.get(I).getFirstAttempt().equalsIgnoreCase("true")){
                        if (mData.get(I).getFirstAttempt != 0) {
                            builderLegalDeliveryMessage.setFirstAttempt(1);
                        } else {
                            builderLegalDeliveryMessage.setFirstAttempt(0);
                        }
                        if (mData.get(I).getSecondAttempt != 0) {
                            builderLegalDeliveryMessage.setSecondAttempt(1);
                        } else {
                            builderLegalDeliveryMessage.setSecondAttempt(0);
                        }
                        if (mData.get(I).getThirdAttempt != 0) {

                            builderLegalDeliveryMessage.setThirdAttempt(1);
                        } else {
                            builderLegalDeliveryMessage.setThirdAttempt(0);
                        }
                        builderLegalDeliveryMessage.setSTND_TYPE10DESC(mData.get(I).getSTND_TYPE10DESC());
                        builderLegalDeliveryMessage.setSTND_INDEXNO(mData.get(I).getSTND_INDEXNO());
                        builderLegalDeliveryMessage.setSTND_Court(mData.get(I).getSTND_Court());
                        builderLegalDeliveryMessage.setSTND_County(mData.get(I).getSTND_County());
                        builderLegalDeliveryMessage.setSTND_Plantiff(mData.get(I).getSTND_Plantiff());
                        builderLegalDeliveryMessage.setSTND_Defendant(mData.get(I).getSTND_Defendant());
                        builderLegalDeliveryMessage.setSTND_FULLNAME(mData.get(I).getSTND_FULLNAME());
                        builderLegalDeliveryMessage.setSTND_ADDRESS(mData.get(I).getSTND_ADDRESS());
                        builderLegalDeliveryMessage.setSTND_APT(mData.get(I).getSTND_APT());
                        builderLegalDeliveryMessage.setSTND_CITY(mData.get(I).getSTND_CITY());
                        builderLegalDeliveryMessage.setSTND_STATE(mData.get(I).getSTND_STATE());
                        builderLegalDeliveryMessage.setSTND_ZIP(mData.get(I).getSTND_ZIP());
                        if (mData.get(I).getServiceCompleted().equalsIgnoreCase("true")) {
                            builderLegalDeliveryMessage.setServiceCompleted(1);
                        } else {
                            builderLegalDeliveryMessage.setServiceCompleted(0);
                        }
                        builderLegalDeliveryMessage.setServiceType(mData.get(I).getServiceType());
                        builderLegalDeliveryMessage.setLTServiceType(mData.get(I).getLTServiceType());
                        builderLegalDeliveryMessage.setLTAddress(mData.get(I).getLTAddress());
                        builderLegalDeliveryMessage.setLTApt(mData.get(I).getLTApt());
                        builderLegalDeliveryMessage.setLTCity(mData.get(I).getLTCity());
                        builderLegalDeliveryMessage.setGPSTime1(mData.get(I).getGPSTime1());
                        builderLegalDeliveryMessage.setGPSTime2(mData.get(I).getGPSTime2());
                        builderLegalDeliveryMessage.setGPSTime3(mData.get(I).getGPSTime3());
                        builderLegalDeliveryMessage.setGpsTimeOfFirstAttempt(mData.get(I).getGpsTimeOfFirstAttempt());
                        builderLegalDeliveryMessage.setGpsTimeOfSecondAttempt(mData.get(I).getGpsTimeOfSecondAttempt());
                        builderLegalDeliveryMessage.setGpsTimeOfThirdAttempt(mData.get(I).getGpsTimeOfThirdAttempt());
                        builderLegalDeliveryMessage.setGpsTimeOfService(mData.get(I).getGpsTimeOfService());
                        builderLegalDeliveryMessage.setGPSDate1(mData.get(I).getGPSDate1());
                        builderLegalDeliveryMessage.setGPSDate2(mData.get(I).getGPSDate2());
                        builderLegalDeliveryMessage.setGPSDate3(mData.get(I).getGPSDate3());
                        builderLegalDeliveryMessage.setTimeOfService(mData.get(I).getTimeOfService());
                        builderLegalDeliveryMessage.setDateOfService(mData.get(I).getDateOfService());
                        builderLegalDeliveryMessage.setLTCity(mData.get(I).getLTCity());
                        builderLegalDeliveryMessage.setLTApt(mData.get(I).getLTApt());
                        builderLegalDeliveryMessage.setLTState(mData.get(I).getLTState());
                        builderLegalDeliveryMessage.setLTZip(mData.get(I).getLTZip());
                        builderLegalDeliveryMessage.setLTAddress(mData.get(I).getLTAddress());
                        builderLegalDeliveryMessage.setServiceResult(mData.get(I).getServiceResult());
                        builderLegalDeliveryMessage.setCaseNo(mData.get(I).getCaseNo());
                        builderLegalDeliveryMessage.setServerLicenceNo(mData.get(I).getServerLicenceNo());
                        String mGender = mData.get(I).getP_Gender();
                        String mP_Skincolor = mData.get(I).getP_Skincolor();
                        String mP_Hair = mData.get(I).getP_Hair();
                        String mP_Age = mData.get(I).getP_Age();
                        String mP_Height = mData.get(I).getP_Height();
                        String mP_Weight = mData.get(I).getP_Weight();
                        String mC_Entry = mData.get(I).getC_Entry();
                        String mC_Wall = mData.get(I).getC_Wall();
                        String mC_Floor = mData.get(I).getC_Floor();
                        String mC_Lock = mData.get(I).getC_Lock();
                        String mC_Door = mData.get(I).getC_Door();
                        String mDoorLock = mData.get(I).getDoorLock();
                        builderLegalDeliveryMessage.setproducttype(ptype);

                        //String pType=ptype;

                        String mPersonDescrption = mGender + ";" + mP_Skincolor + ";" +
                                mP_Hair + ";" + mP_Age + ";" + mP_Height + ";" + mP_Weight;
                        builderLegalDeliveryMessage.setPServ(mPersonDescrption);

                        String mCServDescrption = mC_Entry + ";" + mC_Wall + ";" +
                                mC_Floor + ";" + mC_Lock + ";" + mC_Door + ";" + mDoorLock;

                        builderLegalDeliveryMessage.setCServ(mCServDescrption);
                        pref = getActivity().getSharedPreferences("MyPref", getActivity().getBaseContext().MODE_PRIVATE);
                        Editor editor = pref.edit();
                        editor.putString("product_code", ptype2);
                        editor.commit();
                        Globals.ProductCode = pref.getString("product_code", null);
                        //  Globals.ProductCode=ptype;
                        //  Globals.ProductCode=(mData.get(I).getproducttype());
                        LegalDeliveryMessage legalDeliveryMessage = new LegalDeliveryMessage
                                (builderLegalDeliveryMessage);
                        services.add(legalDeliveryMessage);
                        publishProgress(I);
                        Log.i("msg count:", String.valueOf(I));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (pd.isIndeterminate()) {
                pd.setIndeterminate(false);
            }
            pd.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {


            if (isInvalidUser) {
                LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(getActivity());
                dbHelper.open();
                dbHelper.deleteRecords();
                Intent mIntent = new Intent(getActivity(), BlueScreenDesign.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                getActivity().finish();
                dbHelper.close();
            }

            // ///////////////////////////////////////////////////////////////////////////////
            alreport.reportAudit("Downloading task finished!", getActivity());

            /*if (result.equals("LegalDeliveryNoResponse")) {*/

            setListAdapter(new LDMessageAdapter(getActivity(), services));

            ListView listView = getListView();
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            DownloadControlFragment downloadControl = (DownloadControlFragment) getFragmentManager()
                    .findFragmentById(R.id.DownloadControlFragmentID);

            downloadControl.SetNumOfDownloadMessages(strMessageCount);

            pd.dismiss();

        }
    }

    //mitesh
    private class DownloadTaskFiltered2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Please wait while Downloading.....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... deviceInfo) {
            try {
                FiltStructure fe = new FiltStructure();
                fe.setDeviceID(deviceInfo[0]);
                fe.setFromdate(deviceInfo[1]);
                fe.setTodate(deviceInfo[2]);
                URL url = new URL("http://45.64.105.199/LDSWebService/api/AndroidSearch");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setRequestProperty("authorization", Globals.Authorization);
                conn.setRequestProperty("Thumbprint", Globals.Thumbprint);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.setConnectTimeout(15000);
                Gson gson = new Gson();
                String JSONString = gson.toJson(fe);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                        (conn.getOutputStream(), "UTF-8"));
                bw.write(JSONString);
                bw.flush();
                bw.close();
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    InputStreamReader isw = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(isw);
                    StringBuilder sb = new StringBuilder("");
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    in.close();
                    final String outputfilter = sb.toString();
					/*FilterValue filtValues = gson.fromJson(outputfilter, FilterValue.class);
					ArrayList<FiltStructure> mData  = filtValues.$values2;
					int abc = mData.size();
					System.out.print(abc);*/
                    Map<String, List<String>> map = conn.getHeaderFields();
                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                        System.out.println("Key : " + entry.getKey() +
                                " ,Value : " + entry.getValue());

                    }
                    String ptype = conn.getHeaderField("producttype");
                    String ptype2 = ptype.replaceAll("^[\"']+|[\"']+$", "");
                    System.out.println(ptype2);

                    NewValues newValues = gson.fromJson(outputfilter, NewValues.class);
                    ArrayList<NewMessage> mData = newValues.$values;
                    int abc = mData.size();
                    System.out.print(abc);

                    strMessageCount = Integer.toString(mData.size());

                    for (int I = 0; I < mData.size(); I++) {
                        LegalDeliveryMessage.Builder builderLegalDeliveryMessage = LegalDeliveryMessage.
                                newBuilder();
                        if (mData.get(I).getLegalDeliveryID() != null) {
                            builderLegalDeliveryMessage.setLegalDeliveryID(mData.get(I).getLegalDeliveryID());
                        }
                        if (mData.get(I).getJobNo() != null) {
                            builderLegalDeliveryMessage.setJobNo(mData.get(I).getJobNo());
                        }
                        if (mData.get(I).getClient() != null) {
                            builderLegalDeliveryMessage.setClient(mData.get(I).getClient());
                        }
                        if (mData.get(I).getCaseNo() != null) {
                            builderLegalDeliveryMessage.setCaseNo(mData.get(I).getCaseNo());
                        }
                        if (mData.get(I).getInputDate() != null) {
                            builderLegalDeliveryMessage.setInputDate(mData.get(I).getInputDate());
                        }
                        if (mData.get(I).getLTFullname() != null) {
                            builderLegalDeliveryMessage.setLTFullname(mData.get(I).getLTFullname());
                        }
                        if (mData.get(I).getGPSLat1() != null) {
                            builderLegalDeliveryMessage.setGPSLat1(Double.parseDouble(mData.get(I).getGPSLat1()));
                        }
                        if (mData.get(I).getGPSLon1() != null) {
                            builderLegalDeliveryMessage.setGPSLon1(Double.parseDouble(mData.get(I).getGPSLon1()));
                        }
                        if (mData.get(I).getGPSLat2() != null) {
                            builderLegalDeliveryMessage.setGPSLat2(Double.parseDouble(mData.get(I).getGPSLat2()));

                        }
                        if (mData.get(I).getGPSLon2() != null) {
                            builderLegalDeliveryMessage.setGPSLon2(Double.parseDouble(mData.get(I).getGPSLon2()));
                        }

                        if (mData.get(I).getGPSLat3() != null) {

                            builderLegalDeliveryMessage.setGPSLat3(Double.parseDouble(mData.get(I).getGPSLat3()));
                        }
                        if (mData.get(I).getGPSLon3() != null) {
                            builderLegalDeliveryMessage.setGPSLon3(Double.parseDouble(mData.get(I).getGPSLon3()));
                        }
                        if (mData.get(I).getLTBIZNAME() != null) {
                            builderLegalDeliveryMessage.setLTBIZNAME(mData.get(I).getLTBIZNAME());
                        }
                        if (mData.get(I).getDateOfService() != null) {
                            builderLegalDeliveryMessage.setDateOfService(mData.get(I).getDateOfService());
                        }
                        if (mData.get(I).getDateOfService() != null) {
                            builderLegalDeliveryMessage.setTimeOfService(mData.get(I).getDateOfService());
                        }
                        if (mData.get(I).getPersonSeenSubstitute() != null) {
                            builderLegalDeliveryMessage.setPersonSeenSubstitute(mData.get(I).getPersonSeenSubstitute());
                        }
                        if (mData.get(I).getPersonSeenDoeSubstitute() != null) {
                            builderLegalDeliveryMessage.setPersonSeenDoeSubstitute(mData.get(I).getPersonSeenDoeSubstitute());
                        }
                        if (mData.get(I).getPersonSeenPersonal() != null) {
                            builderLegalDeliveryMessage.setPersonSeenPersonal(mData.get(I).getPersonSeenPersonal());
                        }
                        if (mData.get(I).getPersonsNotSeenPersonal() != null) {
                            builderLegalDeliveryMessage.setPersonsNotSeenPersonal(mData.get(I).getPersonsNotSeenPersonal());
                        }
                        if (mData.get(I).getOtherCommentsC() != null) {
                            builderLegalDeliveryMessage.setOtherCommentsC(mData.get(I).getOtherCommentsC());
                        }
                        if (mData.get(I).getOtherCommentsP() != null) {
                            builderLegalDeliveryMessage.setOtherCommentsP(mData.get(I).getOtherCommentsP());
                        }

                        if (mData.get(I).getFirstAttempt != 0) {
                            builderLegalDeliveryMessage.setFirstAttempt(1);
                        } else {
                            builderLegalDeliveryMessage.setFirstAttempt(0);
                        }

                        if (mData.get(I).getSecondAttempt != 0) {
                            builderLegalDeliveryMessage.setSecondAttempt(1);
                        } else {
                            builderLegalDeliveryMessage.setSecondAttempt(0);
                        }
                        if (mData.get(I).getThirdAttempt != 0) {

                            builderLegalDeliveryMessage.setThirdAttempt(1);
                        } else {
                            builderLegalDeliveryMessage.setThirdAttempt(0);
                        }
                        if (mData.get(I).getSTND_TYPE10DESC() != null) {
                            builderLegalDeliveryMessage.setSTND_TYPE10DESC(mData.get(I).getSTND_TYPE10DESC());
                        }
                        if (mData.get(I).getSTND_INDEXNO() != null) {
                            builderLegalDeliveryMessage.setSTND_INDEXNO(mData.get(I).getSTND_INDEXNO());
                        }
                        if (mData.get(I).getSTND_Court() != null) {
                            builderLegalDeliveryMessage.setSTND_Court(mData.get(I).getSTND_Court());
                        }
                        if (mData.get(I).getSTND_County() != null) {
                            builderLegalDeliveryMessage.setSTND_County(mData.get(I).getSTND_County());
                        }
                        if (mData.get(I).getSTND_Plantiff() != null) {
                            builderLegalDeliveryMessage.setSTND_Plantiff(mData.get(I).getSTND_Plantiff());
                        }
                        if (mData.get(I).getSTND_Defendant() != null) {
                            builderLegalDeliveryMessage.setSTND_Defendant(mData.get(I).getSTND_Defendant());
                        }
                        if (mData.get(I).getSTND_FULLNAME() != null) {
                            builderLegalDeliveryMessage.setSTND_FULLNAME(mData.get(I).getSTND_FULLNAME());
                        }
                        if (mData.get(I).getSTND_ADDRESS() != null) {
                            builderLegalDeliveryMessage.setSTND_ADDRESS(mData.get(I).getSTND_ADDRESS());
                        }
                        if (mData.get(I).getSTND_APT() != null) {
                            builderLegalDeliveryMessage.setSTND_APT(mData.get(I).getSTND_APT());
                        }
                        if (mData.get(I).getSTND_CITY() != null) {
                            builderLegalDeliveryMessage.setSTND_CITY(mData.get(I).getSTND_CITY());
                        }
                        if (mData.get(I).getSTND_STATE() != null) {
                            builderLegalDeliveryMessage.setSTND_STATE(mData.get(I).getSTND_STATE());
                        }
                        if (mData.get(I).getSTND_ZIP() != null) {
                            builderLegalDeliveryMessage.setSTND_ZIP(mData.get(I).getSTND_ZIP());
                        }
                        if (mData.get(I).getServiceCompleted().equalsIgnoreCase("true")) {
                            builderLegalDeliveryMessage.setServiceCompleted(1);
                        } else {
                            builderLegalDeliveryMessage.setServiceCompleted(0);
                        }

                        if (mData.get(I).getServiceType() != null) {
                            builderLegalDeliveryMessage.setServiceType(mData.get(I).getServiceType());
                        }
                        if (mData.get(I).getLTServiceType() != null) {
                            builderLegalDeliveryMessage.setLTServiceType(mData.get(I).getLTServiceType());
                        }
                        if (mData.get(I).getLTAddress() != null) {
                            builderLegalDeliveryMessage.setLTAddress(mData.get(I).getLTAddress());
                        }
                        if (mData.get(I).getLTApt() != null) {
                            builderLegalDeliveryMessage.setLTApt(mData.get(I).getLTApt());
                        }
                        if (mData.get(I).getLTCity() != null) {
                            builderLegalDeliveryMessage.setLTCity(mData.get(I).getLTCity());
                        }
                        if (mData.get(I).getGPSTime1() != null) {
                            builderLegalDeliveryMessage.setGPSTime1(mData.get(I).getGPSTime1());
                        }
                        if (mData.get(I).getGPSTime2() != null) {
                            builderLegalDeliveryMessage.setGPSTime2(mData.get(I).getGPSTime2());
                        }
                        if (mData.get(I).getGPSTime3() != null) {
                            builderLegalDeliveryMessage.setGPSTime3(mData.get(I).getGPSTime3());
                        }
                        if (mData.get(I).getGpsTimeOfFirstAttempt() != null) {
                            builderLegalDeliveryMessage.setGpsTimeOfFirstAttempt(mData.get(I).getGpsTimeOfFirstAttempt());
                        }
                        if (mData.get(I).getGpsTimeOfSecondAttempt() != null) {
                            builderLegalDeliveryMessage.setGpsTimeOfSecondAttempt(mData.get(I).getGpsTimeOfSecondAttempt());
                        }
                        if (mData.get(I).getGpsTimeOfThirdAttempt() != null) {
                            builderLegalDeliveryMessage.setGpsTimeOfThirdAttempt(mData.get(I).getGpsTimeOfThirdAttempt());
                        }
                        if (mData.get(I).getGpsTimeOfService() != null) {
                            builderLegalDeliveryMessage.setGpsTimeOfService(mData.get(I).getGpsTimeOfService());
                        }
                        if (mData.get(I).getGPSDate1() != null) {
                            builderLegalDeliveryMessage.setGPSDate1(mData.get(I).getGPSDate1());
                        }
                        if (mData.get(I).getGPSDate2() != null) {
                            builderLegalDeliveryMessage.setGPSDate2(mData.get(I).getGPSDate2());
                        }
                        if (mData.get(I).getGPSDate3() != null) {
                            builderLegalDeliveryMessage.setGPSDate3(mData.get(I).getGPSDate3());
                        }
                        if (mData.get(I).getTimeOfService() != null) {
                            builderLegalDeliveryMessage.setTimeOfService(mData.get(I).getTimeOfService());
                        }
                        if (mData.get(I).getDateOfService() != null) {
                            builderLegalDeliveryMessage.setDateOfService(mData.get(I).getDateOfService());
                        }
                        if (mData.get(I).getLTCity() != null) {
                            builderLegalDeliveryMessage.setLTCity(mData.get(I).getLTCity());
                        }
                        if (mData.get(I).getLTApt() != null) {
                            builderLegalDeliveryMessage.setLTApt(mData.get(I).getLTApt());
                        }
                        if (mData.get(I).getLTState() != null) {
                            builderLegalDeliveryMessage.setLTState(mData.get(I).getLTState());
                        }
                        if (mData.get(I).getLTZip() != null) {
                            builderLegalDeliveryMessage.setLTZip(mData.get(I).getLTZip());
                        }
                        if (mData.get(I).getLTAddress() != null) {
                            builderLegalDeliveryMessage.setLTAddress(mData.get(I).getLTAddress());
                        }
                        if (mData.get(I).getServiceResult() != null) {
                            builderLegalDeliveryMessage.setServiceResult(mData.get(I).getServiceResult());
                        }
                        if (mData.get(I).getCaseNo() != null) {
                            builderLegalDeliveryMessage.setCaseNo(mData.get(I).getCaseNo());
                        }
                        if (mData.get(I).getServerLicenceNo() != null) {
                            builderLegalDeliveryMessage.setServerLicenceNo(mData.get(I).getServerLicenceNo());
                        }
                        String mGender = mData.get(I).getP_Gender();
                        String mP_Skincolor = mData.get(I).getP_Skincolor();
                        String mP_Hair = mData.get(I).getP_Hair();
                        String mP_Age = mData.get(I).getP_Age();
                        String mP_Height = mData.get(I).getP_Height();
                        String mP_Weight = mData.get(I).getP_Weight();

                        String mC_Entry = mData.get(I).getC_Entry();
                        String mC_Wall = mData.get(I).getC_Wall();
                        String mC_Floor = mData.get(I).getC_Floor();
                        String mC_Lock = mData.get(I).getC_Lock();
                        String mC_Door = mData.get(I).getC_Door();
                        String mDoorLock = mData.get(I).getDoorLock();
                        builderLegalDeliveryMessage.setproducttype(ptype);

                        String mPersonDescrption = mGender + ";" + mP_Skincolor + ";" +
                                mP_Hair + ";" + mP_Age + ";" + mP_Height + ";" + mP_Weight;
                        builderLegalDeliveryMessage.setPServ(mPersonDescrption);

                        String mCServDescrption = mC_Entry + ";" + mC_Wall + ";" +
                                mC_Floor + ";" + mC_Lock + ";" + mC_Door + ";" + mDoorLock;
                        builderLegalDeliveryMessage.setCServ(mCServDescrption);
                        pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        Editor editor = pref.edit();
                        editor.putString("product_code", ptype2);
                        editor.apply();
                        Globals.ProductCode = pref.getString("product_code", null);
                        LegalDeliveryMessage legalDeliveryMessage = new LegalDeliveryMessage
                                (builderLegalDeliveryMessage);
                        services.add(legalDeliveryMessage);

                        Log.i("msg count:", String.valueOf(I));
                    }

                }
            } catch (Exception e) {
                Log.e("error_samsung",e.getMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {

            if (isInvalidUser) {
                LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(getActivity());
                dbHelper.open();
                dbHelper.deleteRecords();
                Intent mIntent = new Intent(getActivity(), BlueScreenDesign.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mIntent);
                getActivity().finish();
                dbHelper.close();
            }


            if (mProgressDialog.isShowing()) {

                mProgressDialog.dismiss();

                setListAdapter(new LDMessageAdapter(getActivity(), services));

                ListView listView = getListView();
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                DownloadControlFragment downloadControl = (DownloadControlFragment) getFragmentManager()
                        .findFragmentById(R.id.DownloadControlFragmentID);

                downloadControl.SetNumOfDownloadMessages(strMessageCount);
            }

        }
    }

    private class DownloadTaskFiltered extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Please wait while Downloading.....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... deviceInfo) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            String returnstatement = "";

            try {
                MultipartEntity entity = new MultipartEntity();
                entity.addPart("DeviceID", new StringBody(deviceInfo[0]));
                entity.addPart("FromDay", new StringBody(deviceInfo[1]));
                entity.addPart("FromMonth", new StringBody(deviceInfo[2]));
                entity.addPart("FromYear", new StringBody(deviceInfo[3]));
                entity.addPart("ToDay", new StringBody(deviceInfo[4]));
                entity.addPart("ToMonth", new StringBody(deviceInfo[5]));
                entity.addPart("ToYear", new StringBody(deviceInfo[6]));

                httppost.setEntity(entity);

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    try {
                        String strDataLength = response.getFirstHeader("DataLength").getValue();
                        int dataLength = Integer.parseInt(strDataLength);

                        strMessageCount = response.getFirstHeader("MessageCount").getValue();
                        int messageCount = Integer.parseInt(strMessageCount);

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        resEntity.writeTo(bos);

                        byte[] totalData = bos.toByteArray();
                        byte[] actualMessages = new byte[dataLength];

                        System.arraycopy(totalData, 0, actualMessages, 0, dataLength);

                        LegalDeliveryMessages ldms = LegalDeliveryMessages.parseFrom(actualMessages);

                        for (int I = 0; I < messageCount; I++) {
                            LegalDeliveryMessage ldm = (LegalDeliveryMessage) ldms.getLegalDeliveryMessage().get(I);
                            services.add(ldm);

                        }
                        returnstatement = "LegalDeliveryNoResponse";
                    } catch (Exception e) {
                        returnstatement = "No data for download :- " + e.getMessage();
                        ;
                        e.printStackTrace();
                    }
                }
            } catch (ClientProtocolException e) {
                returnstatement = "No Response Found From Server :- " + e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                returnstatement = "No Response Found From Server :- " + e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                returnstatement = "No Response Found From Server :- " + e.getMessage();
                e.printStackTrace();
            }

            return returnstatement;
        }

        @Override
        protected void onPostExecute(String result) {

            if (mProgressDialog.isShowing()) {

                mProgressDialog.dismiss();
            }

            if (result.equals("LegalDeliveryNoResponse")) {
                setListAdapter(new LDMessageAdapter(getActivity(), services));

                ListView listView = getListView();
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                DownloadControlFragment downloadControl = (DownloadControlFragment) getFragmentManager()
                        .findFragmentById(R.id.DownloadControlFragmentID);

                downloadControl.SetNumOfDownloadMessages(strMessageCount);
            } else {
                Globals.NoResponse_Data(getActivity().getBaseContext(), result);
            }
        }
    }

    public void setContentView(int blueScreen) {
        // TODO Auto-generated method stub

    }


}