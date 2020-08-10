package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.LegalDeliveryActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.QuickPrefsActivity;

public class DayPair extends Fragment {
	
	RadioGroup _dayPair;
	RadioButton monday_tuesday;
	RadioButton tuesday_wednesday;
	RadioButton wednesday_thursday;
	RadioButton thursday_friday;
	RadioButton friday_saturday;
	RadioButton saturday_monday;
	EditCol1Fragment editCol1Fragment;
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View V = null;
			try{
				V = inflater.inflate(R.layout.day_pair_layout, container,false);
				_dayPair = (RadioGroup)V.findViewById(R.id.radioGroup);
				monday_tuesday = (RadioButton)V.findViewById(R.id.monday_tuesday);
				tuesday_wednesday = (RadioButton)V.findViewById(R.id.tuesday_wednesday);
				wednesday_thursday = (RadioButton)V.findViewById(R.id.wednesday_thursday);
				thursday_friday = (RadioButton)V.findViewById(R.id.thursday_friday);
				friday_saturday = (RadioButton)V.findViewById(R.id.friday_saturday);
				saturday_monday = (RadioButton)V.findViewById(R.id.saturday_monday);
				editCol1Fragment = (EditCol1Fragment) getFragmentManager().findFragmentById(R.id.EditCol1FragmentID);
				_dayPair.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						RadioButton rb = (RadioButton)_dayPair.findViewById(arg1);
						LegalDeliveryActivity.setSelectedDayPairID(rb.getId());
						LegalDeliveryActivity.setSelectedDayPair(rb.getText().toString());

						String ar[] = String.valueOf(rb.getText().toString()).split("-");
						String first_day = QuickPrefsActivity.get1stDateofweekdays(ar[0].trim());
						String second_day = QuickPrefsActivity.get2ndDateofweekdays(ar[1].trim());
						//String third_day = QuickPrefsActivity.get3rdDateofweekdays(weekday)
						editCol1Fragment.gpsdate1TextView.setText(first_day);
						editCol1Fragment.gpsdate2TextView.setText(second_day);
						if(EditActivity.dayPairTabSelectionOneTimeFlag == 0){
							TripTimeDayPair.editTabHostForConsole.setCurrentTab(0);
						}
						EditActivity.dayPairTabSelectionOneTimeFlag = 1;
					}
				});
				
				int selectedDayPairID = LegalDeliveryActivity.getSelectedDayPairID();
				if(selectedDayPairID != 0){
					_dayPair.check(LegalDeliveryActivity.getSelectedDayPairID());
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return V;
		}

}
