package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;

public class ManupulateFile {
	static String FILENAME = "errorLog.txt";
	String string = "just something";

	// create a File object for the parent directory
	String fileuri="/sdcard/myDir/"+FILENAME; // ******** <- what do I have
													// to put HERE for standard
													// data folder????
	//custom path//
	String fileurl="/sdcard/errorlog.txt";
	// have the object build the directory structure, if needed.

	// create a File object for the output file
	File outputFile = new File(fileurl);
	// now attach the OutputStream to the file object, instead of a String
	// representation
	FileOutputStream fos = null;

	// always have to put try/catch around the code - why? I don't know
	public void WriteToFile(String cntnt, Context context) {
		try {
			if (!outputFile.exists())
				try {
					outputFile.createNewFile();
				} catch (IOException ioex) {
					Toast.makeText(context, "Error in Creating new File!",
							Toast.LENGTH_SHORT).show();
				}
			fos = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e2) {
			Toast.makeText(context, "File Not Found!", Toast.LENGTH_SHORT)
					.show();
			e2.printStackTrace();
		}
		try {
			if(fos!=null)
			fos.write(cntnt.getBytes());
			
		} catch (IOException e) {
			Toast.makeText(context, "Error in Writing file!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		finally{
			try {
				if(fos!=null)
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
