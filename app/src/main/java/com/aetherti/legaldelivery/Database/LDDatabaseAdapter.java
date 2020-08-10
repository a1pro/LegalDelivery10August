package com.aetherti.legaldelivery.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.ErrorReporting.AuditLogReporter;

public class LDDatabaseAdapter {
	///////////////////////////////////////////////////////////////////////////////////////////////
	AuditLogReporter alreport;
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Database fields
	public static final String KEY_ROWID 					= "_id";
	public static final String KEY_DeviceID 					= "DeviceID";
	public static final String KEY_JobNo					= "JobNo";
	public static final String KEY_Client					= "Client";
	public static final String KEY_ServiceType				= "ServiceType";
	public static final String KEY_LTServiceType			= "LTServiceType";
	public static final String KEY_LTBIZNAME				= "LTLTBIZNAME";
	public static final String KEY_PPSERVED1				= "PPSERVED1";	
	public static final String KEY_ServerLicenceNo			= "ServerLicenceNo";
	public static final String KEY_CaseNo					= "CaseNo";
	public static final String KEY_LTFullname				= "LTFullname";
	public static final String KEY_PersonSeenDoeSubstitute	= "PersonSeenDoeSubstitute";
	public static final String KEY_PersonSeenSubstitute		= "PersonSeenSubstitute";
	public static final String KEY_PersonSeenPersonal		= "PersonSeenPersonal";
	public static final String KEY_PersonsNotSeenPersonal	= "PersonsNotSeenPersonal";
	public static final String KEY_CorpRecipient			= "CorpRecipient";
	public static final String KEY_CorpRecipientTitle		= "CorpRecipientTitle";
	public static final String KEY_LTAddress				= "LTAddress";
	public static final String KEY_LTApt					= "LTApt";
	public static final String KEY_LTCity					= "LTCity";
	public static final String KEY_LTState					= "LTState";
	public static final String KEY_LTZip					= "LTZip";
	public static final String KEY_STND_FullName			= "STND_FullName";
	public static final String KEY_STND_Address				= "STND_Address";
	public static final String KEY_STND_Apt					= "STND_Apt";
	public static final String KEY_STND_City				= "STND_City";
	public static final String KEY_STND_State				= "STND_State";
	public static final String KEY_STND_Zip					= "STND_Zip";
	public static final String KEY_STND_ServiceType			= "STND_ServiceType";
	public static final String KEY_DateOfService			= "DateOfService";
	public static final String KEY_TimeOfService			= "TimeOfService";
    public static final String KEY_GpsTimeOfService         = "GpsTimeOfService";
	public static final String KEY_GPSDate1					= "GPSDate1";
	public static final String KEY_GPSTime1					= "GPSTime1";
	public static final String KEY_GpsTimeOfFirstAttempt    = "GpsTimeOfFirstAttempt";
	public static final String KEY_GPSLat1 					= "GPSLat1";
	public static final String KEY_GPSLon1 					= "GPSLon1";
	public static final String KEY_GPSDate2					= "GPSDate2";
	public static final String KEY_GPSTime2					= "GPSTime2";
	public static final String KEY_GpsTimeOfSecondAttempt	= "GpsTimeOfSecondAttempt";
	public static final String KEY_GPSLat2 					= "GPSLat2";
	public static final String KEY_GPSLon2 					= "GPSLon2";
	public static final String KEY_GPSDate3					= "GPSDate3";
	public static final String KEY_GPSTime3					= "GPSTime3";
	public static final String KEY_GpsTimeOfThirdAttempt	= "GpsTimeOfThirdAttempt";
	public static final String KEY_GPSLat3 					= "GPSLat3";
	public static final String KEY_GPSLon3 					= "GPSLon3";	
	public static final String KEY_ServiceResult			= "ServiceResult";
	public static final String KEY_InputDate				= "InputDate";
	public static final String KEY_DoorLock					= "DoorLock";
	public static final String KEY_PServ					= "PServ";
	public static final String KEY_CServ					= "CServ";
	public static final String KEY_OtherCommentsP			= "OtherCommentsP";
	public static final String KEY_OtherCommentsC			= "OtherCommentsC";
	public static final String KEY_FirstAttempt				= "FirstAttempt";
	public static final String KEY_SecondAttempt			= "SecondAttempt";
	public static final String KEY_ThirdAttempt				= "ThirdAttempt";
	public static final String KEY_ServiceCompleted			= "ServiceCompleted";
	public static final String KEY_ServiceUploadFlag        = "UpLoadFlag";
	private static final String KEY_HolidayYear				= "HolidayYear";
	public static final String KEY_HolidayDate				= "HolidayDate";
	public static final String KEY_HolidayDescription		= "HolidayDescription";
	private static final String KEY_RelatedPersonLDID		= "LegalDeliveryID";
	private static final String KEY_RelatedPersonFullname	= "Fullname";
	private static final String DATABASE_TABLE_LD 			= "LegalDelivery";
	private static final String DATABASE_TABLE_HOLIDAY 		= "Holiday";
	private static final String DATABASE_TABLE_RELATEDPERSON = "RelatedPerson";
	private static final String DATABASE_TABLE_REPOSITORY    = "REPOSITORY";
	private static final String KEY_RepositoryFullname     	= "RepositoryFullname";
	public static final String KEY_CfirstTime	   			= "CfirstTime";
	public static final String KEY_CsecondTime	  			= "CsecondTime";
	public static final String KEY_CthirdTime	       		= "CthirdTime";
	public static final String KEY_producttype	       		= "producttype";
	
	private Context context;
	private SQLiteDatabase database;
	private LDDatabaseHelper dbHelper;

	public LDDatabaseAdapter(Context context) {
		this.context = context;
	}

	public void open() throws SQLException {
		dbHelper = new LDDatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
	}
	public void open_readable() throws SQLException {
		dbHelper = new LDDatabaseHelper(context);
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}



	// Create a new LD If the LD is successfully created return the new
	// rowId for that note, otherwise return a -1 to indicate failure.
	public void createLD(
			String _id, String JobNo, String Client, String ServiceType, 
			String LTServiceType, String CaseNo, String LTFullname, 
			String LTAddress, String LTApt, String LTCity, String LTState, 
			String LTZip, String InputDate,String producttype
			) {

		ContentValues initialValues = createContentValuesFor_LD_Insert(
				_id, JobNo, Client, ServiceType, LTServiceType,
				CaseNo, LTFullname, LTAddress, LTApt, LTCity, 
				LTState, LTZip, InputDate,producttype
				);

		database.insert(DATABASE_TABLE_LD, null, initialValues);
	}

	
	/**
	 * @return Number of records present in database
	 */
	public int count_no_of_Records(){
		String countQuery = "SELECT * FROM " + DATABASE_TABLE_LD;
		Cursor cursor = database.rawQuery(countQuery, null);
	    /*Log.i(getClass().toString(),"Getting count of Database table LegalDelevery: "+cursor.getCount());*/
	    if(cursor!=null)
	    	return cursor.getCount();
	    else 
	    	return -1;
	} 
	public int count_Upload_required(){
		    Cursor cursor = database.query(DATABASE_TABLE_LD,null, KEY_ServiceUploadFlag + "=?",
		            new String[] { "1" }, null, null, null, null);
		    if (cursor != null)
		    {    cursor.moveToFirst();
		    int cnt = cursor.getCount();
		    cursor.close();
		    return cnt;
		    
		    }else
		    return -1;
	}
	
	// Create a new LD If the LD is successfully created return the new
	// rowId for that note, otherwise return a -1 to indicate failure.
	
	/**
	 * @param _id	id of LegalDelivery record.
	 * @param JobNo of LegalDelivery record.
	 * @param Client of LegalDelivery record.
	 * @param ServiceType of LegalDelivery record.
	 * @param LTServiceType of LegalDelivery record.
	 * @param LTBIZNAME of LegalDelivery record.
	 * @param PPSERVED1 of LegalDelivery record.
	 * @param ServerLicenceNo of LegalDelivery record.
	 * @param CaseNo of LegalDelivery record.
	 * @param LTFullname of LegalDelivery record.
	 * @param LTAddress of LegalDelivery record.
	 * @param LTApt of LegalDelivery record.
	 * @param LTCity of LegalDelivery record.
	 * @param LTState of LegalDelivery record.
	 * @param LTZip of LegalDelivery record.
	 * @param InputDate of LegalDelivery record.
	 * @param STND_FullName of LegalDelivery record.
	 * @param STND_Address of LegalDelivery record.
	 * @param STND_Apt of LegalDelivery record.
	 * @param STND_City of LegalDelivery record.
	 * @param STND_State of LegalDelivery record.
	 * @param STND_Zip of LegalDelivery record.
	 * @param STND_ServiceType of LegalDelivery record.
	 * @param PersonSeenSubstituteFromList of LegalDelivery record.
	 * @param PersonSeenSubstitute of LegalDelivery record.
	 * @param PersonSeenPersonal of LegalDelivery record.
	 * @param PersonsNotSeenPersonal of LegalDelivery record.
	 * @param CorpRecipient of LegalDelivery record.
	 * @param CorpRecipientTitle of LegalDelivery record.
	 * @param DateOfService of LegalDelivery record.
	 * @param TimeOfService of LegalDelivery record.
	 * @param GPSDate1 of LegalDelivery record.
	 * @param GPSTime1 of LegalDelivery record.
	 * @param GPSLat1 of LegalDelivery record.
	 * @param GPSLon1 of LegalDelivery record.
	 * @param GPSDate2 of LegalDelivery record.
	 * @param GPSTime2 of LegalDelivery record.
	 * @param GPSLat2 of LegalDelivery record.
	 * @param GPSLon2 of LegalDelivery record.
	 * @param GPSDate3 of LegalDelivery record.
	 * @param GPSTime3 of LegalDelivery record.
	 * @param GPSLat3 of LegalDelivery record.
	 * @param GPSLon3 of LegalDelivery record.
	 * @param ServiceResult of LegalDelivery record.
	 * @param DoorLock of LegalDelivery record.
	 * @param PServ of LegalDelivery record.
	 * @param CServ of LegalDelivery record.
	 * @param OtherCommentsP of LegalDelivery record.
	 * @param OtherCommentsC of LegalDelivery record.
	 * @param FirstAttempt of LegalDelivery record.
	 * @param SecondAttempt of LegalDelivery record.
	 * @param ThirdAttempt of LegalDelivery record.
	 * @param ServiceCompleted of LegalDelivery record.
	 * @param UpLoadFlag of LegalDelivery record.
	 * @return returns row_id of inserted item.
	 */
	public long createLD_Full(
			String _id, String JobNo, String Client, String ServiceType, 
			String LTServiceType,String LTBIZNAME,String PPSERVED1, String ServerLicenceNo, String CaseNo, String LTFullname, 
			String LTAddress, String LTApt, String LTCity, String LTState, 
			String LTZip, String InputDate, 
			String STND_FullName,String STND_Address,String STND_Apt,
			String STND_City,String STND_State,String STND_Zip,String STND_ServiceType,
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService, 
			String GPSDate1, String GPSTime1, Double GPSLat1, Double GPSLon1, 
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2, 
			String GPSDate3, String GPSTime3, Double GPSLat3, Double GPSLon3, 
			String ServiceResult, String DoorLock, String PServ, String CServ,
			String OtherCommentsP, String OtherCommentsC, 
			int FirstAttempt, int SecondAttempt, int ThirdAttempt, int ServiceCompleted,String GpsTimeOfService,String GpsTimeOfFirstAttempt,
			String GpsTimeOfSecondAttempt, String GpsTimeOfThirdAttempt,int UpLoadFlag,String producttype
			) {
		Log.i(getClass().toString(),"Creating LegalDelevery Database!");
		ContentValues initialValues = createContentValuesFor_LD_Insert_Full(
				_id, JobNo, Client, ServiceType, LTServiceType,LTBIZNAME,PPSERVED1,ServerLicenceNo,
				CaseNo, LTFullname, LTAddress, LTApt, LTCity, 
				LTState, LTZip, InputDate, 
				STND_FullName,STND_Address,STND_Apt,
				STND_City,STND_State,STND_Zip, STND_ServiceType,
				PersonSeenSubstituteFromList, PersonSeenSubstitute, 
				PersonSeenPersonal, PersonsNotSeenPersonal,
				CorpRecipient, CorpRecipientTitle, 
				DateOfService, TimeOfService, 
				GPSDate1, GPSTime1, GPSLat1, GPSLon1, 
				GPSDate2, GPSTime2, GPSLat2, GPSLon2, 
				GPSDate3, GPSTime3, GPSLat3, GPSLon3, 
				ServiceResult, DoorLock, PServ, CServ,
				OtherCommentsP, OtherCommentsC, 
				FirstAttempt, SecondAttempt, ThirdAttempt, ServiceCompleted,GpsTimeOfService,GpsTimeOfFirstAttempt,
				GpsTimeOfSecondAttempt,GpsTimeOfThirdAttempt,UpLoadFlag,producttype
				);

		return database.insert(DATABASE_TABLE_LD, null, initialValues);
	}

	public boolean updateLD_FirstAttempt(String rowId,
			String GPSDate1, String GPSTime1, Double GPSLat1, Double GPSLon1,String mobileTime1,int servicecomplete){//String CfirstTime) {

		ContentValues updateValues = createContentValuesFor_LD_Update_FirstAttempt(
				GPSDate1, GPSTime1, GPSLat1, GPSLon1,mobileTime1,servicecomplete);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}

	public boolean updateLD_SecondAttempt(String rowId,
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2,String mobileTime2,int servicecomplete) {

		ContentValues updateValues = createContentValuesFor_LD_Update_SecondAttempt(
				GPSDate2, GPSTime2, GPSLat2, GPSLon2,mobileTime2,servicecomplete);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}
	
	public boolean updateLD_ThirdAttempt(String rowId,
			String GPSDate3, String GPSTime3, Double GPSLat3, Double GPSLon3,String mobileTime3,int serviceComplete) {

		ContentValues updateValues = createContentValuesFor_LD_Update_ThirdAttempt(
				GPSDate3, GPSTime3, GPSLat3, GPSLon3,mobileTime3,serviceComplete);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}
	

	public boolean updateLD_Conspicuous(String rowId, 
			String DateOfService, String TimeOfService, 
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2, 
			String ServiceResult, String DoorLock, String CServ, String OtherCommentsC, int iAttempt) {

		ContentValues updateValues = createContentValuesFor_LD_Update_Conspicuous(
				DateOfService, TimeOfService, 
				GPSDate2, GPSTime2, GPSLat2, GPSLon2, 
				ServiceResult, DoorLock, CServ,
				OtherCommentsC, iAttempt);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}
	
	public boolean updateLD_Conspicuous(String rowId, 
			String DateOfService, String TimeOfService,
			String ServiceResult, String DoorLock, String CServ, String OtherCommentsC, int iAttempt, String mobileTime) {

		ContentValues updateValues = createContentValuesFor_LD_Update_Conspicuous(
				DateOfService, TimeOfService,
				ServiceResult, DoorLock, CServ,
				OtherCommentsC, iAttempt,mobileTime);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}

	public boolean updateLD_PersonalPlus_Substitute(String rowId, 
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService, 
			String GPSDate, String GPSTime, Double GPSLat, Double GPSLon, 
			String ServiceResult, String PServ, String OtherCommentsP,
			int iAttempt) {

		ContentValues updateValues = createContentValuesFor_LD_Update_PersonalPlus_Substitute(
				PersonSeenSubstituteFromList, PersonSeenSubstitute,
				PersonSeenPersonal, PersonsNotSeenPersonal,
				CorpRecipient, CorpRecipientTitle, 
				DateOfService, TimeOfService, 
				GPSDate, GPSTime, GPSLat, GPSLon, 
				ServiceResult, PServ,
				OtherCommentsP, iAttempt);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}
	
	public boolean updateLD_PersonalPlus_Substitute(String rowId, 
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService,
			String ServiceResult, String PServ, String OtherCommentsP,
			int iAttempt,String mobileTime,int servicecomplete) {

		ContentValues updateValues = createContentValuesFor_LD_Update_PersonalPlus_Substitute(
				PersonSeenSubstituteFromList, PersonSeenSubstitute,
				PersonSeenPersonal, PersonsNotSeenPersonal,
				CorpRecipient, CorpRecipientTitle, 
				DateOfService, TimeOfService, 
				ServiceResult, PServ,
				OtherCommentsP, iAttempt,mobileTime,servicecomplete);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}

	// Update the LD
	public boolean updateLD(String rowId, String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService, 
			String GPSDate1, String GPSTime1, Double GPSLat1, Double GPSLon1, 
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2, 
			String GPSDate3, String GPSTime3, Double GPSLat3, Double GPSLon3, 
			String ServiceResult, String DoorLock, String PServ, String CServ,
			String OtherCommentsP, String OtherCommentsC, 
			int FirstAttempt, int SecondAttempt, int ThirdAttempt, int ServiceCompleted) {

		ContentValues updateValues = createContentValuesFor_LD_Update(
				PersonSeenSubstituteFromList, PersonSeenSubstitute,
				PersonSeenPersonal, PersonsNotSeenPersonal,
				CorpRecipient, CorpRecipientTitle, 
				DateOfService, TimeOfService, 
				GPSDate1, GPSTime1, GPSLat1, GPSLon1, 
				GPSDate2, GPSTime2, GPSLat2, GPSLon2, 
				GPSDate3, GPSTime3, GPSLat3, GPSLon3, 
				ServiceResult, DoorLock, PServ, CServ,
				OtherCommentsP, OtherCommentsC, 
				FirstAttempt, SecondAttempt, ThirdAttempt, ServiceCompleted);

		return database.update(DATABASE_TABLE_LD, updateValues, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}

	public boolean updateLD_UploadFlag(String rowId,int uploadFlag) {

		ContentValues values = new ContentValues();

		values.put(KEY_ServiceUploadFlag,uploadFlag);

		return database.update(DATABASE_TABLE_LD, values, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}

	// Return a Cursor positioned at the defined LD
	public Cursor fetchLD_UploadFlag(String rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_LD, new String[] {
				KEY_ServiceUploadFlag},
				KEY_ROWID + "='" + rowId + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Deletes LD
	public boolean deleteLD(String rowId) {
		return database.delete(DATABASE_TABLE_LD, KEY_ROWID + "='" + rowId + "'", null) > 0;
	}

	// Return a Cursor over the list of all LD in the database
	// @return Cursor over all notes
	public Cursor fetchAllLDs() {
		return database.query(DATABASE_TABLE_LD, new String[] { KEY_ROWID,
				KEY_CaseNo, KEY_LTFullname, KEY_LTAddress }, null, null, null,
				null, null);
	}

	// Return a Cursor over the list of all LD in the database
	// @return Cursor over all notes
	public Cursor fetchAllLDsAllColumns() {
		return database.query(DATABASE_TABLE_LD, null, null, null, null,
				null, null);
	}

	public Cursor fetchSearchResults(String fullname, String address, 
			String apt, String casepapertype, 
			String servicetype, String inputdate)
	{
		String[] columns = new String[] { KEY_ROWID, KEY_ServiceType, KEY_LTServiceType, KEY_LTFullname, 
				KEY_LTBIZNAME, KEY_LTAddress, KEY_LTApt, KEY_LTCity, KEY_LTState, KEY_LTZip,
				KEY_STND_ServiceType, KEY_STND_FullName, KEY_STND_Address, KEY_STND_City,
				KEY_STND_Apt, KEY_STND_State, KEY_STND_Zip };

		StringBuilder whereCondition = new StringBuilder();
				
		if (!address.isEmpty())
		{
			whereCondition.append(KEY_LTAddress + " LIKE'%" + address + "%' OR ");
			whereCondition.append(KEY_STND_Address + " LIKE'%" + address + "%' AND ");
			
			
		}
		if (!apt.isEmpty())
		{
			whereCondition.append(KEY_LTApt + " LIKE'%" + apt + "%' AND ");
		}
		
		if(servicetype.equals("L&T Residential")){	
			if (!fullname.isEmpty())
			{
				whereCondition.append(KEY_LTFullname + " LIKE'%" + fullname + "%' AND ");
			}
			if (!casepapertype.equals("[Not Set]"))
			{
				whereCondition.append(KEY_LTServiceType + "='" + casepapertype + "' AND ");
				
			}
		}else if(servicetype.equals("L&T Commercial")){
			if (!fullname.isEmpty())
			{
				whereCondition.append(KEY_LTBIZNAME + " LIKE'%" + fullname + "%' AND ");
			}

			if (!casepapertype.equals("[Not Set]"))
			{
				whereCondition.append(KEY_LTServiceType + "='" + casepapertype + "' AND ");
			}
		}else if(servicetype.equals("Standard")){
			if (!fullname.isEmpty())
			{
				whereCondition.append(KEY_STND_FullName + " LIKE'%" + fullname + "%' AND ");
			}

			if (!address.isEmpty())
			{
				whereCondition.append(KEY_STND_Address + " LIKE'%" + address + "%' OR ");
				//whereCondition.append(KEY_LTAddress + " LIKE'%" + address + "%' AND ");
			}

			if (!apt.isEmpty())
			{
				whereCondition.append(KEY_STND_Apt + " LIKE'%" + apt + "%' AND ");
			}
			if (!fullname.isEmpty())
			{
				whereCondition.append(KEY_LTFullname + " LIKE'%" + fullname + "%' AND ");
			}
			
			if (!casepapertype.equals("[Not Set]"))
			{
				whereCondition.append(KEY_STND_ServiceType + "='" + casepapertype + "' AND ");
			}
		}else{
			if (!fullname.isEmpty())
			{
				whereCondition.append(KEY_LTFullname + " LIKE'%" + fullname + "%' OR ");
				whereCondition.append(KEY_LTBIZNAME + " LIKE'%" + fullname + "%' OR ");
				whereCondition.append(KEY_STND_FullName + " LIKE'%" + fullname + "%' AND ");
			}
		}
		
				
		if (!servicetype.equals("[Not Set]"))
		{
			whereCondition.append(KEY_ServiceType + "='" + servicetype + "' AND ");
		}

		if (!inputdate.equals("[Not Set]"))
		{
			String mInputDate = inputdate.substring(6) + "-" +
							    inputdate.substring(0, 2) + "-" +
							    inputdate.substring(3, 5);
			whereCondition.append(KEY_InputDate + " LIKE'%" + mInputDate + "%' AND ");
		}
		Cursor mCursor;


		if (whereCondition.toString() != "")
		{
			whereCondition.replace(whereCondition.length()-5, whereCondition.length(), "");
			mCursor = database.query(true, DATABASE_TABLE_LD, columns,whereCondition.toString(),null, null, null, null, null);
		} else {	
			mCursor = database.query(DATABASE_TABLE_LD, null, null, null, null,null, KEY_InputDate+ " DESC");
		}

		if (mCursor != null) 
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Return a Cursor over the list of all edited LD in the database
	// @return Cursor over all notes
	public Cursor fetchAllCompletedServicesAllColumns() {
		return database.query(true, DATABASE_TABLE_LD, null, KEY_ServiceCompleted + "= 1", 
				null, null, null, null, null);
	}

	// Return a Cursor positioned at the defined LD
	public Cursor fetchLD(String rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_LD, new String[] {
				KEY_ROWID, KEY_CaseNo, KEY_LTFullname, KEY_LTAddress,KEY_STND_FullName,KEY_STND_Address,KEY_STND_Apt,KEY_STND_City,KEY_STND_State,KEY_STND_Zip},
				KEY_ROWID + "='" + rowId + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Return a Cursor positioned at the defined LD
	/**
	 * @param rowId that has unique id for 
	 * @return	All data(column) that has this ROWID
	 * @throws SQLException
	 */
	public Cursor fetchLDAllColumns(String rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_LD, null,
				KEY_ROWID + "='" + rowId + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//mitesh
	public Cursor fetch1LDColumns(String rowId) throws SQLException {
		int row=Integer.parseInt(rowId);
		row-=1;
		rowId=Integer.toString(row);
		
		Cursor mCursor = database.query(true, DATABASE_TABLE_LD, null,
				KEY_ROWID + "='" + rowId + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	//mitesh
	
	private ContentValues createContentValuesFor_LD_Insert(
			String _id, String JobNo, String Client, String ServiceType, 
			String LTServiceType, String CaseNo, String LTFullname,   
			String LTAddress, String LTApt, String LTCity, String LTState, 
			String LTZip, String InputDate,String producttype
			) {

		ContentValues values = new ContentValues();

		values.put(KEY_ROWID, 				_id);							// 1
		values.put(KEY_JobNo,			    JobNo);							// 2
		values.put(KEY_Client,			    Client);						// 3
		values.put(KEY_ServiceType,			ServiceType);					// 4
		values.put(KEY_LTServiceType,		LTServiceType);					// 5
		values.put(KEY_CaseNo,				CaseNo);						// 6
		values.put(KEY_LTFullname,			LTFullname);					// 7

		values.putNull(KEY_PersonSeenSubstitute);							// 12
		values.putNull(KEY_PersonSeenDoeSubstitute);						// 13
		values.putNull(KEY_PersonSeenPersonal);								// 14
		values.putNull(KEY_PersonsNotSeenPersonal);							// 15
		values.putNull(KEY_CorpRecipient);									// 16
		values.putNull(KEY_CorpRecipientTitle);								// 17

		values.put(KEY_LTAddress,			LTAddress);						// 18
		values.put(KEY_LTApt,				LTApt);							// 19
		values.put(KEY_LTCity,				LTCity);						// 20
		values.put(KEY_LTState,				LTState);						// 21
		values.put(KEY_LTZip,				LTZip);							// 22

		values.putNull(KEY_DateOfService);									// 23
		values.putNull(KEY_TimeOfService);									// 24
		values.put(KEY_GPSDate1, "[Not Set]");										// 25
		values.put(KEY_GPSTime1,"[Not Set]");										// 26
		values.putNull(KEY_GPSLat1);										// 27
		values.putNull(KEY_GPSLon1);										// 28
		values.put(KEY_GPSDate2 ,"[Not Set]");										// 29
		values.put(KEY_GPSTime2 ,"[Not Set]");										// 30
		values.putNull(KEY_GPSLat2);										// 31
		values.putNull(KEY_GPSLon2);            							// 32
		values.put(KEY_GPSDate3,"[Not Set]");										// 33
		values.put(KEY_GPSTime3,"[Not Set]");										// 34
		values.putNull(KEY_GPSLat3);										// 35
		values.putNull(KEY_GPSLon3);            							// 36              
		values.putNull(KEY_ServiceResult);									// 37

		values.put(KEY_InputDate,			InputDate);						// 38

		values.putNull(KEY_DoorLock);										// 39
		values.putNull(KEY_PServ);											// 40
		values.putNull(KEY_CServ);											// 41
		values.putNull(KEY_OtherCommentsP);									// 42
		values.putNull(KEY_OtherCommentsC);									// 43

		values.put(KEY_FirstAttempt,		0);								// 44
		values.put(KEY_SecondAttempt,		0);								// 45
		values.put(KEY_ThirdAttempt,		0);								// 46
		values.put(KEY_ServiceUploadFlag,	0);								// 46
		values.put(KEY_ServiceCompleted,	0);								// 47
		values.put(KEY_producttype, 	producttype);
		return values;
	}

	private ContentValues createContentValuesFor_LD_Insert_Full(
			String _id, String JobNo, String Client, String ServiceType, 
			String LTServiceType,String LTBIZNAME,String PPSERVED1, String ServerLicenceNo, String CaseNo, String LTFullname,   
			String LTAddress, String LTApt, String LTCity, String LTState, 
			String LTZip, String InputDate, 
			String STND_FullName,String STND_Address,String STND_Apt,
			String STND_City,String STND_State,String STND_Zip,String STND_ServiceType,
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService, 
			String GPSDate1, String GPSTime1, Double GPSLat1, Double GPSLon1, 
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2, 
			String GPSDate3, String GPSTime3, Double GPSLat3, Double GPSLon3, 
			String ServiceResult, String DoorLock, String PServ, String CServ,
			String OtherCommentsP, String OtherCommentsC, 
			int FirstAttempt, int SecondAttempt, int ThirdAttempt, int ServiceCompleted,String GpsTimeOfService,String GpsTimeOfFirstAttempt,
			String GpsTimeOfSecondAttempt, String GpsTimeOfThirdAttempt, int UpLoadFlag,String producttype
	) {

		ContentValues values = new ContentValues();

		values.put(KEY_ROWID, 						_id);							
		values.put(KEY_JobNo,			    		JobNo);							
		values.put(KEY_Client,			    		Client);						
		values.put(KEY_ServiceType,					ServiceType);					
		values.put(KEY_LTServiceType,				LTServiceType);		
		values.put(KEY_LTBIZNAME,					LTBIZNAME);	
		values.put(KEY_PPSERVED1,					PPSERVED1);	
		values.put(KEY_ServerLicenceNo,				ServerLicenceNo);		
		values.put(KEY_CaseNo,						CaseNo);						
		values.put(KEY_LTFullname,					LTFullname);
		values.put(KEY_PersonSeenDoeSubstitute,		PersonSeenSubstituteFromList);
		values.put(KEY_PersonSeenSubstitute,		PersonSeenSubstitute);
		values.put(KEY_PersonSeenPersonal,			PersonSeenPersonal);
		values.put(KEY_PersonsNotSeenPersonal,		PersonsNotSeenPersonal);
		values.put(KEY_CorpRecipient,				CorpRecipient);
		values.put(KEY_CorpRecipientTitle,			CorpRecipientTitle);						
		values.put(KEY_LTAddress,					LTAddress);					
		values.put(KEY_LTApt,						LTApt);						
		values.put(KEY_LTCity,						LTCity);					
		values.put(KEY_LTState,						LTState);					
		values.put(KEY_LTZip,						LTZip);	
		values.put(KEY_STND_FullName,				STND_FullName);					
		values.put(KEY_STND_Address,				STND_Address);						
		values.put(KEY_STND_Apt,					STND_Apt);					
		values.put(KEY_STND_City,					STND_City);					
		values.put(KEY_STND_State,					STND_State);	
		values.put(KEY_STND_Zip,					STND_Zip);
		values.put(KEY_STND_ServiceType,			STND_ServiceType);
		values.put(KEY_DateOfService,				DateOfService);
		values.put(KEY_TimeOfService,				TimeOfService);
		values.put(KEY_GPSDate1,					GPSDate1);
		values.put(KEY_GPSTime1,					GPSTime1);
		values.put(KEY_GPSLat1,						GPSLat1);
		values.put(KEY_GPSLon1, 					GPSLon1);
		values.put(KEY_GPSDate2,					GPSDate2);
		values.put(KEY_GPSTime2,					GPSTime2);
		values.put(KEY_GPSLat2,						GPSLat2);
		values.put(KEY_GPSLon2, 					GPSLon2);   
		values.put(KEY_GPSDate3,					GPSDate3);
		values.put(KEY_GPSTime3,					GPSTime3);
		values.put(KEY_GPSLat3,						GPSLat3);
		values.put(KEY_GPSLon3, 					GPSLon3);       	
		values.put(KEY_ServiceResult,				ServiceResult);
		values.put(KEY_InputDate,					InputDate);					
		values.put(KEY_DoorLock,					DoorLock);	
		values.put(KEY_PServ,						PServ);
		values.put(KEY_CServ,						CServ);
		values.put(KEY_OtherCommentsP,				OtherCommentsP);
		values.put(KEY_OtherCommentsC,				OtherCommentsC);
		values.put(KEY_FirstAttempt,				FirstAttempt);	
		values.put(KEY_SecondAttempt,				SecondAttempt);	
		values.put(KEY_ThirdAttempt,				ThirdAttempt);	
		values.put(KEY_ServiceCompleted,			ServiceCompleted);
		values.put(KEY_GpsTimeOfService,			GpsTimeOfService);
		values.put(KEY_GpsTimeOfFirstAttempt,		GpsTimeOfFirstAttempt);
		values.put(KEY_GpsTimeOfSecondAttempt,		GpsTimeOfSecondAttempt);
	    values.put(KEY_GpsTimeOfThirdAttempt,		GpsTimeOfThirdAttempt);
		values.put(KEY_ServiceUploadFlag,			UpLoadFlag);
		values.put(KEY_producttype,			producttype);

		return values;
	}

	private ContentValues createContentValuesFor_LD_Update_FirstAttempt(
			String GPSDate1, String GPSTime1, Double GPSLat1, Double GPSLon1,String mobileTime1,int servicecomplete)//String CfirstTime)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_GPSDate1,					GPSDate1);
		values.put(KEY_GPSTime1,					GPSTime1);
		values.put(KEY_GPSLat1,						GPSLat1);
		values.put(KEY_GPSLon1, 					GPSLon1);
		values.put(KEY_GpsTimeOfFirstAttempt,       mobileTime1);
		values.put(KEY_ServiceCompleted,            servicecomplete);
		values.put(KEY_FirstAttempt,				1);
		

		return values;
	}

	private ContentValues createContentValuesFor_LD_Update_SecondAttempt(
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2,String mobileTime2,int servicecomplete)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_GPSDate2,					GPSDate2);
		values.put(KEY_GPSTime2,					GPSTime2);
		values.put(KEY_GPSLat2,						GPSLat2);
		values.put(KEY_GPSLon2, 					GPSLon2);
		values.put(KEY_GpsTimeOfSecondAttempt, 		mobileTime2);
		values.put(KEY_SecondAttempt,				1);
		values.put(KEY_ServiceCompleted,            servicecomplete);
		return values;
	}
	
	private ContentValues createContentValuesFor_LD_Update_ThirdAttempt(
			String GPSDate3, String GPSTime3, Double GPSLat3, Double GPSLon3,String mobileTime3,int servicecomplete)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_GPSDate3,					GPSDate3);
		values.put(KEY_GPSTime3,					GPSTime3);
		values.put(KEY_GPSLat3,						GPSLat3);
		values.put(KEY_GPSLon3, 					GPSLon3);
		values.put(KEY_GpsTimeOfThirdAttempt, 		mobileTime3);
		values.put(KEY_ThirdAttempt,				1);
		values.put(KEY_ServiceCompleted,            servicecomplete);
		return values;
	}
	

	private ContentValues createContentValuesFor_LD_Update_Conspicuous(
			String DateOfService, String TimeOfService, 
			String GPSDate, String GPSTime, Double GPSLat, Double GPSLon, 
			String ServiceResult, String DoorLock, String CServ, String OtherCommentsC,
			int iAttempt
			)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_DateOfService,				DateOfService);
		values.put(KEY_TimeOfService,				TimeOfService);

		switch (iAttempt)
		{
		case 1:
			values.put(KEY_GPSDate1,					GPSDate);
			values.put(KEY_GPSTime1,					GPSTime);
			values.put(KEY_GPSLat1,						GPSLat);
			values.put(KEY_GPSLon1, 					GPSLon);
			values.put(KEY_FirstAttempt,				1);
			break;
		case 2:
			values.put(KEY_GPSDate2,					GPSDate);
			values.put(KEY_GPSTime2,					GPSTime);
			values.put(KEY_GPSLat2,						GPSLat);
			values.put(KEY_GPSLon2, 					GPSLon);
			values.put(KEY_SecondAttempt,				1);
			break;
		case 3:
			values.put(KEY_GPSDate3,					GPSDate);
			values.put(KEY_GPSTime3,					GPSTime);
			values.put(KEY_GPSLat3,						GPSLat);
			values.put(KEY_GPSLon3, 					GPSLon);
			values.put(KEY_ThirdAttempt,				1);
			break;
		}        

		values.put(KEY_ServiceResult,				ServiceResult);
		values.put(KEY_DoorLock,					DoorLock);	
		values.put(KEY_CServ,						CServ);
		values.put(KEY_OtherCommentsC,				OtherCommentsC);

		values.put(KEY_ServiceUploadFlag, 			0);
		values.put(KEY_ServiceCompleted,			1);

		return values;
	}
	
	private ContentValues createContentValuesFor_LD_Update_Conspicuous(
			String DateOfService, String TimeOfService,
			String ServiceResult, String DoorLock, String CServ, String OtherCommentsC,
			int iAttempt, String mobileTime
			)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_DateOfService,				DateOfService);
		values.put(KEY_TimeOfService,				TimeOfService);
		values.put(KEY_GpsTimeOfService,            mobileTime);

		switch (iAttempt)
		{
		case 1:			
			values.put(KEY_FirstAttempt,				1);
			break;
		case 2:
			values.put(KEY_SecondAttempt,				1);
			break;
		case 3:
			values.put(KEY_ThirdAttempt,				1);
			break;
		}     

		values.put(KEY_ServiceResult,				ServiceResult);
		values.put(KEY_DoorLock,					DoorLock);	
		values.put(KEY_CServ,						CServ);
		values.put(KEY_OtherCommentsC,				OtherCommentsC);

		values.put(KEY_ServiceUploadFlag, 			0);
		values.put(KEY_ServiceCompleted,			1);

		return values;
	}

	private ContentValues createContentValuesFor_LD_Update_PersonalPlus_Substitute(
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService, 
			String GPSDate, String GPSTime, Double GPSLat, Double GPSLon,
			String ServiceResult, String PServ,
			String OtherCommentsP,
			int iAttempt)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_PersonSeenDoeSubstitute,		PersonSeenSubstituteFromList);
		values.put(KEY_PersonSeenSubstitute,		PersonSeenSubstitute);
		values.put(KEY_PersonSeenPersonal,			PersonSeenPersonal);
		values.put(KEY_PersonsNotSeenPersonal,		PersonsNotSeenPersonal);
		values.put(KEY_CorpRecipient,				CorpRecipient);
		values.put(KEY_CorpRecipientTitle,			CorpRecipientTitle);
		values.put(KEY_DateOfService,				DateOfService);
		values.put(KEY_TimeOfService,				TimeOfService);

	switch (iAttempt)
		{
		case 1:
			values.put(KEY_GPSDate1,					GPSDate);
			values.put(KEY_GPSTime1,					GPSTime);
			values.put(KEY_GPSLat1,						GPSLat);
			values.put(KEY_GPSLon1, 					GPSLon);
			values.put(KEY_FirstAttempt,				1);
			break;
		case 2:
			values.put(KEY_GPSDate2,					GPSDate);
			values.put(KEY_GPSTime2,					GPSTime);
			values.put(KEY_GPSLat2,						GPSLat);
			values.put(KEY_GPSLon2, 					GPSLon);
			values.put(KEY_SecondAttempt,				1);
			break;
		case 3:
			values.put(KEY_GPSDate3,					GPSDate);
			values.put(KEY_GPSTime3,					GPSTime);
			values.put(KEY_GPSLat3,						GPSLat);
			values.put(KEY_GPSLon3, 					GPSLon);
			values.put(KEY_ThirdAttempt,				1);
			break;
		}

		values.put(KEY_ServiceResult,				ServiceResult);
		values.put(KEY_PServ,						PServ);
		values.put(KEY_OtherCommentsP,				OtherCommentsP);

		values.put(KEY_ServiceUploadFlag, 			0);
		values.put(KEY_ServiceCompleted,			1);//insert

		return values;
	}

	private ContentValues createContentValuesFor_LD_Update_PersonalPlus_Substitute(
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle,
			String DateOfService, String TimeOfService, 			
			String ServiceResult, String PServ,
			String OtherCommentsP,
			int iAttempt,String mobileTime,int servicecomplete)
	{
		ContentValues values = new ContentValues();

		values.put(KEY_PersonSeenDoeSubstitute,		PersonSeenSubstituteFromList);
		values.put(KEY_PersonSeenSubstitute,		PersonSeenSubstitute);
		values.put(KEY_PersonSeenPersonal,			PersonSeenPersonal);
		values.put(KEY_PersonsNotSeenPersonal,		PersonsNotSeenPersonal);
		values.put(KEY_CorpRecipient,				CorpRecipient);
		values.put(KEY_CorpRecipientTitle,			CorpRecipientTitle);
		values.put(KEY_DateOfService,				DateOfService);
		values.put(KEY_TimeOfService,				TimeOfService);
		values.put(KEY_GpsTimeOfService,             mobileTime);

		switch (iAttempt)
		{
		case 1:			
			values.put(KEY_FirstAttempt,				1);
			break;
		case 2:
			values.put(KEY_SecondAttempt,				1);
			break;
		case 3:
			values.put(KEY_ThirdAttempt,				1);
			break;
		}   

		values.put(KEY_ServiceResult,				ServiceResult);
		values.put(KEY_PServ,						PServ);
		values.put(KEY_OtherCommentsP,				OtherCommentsP);

		values.put(KEY_ServiceUploadFlag, 			0);
		values.put(KEY_ServiceCompleted,			servicecomplete);//insert

		return values;
	}
	
	private ContentValues createContentValuesFor_LD_Update(
			String PersonSeenSubstituteFromList, String PersonSeenSubstitute, 
			String PersonSeenPersonal, String PersonsNotSeenPersonal,
			String CorpRecipient, String CorpRecipientTitle, 
			String DateOfService, String TimeOfService, 
			String GPSDate1, String GPSTime1, Double GPSLat1, Double GPSLon1, 
			String GPSDate2, String GPSTime2, Double GPSLat2, Double GPSLon2, 
			String GPSDate3, String GPSTime3, Double GPSLat3, Double GPSLon3, 
			String ServiceResult, String DoorLock, String PServ, String CServ,
			String OtherCommentsP, String OtherCommentsC, 
			int FirstAttempt, int SecondAttempt, int ThirdAttempt, int ServiceCompleted) {

		ContentValues values = new ContentValues();

		values.put(KEY_PersonSeenDoeSubstitute,		PersonSeenSubstituteFromList);
		values.put(KEY_PersonSeenSubstitute,		PersonSeenSubstitute);
		values.put(KEY_PersonSeenPersonal,			PersonSeenPersonal);
		values.put(KEY_PersonsNotSeenPersonal,		PersonsNotSeenPersonal);
		values.put(KEY_CorpRecipient,				CorpRecipient);
		values.put(KEY_CorpRecipientTitle,			CorpRecipientTitle);
		values.put(KEY_DateOfService,				DateOfService);
		values.put(KEY_TimeOfService,				TimeOfService);
		values.put(KEY_GPSDate1,					GPSDate1);
		values.put(KEY_GPSTime1,					GPSTime1);
		values.put(KEY_GPSLat1,						GPSLat1);
		values.put(KEY_GPSLon1, 					GPSLon1);
		values.put(KEY_GPSDate2,					GPSDate2);
		values.put(KEY_GPSTime2,					GPSTime2);
		values.put(KEY_GPSLat2,						GPSLat2);
		values.put(KEY_GPSLon2, 					GPSLon2);   
		values.put(KEY_GPSDate3,					GPSDate3);
		values.put(KEY_GPSTime3,					GPSTime3);
		values.put(KEY_GPSLat3,						GPSLat3);
		values.put(KEY_GPSLon3, 					GPSLon3);       	
		values.put(KEY_ServiceResult,				ServiceResult);
		values.put(KEY_DoorLock,					DoorLock);	
		values.put(KEY_PServ,						PServ);
		values.put(KEY_CServ,						CServ);
		values.put(KEY_OtherCommentsP,				OtherCommentsP);
		values.put(KEY_OtherCommentsC,				OtherCommentsC);
		values.put(KEY_FirstAttempt,				FirstAttempt);	
		values.put(KEY_SecondAttempt,				SecondAttempt);	
		values.put(KEY_ThirdAttempt,				ThirdAttempt);	
		values.put(KEY_ServiceCompleted,			ServiceCompleted);	

		return values;
	}

	public long createRelatedPerson(String _id, String LegalDeliveryID, String Fullname) {

		ContentValues initialValues = createContentValuesFor_RelatedPerson_Insert(
				_id, LegalDeliveryID, Fullname
				);

		return database.insert(DATABASE_TABLE_RELATEDPERSON, null, initialValues);
	}

	
	/**
	 * @param _legalDeliveryID Get ROWID for particular Person ;
	 * @return	Cursor to related person.
	 * @throws SQLException
	 */
	public Cursor fetchRelatedPersons(String _legalDeliveryID) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_RELATEDPERSON, new String[] {
				KEY_RelatedPersonFullname },
				KEY_RelatedPersonLDID + "='" + _legalDeliveryID + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValuesFor_RelatedPerson_Insert(
			String _id, String LegalDeliveryID, String Fullname) {

		ContentValues values = new ContentValues();

		values.put(KEY_ROWID, 						_id);							// 1
		values.put(KEY_RelatedPersonLDID,			LegalDeliveryID);					// 2
		values.put(KEY_RelatedPersonFullname,		Fullname);					// 3

		return values;
	}

	public long createHoliday(String _id, String HolidayYear, String HolidayDate, String HolidayDescription) {

		ContentValues initialValues = createContentValuesFor_Holiday_Insert(
				_id, HolidayYear, HolidayDate, HolidayDescription);

		return database.insert(DATABASE_TABLE_HOLIDAY, null, initialValues);
	}

	// Return a Cursor positioned at the defined Holiday
	/**
	 * @param HolidayYear pass year which holidays you want to check
	 * @return cursor 
	 * @throws SQLException if not available it throws this exception.
	 */
	public Cursor fetchHolidays(String HolidayYear,String HolidayMonth,String HolydayDay) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_HOLIDAY, new String[] {
				KEY_ROWID, KEY_HolidayYear, KEY_HolidayDate,KEY_HolidayDescription },
				KEY_HolidayYear + "='" + HolidayYear + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	public Cursor fetchHolidays(String HolidayYear) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_HOLIDAY, new String[] {
				KEY_ROWID, KEY_HolidayYear,KEY_HolidayDate, KEY_HolidayDescription },
				KEY_HolidayYear + "='" + HolidayYear + "'", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Deletes Holidays for the given year
	public boolean deleteHolidayYear(String HolidayYear) {
		return database.delete(DATABASE_TABLE_HOLIDAY, KEY_HolidayYear + "='" + HolidayYear + "'", null) > 0;
	}

	private ContentValues createContentValuesFor_Holiday_Insert(
			String _id, String HolidayYear, String HolidayDate, String HolidayDescription) {

		ContentValues values = new ContentValues();

		values.put(KEY_ROWID, 					_id);							// 1
		values.put(KEY_HolidayYear,			    HolidayYear);					// 2
		values.put(KEY_HolidayDate,			    HolidayDate);					// 3
		values.put(KEY_HolidayDescription,		HolidayDescription);			// 4

		return values;
	}

	//Repository
	public long createRepository(String RepositoryFullName) {

		ContentValues initialValues = createContentValuesFor_createRepository(
				RepositoryFullName);

		return database.insert(DATABASE_TABLE_REPOSITORY, null, initialValues);
	}

	private ContentValues createContentValuesFor_createRepository(
			String repositoryFullName) {

		ContentValues values=new ContentValues();
		values.put(KEY_RepositoryFullname, repositoryFullName);
		return values;
	}
	public Cursor fetchRepository() throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE_REPOSITORY, new String[] {
				KEY_RepositoryFullname},
				null, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	// Delete Repository
	public boolean deleteRepository(String repoFullName) {
		return database.delete(DATABASE_TABLE_REPOSITORY, KEY_RepositoryFullname + "='" + repoFullName + "'", null) > 0;
	}

	/**
	 * Deletes All rows in the table
	 */
	public void deleteLD() {
		database.delete(DATABASE_TABLE_LD,  null, null);
	}

	public void deleteHoliday() {
		database.delete(DATABASE_TABLE_HOLIDAY, null, null);
	}

	public void deleteRelatedP() {
		database.delete(DATABASE_TABLE_RELATEDPERSON, null, null);
	}

	public void deleteRepository() {
		database.delete(DATABASE_TABLE_REPOSITORY, null, null);
	}
	public void deleteRecords() {
		database.execSQL("DROP TABLE IF EXISTS LegalDelivery");
	}
	

}
