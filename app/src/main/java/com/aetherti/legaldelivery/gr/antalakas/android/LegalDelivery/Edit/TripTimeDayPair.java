package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.aetherti.legaldelivery.R;

public class TripTimeDayPair extends Fragment {
	
	public static TabHost editTabHostForConsole;
	TabHost.TabSpec specForConsole;
	public static EditCol0Fragment editCol0Fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.demo_today_tabs, container,false);
		try{
		editTabHostForConsole = (TabHost) V.findViewById(R.id.tabhostForConsole);
		editTabHostForConsole.setup();
		specForConsole = editTabHostForConsole.newTabSpec("TripTime");
		specForConsole.setContent(R.id.tab1ForConsole);
		specForConsole.setIndicator("Trip Time");
		editTabHostForConsole.addTab(specForConsole);
		specForConsole = editTabHostForConsole.newTabSpec("DayPair");
		specForConsole.setContent(R.id.tab2ForConsole);
		specForConsole.setIndicator("Day pair");
		editTabHostForConsole.addTab(specForConsole);
		initializeLayout();
		editTabHostForConsole.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				if(arg0.toString().equalsIgnoreCase("TripTime")){
					initializeLayout();
				}
				if(arg0.toString().equalsIgnoreCase("DayPair")){
					Fragment fragment;
					fragment = new DayPair();
					FragmentManager fragmentManager = getFragmentManager();
					 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					 fragmentTransaction.replace(R.id.showConsoleFragments1, fragment);
					 fragmentTransaction.commit();
				}
			}
		});
		}catch(Exception e){
			e.printStackTrace();
		}
		return V;
	}

	private void initializeLayout() {
		try{
			Fragment fragment = new EditCol0Fragment();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.showConsoleFragments1, fragment);
			fragmentTransaction.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
