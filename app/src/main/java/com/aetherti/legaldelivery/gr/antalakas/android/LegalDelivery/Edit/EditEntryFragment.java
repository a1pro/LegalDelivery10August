package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.aetherti.legaldelivery.R;

public class EditEntryFragment extends Fragment implements OnCheckedChangeListener {

	CheckBox checkBoxWALKUP;
	CheckBox checkBoxELEVAT;
	CheckBox checkBoxSTOREFRONT;
	 Button saveButton3;
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V =  inflater.inflate(R.layout.ld_edit_entry_fragment, container, false);

		checkBoxWALKUP = (CheckBox)V.findViewById(R.id.checkBoxWALKUP);
		checkBoxELEVAT = (CheckBox)V.findViewById(R.id.checkBoxELEVAT);
		checkBoxSTOREFRONT = (CheckBox)V.findViewById(R.id.checkBoxSTOREFRONT);
		
		checkBoxWALKUP.setOnCheckedChangeListener(this);
		checkBoxELEVAT.setOnCheckedChangeListener(this);
		checkBoxSTOREFRONT.setOnCheckedChangeListener(this);
		
		saveButton3 = (Button)V.findViewById(R.id.saveButton3);

		saveButton3.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{            	
				try {
					EditCol1Fragment editCol1Fragment;
					editCol1Fragment = (EditCol1Fragment) getFragmentManager()
							.findFragmentById(R.id.EditCol1FragmentID);
					String timecheck=editCol1Fragment.gpstime1TextView.getText().toString();
					String datecpmplare=editCol1Fragment.gpsdate1TextView.getText().toString();
					EditCol2Fragment editCol2Fragment = (EditCol2Fragment) getFragmentManager().findFragmentById(R.id.EditCol2FragmentID);
					if((!datecpmplare.equals("[Not Set]"))&&(!timecheck.equals("[Not Set]"))){
						editCol2Fragment.SaveService();
					}							  
				}
				catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		
		final Button backToDeliveryInfo = (Button)V.findViewById(R.id.backToDeliveryInfo);

		backToDeliveryInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {            	
				try {	
					((EditActivity)getActivity()).editTabHost.setCurrentTab(0);
				}
				catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		

		final Button backToSearch2Button = (Button)V.findViewById(R.id.backToSearch2Button);

		backToSearch2Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {            	
				try {	
					getActivity().finish();
				}
				catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});

		populateFields();
		return V;
	}

	Boolean IsAtLeastOneSelected() {
		if (checkBoxWALKUP.isChecked() || checkBoxELEVAT.isChecked()
				|| checkBoxSTOREFRONT.isChecked())
			return true;

		return false;
	}


	String GetEntry() {

		StringBuilder sb = new StringBuilder();

		sb.append(checkBoxWALKUP.isChecked() ? "1" : "0");
		sb.append(checkBoxELEVAT.isChecked() ? "1" : "0");
		sb.append(checkBoxSTOREFRONT.isChecked() ? "1" : "0");

		return sb.toString();
	}

	void SetEntry(String strEntry) {
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if(serviceResult != null){
			if(serviceResult.equals("Conspicuous")){
				char charWALKUP = strEntry.charAt(0);
				char charELEVAT = strEntry.charAt(1);
				char charSTOREFRONT = strEntry.charAt(2);

				checkBoxWALKUP.setChecked(charWALKUP == '1' ? true : false);
				checkBoxELEVAT.setChecked(charELEVAT == '1' ? true : false);
				checkBoxSTOREFRONT.setChecked(charSTOREFRONT == '1' ? true : false);
			}
		}
	}

	private void populateFields()
	{
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String strCService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_CServ));

		if (strCService == null)
			return;

		String [] strCServiceItems = strCService.split("_");
		SetEntry(strCServiceItems[0]);
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBoxWALKUP:
			if (checkBoxWALKUP.isChecked()) {
				checkBoxELEVAT.setChecked(false);
				checkBoxSTOREFRONT.setChecked(false);
			}
			break;

		case R.id.checkBoxELEVAT:
			if (checkBoxELEVAT.isChecked()) {
				checkBoxWALKUP.setChecked(false);
				checkBoxSTOREFRONT.setChecked(false);
			}
			break;

		case R.id.checkBoxSTOREFRONT:
			if (checkBoxSTOREFRONT.isChecked()) {
				checkBoxWALKUP.setChecked(false);
				checkBoxELEVAT.setChecked(false);
			}
			break;
			}
	}
}
