package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.text.ParseException;
import java.util.Calendar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterFragment extends Fragment implements OnClickListener {

	EditText fullnameEditText;
	EditText addressEditText;
	EditText aptEditText;
	Spinner ltservicetypeSpinner;
	Spinner servicetypeSpinner;
	TextView inputDateTextView;
	TextView DomainLabel;
	AuditLogReporter alreport;
	Calendar cal = Calendar.getInstance();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		///////////////////////////////////////////////////////////////////////////////////////////
		alreport=new AuditLogReporter();
		final Context con=getActivity();
		///////////////////////////////////////////////////////////////////////////////////////////
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.ld_filter_fragment, container, false);
		   
		fullnameEditText = (EditText) V.findViewById(R.id.fullnameEditText);
		addressEditText = (EditText) V.findViewById(R.id.addressEditText);
		aptEditText = (EditText) V.findViewById(R.id.aptEditText);
		DomainLabel = (TextView) V.findViewById(R.id.DomainLabel);
		if(Globals.Domain.equals("http://45.64.105.199/LDSWeb")){
		DomainLabel.setText("Server 2");
		}
		else if(Globals.Domain.equals("http://45.64.105.198/LDSWeb")){
			DomainLabel.setText("Server 1");
		}
		ltservicetypeSpinner = (Spinner) V
				.findViewById(R.id.ltservicetypeSpinner);
		servicetypeSpinner = (Spinner) V.findViewById(R.id.servicetypeSpinner);

		inputDateTextView = (TextView) V.findViewById(R.id.inputDateTextView);

		inputDateTextView.setOnClickListener(this);

		servicetypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,View view, int position, long arg) {
						String ServiceType = servicetypeSpinner.getSelectedItem().toString();
						///////////////////////////////////////////////////////
						alreport.reportAudit("Service Type selected ? "+ServiceType,con);
						///////////////////////////////////////////////////////

						Log.i("SelectdFilterFragmnt:", ServiceType);
						if (ServiceType.equals("Standard")) {

							ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
											getActivity(),
											R.array.stndservicetypes,
											android.R.layout.simple_spinner_item);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							ltservicetypeSpinner.setAdapter(adapter);

							
						} else if (ServiceType.equals("L&T Commercial")
								|| ServiceType.equals("L&T Residential")) {

							ArrayAdapter<CharSequence> adapter = ArrayAdapter
									.createFromResource(
											getActivity(),
											R.array.ltservicetypes,
											android.R.layout.simple_spinner_item);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							ltservicetypeSpinner.setAdapter(adapter);

						
						} else if (ServiceType.equals("[Not Set]")) {

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									getActivity(),
									android.R.layout.simple_spinner_dropdown_item);
							adapter.add("[Not Set]");
							
							ltservicetypeSpinner.setAdapter(adapter);
						}
						
					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		final Button searchButton = (Button) V.findViewById(R.id.searchButton);

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					FilterResultFragment filterResultList = (FilterResultFragment)getFragmentManager().findFragmentById(R.id.FilterResultFragmentID);

					filterResultList.Search(
							fullnameEditText.getText().toString(), 
							addressEditText.getText().toString(),
							aptEditText.getText().toString(),
							ltservicetypeSpinner.getSelectedItem().toString(),
							servicetypeSpinner.getSelectedItem().toString(),
							inputDateTextView.getText().toString());
				} catch (Exception exception) {
					Log.i("Search : ", exception.getMessage());
					exception.printStackTrace();
				}
			}
		});

		// Only for testing, Clear button will add a test service database
		// record
		final Button clearButton = (Button) V.findViewById(R.id.clearButton);

		clearButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FilterResultFragment filterResultList = (FilterResultFragment) getFragmentManager()
						.findFragmentById(R.id.FilterResultFragmentID);
				///////////////////////////////////////////////////////
				alreport.reportAudit("Cleared!", con);
				///////////////////////////////////////////////////////

				filterResultList.AddTestService(
						"2064f83e-1f85-4365-99b8-52d317ed02c1",
						"9d1fe65d-7e76-482e-b7c9-8208772d9d1b");
			}
		});

		return V;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.inputDateTextView:
			HandleDateAttempt(v);
			break;
		}
	}

	private void HandleDateAttempt(View v) {
		TextView tv = (((LegalDeliveryActivity) getActivity()).lastDateView) = (TextView) v;
		String strDate = "";
		if (tv.getText().toString().equals("[Not Set]"))
			strDate = String.format("%02d-%02d-%04d",
					((LegalDeliveryActivity) getActivity()).mMonth,
					((LegalDeliveryActivity) getActivity()).mDay,
					((LegalDeliveryActivity) getActivity()).mYear);
		else
			strDate = tv.getText().toString();

		try {
			cal.setTime(Globals.sdf.parse(strDate + " 0:00 AM"));
			(((LegalDeliveryActivity) getActivity()).mYear) = cal
					.get(Calendar.YEAR);
			(((LegalDeliveryActivity) getActivity()).mMonth) = cal
					.get(Calendar.MONTH);
			(((LegalDeliveryActivity) getActivity()).mDay) = cal
					.get(Calendar.DAY_OF_MONTH);
			getActivity().showDialog(Globals.DATE_DIALOG_ID);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void ClearFilters() {
		fullnameEditText.setText("");
		addressEditText.setText("");
		aptEditText.setText("");
		ltservicetypeSpinner.setSelection(0);
		servicetypeSpinner.setSelection(0);
		inputDateTextView.setText("[Not Set]");
	}

	public String getSelectedServiceType() {
		return servicetypeSpinner.getSelectedItem().toString();
	}
}
