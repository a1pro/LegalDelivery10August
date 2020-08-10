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

public class EditAgeFragment extends Fragment implements OnCheckedChangeListener {

	CheckBox checkBox1417;
	CheckBox checkBox1821;
	CheckBox checkBox2230;
	CheckBox checkBox3139;
	CheckBox checkBox4049;
	CheckBox checkBox5059;
	CheckBox checkBox6069;
	CheckBox checkBox70;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.ld_edit_age_fragment, container,false);

		checkBox1417 = (CheckBox) V.findViewById(R.id.checkBox1417);
		checkBox1821 = (CheckBox) V.findViewById(R.id.checkBox1821);
		checkBox2230 = (CheckBox) V.findViewById(R.id.checkBox2230);
		checkBox3139 = (CheckBox) V.findViewById(R.id.checkBox3139);
		checkBox4049 = (CheckBox) V.findViewById(R.id.checkBox4049);
		checkBox5059 = (CheckBox) V.findViewById(R.id.checkBox5059);
		checkBox6069 = (CheckBox) V.findViewById(R.id.checkBox6069);
		checkBox70 = (CheckBox) V.findViewById(R.id.checkBox70);

		checkBox1417.setOnCheckedChangeListener(this);
		checkBox1821.setOnCheckedChangeListener(this);
		checkBox2230.setOnCheckedChangeListener(this);
		checkBox3139.setOnCheckedChangeListener(this);
		checkBox4049.setOnCheckedChangeListener(this);
		checkBox5059.setOnCheckedChangeListener(this);
		checkBox6069.setOnCheckedChangeListener(this);
		checkBox70.setOnCheckedChangeListener(this);
		
		populateFields();

		return V;
	}

	Boolean IsAtLeastOneSelected() {
		if (checkBox1417.isChecked() || checkBox1821.isChecked()
				|| checkBox2230.isChecked() || checkBox3139.isChecked()
				|| checkBox4049.isChecked() || checkBox5059.isChecked()
				|| checkBox6069.isChecked() || checkBox70.isChecked())
			return true;

		return false;
	}

	String GetAge() {

		StringBuilder sb = new StringBuilder();

		sb.append(checkBox1417.isChecked() ? "1" : "0");
		sb.append(checkBox1821.isChecked() ? "1" : "0");
		sb.append(checkBox2230.isChecked() ? "1" : "0");
		sb.append(checkBox3139.isChecked() ? "1" : "0");
		sb.append(checkBox4049.isChecked() ? "1" : "0");
		sb.append(checkBox5059.isChecked() ? "1" : "0");
		sb.append(checkBox6069.isChecked() ? "1" : "0");
		sb.append(checkBox70.isChecked() ? "1" : "0");

		return sb.toString();
	}

	void SetAge(String strAge) {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if (serviceResult != null) {
			if (serviceResult.equals("Personal") || serviceResult.equals("Personal Plus")
					|| serviceResult.equals("Substitute")) {
				char char1417 = strAge.charAt(0);
				char char1821 = strAge.charAt(1);
				char char2230 = strAge.charAt(2);
				char char3139 = strAge.charAt(3);
				char char4049 = strAge.charAt(4);
				char char5059 = strAge.charAt(5);
				char char6069 = strAge.charAt(6);
				char char70 = strAge.charAt(7);

				checkBox1417.setChecked(char1417 == '1' ? true : false);
				checkBox1821.setChecked(char1821 == '1' ? true : false);
				checkBox2230.setChecked(char2230 == '1' ? true : false);
				checkBox3139.setChecked(char3139 == '1' ? true : false);
				checkBox4049.setChecked(char4049 == '1' ? true : false);
				checkBox5059.setChecked(char5059 == '1' ? true : false);
				checkBox6069.setChecked(char6069 == '1' ? true : false);
				checkBox70.setChecked(char70 == '1' ? true : false);
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
		SetAge(strPServiceItems[3]);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBox1417:
			if (checkBox1417.isChecked()) {
				checkBox1821.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox6069.setChecked(false);
				checkBox70.setChecked(false);
				
			}
			break;
		case R.id.checkBox1821:
			if (checkBox1821.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox6069.setChecked(false);
				checkBox70.setChecked(false);
				
			}
			break;
		case R.id.checkBox2230:
			if (checkBox2230.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox1821.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox6069.setChecked(false);
				checkBox70.setChecked(false);
				
			}
			break;
		case R.id.checkBox3139:
			if (checkBox3139.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox1821.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox6069.setChecked(false);
				checkBox70.setChecked(false);
			
			}
			break;
		case R.id.checkBox4049:
			if (checkBox4049.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox1821.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox6069.setChecked(false);
				checkBox70.setChecked(false);
				
			}
			break;
		case R.id.checkBox5059:
			if (checkBox5059.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox1821.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox6069.setChecked(false);
				checkBox70.setChecked(false);
			}
			break;
		case R.id.checkBox6069:
			if (checkBox6069.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox1821.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox70.setChecked(false);
			}
			break;
		case R.id.checkBox70:
			if (checkBox70.isChecked()) {
				checkBox1417.setChecked(false);
				checkBox1821.setChecked(false);
				checkBox2230.setChecked(false);
				checkBox3139.setChecked(false);
				checkBox4049.setChecked(false);
				checkBox5059.setChecked(false);
				checkBox6069.setChecked(false);
			}
			break;
		}
	}
}
