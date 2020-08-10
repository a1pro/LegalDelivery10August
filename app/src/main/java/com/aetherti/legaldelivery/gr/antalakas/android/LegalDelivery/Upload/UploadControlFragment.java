package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Upload;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
public class UploadControlFragment extends Fragment{

	TextView numOfRecordsTextView;
	AuditLogReporter alreport;
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		alreport=new AuditLogReporter();
		// Inflate the layout for this fragment
		View V =  inflater.inflate(R.layout.ld_upload_control_fragment, container, false);

		final Button searchButton = (Button)V.findViewById(R.id.searchButton);
		final Button uploadButton = (Button)V.findViewById(R.id.uploadButton);
		final Button selectAllButton = (Button)V.findViewById(R.id.selectAllButton);
		final Button clearAllButton = (Button)V.findViewById(R.id.clearAllButton);
		numOfRecordsTextView = (TextView)V.findViewById(R.id.numOfRecords);

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {            	
				alreport.reportAudit("Upload Search Button Touched!", getActivity());
				UploadListFragment uploadList = (UploadListFragment) getFragmentManager().findFragmentById(R.id.UploadListFragmentID);
				uploadList.Search();
			}
		});

		uploadButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {   
				alreport.reportAudit("Upload Button Touched!", getActivity());
				UploadListFragment uploadList = (UploadListFragment) getFragmentManager().findFragmentById(R.id.UploadListFragmentID);//initiallize upload page and database
				uploadList.Upload();
			}
		});

		selectAllButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				alreport.reportAudit("Selcet-All Button touched! ",getActivity());
				UploadListFragment uploadList = (UploadListFragment) getFragmentManager().findFragmentById(R.id.UploadListFragmentID);
				uploadList.SelectAll();
			}
		});

		clearAllButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {            	
				alreport.reportAudit("Clear-All  ",getActivity());
				UploadListFragment uploadList = (UploadListFragment) getFragmentManager().findFragmentById(R.id.UploadListFragmentID);
				uploadList.ClearAll();
			}
		});

		return V;
	}

	public void SetNumOfMessagesToUpload(String strMessageCount) {
		numOfRecordsTextView.setText(strMessageCount);
	}
}
