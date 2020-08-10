package com.aetherti.legaldelivery.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;

public class LDDatabaseHelper extends SQLiteOpenHelper {

	private Context parentContext;
	///////////////////////////////////////////////////////////////////////////////////////////////
	AuditLogReporter alreport;
	///////////////////////////////////////////////////////////////////////////////////////////////

	private static final String DATABASE_NAME = "LegalDeliveryDB";

	private static final int DATABASE_VERSION = 8;

	private static final String DATABASE_CREATE_LD = 
			"CREATE TABLE LegalDelivery (" +
					"_id TEXT PRIMARY KEY NOT NULL, " +
					"JobNo TEXT, " +
					"Client TEXT, " +
					"ServiceType TEXT, " +
					"LTServiceType TEXT, " +
					"LTLTBIZNAME TEXT, " +
					"PPSERVED1 TEXT, " +
					"ServerLicenceNo TEXT, " +					
					"CaseNo TEXT, " +
					"LTFullname TEXT, " +
					"PersonSeenSubstitute TEXT, " +
					"PersonSeenDoeSubstitute TEXT, " +
					"PersonSeenPersonal TEXT, " +
					"PersonsNotSeenPersonal TEXT, " +
					"CorpRecipient TEXT, " +
					"CorpRecipientTitle TEXT, " +
					"LTAddress TEXT, " +
					"LTApt TEXT, " +
					"LTCity TEXT, " +
					"LTState TEXT, " +
					"LTZip TEXT, " +
					"STND_FullName TEXT, " +
					"STND_Address TEXT, " +
					"STND_Apt TEXT, " +
					"STND_City TEXT, " +
					"STND_State TEXT, " +
					"STND_Zip TEXT, " +
					"STND_ServiceType TEXT, " +
					"DateOfService TEXT, " +
					"TimeOfService TEXT, " +
					"GPSDate1 TEXT, " +
					"GPSTime1 TEXT, " +
					"GPSLat1 REAL, " +
					"GPSLon1 REAL, " +
					"GPSDate2 TEXT, " +
					"GPSTime2 TEXT, " +
					"GPSLat2 REAL, " +
					"GPSLon2 REAL, " +		
					"GPSDate3 TEXT, " +
					"GPSTime3 TEXT, " +
					"GPSLat3 REAL, " +
					"GPSLon3 REAL, " +	
					"ServiceResult TEXT, " +
					"InputDate TEXT, " +
					"DoorLock INTEGER, " +
					"PServ TEXT, " +
					"CServ TEXT, " +
					"OtherCommentsP TEXT, " +
					"OtherCommentsC TEXT, " +
					"FirstAttempt INTEGER NOT NULL DEFAULT 0, " + 
					"SecondAttempt INTEGER NOT NULL DEFAULT 0, " + 
					"ThirdAttempt INTEGER NOT NULL DEFAULT 0, " + 
					//					"ServiceCompleted INTEGER NOT NULL DEFAULT 0)";
					"ServiceCompleted INTEGER NOT NULL DEFAULT 0, "+
					"GpsTimeOfService Text,"+
					"GpsTimeOfFirstAttempt Text,"+
					"GpsTimeOfSecondAttempt Text,"+
					"GpsTimeOfThirdAttempt Text,"+
					"UpLoadFlag INTEGER NOT NULL DEFAULT 0,"+
					"CfirstTime Text,"+
					"CsecondTime Text,"+
					"CthirdTime Text, " +
					"producttype ) ";

	
	
	private static final String DATABASE_CREATE_HOLIDAY = 
			"CREATE TABLE Holiday (" +
					"_id TEXT PRIMARY KEY NOT NULL, " +
					"HolidayYear INTEGER NOT NULL, " +
					"HolidayDate TEXT NOT NULL, " +
					"HolidayDescription TEXT NOT NULL)";	

	private static final String DATABASE_CREATE_RELATED_PERSON = 
			"CREATE TABLE RelatedPerson (" +
					"_id TEXT PRIMARY KEY NOT NULL, " +
					"LegalDeliveryID TEXT NOT NULL, " +
					"Fullname TEXT NOT NULL)";	

	private static final String DATABASE_CREATE_RELATED_BUSINESS = 
			"CREATE TABLE RelatedBusiness (" +
					"_id TEXT PRIMARY KEY NOT NULL, " +
					"LegalDeliveryID TEXT NOT NULL, " +
					"BusinessName TEXT NOT NULL)";	

	private static final String DATABASE_CREATE_REPOSITORY =
			"CREATE TABLE REPOSITORY("+ 
					"RepositoryFullname TEXT)";

	public LDDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		parentContext = context;
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {

		try {
			database.execSQL(DATABASE_CREATE_LD);
		} catch (SQLiteException exception) {

			Log.i("Database : onCreate", exception.getMessage());
			exception.printStackTrace();
		}

		try {
			database.execSQL(DATABASE_CREATE_HOLIDAY);
		} catch (SQLiteException exception) {

			Log.i("Database : onCreate", exception.getMessage());
			exception.printStackTrace();
		}	      

		try {
			database.execSQL(DATABASE_CREATE_RELATED_PERSON);
		} catch (SQLiteException exception) {

			Log.i("Database : onCreate", exception.getMessage());
			exception.printStackTrace();
		}	

		try {
			database.execSQL(DATABASE_CREATE_RELATED_BUSINESS);
		} catch (SQLiteException exception) {

			Log.i("Database : onCreate", exception.getMessage());
			exception.printStackTrace();
		}

		try {
			database.execSQL(DATABASE_CREATE_REPOSITORY);
		} catch (SQLiteException exception) {

			Log.i("Database : onCreate", exception.getMessage());
			exception.printStackTrace();
		}
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		String message = "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data";

		alreport=new AuditLogReporter();
		///////////////////////////////////////////////////////////////////////////////////////////////
		alreport.reportAudit("Updating Database",parentContext);
		///////////////////////////////////////////////////////////////////////////////////////////////

		Log.w(LDDatabaseHelper.class.getName(), message);
		Toast.makeText(parentContext, message, Toast.LENGTH_LONG).show();
		database.execSQL("DROP TABLE IF EXISTS LegalDelivery");
		database.execSQL("DROP TABLE IF EXISTS Holiday");
		database.execSQL("DROP TABLE IF EXISTS RelatedPerson");
		database.execSQL("DROP TABLE IF EXISTS RelatedBusiness");
		onCreate(database);
	}
}
