package com.aetherti.legaldelivery.Keyboard;

import java.util.List;

import LD.beans.Repository;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aetherti.legaldelivery.R;

public class KeyBoardList extends ArrayAdapter<Repository>{

	private final List<Repository> list;
	private final Activity context;

	public KeyBoardList(Activity context, List<Repository> objects) {
		super(context, R.layout.ld_custom_keyboard_line, objects);
		this.context = context;
		this.list = objects;
	}
	static class ViewHolder {
		protected TextView repositoryFullname;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.ld_custom_keyboard_line, null);

			final ViewHolder viewHolder = new ViewHolder();			
			viewHolder.repositoryFullname = (TextView) view.findViewById(R.id.dl_textViewRepository);
			view.setTag(viewHolder);
		} 
		else{
			view = convertView;
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.repositoryFullname.setText(list.get(position).getRepoFullname());
		return view;
	}
}





