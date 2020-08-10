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

public class EditHairFragment extends Fragment {

	CheckBox checkBoxWHTE;
	CheckBox checkBoxBLK;
	CheckBox checkBoxLTBRN;
	CheckBox checkBoxBRN;
	CheckBox checkBoxGRAY;
	CheckBox checkBoxRED;	
	CheckBox checkBoxBALD;
	CheckBox checkblonde;
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V =  inflater.inflate(R.layout.ld_edit_hair_fragment, container, false);
		checkBoxWHTE = (CheckBox)V.findViewById(R.id.checkBoxWHTE);
		checkBoxBLK = (CheckBox)V.findViewById(R.id.checkBoxBLK);
		checkBoxLTBRN = (CheckBox)V.findViewById(R.id.checkBoxLTBRN);
		checkBoxBRN = (CheckBox)V.findViewById(R.id.checkBoxBRN);
		checkBoxGRAY = (CheckBox)V.findViewById(R.id.checkBoxGRAY);
		checkBoxRED = (CheckBox)V.findViewById(R.id.checkBoxRED);
		checkBoxBALD = (CheckBox)V.findViewById(R.id.checkBoxBALD);
		checkblonde =(CheckBox)V.findViewById(R.id.hairBLCheckBox);
		populateFields();

		return V;
	}

	String GetHair() {

		StringBuilder sb = new StringBuilder();

		sb.append(checkBoxWHTE.isChecked() ? "1" : "0");
		sb.append(checkBoxBLK.isChecked() ? "1" : "0");
		sb.append(checkBoxLTBRN.isChecked() ? "1" : "0");
		sb.append(checkBoxBRN.isChecked() ? "1" : "0");
		sb.append(checkBoxGRAY.isChecked() ? "1" : "0");
		sb.append(checkBoxRED.isChecked() ? "1" : "0");		
		sb.append(checkBoxBALD.isChecked() ? "1" : "0");
		sb.append(checkblonde.isChecked() ? "1" : "0");

		return sb.toString();
	}

	void SetHair(String strHair) {
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if(serviceResult != null){
			if(serviceResult.equals("Personal") || serviceResult.equals("Personal Plus") || serviceResult.equals("Substitute")){
				char charWHTE = strHair.charAt(0);
				char charBLK = strHair.charAt(1);
				char charBLD = strHair.charAt(2);
				char charBRN = strHair.charAt(3);
				char charGRAY = strHair.charAt(4);
				char charRED = strHair.charAt(5);
				char charBALD = strHair.charAt(6);
				char charBlonde = strHair.charAt(7);
				

				checkBoxWHTE.setChecked((charWHTE == '1') ? true : false);
				checkBoxBLK.setChecked((charBLK == '1') ? true : false);
				checkBoxLTBRN.setChecked((charBLD == '1') ? true : false);
				checkBoxBRN.setChecked((charBRN == '1') ? true : false);
				checkBoxGRAY.setChecked((charGRAY == '1') ? true : false);
				checkBoxRED.setChecked((charRED == '1') ? true : false);
				checkBoxBALD.setChecked((charBALD == '1') ? true : false);	
				checkblonde.setChecked((charBlonde =='1') ? true : false);
				
			}
		}
	}

	private void populateFields()
	{
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String strPService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_PServ));
		if (strPService == null)
			return;

		String [] strPServiceItems = strPService.split("_");
		SetHair(strPServiceItems[2]);
	}
	public boolean IsAtLeast1AndMax3Selected(){
		 int count=0;
		if(checkBoxWHTE.isChecked()){
			count++;
		}
		 if(checkBoxBLK.isChecked()){
			count++;
		 }
		if(checkBoxLTBRN.isChecked()){
			count++;
		}
		 if(checkBoxBRN.isChecked()){
			count++;
		 }
		
		if(checkBoxGRAY.isChecked()){
			count++;
		}
		if(checkBoxRED.isChecked()){
			count++;
		}
		 if(checkBoxBALD.isChecked() ){
			count++;
		 }
		 if(checkblonde.isChecked() ){
			count++;
		 }
		
			if((!(count < 4))||(count==0)){
				return false ;
			}else{
				return true;
			}
	}
}