package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.aetherti.legaldelivery.R;

public class HourAdapter extends BaseAdapter{

	static ArrayList<Integer> hours = new ArrayList<Integer>();
	Context mContext;
	
	public HourAdapter(Context context,ArrayList<Integer> data) {
		mContext = context;
		hours = data;
	}
	@Override
	public int getCount() {
		return hours.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	public static synchronized void addInHourList(int number){
		hours.add(number);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		try{
			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.hour_list_elements, null);
			final Button hourCountTxtView = (Button)convertView.findViewById(R.id.houresCounting);
			hourCountTxtView.setText(hours.get(arg0).toString());
			hourCountTxtView.setTag(hours.get(arg0).toString());
			hourCountTxtView.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					EditCol1Fragment.set_actualTimeSelectedTextViewText(v.getTag().toString());
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertView;
	}
}
