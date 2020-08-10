package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.aetherti.legaldelivery.R;

public class EditDoorFragment extends Fragment {

	CheckBox checkBoxWHTE;
	CheckBox checkBoxLTBR;
	CheckBox checkBoxBRWN;
	CheckBox checkBoxBLUE;
	CheckBox checkBoxGREN;
	CheckBox checkBoxGRAY;
	CheckBox checkBoxRED;
	CheckBox checkBoxBEIG;
	CheckBox checkBoxBLK;
	CheckBox checkBoxWOOD;
	CheckBox checkBoxWOODGRAIN;
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V =  inflater.inflate(R.layout.ld_edit_door_fragment, container, false);

		checkBoxWHTE = (CheckBox)V.findViewById(R.id.checkBoxWHTE);
		checkBoxLTBR = (CheckBox)V.findViewById(R.id.checkBoxLTBR);
		checkBoxBRWN = (CheckBox)V.findViewById(R.id.checkBoxBRWN);
		checkBoxBLUE = (CheckBox)V.findViewById(R.id.checkBoxBLUE);
		checkBoxGREN = (CheckBox)V.findViewById(R.id.checkBoxGREN);
		checkBoxGRAY = (CheckBox)V.findViewById(R.id.checkBoxGRAY);
		checkBoxRED = (CheckBox)V.findViewById(R.id.checkBoxRED);
		checkBoxBEIG = (CheckBox)V.findViewById(R.id.checkBoxBEIG);
		checkBoxBLK = (CheckBox)V.findViewById(R.id.checkBoxBLK);
		checkBoxWOOD = (CheckBox)V.findViewById(R.id.checkBoxWOOD);
		checkBoxWOODGRAIN = (CheckBox)V.findViewById(R.id.checkBoxWOODGRAIN);

		populateFields();

		return V;
	}

	
	
	

	String GetDoor() {

		StringBuilder sb = new StringBuilder();

		sb.append(checkBoxWHTE.isChecked() ? "1" : "0");
		sb.append(checkBoxLTBR.isChecked() ? "1" : "0");
		sb.append(checkBoxBRWN.isChecked() ? "1" : "0");
		sb.append(checkBoxBLUE.isChecked() ? "1" : "0");
		sb.append(checkBoxGREN.isChecked() ? "1" : "0");
		sb.append(checkBoxGRAY.isChecked() ? "1" : "0");
		sb.append(checkBoxRED.isChecked() ? "1" : "0");
		sb.append(checkBoxBEIG.isChecked() ? "1" : "0");
		sb.append(checkBoxBLK.isChecked() ? "1" : "0");
		sb.append(checkBoxWOOD.isChecked() ? "1" : "0");
		sb.append(checkBoxWOODGRAIN.isChecked() ? "1" : "0");

		return sb.toString();
	}

	void SetDoor(String strDoor) {
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if(serviceResult != null){
			if(serviceResult.equals("Conspicuous")){
				char charWHTE = strDoor.charAt(0);
				char charLTBR = strDoor.charAt(1);
				char charBRWN = strDoor.charAt(2);
				char charBLUE = strDoor.charAt(3);
				char charGREN = strDoor.charAt(4);
				char charGRAY = strDoor.charAt(5);
				char charRED = strDoor.charAt(6);
				char charBEIG = strDoor.charAt(7);
				char charBLK = strDoor.charAt(8);
				char charWOOD = strDoor.charAt(9);
				char charWOODGRAIN = strDoor.charAt(10);

				checkBoxWHTE.setChecked(charWHTE == '1' ? true : false);
				checkBoxLTBR.setChecked(charLTBR == '1' ? true : false);
				checkBoxBRWN.setChecked(charBRWN == '1' ? true : false);
				checkBoxBLUE.setChecked(charBLUE == '1' ? true : false);
				checkBoxGREN.setChecked(charGREN == '1' ? true : false);
				checkBoxGRAY.setChecked(charGRAY == '1' ? true : false);
				checkBoxRED.setChecked(charRED == '1' ? true : false);
				checkBoxBEIG.setChecked(charBEIG == '1' ? true : false);
				checkBoxBLK.setChecked(charBLK == '1' ? true : false);
				checkBoxWOOD.setChecked(charWOOD == '1' ? true : false);
				checkBoxWOODGRAIN.setChecked(charWOODGRAIN == '1' ? true : false);	
			}
		}
	}

	private void populateFields(){
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String strCService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_CServ));

		if (strCService == null)
			return;

		String [] strCServiceItems = strCService.split("_");
		SetDoor(strCServiceItems[3]);
	}
	public boolean IsAtLeast1AndMax3Selected(){
		 int count=0;
		if(checkBoxWHTE.isChecked()){
			count++;
		}
		if(checkBoxLTBR.isChecked()){
			count++;
		 }
		if(checkBoxBRWN.isChecked()){
			count++;
		}
		if(checkBoxBLUE.isChecked()){
			count++;
		 }
		if(checkBoxGREN.isChecked()){
			count++;
		 }
		if(checkBoxGRAY.isChecked()){
			count++;
		}
		if(checkBoxRED.isChecked()){
			count++;
		}
		 if(checkBoxBEIG.isChecked() ) {
			count++;
		 }
		 if(checkBoxBLK.isChecked()) {
			count++;
		 }
		 if(checkBoxWOOD.isChecked()) {
			count++;
		 }
		if(checkBoxWOODGRAIN.isChecked()){
			count++;
		}
			if((!(count < 4))||(count==0)){
				return false ;
			}else{
				return true;
			}
	}
}
