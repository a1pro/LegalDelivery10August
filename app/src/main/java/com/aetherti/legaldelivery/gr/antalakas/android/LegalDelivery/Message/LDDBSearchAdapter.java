package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;

public class LDDBSearchAdapter extends CursorAdapter {
	public LDDBSearchAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView ServiceType = (TextView)view.findViewById(R.id.fr_servicetypeTextView);

		TextView fullname = (TextView)view.findViewById(R.id.fr_fullnameTextView);
		TextView bussName = (TextView)view.findViewById(R.id.fr_BusinessnameTextView);
		TextView StndName = (TextView)view.findViewById(R.id.fr_StndFullnameTextView);
		TextView textViewFullname = (TextView)view.findViewById(R.id.textViewFullname);
		TextView textViewBussName = (TextView)view.findViewById(R.id.textViewBussName);
		TextView textViewStndName = (TextView)view.findViewById(R.id.textViewStndName);
		TextView Add = (TextView)view.findViewById(R.id.fr_addressTextView);
		TextView City = (TextView)view.findViewById(R.id.fr_cityTextView);
		TextView Apt = (TextView)view.findViewById(R.id.fr_aptTextView);
		TextView State = (TextView)view.findViewById(R.id.fr_stateTextView);
		TextView Zip = (TextView)view.findViewById(R.id.fr_zipTextView);
		TextView CasePaperType = (TextView)view.findViewById(R.id.fr_CasepaperTypeTextView);
		String StrServiceType = cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceType));
		ServiceType.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_ServiceType)));
		if(StrServiceType!=null) {
			if (StrServiceType.equals("Standard")) {
				fullname.setVisibility(View.GONE);
				textViewFullname.setVisibility(View.GONE);
				bussName.setVisibility(View.GONE);
				textViewBussName.setVisibility(View.GONE);
				StndName.setVisibility(View.VISIBLE);
				textViewStndName.setVisibility(View.VISIBLE);
				CasePaperType.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_ServiceType)));
				StndName.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_FullName)));
				Add.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Address)));
				City.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_City)));
				Apt.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Apt)));
				State.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_State)));
				Zip.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_STND_Zip)));
			}

			if (StrServiceType.equals("L&T Commercial")) {
				fullname.setVisibility(View.GONE);
				textViewFullname.setVisibility(View.GONE);
				StndName.setVisibility(View.GONE);
				textViewStndName.setVisibility(View.GONE);
				bussName.setVisibility(View.VISIBLE);
				textViewBussName.setVisibility(View.VISIBLE);
				CasePaperType.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTServiceType)));
				bussName.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTBIZNAME)));
				Add.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTAddress)));
				City.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTCity)));
				Apt.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTApt)));
				State.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTState)));
				Zip.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTZip)));
			}

			if (StrServiceType.equals("L&T Residential")) {
				bussName.setVisibility(View.GONE);
				textViewBussName.setVisibility(View.GONE);
				StndName.setVisibility(View.GONE);
				textViewStndName.setVisibility(View.GONE);
				fullname.setVisibility(View.VISIBLE);
				textViewFullname.setVisibility(View.VISIBLE);
				CasePaperType.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTServiceType)));
				fullname.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTFullname)));
				Add.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTAddress)));
				City.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTCity)));
				Apt.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTApt)));
				State.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTState)));
				Zip.setText(cursor.getString(cursor.getColumnIndex(LDDatabaseAdapter.KEY_LTZip)));
			}
		}else {
			Toast.makeText(context,"No record saved",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.ld_filterresults_line, parent, false);
		return v;
	}
}