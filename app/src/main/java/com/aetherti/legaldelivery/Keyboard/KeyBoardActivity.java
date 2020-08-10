package com.aetherti.legaldelivery.Keyboard;

import java.util.ArrayList;
import java.util.List;

import LD.beans.Repository;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.aetherti.legaldelivery.Database.LDDatabaseAdapter;
import com.aetherti.legaldelivery.R;

public class KeyBoardActivity extends Activity implements OnClickListener {

	EditText textKeyboard;
	ListView repoListView;
	List<Repository> repoList = new ArrayList<Repository>();
	int DownX;
	int UpX;

	Button btnA;
	Button btnB;
	Button btnC;
	Button btnD;
	Button btnE;
	Button btnF;
	Button btnG;
	Button btnH;
	Button btnI;
	Button btnJ;
	Button btnK;
	Button btnL;
	Button btnM;
	Button btnN;
	Button btnO;
	Button btnP;
	Button btnQ;
	Button btnR;
	Button btnS;
	Button btnT;
	Button btnU;
	Button btnV;
	Button btnW;
	Button btnX;
	Button btnY;
	Button btnZ;
	Button btnSpace;
	Button btnOk;
	Button btnCancel;
	Button btnBackspace;
	final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 50);      

	//	int UpY;
	//	int MoveX;
	//	int MoveY;

	public static String repositoryId="Repository";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ld_custom_keyboard);

		textKeyboard =(EditText)findViewById(R.id.textViewKeyboard);
		repoListView=(ListView)findViewById(R.id.listViewRepository);
		repoListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);		
		btnBackspace =(Button)findViewById(R.id.buttonBackspace);

		repoListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Repository repository =  (Repository)repoListView.getAdapter().getItem(position);
				String reponame = repository.getRepoFullname();
				textKeyboard.setText(reponame);
				if((UpX - DownX ) >= 100){
					textKeyboard.setText("");
					LDDatabaseAdapter dbHelper = new LDDatabaseAdapter(getBaseContext());
					dbHelper.open();
					dbHelper.deleteRepository(reponame);
					dbHelper.close();
					getValue("");
				}
			}
		});


		repoListView.setOnTouchListener(new OnTouchListener() {  

			public boolean onTouch(View v, MotionEvent event) {  
				int x=(int)event.getX();  
				//				int y=(int)event.getY();  

				if (event.getAction() == MotionEvent.ACTION_DOWN) {  
					//				    DownX = x;
					//					DownY = y;
					//					Toast.makeText(getBaseContext(), "Drag start", Toast.LENGTH_LONG).show();
				}  
				if (event.getAction() == MotionEvent.ACTION_UP) { 
					UpX = x;
					//					UpY = y;
					//					Toast.makeText(getBaseContext(), "Drag stop", Toast.LENGTH_LONG).show();
				} 
				if (event.getAction() == MotionEvent.ACTION_MOVE) {  
					//					MoveX = x;
					//					MoveY = y;
					//					Toast.makeText(getBaseContext(), "Drag Move", Toast.LENGTH_LONG).show();
				}
				return false;  
			}  
		}); 


		btnA = (Button)findViewById(R.id.buttonA);
		btnB = (Button)findViewById(R.id.buttonB);
		btnC = (Button)findViewById(R.id.buttonC);
		btnD = (Button)findViewById(R.id.buttonD);
		btnE = (Button)findViewById(R.id.buttonE);
		btnF = (Button)findViewById(R.id.buttonF);
		btnG = (Button)findViewById(R.id.buttonG);
		btnH = (Button)findViewById(R.id.buttonH);
		btnI = (Button)findViewById(R.id.buttonI);
		btnJ = (Button)findViewById(R.id.buttonJ);
		btnK = (Button)findViewById(R.id.buttonK);
		btnL = (Button)findViewById(R.id.buttonL);
		btnM = (Button)findViewById(R.id.buttonM);
		btnN = (Button)findViewById(R.id.buttonN);
		btnO = (Button)findViewById(R.id.buttonO);
		btnP = (Button)findViewById(R.id.buttonP);
		btnQ = (Button)findViewById(R.id.buttonQ);
		btnR = (Button)findViewById(R.id.buttonR);
		btnS = (Button)findViewById(R.id.buttonS);
		btnT = (Button)findViewById(R.id.buttonT);
		btnU = (Button)findViewById(R.id.buttonU);
		btnV = (Button)findViewById(R.id.buttonV);
		btnW = (Button)findViewById(R.id.buttonW);
		btnX = (Button)findViewById(R.id.buttonX);
		btnY = (Button)findViewById(R.id.buttonY);
		btnZ = (Button)findViewById(R.id.buttonZ);
		btnSpace = (Button)findViewById(R.id.buttonSpace);
		btnOk = (Button)findViewById(R.id.buttonOk);
		btnCancel =(Button)findViewById(R.id.buttonCancel);

		textKeyboard.setBackgroundColor(Color.rgb(65, 73, 83));
		btnA.setBackgroundColor(Color.rgb(65, 73, 83));
		btnB.setBackgroundColor(Color.rgb(65, 73, 83));
		btnC.setBackgroundColor(Color.rgb(65, 73, 83));
		btnD.setBackgroundColor(Color.rgb(65, 73, 83));
		btnE.setBackgroundColor(Color.rgb(65, 73, 83));
		btnF.setBackgroundColor(Color.rgb(65, 73, 83));
		btnG.setBackgroundColor(Color.rgb(65, 73, 83));
		btnH.setBackgroundColor(Color.rgb(65, 73, 83));
		btnI.setBackgroundColor(Color.rgb(65, 73, 83));
		btnJ.setBackgroundColor(Color.rgb(65, 73, 83));
		btnK.setBackgroundColor(Color.rgb(65, 73, 83));
		btnL.setBackgroundColor(Color.rgb(65, 73, 83));
		btnM.setBackgroundColor(Color.rgb(65, 73, 83));
		btnN.setBackgroundColor(Color.rgb(65, 73, 83));
		btnO.setBackgroundColor(Color.rgb(65, 73, 83));
		btnP.setBackgroundColor(Color.rgb(65, 73, 83));
		btnQ.setBackgroundColor(Color.rgb(65, 73, 83));
		btnR.setBackgroundColor(Color.rgb(65, 73, 83));
		btnS.setBackgroundColor(Color.rgb(65, 73, 83));
		btnT.setBackgroundColor(Color.rgb(65, 73, 83));
		btnU.setBackgroundColor(Color.rgb(65, 73, 83));
		btnV.setBackgroundColor(Color.rgb(65, 73, 83));
		btnW.setBackgroundColor(Color.rgb(65, 73, 83));
		btnX.setBackgroundColor(Color.rgb(65, 73, 83));
		btnY.setBackgroundColor(Color.rgb(65, 73, 83));
		btnZ.setBackgroundColor(Color.rgb(65, 73, 83));
		btnSpace.setBackgroundColor(Color.rgb(65, 73, 83));
		btnOk.setBackgroundColor(Color.rgb(65, 73, 83));
		btnCancel.setBackgroundColor(Color.rgb(65, 73, 83));
		btnBackspace.setBackgroundColor(Color.rgb(65, 73, 83));

		btnBackspace.setOnClickListener(this);
		btnSpace.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		btnA.setOnClickListener(this);
		btnB.setOnClickListener(this);
		btnC.setOnClickListener(this);
		btnD.setOnClickListener(this);
		btnE.setOnClickListener(this);
		btnF.setOnClickListener(this);
		btnG.setOnClickListener(this);
		btnH.setOnClickListener(this);
		btnI.setOnClickListener(this);
		btnJ.setOnClickListener(this);
		btnK.setOnClickListener(this);
		btnL.setOnClickListener(this);
		btnM.setOnClickListener(this);
		btnN.setOnClickListener(this);
		btnO.setOnClickListener(this);
		btnP.setOnClickListener(this);
		btnQ.setOnClickListener(this);
		btnR.setOnClickListener(this);
		btnS.setOnClickListener(this);
		btnT.setOnClickListener(this);
		btnU.setOnClickListener(this);
		btnV.setOnClickListener(this);
		btnW.setOnClickListener(this);
		btnX.setOnClickListener(this);
		btnY.setOnClickListener(this);
		btnZ.setOnClickListener(this);

		btnBackspace.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				String keyStr = textKeyboard.getText().toString();
				int length = keyStr.length();
				boolean incr =false;
				if(keyStr.length() == 0){
					textKeyboard.setText("");
				}
				else{
					try{
						keyStr = keyStr.substring(0, length-4);
						textKeyboard.setText(keyStr);
						textKeyboard.setSelection(length-4);
						incr =true;
					}catch (Exception e) {
						e.printStackTrace();
					}
					if(incr){
						textKeyboard.setText("");
					}
				}
				String strkey = textKeyboard.getText().toString();
				getValue(strkey);
				return true;
			}
		});


		Bundle extras = getIntent().getExtras();
		String insertedText = "";
		if (extras != null) {
			insertedText  = extras.getString("insertedValue");
		}
		textKeyboard.requestFocus();

		textKeyboard.setText(insertedText);

		//hide keyboard
		textKeyboard.setOnTouchListener(new View.OnTouchListener() {					
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		textKeyboard.setEnabled(true);
		Intent i = new Intent();
		i.putExtra("Ok", "0");
		i.putExtra("Cancel", "1");
		setResult(0, i);
		finish();
		return super.onTouchEvent(event);
	}

	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.buttonA:
			AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); // Get audioManager
			//btnA.setBackgroundColor(Color.rgb(30, 144, 255));
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			String strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("A");
			}
			else{
				textKeyboard.append("a");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			SystemClock.sleep(1000);
			//	btnA.setBackgroundColor(Color.rgb(65, 73, 83));
			break;
		case R.id.buttonB:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("B");
			}
			else{
				textKeyboard.append("b");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonC:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("C");
			}
			else{
				textKeyboard.append("c");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonD:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("D");
			}
			else{
				textKeyboard.append("d");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonE:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("E");
			}
			else{
				textKeyboard.append("e");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonF:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("F");
			}
			else{
				textKeyboard.append("f");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonG:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("G");
			}
			else{
				textKeyboard.append("g");
			}
			break;
		case R.id.buttonH:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("H");
			}
			else{
				textKeyboard.append("h");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonI:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("I");
			}
			else{
				textKeyboard.append("i");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonJ:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("J");
			}
			else{
				textKeyboard.append("j");
			}
			break;
		case R.id.buttonK:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("K");
			}
			else{
				textKeyboard.append("k");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonL:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("L");
			}
			else{
				textKeyboard.append("l");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonM:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("M");
			}
			else{
				textKeyboard.append("m");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonN:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("N");
			}
			else{
				textKeyboard.append("n");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonO:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("O");
			}
			else{
				textKeyboard.append("o");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonP:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("P");
			}
			else{
				textKeyboard.append("p");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonQ:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("Q");
			}
			else{
				textKeyboard.append("q");
			}
			break;
		case R.id.buttonR:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("R");
			}
			else{
				textKeyboard.append("r");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonS:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("S");
			}
			else{
				textKeyboard.append("s");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonT:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("T");
			}
			else{
				textKeyboard.append("t");
			}
			break;
		case R.id.buttonU:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("U");
			}
			else{
				textKeyboard.append("u");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonV:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("V");
			}
			else{
				textKeyboard.append("v");
			}
			break;
		case R.id.buttonW:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("W");
			}
			else{
				textKeyboard.append("w");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonX:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("X");
			}
			else{
				textKeyboard.append("x");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonY:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("Y");
			}
			else{
				textKeyboard.append("y");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonZ:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.setEnabled(true);
			strkey = textKeyboard.getText().toString();
			if(getFirstcharKeyboard(strkey)){
				textKeyboard.append("Z");
			}
			else{
				textKeyboard.append("z");
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonBackspace:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			String keyStr = textKeyboard.getText().toString();
			int length = keyStr.length();
			if(keyStr.length() == 0){
				textKeyboard.setText("");
			}
			else{
				keyStr = keyStr.substring(0, length-1);
				textKeyboard.setText(keyStr);
				textKeyboard.setSelection(length-1);
			}
			strkey = textKeyboard.getText().toString();
			getValue(strkey);
			break;
		case R.id.buttonSpace:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			textKeyboard.append(" ");
			break;
		case R.id.buttonOk:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			LDDatabaseAdapter dbHelper;
			dbHelper = new LDDatabaseAdapter(getBaseContext());
			dbHelper.open();
			Cursor c = null;
			String keyboard = textKeyboard.getText().toString();
			if(!keyboard.equals("")){
				boolean hasValue = false;
				c=dbHelper.fetchRepository();
				if(c!=null && c.getCount()!=0){
					String strDbCheck;
					do{
						strDbCheck = c.getString(0);
						if(strDbCheck.equals(keyboard)){
							hasValue = true;
						}
					}while(c.moveToNext());
				}

				if(!hasValue){
					dbHelper.createRepository(keyboard);
				}
			}

			Intent i = new Intent();
			i.putExtra("Ok", "1");
			i.putExtra("Cancel", "0");
			i.putExtra("Value", keyboard);
			setResult(0, i);
			finish();
			break;
		case R.id.buttonCancel:
			tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE); 
			Intent i1 = new Intent();
			i1.putExtra("Ok", "0");
			i1.putExtra("Cancel", "1");
			setResult(0, i1);
			finish();
			break;
		default:
			break;
		}

	}
	private boolean getFirstcharKeyboard(String strkey) {
		int newKeylen  = strkey.length();
		char newChar ='C';
		if(newKeylen>0){
			newChar = strkey.charAt(newKeylen-1);
		}
		if(newChar ==' ' || newChar =='C'){
			return true;
		}else{
			return false;
		}
	}

	public void getValue(String keyboardvalue){
		LDDatabaseAdapter dbHelper;
		Cursor cursor=null;
		Repository repo=null;
		dbHelper = new LDDatabaseAdapter(getBaseContext());
		dbHelper.open();
		if(repoList.size()>0){
			repoList.clear();
		}
		cursor = dbHelper.fetchRepository();
		if(cursor != null && cursor.getCount()!=0){
			String str;
			do{
				repo=new Repository(cursor.getString(0));
				str = repo.getRepoFullname();
				if(str.contains(keyboardvalue)){
					repoList.add(repo);
				}
			}while(cursor.moveToNext());
			KeyBoardList itemsAdapter = new KeyBoardList(this ,repoList);
			repoListView.setAdapter(itemsAdapter);	
		}
		dbHelper.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent();
			i.putExtra("Ok", "0");
			i.putExtra("Cancel", "1");
			setResult(0, i);
			finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
