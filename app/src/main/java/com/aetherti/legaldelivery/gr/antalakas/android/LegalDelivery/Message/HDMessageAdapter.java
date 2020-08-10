package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.HolidayMessage;

import com.aetherti.legaldelivery.R;


public class HDMessageAdapter extends ArrayAdapter<HolidayMessage> {

	private final List<com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.HolidayMessage> list;
	private final Activity context;
	
	public HDMessageAdapter(Activity context, List<HolidayMessage> objects) {
		super(context, R.layout.hd_download_line, objects);
		
		this.context = context;
		this.list = objects;
	}

	static class ViewHolder {
		protected TextView holidayYear;
		protected TextView holidayDate;
		protected TextView holidayDescription;
	}
	/*
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.hd_download_line, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.holidayYear = (TextView) view.findViewById(R.id.dl_holidayYearTextView);
			viewHolder.holidayDate = (TextView) view.findViewById(R.id.dl_holidayDateTextView);
			viewHolder.holidayDescription = (TextView) view.findViewById(R.id.dl_holidayDescriptionTextView);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		String year = Integer.toString(list.get(position).getHolidayYear());
		holder.holidayYear.setText(year);
		holder.holidayDate.setText(list.get(position).getHolidayDate());
		holder.holidayDescription.setText(list.get(position).getHolidayDescription());
		return view;
	}	
	*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.hd_download_line, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.holidayYear = (TextView) view.findViewById(R.id.dl_holidayYearTextView);
			viewHolder.holidayDate = (TextView) view.findViewById(R.id.dl_holidayDateTextView);
			viewHolder.holidayDescription = (TextView) view.findViewById(R.id.dl_holidayDescriptionTextView);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		String year = Integer.toString(list.get(position).getHolidayYear());
		holder.holidayYear.setText(year);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		SimpleDateFormat sdatef = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			Date d = sdatef.parse(list.get(position).getHolidayDate());
			holder.holidayDate.setText(sdatef.format(d));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		holder.holidayDescription.setText(list.get(position).getHolidayDescription());
		return view;
	}
	
	
	
	
}
