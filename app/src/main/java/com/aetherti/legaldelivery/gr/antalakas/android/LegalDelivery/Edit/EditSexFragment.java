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


public class EditSexFragment extends Fragment implements OnCheckedChangeListener{

	CheckBox checkBoxM;
	CheckBox checkBoxF;
	Button saveButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.ld_edit_sex_fragment, container,
				false);

		checkBoxM = (CheckBox) V.findViewById(R.id.checkBoxM);
		checkBoxF = (CheckBox) V.findViewById(R.id.checkBoxF);

		checkBoxM.setOnCheckedChangeListener(this);
		checkBoxF.setOnCheckedChangeListener(this);
		saveButton = (Button) V.findViewById(R.id.saveButton2);

		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditCol1Fragment editCol1Fragment;
				editCol1Fragment = (EditCol1Fragment) getFragmentManager()
						.findFragmentById(R.id.EditCol1FragmentID);
				EditCol2Fragment editCol2Fragment;
				editCol2Fragment = (EditCol2Fragment) getFragmentManager()
						.findFragmentById(R.id.EditCol2FragmentID);
				try {
					String timecheck=editCol1Fragment.gpstime1TextView.getText().toString();
					String datecpmplare=editCol1Fragment.gpsdate1TextView.getText().toString();
					if((!datecpmplare.equals("[Not Set]"))&&(!timecheck.equals("[Not Set]"))){
					editCol2Fragment.SaveService();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		final Button backToDeliveryInfo = (Button) V.findViewById(R.id.backToDeliveryInfo);
		backToDeliveryInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					((EditActivity) getActivity()).editTabHost.setCurrentTab(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		final Button backToSearch1Button = (Button) V.findViewById(R.id.backToSearch1Button);
		backToSearch1Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					getActivity().finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		populateFields();
		return V;
	}

	Boolean IsAtLeastOneSelected() {
		if (checkBoxM.isChecked() || checkBoxF.isChecked())
			return true;

		return false;
	}

	String GetSex() {
		StringBuilder sb = new StringBuilder();
		sb.append(checkBoxM.isChecked() ? "1" : "0");
		sb.append(checkBoxF.isChecked() ? "1" : "0");
		return sb.toString();
	}

	void SetSex(String strSex) {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if (serviceResult != null) {
			if (serviceResult.equals("Personal")|| serviceResult.equals("Personal Plus")|| serviceResult.equals("Substitute")) {
				char charM = strSex.charAt(0);
				char charF = strSex.charAt(1);

				checkBoxM.setChecked((charM == '1') ? true : false);
				checkBoxF.setChecked((charF == '1') ? true : false);
			}
		}
	}

	private void populateFields() {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String strPService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PServ));
		if (strPService == null)
			return;

		String[] strPServiceItems = strPService.split("_");
		SetSex(strPServiceItems[0]);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	if(buttonView == checkBoxM){
		if(checkBoxM.isChecked())
			checkBoxF.setChecked(false);
	}
	if(buttonView == checkBoxF){
		if(checkBoxF.isChecked())
			checkBoxM.setChecked(false);
	}
	}
}