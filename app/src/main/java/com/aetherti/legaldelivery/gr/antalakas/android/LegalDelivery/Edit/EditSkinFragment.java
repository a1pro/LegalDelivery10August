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

public class EditSkinFragment extends Fragment implements
		OnCheckedChangeListener {

	CheckBox checkBoxWHTE;
	CheckBox checkBoxBLK;
	CheckBox checkBoxBRWN;
	CheckBox checkBoxASN;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.ld_edit_skin_fragment, container,false);

		checkBoxWHTE = (CheckBox) V.findViewById(R.id.checkBoxWHTE);
		checkBoxBLK = (CheckBox) V.findViewById(R.id.checkBoxBLK);
		checkBoxBRWN = (CheckBox) V.findViewById(R.id.checkBoxBRWN);
		checkBoxASN=(CheckBox) V.findViewById(R.id.checkBoxASN);
		checkBoxWHTE.setOnCheckedChangeListener(this);
		checkBoxBLK.setOnCheckedChangeListener(this);
		checkBoxBRWN.setOnCheckedChangeListener(this);
		checkBoxASN.setOnCheckedChangeListener(this);
		populateFields();
		return V;
	}

	Boolean IsAtLeastOneSelected() {
		if (checkBoxWHTE.isChecked() || checkBoxBLK.isChecked()
				|| checkBoxBRWN.isChecked() || checkBoxASN.isChecked())
			return true;

		return false;
	}

	String GetSkin() {

		StringBuilder sb = new StringBuilder();
		sb.append(checkBoxWHTE.isChecked() ? "1" : "0");
		sb.append(checkBoxBLK.isChecked() ? "1" : "0");
		sb.append(checkBoxBRWN.isChecked() ? "1" : "0");
		sb.append(checkBoxASN.isChecked() ? "1" : "0");
		return sb.toString();
	}

	void SetSkin(String strSkin) {
		EditActivity editActivity = (EditActivity) getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if (serviceResult != null) {
			if (serviceResult.equals("Personal")|| serviceResult.equals("Personal Plus")|| serviceResult.equals("Substitute")) {
				char charWHTE = strSkin.charAt(0);
				char charBLK = strSkin.charAt(1);
				char charBLD = strSkin.charAt(2);
				char charASN = strSkin.charAt(3);
				
				checkBoxWHTE.setChecked((charWHTE == '1') ? true : false);
				checkBoxBLK.setChecked((charBLK == '1') ? true : false);
				checkBoxBRWN.setChecked((charBLD == '1') ? true : false);
				checkBoxASN.setChecked((charASN == '1') ? true : false);
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
		SetSkin(strPServiceItems[1]);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.checkBoxWHTE:
			if (checkBoxWHTE.isChecked()) {
				checkBoxBLK.setChecked(false);
				checkBoxBRWN.setChecked(false);
				checkBoxASN.setChecked(false);
			}
			break;

		case R.id.checkBoxBLK:
			if (checkBoxBLK.isChecked()) {
				checkBoxWHTE.setChecked(false);
				checkBoxASN.setChecked(false);
				checkBoxBRWN.setChecked(false);
			}
			break;

		case R.id.checkBoxBRWN:
			if (checkBoxBRWN.isChecked()) {
				checkBoxWHTE.setChecked(false);
				checkBoxBLK.setChecked(false);
				checkBoxASN.setChecked(false);
			}
			break;
		case R.id.checkBoxASN:
			if (checkBoxASN.isChecked()) {
				checkBoxWHTE.setChecked(false);
				checkBoxBLK.setChecked(false);
				checkBoxBRWN.setChecked(false);
			}
			break;
			}
	}
}