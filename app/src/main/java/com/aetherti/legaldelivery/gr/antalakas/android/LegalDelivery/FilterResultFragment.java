package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Edit.EditActivity;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.ErroReportingInBackground;
import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message.LDDBSearchAdapter;

public class FilterResultFragment extends ListFragment {

	private LDDatabaseAdapter dbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.ld_filterresults_fragment, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new LDDatabaseAdapter(getActivity());
		dbHelper.open();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		dbHelper.close();
	}

	public void onListItemClick(ListView parent, View v,
			int position, long id)
	
	{	
		Cursor cursor = ((LDDBSearchAdapter)parent.getAdapter()).getCursor();
		cursor.moveToPosition(position); 
		String rowID = cursor.getString(0); 

		if (rowID == null){
			Toast.makeText(getActivity(), "Cannot find row ID", Toast.LENGTH_LONG).show();
			return;
		}

		if (rowID.isEmpty()){
			Toast.makeText(getActivity(), "Cannot find row ID", Toast.LENGTH_LONG).show();
			return;
		}

		Intent i = new Intent(getActivity(), EditActivity.class);
		Log.i("ROW ID is:", rowID);
		i.putExtra(LDDatabaseAdapter.KEY_ROWID, rowID);
		startActivityForResult(i, 0);
	}

	public void Search(String fullname, String address,String apt,
			String casepapertype,String servicetype, String inputdate)
	{
		try {	
			Cursor cursor = dbHelper.fetchSearchResults(fullname, address, apt, casepapertype, servicetype,inputdate);
			if(!cursor.moveToFirst()){
				Toast.makeText(getActivity(), "No Data Found !", Toast.LENGTH_LONG).show();
			}
			//  	  	 	  		SimpleCursorAdapter adapter = 
			//  	  				new SimpleCursorAdapter(getActivity(), R.layout.ld_filterresults_line, cursor, columns, to);
			LDDBSearchAdapter adapter = new LDDBSearchAdapter(getActivity(), cursor);
			setListAdapter(adapter);
		}
		catch (Exception exception) {
			Log.i("Search : ", exception.getMessage());
			exception.printStackTrace();
			StringWriter st=new StringWriter();
			exception.printStackTrace(new PrintWriter(st));
			ManupulateFile manFile=new ManupulateFile();
			manFile.WriteToFile(st.toString(),getActivity());
			ErroReportingInBackground errreprt=new ErroReportingInBackground(getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!", Toast.LENGTH_SHORT).show();
		}
	}

	public void AddTestService(String strID1, String strID2)
	{
		try {
			FilterFragment filterList = (FilterFragment) getFragmentManager()
					.findFragmentById(R.id.FilterFragmentID);
			filterList.ClearFilters();
		}
		catch (Exception e) {
			e.printStackTrace();
			StringWriter st=new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			ManupulateFile manFile=new ManupulateFile();
			manFile.WriteToFile(st.toString(),getActivity());
			ErroReportingInBackground errreprt=new ErroReportingInBackground(getActivity());
			errreprt.execute(st.toString());
			Toast.makeText(getActivity(), "Error reported through mail!", Toast.LENGTH_SHORT).show();
		}		
	}
}
