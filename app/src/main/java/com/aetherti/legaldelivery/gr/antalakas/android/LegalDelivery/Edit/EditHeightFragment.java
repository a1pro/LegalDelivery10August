package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.aetherti.legaldelivery.R;

public class EditHeightFragment extends Fragment implements
		OnCheckedChangeListener {

	CheckBox checkBox5;
	CheckBox checkBox553;
	CheckBox checkBox5458;
	CheckBox checkBox5960;
	CheckBox checkBox6;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.ld_edit_height_fragment, container,
				false);

		checkBox5 = (CheckBox) V.findViewById(R.id.checkBox5);
		checkBox553 = (CheckBox) V.findViewById(R.id.checkBox553);
		checkBox5458 = (CheckBox) V.findViewById(R.id.checkBox5458);
		checkBox5960 = (CheckBox) V.findViewById(R.id.checkBox5960);
		checkBox6 = (CheckBox) V.findViewById(R.id.checkBox6);
		checkBox5.setOnCheckedChangeListener(this);
		checkBox553.setOnCheckedChangeListener(this);
		checkBox5458.setOnCheckedChangeListener(this);
		checkBox5960.setOnCheckedChangeListener(this);
		checkBox6.setOnCheckedChangeListener(this);

		populateFields();

		return V;
	}

	Boolean IsAtLeastOneSelected() {
		if (checkBox5.isChecked() || checkBox553.isChecked()
				|| checkBox5458.isChecked() || checkBox5960.isChecked()
				|| checkBox6.isChecked())
			return true;

		return false;
	}

	String GetHeight() {

		StringBuilder sb = new StringBuilder();

		sb.append(checkBox5.isChecked() ? "1" : "0");
		sb.append(checkBox553.isChecked() ? "1" : "0");
		sb.append(checkBox5458.isChecked() ? "1" : "0");
		sb.append(checkBox5960.isChecked() ? "1" : "0");
		sb.append(checkBox6.isChecked() ? "1" : "0");

		return sb.toString();
	}

	void SetHeight(String strHeight) {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor
				.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if (serviceResult != null) {
			if (serviceResult.equals("Personal")
					|| serviceResult.equals("Personal Plus")
					|| serviceResult.equals("Substitute")) {
				char char5 = strHeight.charAt(0);
				char char553 = strHeight.charAt(1);
				char char5458 = strHeight.charAt(2);
				char char5960 = strHeight.charAt(3);
				char char6 = strHeight.charAt(4);

				checkBox5.setChecked(char5 == '1' ? true : false);
				checkBox553.setChecked(char553 == '1' ? true : false);
				checkBox5458.setChecked(char5458 == '1' ? true : false);
				checkBox5960.setChecked(char5960 == '1' ? true : false);
				checkBox6.setChecked(char6 == '1' ? true : false);
				
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
		SetHeight(strPServiceItems[4]);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBox5:
			if (checkBox5.isChecked()) {
				checkBox553.setChecked(false); 
				checkBox5458.setChecked(false); 
				checkBox5960.setChecked(false); 
				checkBox6.setChecked(false); 
			}
			break;
		case R.id.checkBox553:
			if (checkBox553.isChecked()) {
				checkBox5.setChecked(false); 
				checkBox5458.setChecked(false); 
				checkBox5960.setChecked(false); 
				checkBox6.setChecked(false); 
			}
			break;
		case R.id.checkBox5458:
			if (checkBox5458.isChecked()) {
				checkBox5.setChecked(false); 
				checkBox553.setChecked(false); 
				checkBox5960.setChecked(false); 
				checkBox6.setChecked(false); 
			}
			break;
		case R.id.checkBox5960:
			if (checkBox5960.isChecked()) {
				checkBox5.setChecked(false); 
				checkBox553.setChecked(false); 
				checkBox5458.setChecked(false); 
				checkBox6.setChecked(false); 
			}
			break;
		case R.id.checkBox6:
			if (checkBox6.isChecked()) {
				checkBox5.setChecked(false); 
				checkBox553.setChecked(false); 
				checkBox5458.setChecked(false); 
				checkBox5960.setChecked(false); 
			}
			break;
		}
	}
}
