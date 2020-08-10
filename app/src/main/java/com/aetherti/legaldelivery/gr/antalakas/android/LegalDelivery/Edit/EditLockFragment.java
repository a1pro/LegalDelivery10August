package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.aetherti.legaldelivery.R;

public class EditLockFragment extends Fragment {

	CheckBox checkBoxBELL;
	CheckBox checkBoxPEEP;
	CheckBox checkBoxKNOCK;
	CheckBox checkBoxAPT;
	CheckBox checkBoxNMPL;
	EditText doorlocksEditText;


	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View V =  inflater.inflate(R.layout.ld_edit_lock_fragment, container, false);

		checkBoxBELL = (CheckBox)V.findViewById(R.id.checkBoxBELL);
		checkBoxPEEP = (CheckBox)V.findViewById(R.id.checkBoxPEEP);
		checkBoxKNOCK = (CheckBox)V.findViewById(R.id.checkBoxKNOCK);
		checkBoxAPT = (CheckBox)V.findViewById(R.id.checkBoxAPT);
		checkBoxNMPL = (CheckBox)V.findViewById(R.id.checkBoxNMPL);
		doorlocksEditText = (EditText)V.findViewById(R.id.doorlocksEditText);
		populateFields();

		return V;
	}

	String GetLocks() {

		StringBuilder sb = new StringBuilder();
		sb.append(checkBoxBELL.isChecked() ? "1" : "0");
		sb.append(checkBoxPEEP.isChecked() ? "1" : "0");
		sb.append(checkBoxKNOCK.isChecked() ? "1" : "0");
		sb.append(checkBoxAPT.isChecked() ? "1" : "0");
		sb.append(checkBoxNMPL.isChecked() ? "1" : "0");
		return sb.toString();
	}

	void SetLocks(String strLock) {
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String serviceResult = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_ServiceResult));
		if(!(strLock.equalsIgnoreCase("00000"))){
			if(serviceResult != null){
				if(serviceResult.equals("Conspicuous")){
					char charBELL = strLock.charAt(0);
					char charPEEP = strLock.charAt(1);
					char charKNOCK = strLock.charAt(2);
					char charAPT = strLock.charAt(3);
					char charNMPL = strLock.charAt(4);

					checkBoxBELL.setChecked(charBELL == '1' ? true : false);
					checkBoxPEEP.setChecked(charPEEP == '1' ? true : false);
					checkBoxKNOCK.setChecked(charKNOCK == '1' ? true : false);
					checkBoxAPT.setChecked(charAPT == '1' ? true : false);
					checkBoxNMPL.setChecked(charNMPL == '1' ? true : false);
				}
			}
		}
	}

	String GetNumOfLocks(){
		String strDoorLocks = doorlocksEditText.getText().toString();
		return strDoorLocks;
	}

	void SetNumOfLocks(int intDoorLocks){
		if (intDoorLocks > 0)
			doorlocksEditText.setText(Integer.toString(intDoorLocks));
	}

	private void populateFields(){
		EditActivity editActivity = (EditActivity)getActivity();
		Cursor cursor = editActivity.GetCursor();
		String strCService = cursor.getString(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_CServ));

		if (strCService == null)
			return;

		String [] strCServiceItems = strCService.split("_");
		SetLocks(strCServiceItems[4]);
		int intDoorLocks = cursor.getInt(cursor.getColumnIndexOrThrow(LDDatabaseAdapter.KEY_DoorLock));
		SetNumOfLocks(intDoorLocks);
	}
	public boolean IsAtLeast1AndMax3Selected(){
		 int count=0;
		if(checkBoxBELL.isChecked()){
			count++;
		}
		 if(checkBoxPEEP.isChecked()){
			count++;
		 }
		 if(checkBoxKNOCK.isChecked()){
			count++;
		 }
		 if(checkBoxAPT.isChecked()){
			count++;
		 }
		 if(checkBoxNMPL.isChecked()){
			count++;
		 }
		
		if((!(count < 4))||(count == 0)){
			return false ;
		}else{
			return true;
		}
	}
}
