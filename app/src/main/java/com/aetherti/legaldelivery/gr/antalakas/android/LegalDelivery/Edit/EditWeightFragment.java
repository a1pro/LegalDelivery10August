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

public class EditWeightFragment extends Fragment implements
		OnCheckedChangeListener {

	CheckBox checkBox099;
	CheckBox checkBox100130;
	CheckBox checkBox131160;
	CheckBox checkBox161200;
	CheckBox checkBox200;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.ld_edit_weight_fragment, container,
				false);

		checkBox099 = (CheckBox) V.findViewById(R.id.checkBox099);
		checkBox100130 = (CheckBox) V.findViewById(R.id.checkBox100130);
		checkBox131160 = (CheckBox) V.findViewById(R.id.checkBox131160);
		checkBox161200 = (CheckBox) V.findViewById(R.id.checkBox161200);
		checkBox200 = (CheckBox) V.findViewById(R.id.checkBox200);

		checkBox099.setOnCheckedChangeListener(this);
		checkBox100130.setOnCheckedChangeListener(this);
		checkBox131160.setOnCheckedChangeListener(this);
		checkBox161200.setOnCheckedChangeListener(this);
		checkBox200.setOnCheckedChangeListener(this);

		populateFields();

		return V;
	}

	Boolean IsAtLeastOneSelected() {
		if (checkBox099.isChecked() || checkBox100130.isChecked()
				|| checkBox131160.isChecked() || checkBox161200.isChecked()
				|| checkBox200.isChecked())
			return true;

		return false;
	}

	String GetWeight() {

		StringBuilder sb = new StringBuilder();

		sb.append(checkBox099.isChecked() ? "1" : "0");
		sb.append(checkBox100130.isChecked() ? "1" : "0");
		sb.append(checkBox131160.isChecked() ? "1" : "0");
		sb.append(checkBox161200.isChecked() ? "1" : "0");
		sb.append(checkBox200.isChecked() ? "1" : "0");

		return sb.toString();
	}

	void SetWeight(String strWeight) {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor
				.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if (serviceResult != null) {
			if (serviceResult.equals("Personal")
					|| serviceResult.equals("Personal Plus")
					|| serviceResult.equals("Substitute")) {
				char char099 = strWeight.charAt(0);
				char char100130 = strWeight.charAt(1);
				char char131160 = strWeight.charAt(2);
				char char161200 = strWeight.charAt(3);
				char char200 = strWeight.charAt(4);

				checkBox099.setChecked(char099 == '1' ? true : false);
				checkBox100130.setChecked(char100130 == '1' ? true : false);
				checkBox131160.setChecked(char131160 == '1' ? true : false);
				checkBox161200.setChecked(char161200 == '1' ? true : false);
				checkBox200.setChecked(char200 == '1' ? true : false);
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
		SetWeight(strPServiceItems[5]);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBox099:
			if(checkBox099.isChecked()){
				 checkBox100130.setChecked(false);
				 checkBox131160.setChecked(false);
				 checkBox161200.setChecked(false);
				 checkBox200.setChecked(false);
			}
			break;
		case R.id.checkBox100130:
			if(checkBox100130.isChecked()){
				 checkBox099.setChecked(false);
				 checkBox131160.setChecked(false);
				 checkBox161200.setChecked(false);
				 checkBox200.setChecked(false);
			}
			break;
		case R.id.checkBox131160:
			if(checkBox131160.isChecked()){
				 checkBox099.setChecked(false);
				 checkBox100130.setChecked(false);
				 checkBox161200.setChecked(false);
				 checkBox200.setChecked(false);
			}
			break;
		case R.id.checkBox161200:
			if(checkBox161200.isChecked()){
				 checkBox099.setChecked(false);
				 checkBox100130.setChecked(false);
				 checkBox131160.setChecked(false);
				 checkBox200.setChecked(false);
			}
			break;
		case R.id.checkBox200:
			if(checkBox200.isChecked()){
				 checkBox099.setChecked(false);
				 checkBox100130.setChecked(false);
				 checkBox131160.setChecked(false);
				 checkBox161200.setChecked(false);
			}
			break;
		}
	}
}
