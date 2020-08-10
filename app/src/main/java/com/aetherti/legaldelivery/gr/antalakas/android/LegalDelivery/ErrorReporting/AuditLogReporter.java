package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class AuditLogReporter {
	OutputStreamWriter foutwriter;
	FileOutputStream fOut;
	@SuppressLint("SdCardPath")
	File auditFile = new File("/sdcard/auditlog.txt");

	public String reportAudit(String Snote, Context con) {
		try {
			Log.i("Snote:", Snote);
			if (!auditFile.exists())
				auditFile.createNewFile();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(auditFile, true)));
			out.println(new Date().toString() + "In Package:"+ con.getPackageName() + "Message:" + Snote);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "AuditLog file not found or SDCard is not inserted!";

		} catch (IOException e) {
			e.printStackTrace();
			return "SDcard not inserted!";
		} 
		return "OK";

	}
	public void clearFile(){
		try {
			new FileWriter(auditFile,false)
			.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
