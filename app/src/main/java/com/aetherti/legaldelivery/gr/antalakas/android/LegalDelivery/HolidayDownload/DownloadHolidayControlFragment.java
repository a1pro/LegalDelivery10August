package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.HolidayDownload;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Globals;

public class DownloadHolidayControlFragment extends Fragment{
	
	TextView numOfRecordsTextView;
	Button hdSelectAllBtn;
	Button hdClearAllBtn;
	View V;
	@Override
    public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
	ViewGroup container, Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
		 V =  inflater.inflate(R.layout.hd_download_control_fragment, container, false);
		//final Button downloadButton = (Button) getView().findViewById(R.id.downloadButton);
		final Button downloadButton = (Button)V.findViewById(R.id.downloadButton);
		final Button saveButton = (Button)V.findViewById(R.id.saveButton);
		final EditText yearEditText = (EditText)V.findViewById(R.id.yearEditText);
		hdSelectAllBtn = (Button) V.findViewById(R.id.hdselectAllButton);
		hdSelectAllBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DownloadHolidayListFragment hddownloadList = (DownloadHolidayListFragment) getFragmentManager()
						.findFragmentById(R.id.DownloadHolidayListFragmentID);
				hddownloadList.SelectAll();
			}
		});
		hdClearAllBtn=(Button)V.findViewById(R.id.hdclearAllButton);
		hdClearAllBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DownloadHolidayListFragment hddownloadList = (DownloadHolidayListFragment) getFragmentManager()
						.findFragmentById(R.id.DownloadHolidayListFragmentID);
				hddownloadList.ClearAll();
			}
		});
		
		numOfRecordsTextView = (TextView)V.findViewById(R.id.numOfRecords);
	
		downloadButton.setOnClickListener(new View.OnClickListener() {
			
            public void onClick(View v) {            
            	String sYear = yearEditText.getText().toString();
            	if (sYear.isEmpty()){
            		Globals.ShowDialog("Please enter year!", getActivity());
            		yearEditText.requestFocus();
            		return;
            	}
            	
	           	DownloadHolidayListFragment downloadList = (DownloadHolidayListFragment) getFragmentManager()
	     	           .findFragmentById(R.id.DownloadHolidayListFragmentID);
	           	downloadList.Download(sYear);
            }
        });
		
		saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {            	
	           	 DownloadHolidayListFragment downloadList = (DownloadHolidayListFragment) getFragmentManager()
	     	            .findFragmentById(R.id.DownloadHolidayListFragmentID);
	           	downloadList.Save();
            }
        });
		return V;
	}
	
	public void SetNumOfDownloadMessages(String strMessageCount) {
		numOfRecordsTextView.setText(strMessageCount);
	}
}
