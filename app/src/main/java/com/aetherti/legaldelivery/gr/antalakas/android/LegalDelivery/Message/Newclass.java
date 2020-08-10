package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery.Message;

import java.util.ArrayList;


public class Newclass {
	
	
	public class Application {
		
		  private String $id;
		  private String LegalDeliveryID;
		  private float JOBNO;
		  private String DATEENTERED;
		  private String DeviceID;
		  private String ClientID;
		  private String ServiceTypeID;
		  private String CASENO;
		  private String LTServiceTypeID;
		  private boolean LTTYPE_3Day;
		  private boolean LTTYPE_5Day;
		  private boolean LTTYPE_10Day;
		  private boolean LTTYPE_15Day;
		  private boolean LTTYPE_30DayTermination;
		  private boolean LTTYPE_Petition;
		  private boolean LTTYPE_PetitionHoldover;
		  private boolean LTTYPE_OtherLT;
		  private boolean LTTYPE_3Day30DayDeptor;
		  private String LTTYPE9DESC;
		  private String LTINDEXNO;
		  private String LTFULLNAME;
		  private String LT1ADDRESS;
		  private String LT1APT;
		  private String LT1CITY;
		  private String LT1DESC;
		  private String LT1STATE;
		  private String LT1ZIP;
		  private String LTSERVED;
		  private float GPSLat1;
		  private float GPSLon1;
		  private float GPSLat2;
		  private float GPSLon2;
		  private float GPSLat3;
		  private float GPSLon3;
		  private String GPSStatus = null;
		  private String LTBIZNAME;
		  private String ServiceResultID;
		  private String ServerID;
		  private String DATEOFSERVICE;
		  private String TIMEOFSERVICE;
		  private String DATEOFFIRSTATTEMPT;
		  private String TIMEOFFIRSTATTEMPT;
		  private String DATEOFSECONDATTEMPT;
		  private String TIMEOFSECONDATTEMPT;
		  private String DATEOFTHIRDATTEMPT = null;
		  private String TIMEOFTHIRDATTEMPT = null;
		  private String GPS_DATEOFSERVICE;
		  private String GPS_TIMEOFSERVICE;
		  private String GPS_DATEOFFIRSTATTEMPT;
		  private String GPS_TIMEOFFIRSTATTEMPT;
		  private String GPS_DATEOFSECONDATTEMPT;
		  private String GPS_TIMEOFSECONDATTEMPT;
		  private String GPS_DATEOFTHIRDATTEMPT = null;
		  private String GPS_TIMEOFTHIRDATTEMPT = null;
		  private String ResultsInputDate;
		  private String DOORLOCK = null;
		  private String PersonSeenSubstitute = null;
		  private String PersonSeenDoeSubstitute;
		  private String PersonSeenPersonal = null;
		  private String PersonsNotSeenPersonal = null;
		  private String CORPRECIPIENT;
		  private String CORPRECIPIENTTITLE;
		  private String RPTLOTHER = null;
		  private boolean CServ_Entry_WALKUP;
		  private boolean CServ_Entry_ELEVAT;
		  private boolean CServ_Entry_STOREFRONT;
		  private boolean CServ_Wall_WHTE;
		  private boolean CServ_Wall_LTBR;
		  private boolean CServ_Wall_BRWN;
		  private boolean CServ_Wall_BLUE;
		  private boolean CServ_Wall_GREN;
		  private boolean CServ_Wall_GRAY;
		  private boolean CServ_Wall_RED;
		  private boolean CServ_Wall_BEIG;
		  private boolean CServ_Wall_BLK;
		  private boolean CServ_Floor_WHTE;
		  private boolean CServ_Floor_LTBR;
		  private boolean CServ_Floor_BRWN;
		  private boolean CServ_Floor_BLUE;
		  private boolean CServ_Floor_GREN;
		  private boolean CServ_Floor_GRAY;
		  private boolean CServ_Floor_RED;
		  private boolean CServ_Floor_BEIG;
		  private boolean CServ_Floor_BLK;
		  private boolean CServ_Floor_TILE;
		  private boolean CServ_Floor_CARP;
		  private boolean CServ_Floor_CMNT;
		  private boolean CServ_Floor_WOOD;
		  private boolean CServ_Floor_TRRZO;
		  private boolean CServ_Door_WHTE;
		  private boolean CServ_Door_LTBR;
		  private boolean CServ_Door_BRWN;
		  private boolean CServ_Door_BLUE;
		  private boolean CServ_Door_GREN;
		  private boolean CServ_Door_GRAY;
		  private boolean CServ_Door_RED;
		  private boolean CServ_Door_BEIG;
		  private boolean CServ_Door_BLK;
		  private boolean CServ_Door_WOOD;
		  private boolean CServ_Door_WOODGRAIN;
		  private boolean CServ_Lock_BELL;
		  private boolean CServ_Lock_PEEP;
		  private boolean CServ_Lock_KNOCK;
		  private boolean CServ_Lock_APT;
		  private boolean CServ_Lock_NMPL;
		  private String OtherCommentsC = null;
		  private boolean PServ_Sex_M;
		  private boolean PServ_Sex_F;
		  private boolean PServ_Skin_WHTE;
		  private boolean PServ_Skin_BLK;
		  private boolean PServ_Skin_BRWN;
		  private boolean PServ_Hair_WHTE;
		  private boolean PServ_Hair_BLK;
		  private boolean PServ_Hair_LTBRN;
		  private boolean PServ_Hair_BRN;
		  private boolean PServ_Hair_GRAY;
		  private boolean PServ_Hair_RED;
		  private boolean PServ_Hair_BALD;
		  private boolean PServ_Age_14_17;
		  private boolean PServ_Age_18_21;
		  private boolean PServ_Age_22_30;
		  private boolean PServ_Age_31_39;
		  private boolean PServ_Age_40_49;
		  private boolean PServ_Age_50_59;
		  private boolean PServ_Age_60_69;
		  private boolean PServ_Age_Over_70;
		  private boolean PServ_Height_Under_5;
		  private boolean PServ_Height_5_53;
		  private boolean PServ_Height_54_58;
		  private boolean PServ_Height_59_6;
		  private boolean PServ_Height_Over_6;
		  private boolean PServ_Weight_0_99;
		  private boolean PServ_Weight_100_130;
		  private boolean PServ_Weight_131_160;
		  private boolean PServ_Weight_161_200;
		  private boolean PServ_Weight_Over_200;
		  private String OtherCommentsP;
		  private boolean FirstAttempt;
		  private boolean SecondAttempt;
		  private boolean ThirdAttempt;
		  private boolean ServiceCompleted;
		  private boolean STND_Summons;
		  private boolean STND_SummonsWONotice;
		  private boolean STND_SummonsWVerifiedComplaint;
		  private boolean STND_SummonsWNoticeAndVerComplaint;
		  private boolean STND_SummonsWEndorsedComplaint;
		  private boolean STND_Citation;
		  private boolean STND_JudicialSubpoena;
		  private boolean STND_JudicialSubpoenaDucestecum;
		  private boolean STND_JudicialSubpoenaAndDucestecum;
		  private boolean STND_Petition;
		  private boolean STND_OrderToShowCause;
		  private boolean STND_OrderStandard;
		  private String STND_TYPE10DESC;
		  private String STND_INDEXNO;
		  private String STND_Court;
		  private String STND_County;
		  private String STND_Plantiff;
		  private String STND_Defendant;
		  private String STND_FULLNAME;
		  private String STND_ADDRESS;
		  private String STND_APT;
		  private String STND_CITY;
		  private String STND_STATE;
		  private String STND_ZIP;
		  private String CreatedByUserId;
		  private String CreatedDate;
		  private String ModifiedByUserId;
		  private String ModifiedDate;
		  private String PetitionNoticeRider;
		  private boolean LTM1IsRRR;
		  private boolean LTM2IsRRR;
		  private boolean LTM3IsRRR;
		  private boolean LTM4IsRRR;
		  private boolean LTM5IsRRR;
		  private boolean LTM6IsRRR;
		  private boolean LTM7IsRRR;
		  private boolean LTM8IsRRR;
		  private boolean LTM9IsRRR;
		  private boolean LTM10IsRRR;
		  private String PostedOnDoor = null;
		  private String LTNoOfMailBox = null;
		  private String WitnessBox_Fee = null;
		  private boolean PServ_Skin_ASN;
		  private String RPTLDELIVEREDTO;
		  private String LTNOTSERVED;
		  private boolean PServ_Hair_Blonde;
		  private String P_Gender;
		  private String P_Skincolor;
		  private String P_Hair;
		  private String P_Age = null;
		  private String P_Height = null;
		  private String P_Weight = null;
		  private String C_Entry;
		  private String C_Wall;
		  private String C_Door;
		  private String C_Floor;
		  private String C_Lock;


		 // Getter Methods 

		  public String get$id() {
		    return $id;
		  }

		  public String getLegalDeliveryID() {
		    return LegalDeliveryID;
		  }

		  public float getJOBNO() {
		    return JOBNO;
		  }

		  public String getDATEENTERED() {
		    return DATEENTERED;
		  }

		  public String getDeviceID() {
		    return DeviceID;
		  }

		  public String getClientID() {
		    return ClientID;
		  }

		  public String getServiceTypeID() {
		    return ServiceTypeID;
		  }

		  public String getCASENO() {
		    return CASENO;
		  }

		  public String getLTServiceTypeID() {
		    return LTServiceTypeID;
		  }

		  public boolean getLTTYPE_3Day() {
		    return LTTYPE_3Day;
		  }

		  public boolean getLTTYPE_5Day() {
		    return LTTYPE_5Day;
		  }

		  public boolean getLTTYPE_10Day() {
		    return LTTYPE_10Day;
		  }

		  public boolean getLTTYPE_15Day() {
		    return LTTYPE_15Day;
		  }

		  public boolean getLTTYPE_30DayTermination() {
		    return LTTYPE_30DayTermination;
		  }

		  public boolean getLTTYPE_Petition() {
		    return LTTYPE_Petition;
		  }

		  public boolean getLTTYPE_PetitionHoldover() {
		    return LTTYPE_PetitionHoldover;
		  }

		  public boolean getLTTYPE_OtherLT() {
		    return LTTYPE_OtherLT;
		  }

		  public boolean getLTTYPE_3Day30DayDeptor() {
		    return LTTYPE_3Day30DayDeptor;
		  }

		  public String getLTTYPE9DESC() {
		    return LTTYPE9DESC;
		  }

		  public String getLTINDEXNO() {
		    return LTINDEXNO;
		  }

		  public String getLTFULLNAME() {
		    return LTFULLNAME;
		  }

		  public String getLT1ADDRESS() {
		    return LT1ADDRESS;
		  }

		  public String getLT1APT() {
		    return LT1APT;
		  }

		  public String getLT1CITY() {
		    return LT1CITY;
		  }

		  public String getLT1DESC() {
		    return LT1DESC;
		  }

		  public String getLT1STATE() {
		    return LT1STATE;
		  }

		  public String getLT1ZIP() {
		    return LT1ZIP;
		  }

		  public String getLTSERVED() {
		    return LTSERVED;
		  }

		  public float getGPSLat1() {
		    return GPSLat1;
		  }

		  public float getGPSLon1() {
		    return GPSLon1;
		  }

		  public float getGPSLat2() {
		    return GPSLat2;
		  }

		  public float getGPSLon2() {
		    return GPSLon2;
		  }

		  public float getGPSLat3() {
		    return GPSLat3;
		  }

		  public float getGPSLon3() {
		    return GPSLon3;
		  }

		  public String getGPSStatus() {
		    return GPSStatus;
		  }

		  public String getLTBIZNAME() {
		    return LTBIZNAME;
		  }

		  public String getServiceResultID() {
		    return ServiceResultID;
		  }

		  public String getServerID() {
		    return ServerID;
		  }

		  public String getDATEOFSERVICE() {
		    return DATEOFSERVICE;
		  }

		  public String getTIMEOFSERVICE() {
		    return TIMEOFSERVICE;
		  }

		  public String getDATEOFFIRSTATTEMPT() {
		    return DATEOFFIRSTATTEMPT;
		  }

		  public String getTIMEOFFIRSTATTEMPT() {
		    return TIMEOFFIRSTATTEMPT;
		  }

		  public String getDATEOFSECONDATTEMPT() {
		    return DATEOFSECONDATTEMPT;
		  }

		  public String getTIMEOFSECONDATTEMPT() {
		    return TIMEOFSECONDATTEMPT;
		  }

		  public String getDATEOFTHIRDATTEMPT() {
		    return DATEOFTHIRDATTEMPT;
		  }

		  public String getTIMEOFTHIRDATTEMPT() {
		    return TIMEOFTHIRDATTEMPT;
		  }

		  public String getGPS_DATEOFSERVICE() {
		    return GPS_DATEOFSERVICE;
		  }

		  public String getGPS_TIMEOFSERVICE() {
		    return GPS_TIMEOFSERVICE;
		  }

		  public String getGPS_DATEOFFIRSTATTEMPT() {
		    return GPS_DATEOFFIRSTATTEMPT;
		  }

		  public String getGPS_TIMEOFFIRSTATTEMPT() {
		    return GPS_TIMEOFFIRSTATTEMPT;
		  }

		  public String getGPS_DATEOFSECONDATTEMPT() {
		    return GPS_DATEOFSECONDATTEMPT;
		  }

		  public String getGPS_TIMEOFSECONDATTEMPT() {
		    return GPS_TIMEOFSECONDATTEMPT;
		  }

		  public String getGPS_DATEOFTHIRDATTEMPT() {
		    return GPS_DATEOFTHIRDATTEMPT;
		  }

		  public String getGPS_TIMEOFTHIRDATTEMPT() {
		    return GPS_TIMEOFTHIRDATTEMPT;
		  }

		  public String getResultsInputDate() {
		    return ResultsInputDate;
		  }

		  public String getDOORLOCK() {
		    return DOORLOCK;
		  }

		  public String getPersonSeenSubstitute() {
		    return PersonSeenSubstitute;
		  }

		  public String getPersonSeenDoeSubstitute() {
		    return PersonSeenDoeSubstitute;
		  }

		  public String getPersonSeenPersonal() {
		    return PersonSeenPersonal;
		  }

		  public String getPersonsNotSeenPersonal() {
		    return PersonsNotSeenPersonal;
		  }

		  public String getCORPRECIPIENT() {
		    return CORPRECIPIENT;
		  }

		  public String getCORPRECIPIENTTITLE() {
		    return CORPRECIPIENTTITLE;
		  }

		  public String getRPTLOTHER() {
		    return RPTLOTHER;
		  }

		  public boolean getCServ_Entry_WALKUP() {
		    return CServ_Entry_WALKUP;
		  }

		  public boolean getCServ_Entry_ELEVAT() {
		    return CServ_Entry_ELEVAT;
		  }

		  public boolean getCServ_Entry_STOREFRONT() {
		    return CServ_Entry_STOREFRONT;
		  }

		  public boolean getCServ_Wall_WHTE() {
		    return CServ_Wall_WHTE;
		  }

		  public boolean getCServ_Wall_LTBR() {
		    return CServ_Wall_LTBR;
		  }

		  public boolean getCServ_Wall_BRWN() {
		    return CServ_Wall_BRWN;
		  }

		  public boolean getCServ_Wall_BLUE() {
		    return CServ_Wall_BLUE;
		  }

		  public boolean getCServ_Wall_GREN() {
		    return CServ_Wall_GREN;
		  }

		  public boolean getCServ_Wall_GRAY() {
		    return CServ_Wall_GRAY;
		  }

		  public boolean getCServ_Wall_RED() {
		    return CServ_Wall_RED;
		  }

		  public boolean getCServ_Wall_BEIG() {
		    return CServ_Wall_BEIG;
		  }

		  public boolean getCServ_Wall_BLK() {
		    return CServ_Wall_BLK;
		  }

		  public boolean getCServ_Floor_WHTE() {
		    return CServ_Floor_WHTE;
		  }

		  public boolean getCServ_Floor_LTBR() {
		    return CServ_Floor_LTBR;
		  }

		  public boolean getCServ_Floor_BRWN() {
		    return CServ_Floor_BRWN;
		  }

		  public boolean getCServ_Floor_BLUE() {
		    return CServ_Floor_BLUE;
		  }

		  public boolean getCServ_Floor_GREN() {
		    return CServ_Floor_GREN;
		  }

		  public boolean getCServ_Floor_GRAY() {
		    return CServ_Floor_GRAY;
		  }

		  public boolean getCServ_Floor_RED() {
		    return CServ_Floor_RED;
		  }

		  public boolean getCServ_Floor_BEIG() {
		    return CServ_Floor_BEIG;
		  }

		  public boolean getCServ_Floor_BLK() {
		    return CServ_Floor_BLK;
		  }

		  public boolean getCServ_Floor_TILE() {
		    return CServ_Floor_TILE;
		  }

		  public boolean getCServ_Floor_CARP() {
		    return CServ_Floor_CARP;
		  }

		  public boolean getCServ_Floor_CMNT() {
		    return CServ_Floor_CMNT;
		  }

		  public boolean getCServ_Floor_WOOD() {
		    return CServ_Floor_WOOD;
		  }

		  public boolean getCServ_Floor_TRRZO() {
		    return CServ_Floor_TRRZO;
		  }

		  public boolean getCServ_Door_WHTE() {
		    return CServ_Door_WHTE;
		  }

		  public boolean getCServ_Door_LTBR() {
		    return CServ_Door_LTBR;
		  }

		  public boolean getCServ_Door_BRWN() {
		    return CServ_Door_BRWN;
		  }

		  public boolean getCServ_Door_BLUE() {
		    return CServ_Door_BLUE;
		  }

		  public boolean getCServ_Door_GREN() {
		    return CServ_Door_GREN;
		  }

		  public boolean getCServ_Door_GRAY() {
		    return CServ_Door_GRAY;
		  }

		  public boolean getCServ_Door_RED() {
		    return CServ_Door_RED;
		  }

		  public boolean getCServ_Door_BEIG() {
		    return CServ_Door_BEIG;
		  }

		  public boolean getCServ_Door_BLK() {
		    return CServ_Door_BLK;
		  }

		  public boolean getCServ_Door_WOOD() {
		    return CServ_Door_WOOD;
		  }

		  public boolean getCServ_Door_WOODGRAIN() {
		    return CServ_Door_WOODGRAIN;
		  }

		  public boolean getCServ_Lock_BELL() {
		    return CServ_Lock_BELL;
		  }

		  public boolean getCServ_Lock_PEEP() {
		    return CServ_Lock_PEEP;
		  }

		  public boolean getCServ_Lock_KNOCK() {
		    return CServ_Lock_KNOCK;
		  }

		  public boolean getCServ_Lock_APT() {
		    return CServ_Lock_APT;
		  }

		  public boolean getCServ_Lock_NMPL() {
		    return CServ_Lock_NMPL;
		  }

		  public String getOtherCommentsC() {
		    return OtherCommentsC;
		  }

		  public boolean getPServ_Sex_M() {
		    return PServ_Sex_M;
		  }

		  public boolean getPServ_Sex_F() {
		    return PServ_Sex_F;
		  }

		  public boolean getPServ_Skin_WHTE() {
		    return PServ_Skin_WHTE;
		  }

		  public boolean getPServ_Skin_BLK() {
		    return PServ_Skin_BLK;
		  }

		  public boolean getPServ_Skin_BRWN() {
		    return PServ_Skin_BRWN;
		  }

		  public boolean getPServ_Hair_WHTE() {
		    return PServ_Hair_WHTE;
		  }

		  public boolean getPServ_Hair_BLK() {
		    return PServ_Hair_BLK;
		  }

		  public boolean getPServ_Hair_LTBRN() {
		    return PServ_Hair_LTBRN;
		  }

		  public boolean getPServ_Hair_BRN() {
		    return PServ_Hair_BRN;
		  }

		  public boolean getPServ_Hair_GRAY() {
		    return PServ_Hair_GRAY;
		  }

		  public boolean getPServ_Hair_RED() {
		    return PServ_Hair_RED;
		  }

		  public boolean getPServ_Hair_BALD() {
		    return PServ_Hair_BALD;
		  }

		  public boolean getPServ_Age_14_17() {
		    return PServ_Age_14_17;
		  }

		  public boolean getPServ_Age_18_21() {
		    return PServ_Age_18_21;
		  }

		  public boolean getPServ_Age_22_30() {
		    return PServ_Age_22_30;
		  }

		  public boolean getPServ_Age_31_39() {
		    return PServ_Age_31_39;
		  }

		  public boolean getPServ_Age_40_49() {
		    return PServ_Age_40_49;
		  }

		  public boolean getPServ_Age_50_59() {
		    return PServ_Age_50_59;
		  }

		  public boolean getPServ_Age_60_69() {
		    return PServ_Age_60_69;
		  }

		  public boolean getPServ_Age_Over_70() {
		    return PServ_Age_Over_70;
		  }

		  public boolean getPServ_Height_Under_5() {
		    return PServ_Height_Under_5;
		  }

		  public boolean getPServ_Height_5_53() {
		    return PServ_Height_5_53;
		  }

		  public boolean getPServ_Height_54_58() {
		    return PServ_Height_54_58;
		  }

		  public boolean getPServ_Height_59_6() {
		    return PServ_Height_59_6;
		  }

		  public boolean getPServ_Height_Over_6() {
		    return PServ_Height_Over_6;
		  }

		  public boolean getPServ_Weight_0_99() {
		    return PServ_Weight_0_99;
		  }

		  public boolean getPServ_Weight_100_130() {
		    return PServ_Weight_100_130;
		  }

		  public boolean getPServ_Weight_131_160() {
		    return PServ_Weight_131_160;
		  }

		  public boolean getPServ_Weight_161_200() {
		    return PServ_Weight_161_200;
		  }

		  public boolean getPServ_Weight_Over_200() {
		    return PServ_Weight_Over_200;
		  }

		  public String getOtherCommentsP() {
		    return OtherCommentsP;
		  }

		  public boolean getFirstAttempt() {
		    return FirstAttempt;
		  }

		  public boolean getSecondAttempt() {
		    return SecondAttempt;
		  }

		  public boolean getThirdAttempt() {
		    return ThirdAttempt;
		  }

		  public boolean getServiceCompleted() {
		    return ServiceCompleted;
		  }

		  public boolean getSTND_Summons() {
		    return STND_Summons;
		  }

		  public boolean getSTND_SummonsWONotice() {
		    return STND_SummonsWONotice;
		  }

		  public boolean getSTND_SummonsWVerifiedComplaint() {
		    return STND_SummonsWVerifiedComplaint;
		  }

		  public boolean getSTND_SummonsWNoticeAndVerComplaint() {
		    return STND_SummonsWNoticeAndVerComplaint;
		  }

		  public boolean getSTND_SummonsWEndorsedComplaint() {
		    return STND_SummonsWEndorsedComplaint;
		  }

		  public boolean getSTND_Citation() {
		    return STND_Citation;
		  }

		  public boolean getSTND_JudicialSubpoena() {
		    return STND_JudicialSubpoena;
		  }

		  public boolean getSTND_JudicialSubpoenaDucestecum() {
		    return STND_JudicialSubpoenaDucestecum;
		  }

		  public boolean getSTND_JudicialSubpoenaAndDucestecum() {
		    return STND_JudicialSubpoenaAndDucestecum;
		  }

		  public boolean getSTND_Petition() {
		    return STND_Petition;
		  }

		  public boolean getSTND_OrderToShowCause() {
		    return STND_OrderToShowCause;
		  }

		  public boolean getSTND_OrderStandard() {
		    return STND_OrderStandard;
		  }

		  public String getSTND_TYPE10DESC() {
		    return STND_TYPE10DESC;
		  }

		  public String getSTND_INDEXNO() {
		    return STND_INDEXNO;
		  }

		  public String getSTND_Court() {
		    return STND_Court;
		  }

		  public String getSTND_County() {
		    return STND_County;
		  }

		  public String getSTND_Plantiff() {
		    return STND_Plantiff;
		  }

		  public String getSTND_Defendant() {
		    return STND_Defendant;
		  }

		  public String getSTND_FULLNAME() {
		    return STND_FULLNAME;
		  }

		  public String getSTND_ADDRESS() {
		    return STND_ADDRESS;
		  }

		  public String getSTND_APT() {
		    return STND_APT;
		  }

		  public String getSTND_CITY() {
		    return STND_CITY;
		  }

		  public String getSTND_STATE() {
		    return STND_STATE;
		  }

		  public String getSTND_ZIP() {
		    return STND_ZIP;
		  }

		  public String getCreatedByUserId() {
		    return CreatedByUserId;
		  }

		  public String getCreatedDate() {
		    return CreatedDate;
		  }

		  public String getModifiedByUserId() {
		    return ModifiedByUserId;
		  }

		  public String getModifiedDate() {
		    return ModifiedDate;
		  }

		  public String getPetitionNoticeRider() {
		    return PetitionNoticeRider;
		  }

		  public boolean getLTM1IsRRR() {
		    return LTM1IsRRR;
		  }

		  public boolean getLTM2IsRRR() {
		    return LTM2IsRRR;
		  }

		  public boolean getLTM3IsRRR() {
		    return LTM3IsRRR;
		  }

		  public boolean getLTM4IsRRR() {
		    return LTM4IsRRR;
		  }

		  public boolean getLTM5IsRRR() {
		    return LTM5IsRRR;
		  }

		  public boolean getLTM6IsRRR() {
		    return LTM6IsRRR;
		  }

		  public boolean getLTM7IsRRR() {
		    return LTM7IsRRR;
		  }

		  public boolean getLTM8IsRRR() {
		    return LTM8IsRRR;
		  }

		  public boolean getLTM9IsRRR() {
		    return LTM9IsRRR;
		  }

		  public boolean getLTM10IsRRR() {
		    return LTM10IsRRR;
		  }

		  public String getPostedOnDoor() {
		    return PostedOnDoor;
		  }

		  public String getLTNoOfMailBox() {
		    return LTNoOfMailBox;
		  }

		  public String getWitnessBox_Fee() {
		    return WitnessBox_Fee;
		  }

		  public boolean getPServ_Skin_ASN() {
		    return PServ_Skin_ASN;
		  }

		  public String getRPTLDELIVEREDTO() {
		    return RPTLDELIVEREDTO;
		  }

		  public String getLTNOTSERVED() {
		    return LTNOTSERVED;
		  }

		  public boolean getPServ_Hair_Blonde() {
		    return PServ_Hair_Blonde;
		  }

		  public String getP_Gender() {
		    return P_Gender;
		  }

		  public String getP_Skincolor() {
		    return P_Skincolor;
		  }

		  public String getP_Hair() {
		    return P_Hair;
		  }

		  public String getP_Age() {
		    return P_Age;
		  }

		  public String getP_Height() {
		    return P_Height;
		  }

		  public String getP_Weight() {
		    return P_Weight;
		  }

		  public String getC_Entry() {
		    return C_Entry;
		  }

		  public String getC_Wall() {
		    return C_Wall;
		  }

		  public String getC_Door() {
		    return C_Door;
		  }

		  public String getC_Floor() {
		    return C_Floor;
		  }

		  public String getC_Lock() {
		    return C_Lock;
		  }

		 // Setter Methods 

		  public void set$id( String $id ) {
		    this.$id = $id;
		  }

		  public void setLegalDeliveryID( String LegalDeliveryID ) {
		    this.LegalDeliveryID = LegalDeliveryID;
		  }

		  public void setJOBNO( float JOBNO ) {
		    this.JOBNO = JOBNO;
		  }

		  public void setDATEENTERED( String DATEENTERED ) {
		    this.DATEENTERED = DATEENTERED;
		  }

		  public void setDeviceID( String DeviceID ) {
		    this.DeviceID = DeviceID;
		  }

		  public void setClientID( String ClientID ) {
		    this.ClientID = ClientID;
		  }

		  public void setServiceTypeID( String ServiceTypeID ) {
		    this.ServiceTypeID = ServiceTypeID;
		  }

		  public void setCASENO( String CASENO ) {
		    this.CASENO = CASENO;
		  }

		  public void setLTServiceTypeID( String LTServiceTypeID ) {
		    this.LTServiceTypeID = LTServiceTypeID;
		  }

		  public void setLTTYPE_3Day( boolean LTTYPE_3Day ) {
		    this.LTTYPE_3Day = LTTYPE_3Day;
		  }

		  public void setLTTYPE_5Day( boolean LTTYPE_5Day ) {
		    this.LTTYPE_5Day = LTTYPE_5Day;
		  }

		  public void setLTTYPE_10Day( boolean LTTYPE_10Day ) {
		    this.LTTYPE_10Day = LTTYPE_10Day;
		  }

		  public void setLTTYPE_15Day( boolean LTTYPE_15Day ) {
		    this.LTTYPE_15Day = LTTYPE_15Day;
		  }

		  public void setLTTYPE_30DayTermination( boolean LTTYPE_30DayTermination ) {
		    this.LTTYPE_30DayTermination = LTTYPE_30DayTermination;
		  }

		  public void setLTTYPE_Petition( boolean LTTYPE_Petition ) {
		    this.LTTYPE_Petition = LTTYPE_Petition;
		  }

		  public void setLTTYPE_PetitionHoldover( boolean LTTYPE_PetitionHoldover ) {
		    this.LTTYPE_PetitionHoldover = LTTYPE_PetitionHoldover;
		  }

		  public void setLTTYPE_OtherLT( boolean LTTYPE_OtherLT ) {
		    this.LTTYPE_OtherLT = LTTYPE_OtherLT;
		  }

		  public void setLTTYPE_3Day30DayDeptor( boolean LTTYPE_3Day30DayDeptor ) {
		    this.LTTYPE_3Day30DayDeptor = LTTYPE_3Day30DayDeptor;
		  }

		  public void setLTTYPE9DESC( String LTTYPE9DESC ) {
		    this.LTTYPE9DESC = LTTYPE9DESC;
		  }

		  public void setLTINDEXNO( String LTINDEXNO ) {
		    this.LTINDEXNO = LTINDEXNO;
		  }

		  public void setLTFULLNAME( String LTFULLNAME ) {
		    this.LTFULLNAME = LTFULLNAME;
		  }

		  public void setLT1ADDRESS( String LT1ADDRESS ) {
		    this.LT1ADDRESS = LT1ADDRESS;
		  }

		  public void setLT1APT( String LT1APT ) {
		    this.LT1APT = LT1APT;
		  }

		  public void setLT1CITY( String LT1CITY ) {
		    this.LT1CITY = LT1CITY;
		  }

		  public void setLT1DESC( String LT1DESC ) {
		    this.LT1DESC = LT1DESC;
		  }

		  public void setLT1STATE( String LT1STATE ) {
		    this.LT1STATE = LT1STATE;
		  }

		  public void setLT1ZIP( String LT1ZIP ) {
		    this.LT1ZIP = LT1ZIP;
		  }

		  public void setLTSERVED( String LTSERVED ) {
		    this.LTSERVED = LTSERVED;
		  }

		  public void setGPSLat1( float GPSLat1 ) {
		    this.GPSLat1 = GPSLat1;
		  }

		  public void setGPSLon1( float GPSLon1 ) {
		    this.GPSLon1 = GPSLon1;
		  }

		  public void setGPSLat2( float GPSLat2 ) {
		    this.GPSLat2 = GPSLat2;
		  }

		  public void setGPSLon2( float GPSLon2 ) {
		    this.GPSLon2 = GPSLon2;
		  }

		  public void setGPSLat3( float GPSLat3 ) {
		    this.GPSLat3 = GPSLat3;
		  }

		  public void setGPSLon3( float GPSLon3 ) {
		    this.GPSLon3 = GPSLon3;
		  }

		  public void setGPSStatus( String GPSStatus ) {
		    this.GPSStatus = GPSStatus;
		  }

		  public void setLTBIZNAME( String LTBIZNAME ) {
		    this.LTBIZNAME = LTBIZNAME;
		  }

		  public void setServiceResultID( String ServiceResultID ) {
		    this.ServiceResultID = ServiceResultID;
		  }

		  public void setServerID( String ServerID ) {
		    this.ServerID = ServerID;
		  }

		  public void setDATEOFSERVICE( String DATEOFSERVICE ) {
		    this.DATEOFSERVICE = DATEOFSERVICE;
		  }

		  public void setTIMEOFSERVICE( String TIMEOFSERVICE ) {
		    this.TIMEOFSERVICE = TIMEOFSERVICE;
		  }

		  public void setDATEOFFIRSTATTEMPT( String DATEOFFIRSTATTEMPT ) {
		    this.DATEOFFIRSTATTEMPT = DATEOFFIRSTATTEMPT;
		  }

		  public void setTIMEOFFIRSTATTEMPT( String TIMEOFFIRSTATTEMPT ) {
		    this.TIMEOFFIRSTATTEMPT = TIMEOFFIRSTATTEMPT;
		  }

		  public void setDATEOFSECONDATTEMPT( String DATEOFSECONDATTEMPT ) {
		    this.DATEOFSECONDATTEMPT = DATEOFSECONDATTEMPT;
		  }

		  public void setTIMEOFSECONDATTEMPT( String TIMEOFSECONDATTEMPT ) {
		    this.TIMEOFSECONDATTEMPT = TIMEOFSECONDATTEMPT;
		  }

		  public void setDATEOFTHIRDATTEMPT( String DATEOFTHIRDATTEMPT ) {
		    this.DATEOFTHIRDATTEMPT = DATEOFTHIRDATTEMPT;
		  }

		  public void setTIMEOFTHIRDATTEMPT( String TIMEOFTHIRDATTEMPT ) {
		    this.TIMEOFTHIRDATTEMPT = TIMEOFTHIRDATTEMPT;
		  }

		  public void setGPS_DATEOFSERVICE( String GPS_DATEOFSERVICE ) {
		    this.GPS_DATEOFSERVICE = GPS_DATEOFSERVICE;
		  }

		  public void setGPS_TIMEOFSERVICE( String GPS_TIMEOFSERVICE ) {
		    this.GPS_TIMEOFSERVICE = GPS_TIMEOFSERVICE;
		  }

		  public void setGPS_DATEOFFIRSTATTEMPT( String GPS_DATEOFFIRSTATTEMPT ) {
		    this.GPS_DATEOFFIRSTATTEMPT = GPS_DATEOFFIRSTATTEMPT;
		  }

		  public void setGPS_TIMEOFFIRSTATTEMPT( String GPS_TIMEOFFIRSTATTEMPT ) {
		    this.GPS_TIMEOFFIRSTATTEMPT = GPS_TIMEOFFIRSTATTEMPT;
		  }

		  public void setGPS_DATEOFSECONDATTEMPT( String GPS_DATEOFSECONDATTEMPT ) {
		    this.GPS_DATEOFSECONDATTEMPT = GPS_DATEOFSECONDATTEMPT;
		  }

		  public void setGPS_TIMEOFSECONDATTEMPT( String GPS_TIMEOFSECONDATTEMPT ) {
		    this.GPS_TIMEOFSECONDATTEMPT = GPS_TIMEOFSECONDATTEMPT;
		  }

		  public void setGPS_DATEOFTHIRDATTEMPT( String GPS_DATEOFTHIRDATTEMPT ) {
		    this.GPS_DATEOFTHIRDATTEMPT = GPS_DATEOFTHIRDATTEMPT;
		  }

		  public void setGPS_TIMEOFTHIRDATTEMPT( String GPS_TIMEOFTHIRDATTEMPT ) {
		    this.GPS_TIMEOFTHIRDATTEMPT = GPS_TIMEOFTHIRDATTEMPT;
		  }

		  public void setResultsInputDate( String ResultsInputDate ) {
		    this.ResultsInputDate = ResultsInputDate;
		  }

		  public void setDOORLOCK( String DOORLOCK ) {
		    this.DOORLOCK = DOORLOCK;
		  }

		  public void setPersonSeenSubstitute( String PersonSeenSubstitute ) {
		    this.PersonSeenSubstitute = PersonSeenSubstitute;
		  }

		  public void setPersonSeenDoeSubstitute( String PersonSeenDoeSubstitute ) {
		    this.PersonSeenDoeSubstitute = PersonSeenDoeSubstitute;
		  }

		  public void setPersonSeenPersonal( String PersonSeenPersonal ) {
		    this.PersonSeenPersonal = PersonSeenPersonal;
		  }

		  public void setPersonsNotSeenPersonal( String PersonsNotSeenPersonal ) {
		    this.PersonsNotSeenPersonal = PersonsNotSeenPersonal;
		  }

		  public void setCORPRECIPIENT( String CORPRECIPIENT ) {
		    this.CORPRECIPIENT = CORPRECIPIENT;
		  }

		  public void setCORPRECIPIENTTITLE( String CORPRECIPIENTTITLE ) {
		    this.CORPRECIPIENTTITLE = CORPRECIPIENTTITLE;
		  }

		  public void setRPTLOTHER( String RPTLOTHER ) {
		    this.RPTLOTHER = RPTLOTHER;
		  }

		  public void setCServ_Entry_WALKUP( boolean CServ_Entry_WALKUP ) {
		    this.CServ_Entry_WALKUP = CServ_Entry_WALKUP;
		  }

		  public void setCServ_Entry_ELEVAT( boolean CServ_Entry_ELEVAT ) {
		    this.CServ_Entry_ELEVAT = CServ_Entry_ELEVAT;
		  }

		  public void setCServ_Entry_STOREFRONT( boolean CServ_Entry_STOREFRONT ) {
		    this.CServ_Entry_STOREFRONT = CServ_Entry_STOREFRONT;
		  }

		  public void setCServ_Wall_WHTE( boolean CServ_Wall_WHTE ) {
		    this.CServ_Wall_WHTE = CServ_Wall_WHTE;
		  }

		  public void setCServ_Wall_LTBR( boolean CServ_Wall_LTBR ) {
		    this.CServ_Wall_LTBR = CServ_Wall_LTBR;
		  }

		  public void setCServ_Wall_BRWN( boolean CServ_Wall_BRWN ) {
		    this.CServ_Wall_BRWN = CServ_Wall_BRWN;
		  }

		  public void setCServ_Wall_BLUE( boolean CServ_Wall_BLUE ) {
		    this.CServ_Wall_BLUE = CServ_Wall_BLUE;
		  }

		  public void setCServ_Wall_GREN( boolean CServ_Wall_GREN ) {
		    this.CServ_Wall_GREN = CServ_Wall_GREN;
		  }

		  public void setCServ_Wall_GRAY( boolean CServ_Wall_GRAY ) {
		    this.CServ_Wall_GRAY = CServ_Wall_GRAY;
		  }

		  public void setCServ_Wall_RED( boolean CServ_Wall_RED ) {
		    this.CServ_Wall_RED = CServ_Wall_RED;
		  }

		  public void setCServ_Wall_BEIG( boolean CServ_Wall_BEIG ) {
		    this.CServ_Wall_BEIG = CServ_Wall_BEIG;
		  }

		  public void setCServ_Wall_BLK( boolean CServ_Wall_BLK ) {
		    this.CServ_Wall_BLK = CServ_Wall_BLK;
		  }

		  public void setCServ_Floor_WHTE( boolean CServ_Floor_WHTE ) {
		    this.CServ_Floor_WHTE = CServ_Floor_WHTE;
		  }

		  public void setCServ_Floor_LTBR( boolean CServ_Floor_LTBR ) {
		    this.CServ_Floor_LTBR = CServ_Floor_LTBR;
		  }

		  public void setCServ_Floor_BRWN( boolean CServ_Floor_BRWN ) {
		    this.CServ_Floor_BRWN = CServ_Floor_BRWN;
		  }

		  public void setCServ_Floor_BLUE( boolean CServ_Floor_BLUE ) {
		    this.CServ_Floor_BLUE = CServ_Floor_BLUE;
		  }

		  public void setCServ_Floor_GREN( boolean CServ_Floor_GREN ) {
		    this.CServ_Floor_GREN = CServ_Floor_GREN;
		  }

		  public void setCServ_Floor_GRAY( boolean CServ_Floor_GRAY ) {
		    this.CServ_Floor_GRAY = CServ_Floor_GRAY;
		  }

		  public void setCServ_Floor_RED( boolean CServ_Floor_RED ) {
		    this.CServ_Floor_RED = CServ_Floor_RED;
		  }

		  public void setCServ_Floor_BEIG( boolean CServ_Floor_BEIG ) {
		    this.CServ_Floor_BEIG = CServ_Floor_BEIG;
		  }

		  public void setCServ_Floor_BLK( boolean CServ_Floor_BLK ) {
		    this.CServ_Floor_BLK = CServ_Floor_BLK;
		  }

		  public void setCServ_Floor_TILE( boolean CServ_Floor_TILE ) {
		    this.CServ_Floor_TILE = CServ_Floor_TILE;
		  }

		  public void setCServ_Floor_CARP( boolean CServ_Floor_CARP ) {
		    this.CServ_Floor_CARP = CServ_Floor_CARP;
		  }

		  public void setCServ_Floor_CMNT( boolean CServ_Floor_CMNT ) {
		    this.CServ_Floor_CMNT = CServ_Floor_CMNT;
		  }

		  public void setCServ_Floor_WOOD( boolean CServ_Floor_WOOD ) {
		    this.CServ_Floor_WOOD = CServ_Floor_WOOD;
		  }

		  public void setCServ_Floor_TRRZO( boolean CServ_Floor_TRRZO ) {
		    this.CServ_Floor_TRRZO = CServ_Floor_TRRZO;
		  }

		  public void setCServ_Door_WHTE( boolean CServ_Door_WHTE ) {
		    this.CServ_Door_WHTE = CServ_Door_WHTE;
		  }

		  public void setCServ_Door_LTBR( boolean CServ_Door_LTBR ) {
		    this.CServ_Door_LTBR = CServ_Door_LTBR;
		  }

		  public void setCServ_Door_BRWN( boolean CServ_Door_BRWN ) {
		    this.CServ_Door_BRWN = CServ_Door_BRWN;
		  }

		  public void setCServ_Door_BLUE( boolean CServ_Door_BLUE ) {
		    this.CServ_Door_BLUE = CServ_Door_BLUE;
		  }

		  public void setCServ_Door_GREN( boolean CServ_Door_GREN ) {
		    this.CServ_Door_GREN = CServ_Door_GREN;
		  }

		  public void setCServ_Door_GRAY( boolean CServ_Door_GRAY ) {
		    this.CServ_Door_GRAY = CServ_Door_GRAY;
		  }

		  public void setCServ_Door_RED( boolean CServ_Door_RED ) {
		    this.CServ_Door_RED = CServ_Door_RED;
		  }

		  public void setCServ_Door_BEIG( boolean CServ_Door_BEIG ) {
		    this.CServ_Door_BEIG = CServ_Door_BEIG;
		  }

		  public void setCServ_Door_BLK( boolean CServ_Door_BLK ) {
		    this.CServ_Door_BLK = CServ_Door_BLK;
		  }

		  public void setCServ_Door_WOOD( boolean CServ_Door_WOOD ) {
		    this.CServ_Door_WOOD = CServ_Door_WOOD;
		  }

		  public void setCServ_Door_WOODGRAIN( boolean CServ_Door_WOODGRAIN ) {
		    this.CServ_Door_WOODGRAIN = CServ_Door_WOODGRAIN;
		  }

		  public void setCServ_Lock_BELL( boolean CServ_Lock_BELL ) {
		    this.CServ_Lock_BELL = CServ_Lock_BELL;
		  }

		  public void setCServ_Lock_PEEP( boolean CServ_Lock_PEEP ) {
		    this.CServ_Lock_PEEP = CServ_Lock_PEEP;
		  }

		  public void setCServ_Lock_KNOCK( boolean CServ_Lock_KNOCK ) {
		    this.CServ_Lock_KNOCK = CServ_Lock_KNOCK;
		  }

		  public void setCServ_Lock_APT( boolean CServ_Lock_APT ) {
		    this.CServ_Lock_APT = CServ_Lock_APT;
		  }

		  public void setCServ_Lock_NMPL( boolean CServ_Lock_NMPL ) {
		    this.CServ_Lock_NMPL = CServ_Lock_NMPL;
		  }

		  public void setOtherCommentsC( String OtherCommentsC ) {
		    this.OtherCommentsC = OtherCommentsC;
		  }

		  public void setPServ_Sex_M( boolean PServ_Sex_M ) {
		    this.PServ_Sex_M = PServ_Sex_M;
		  }

		  public void setPServ_Sex_F( boolean PServ_Sex_F ) {
		    this.PServ_Sex_F = PServ_Sex_F;
		  }

		  public void setPServ_Skin_WHTE( boolean PServ_Skin_WHTE ) {
		    this.PServ_Skin_WHTE = PServ_Skin_WHTE;
		  }

		  public void setPServ_Skin_BLK( boolean PServ_Skin_BLK ) {
		    this.PServ_Skin_BLK = PServ_Skin_BLK;
		  }

		  public void setPServ_Skin_BRWN( boolean PServ_Skin_BRWN ) {
		    this.PServ_Skin_BRWN = PServ_Skin_BRWN;
		  }

		  public void setPServ_Hair_WHTE( boolean PServ_Hair_WHTE ) {
		    this.PServ_Hair_WHTE = PServ_Hair_WHTE;
		  }

		  public void setPServ_Hair_BLK( boolean PServ_Hair_BLK ) {
		    this.PServ_Hair_BLK = PServ_Hair_BLK;
		  }

		  public void setPServ_Hair_LTBRN( boolean PServ_Hair_LTBRN ) {
		    this.PServ_Hair_LTBRN = PServ_Hair_LTBRN;
		  }

		  public void setPServ_Hair_BRN( boolean PServ_Hair_BRN ) {
		    this.PServ_Hair_BRN = PServ_Hair_BRN;
		  }

		  public void setPServ_Hair_GRAY( boolean PServ_Hair_GRAY ) {
		    this.PServ_Hair_GRAY = PServ_Hair_GRAY;
		  }

		  public void setPServ_Hair_RED( boolean PServ_Hair_RED ) {
		    this.PServ_Hair_RED = PServ_Hair_RED;
		  }

		  public void setPServ_Hair_BALD( boolean PServ_Hair_BALD ) {
		    this.PServ_Hair_BALD = PServ_Hair_BALD;
		  }

		  public void setPServ_Age_14_17( boolean PServ_Age_14_17 ) {
		    this.PServ_Age_14_17 = PServ_Age_14_17;
		  }

		  public void setPServ_Age_18_21( boolean PServ_Age_18_21 ) {
		    this.PServ_Age_18_21 = PServ_Age_18_21;
		  }

		  public void setPServ_Age_22_30( boolean PServ_Age_22_30 ) {
		    this.PServ_Age_22_30 = PServ_Age_22_30;
		  }

		  public void setPServ_Age_31_39( boolean PServ_Age_31_39 ) {
		    this.PServ_Age_31_39 = PServ_Age_31_39;
		  }

		  public void setPServ_Age_40_49( boolean PServ_Age_40_49 ) {
		    this.PServ_Age_40_49 = PServ_Age_40_49;
		  }

		  public void setPServ_Age_50_59( boolean PServ_Age_50_59 ) {
		    this.PServ_Age_50_59 = PServ_Age_50_59;
		  }

		  public void setPServ_Age_60_69( boolean PServ_Age_60_69 ) {
		    this.PServ_Age_60_69 = PServ_Age_60_69;
		  }

		  public void setPServ_Age_Over_70( boolean PServ_Age_Over_70 ) {
		    this.PServ_Age_Over_70 = PServ_Age_Over_70;
		  }

		  public void setPServ_Height_Under_5( boolean PServ_Height_Under_5 ) {
		    this.PServ_Height_Under_5 = PServ_Height_Under_5;
		  }

		  public void setPServ_Height_5_53( boolean PServ_Height_5_53 ) {
		    this.PServ_Height_5_53 = PServ_Height_5_53;
		  }

		  public void setPServ_Height_54_58( boolean PServ_Height_54_58 ) {
		    this.PServ_Height_54_58 = PServ_Height_54_58;
		  }

		  public void setPServ_Height_59_6( boolean PServ_Height_59_6 ) {
		    this.PServ_Height_59_6 = PServ_Height_59_6;
		  }

		  public void setPServ_Height_Over_6( boolean PServ_Height_Over_6 ) {
		    this.PServ_Height_Over_6 = PServ_Height_Over_6;
		  }

		  public void setPServ_Weight_0_99( boolean PServ_Weight_0_99 ) {
		    this.PServ_Weight_0_99 = PServ_Weight_0_99;
		  }

		  public void setPServ_Weight_100_130( boolean PServ_Weight_100_130 ) {
		    this.PServ_Weight_100_130 = PServ_Weight_100_130;
		  }

		  public void setPServ_Weight_131_160( boolean PServ_Weight_131_160 ) {
		    this.PServ_Weight_131_160 = PServ_Weight_131_160;
		  }

		  public void setPServ_Weight_161_200( boolean PServ_Weight_161_200 ) {
		    this.PServ_Weight_161_200 = PServ_Weight_161_200;
		  }

		  public void setPServ_Weight_Over_200( boolean PServ_Weight_Over_200 ) {
		    this.PServ_Weight_Over_200 = PServ_Weight_Over_200;
		  }

		  public void setOtherCommentsP( String OtherCommentsP ) {
		    this.OtherCommentsP = OtherCommentsP;
		  }

		  public void setFirstAttempt( boolean FirstAttempt ) {
		    this.FirstAttempt = FirstAttempt;
		  }

		  public void setSecondAttempt( boolean SecondAttempt ) {
		    this.SecondAttempt = SecondAttempt;
		  }

		  public void setThirdAttempt( boolean ThirdAttempt ) {
		    this.ThirdAttempt = ThirdAttempt;
		  }

		  public void setServiceCompleted( boolean ServiceCompleted ) {
		    this.ServiceCompleted = ServiceCompleted;
		  }

		  public void setSTND_Summons( boolean STND_Summons ) {
		    this.STND_Summons = STND_Summons;
		  }

		  public void setSTND_SummonsWONotice( boolean STND_SummonsWONotice ) {
		    this.STND_SummonsWONotice = STND_SummonsWONotice;
		  }

		  public void setSTND_SummonsWVerifiedComplaint( boolean STND_SummonsWVerifiedComplaint ) {
		    this.STND_SummonsWVerifiedComplaint = STND_SummonsWVerifiedComplaint;
		  }

		  public void setSTND_SummonsWNoticeAndVerComplaint( boolean STND_SummonsWNoticeAndVerComplaint ) {
		    this.STND_SummonsWNoticeAndVerComplaint = STND_SummonsWNoticeAndVerComplaint;
		  }

		  public void setSTND_SummonsWEndorsedComplaint( boolean STND_SummonsWEndorsedComplaint ) {
		    this.STND_SummonsWEndorsedComplaint = STND_SummonsWEndorsedComplaint;
		  }

		  public void setSTND_Citation( boolean STND_Citation ) {
		    this.STND_Citation = STND_Citation;
		  }

		  public void setSTND_JudicialSubpoena( boolean STND_JudicialSubpoena ) {
		    this.STND_JudicialSubpoena = STND_JudicialSubpoena;
		  }

		  public void setSTND_JudicialSubpoenaDucestecum( boolean STND_JudicialSubpoenaDucestecum ) {
		    this.STND_JudicialSubpoenaDucestecum = STND_JudicialSubpoenaDucestecum;
		  }

		  public void setSTND_JudicialSubpoenaAndDucestecum( boolean STND_JudicialSubpoenaAndDucestecum ) {
		    this.STND_JudicialSubpoenaAndDucestecum = STND_JudicialSubpoenaAndDucestecum;
		  }

		  public void setSTND_Petition( boolean STND_Petition ) {
		    this.STND_Petition = STND_Petition;
		  }

		  public void setSTND_OrderToShowCause( boolean STND_OrderToShowCause ) {
		    this.STND_OrderToShowCause = STND_OrderToShowCause;
		  }

		  public void setSTND_OrderStandard( boolean STND_OrderStandard ) {
		    this.STND_OrderStandard = STND_OrderStandard;
		  }

		  public void setSTND_TYPE10DESC( String STND_TYPE10DESC ) {
		    this.STND_TYPE10DESC = STND_TYPE10DESC;
		  }

		  public void setSTND_INDEXNO( String STND_INDEXNO ) {
		    this.STND_INDEXNO = STND_INDEXNO;
		  }

		  public void setSTND_Court( String STND_Court ) {
		    this.STND_Court = STND_Court;
		  }

		  public void setSTND_County( String STND_County ) {
		    this.STND_County = STND_County;
		  }

		  public void setSTND_Plantiff( String STND_Plantiff ) {
		    this.STND_Plantiff = STND_Plantiff;
		  }

		  public void setSTND_Defendant( String STND_Defendant ) {
		    this.STND_Defendant = STND_Defendant;
		  }

		  public void setSTND_FULLNAME( String STND_FULLNAME ) {
		    this.STND_FULLNAME = STND_FULLNAME;
		  }

		  public void setSTND_ADDRESS( String STND_ADDRESS ) {
		    this.STND_ADDRESS = STND_ADDRESS;
		  }

		  public void setSTND_APT( String STND_APT ) {
		    this.STND_APT = STND_APT;
		  }

		  public void setSTND_CITY( String STND_CITY ) {
		    this.STND_CITY = STND_CITY;
		  }

		  public void setSTND_STATE( String STND_STATE ) {
		    this.STND_STATE = STND_STATE;
		  }

		  public void setSTND_ZIP( String STND_ZIP ) {
		    this.STND_ZIP = STND_ZIP;
		  }

		  public void setCreatedByUserId( String CreatedByUserId ) {
		    this.CreatedByUserId = CreatedByUserId;
		  }

		  public void setCreatedDate( String CreatedDate ) {
		    this.CreatedDate = CreatedDate;
		  }

		  public void setModifiedByUserId( String ModifiedByUserId ) {
		    this.ModifiedByUserId = ModifiedByUserId;
		  }

		  public void setModifiedDate( String ModifiedDate ) {
		    this.ModifiedDate = ModifiedDate;
		  }

		  public void setPetitionNoticeRider( String PetitionNoticeRider ) {
		    this.PetitionNoticeRider = PetitionNoticeRider;
		  }

		  public void setLTM1IsRRR( boolean LTM1IsRRR ) {
		    this.LTM1IsRRR = LTM1IsRRR;
		  }

		  public void setLTM2IsRRR( boolean LTM2IsRRR ) {
		    this.LTM2IsRRR = LTM2IsRRR;
		  }

		  public void setLTM3IsRRR( boolean LTM3IsRRR ) {
		    this.LTM3IsRRR = LTM3IsRRR;
		  }

		  public void setLTM4IsRRR( boolean LTM4IsRRR ) {
		    this.LTM4IsRRR = LTM4IsRRR;
		  }

		  public void setLTM5IsRRR( boolean LTM5IsRRR ) {
		    this.LTM5IsRRR = LTM5IsRRR;
		  }

		  public void setLTM6IsRRR( boolean LTM6IsRRR ) {
		    this.LTM6IsRRR = LTM6IsRRR;
		  }

		  public void setLTM7IsRRR( boolean LTM7IsRRR ) {
		    this.LTM7IsRRR = LTM7IsRRR;
		  }

		  public void setLTM8IsRRR( boolean LTM8IsRRR ) {
		    this.LTM8IsRRR = LTM8IsRRR;
		  }

		  public void setLTM9IsRRR( boolean LTM9IsRRR ) {
		    this.LTM9IsRRR = LTM9IsRRR;
		  }

		  public void setLTM10IsRRR( boolean LTM10IsRRR ) {
		    this.LTM10IsRRR = LTM10IsRRR;
		  }

		  public void setPostedOnDoor( String PostedOnDoor ) {
		    this.PostedOnDoor = PostedOnDoor;
		  }

		  public void setLTNoOfMailBox( String LTNoOfMailBox ) {
		    this.LTNoOfMailBox = LTNoOfMailBox;
		  }

		  public void setWitnessBox_Fee( String WitnessBox_Fee ) {
		    this.WitnessBox_Fee = WitnessBox_Fee;
		  }

		  public void setPServ_Skin_ASN( boolean PServ_Skin_ASN ) {
		    this.PServ_Skin_ASN = PServ_Skin_ASN;
		  }

		  public void setRPTLDELIVEREDTO( String RPTLDELIVEREDTO ) {
		    this.RPTLDELIVEREDTO = RPTLDELIVEREDTO;
		  }

		  public void setLTNOTSERVED( String LTNOTSERVED ) {
		    this.LTNOTSERVED = LTNOTSERVED;
		  }

		  public void setPServ_Hair_Blonde( boolean PServ_Hair_Blonde ) {
		    this.PServ_Hair_Blonde = PServ_Hair_Blonde;
		  }

		  public void setP_Gender( String P_Gender ) {
		    this.P_Gender = P_Gender;
		  }

		  public void setP_Skincolor( String P_Skincolor ) {
		    this.P_Skincolor = P_Skincolor;
		  }

		  public void setP_Hair( String P_Hair ) {
		    this.P_Hair = P_Hair;
		  }

		  public void setP_Age( String P_Age ) {
		    this.P_Age = P_Age;
		  }

		  public void setP_Height( String P_Height ) {
		    this.P_Height = P_Height;
		  }

		  public void setP_Weight( String P_Weight ) {
		    this.P_Weight = P_Weight;
		  }

		  public void setC_Entry( String C_Entry ) {
		    this.C_Entry = C_Entry;
		  }

		  public void setC_Wall( String C_Wall ) {
		    this.C_Wall = C_Wall;
		  }

		  public void setC_Door( String C_Door ) {
		    this.C_Door = C_Door;
		  }

		  public void setC_Floor( String C_Floor ) {
		    this.C_Floor = C_Floor;
		  }

		  public void setC_Lock( String C_Lock ) {
		    this.C_Lock = C_Lock;
		  }
		}
	
	   class ServiceResult{
		
		
		
		 private String $id;
		  private String ServiceResultID;
		  private String ServiceResultCode;
		  private String ServiceResultDescription;


		 // Getter Methods 

		  public String get$id() {
		    return $id;
		  }

		  public String getServiceResultID() {
		    return ServiceResultID;
		  }

		  public String getServiceResultCode() {
		    return ServiceResultCode;
		  }

		  public String getServiceResultDescription() {
		    return ServiceResultDescription;
		  }

		 // Setter Methods 

		  public void set$id( String $id ) {
		    this.$id = $id;
		  }

		  public void setServiceResultID( String ServiceResultID ) {
		    this.ServiceResultID = ServiceResultID;
		  }

		  public void setServiceResultCode( String ServiceResultCode ) {
		    this.ServiceResultCode = ServiceResultCode;
		  }

		  public void setServiceResultDescription( String ServiceResultDescription ) {
		    this.ServiceResultDescription = ServiceResultDescription;
		  }
		}
	
	    class ServiceType
	      {
		    private String $id;
	    	  private String ServiceResultID;
	    	  private String ServiceResultCode;
	    	  private String ServiceResultDescription;


	    	 // Getter Methods 

	    	  public String get$id() {
	    	    return $id;
	    	  }

	    	  public String getServiceResultID() {
	    	    return ServiceResultID;
	    	  }

	    	  public String getServiceResultCode() {
	    	    return ServiceResultCode;
	    	  }

	    	  public String getServiceResultDescription() {
	    	    return ServiceResultDescription;
	    	  }

	    	 // Setter Methods 

	    	  public void set$id( String $id ) {
	    	    this.$id = $id;
	    	  }

	    	  public void setServiceResultID( String ServiceResultID ) {
	    	    this.ServiceResultID = ServiceResultID;
	    	  }

	    	  public void setServiceResultCode( String ServiceResultCode ) {
	    	    this.ServiceResultCode = ServiceResultCode;
	    	  }

	    	  public void setServiceResultDescription( String ServiceResultDescription ) {
	    	    this.ServiceResultDescription = ServiceResultDescription;
	    	  }
	    	}
	
	    
	    class LegalDeliveryDetail
	    {
	    	
	    	private String $id;
	    	  private String LegalDeliveryID;
	    	  private String SERVERFNAME;
	    	  private String SERVERLNAME;
	    	  private String SERVERLNO;
	    	  private String SERVERCOUNTY;
	    	  private String SERVERSTATE;
	    	  private String SERVERCITY;
	    	  private String PPSERVED1;
	    	  private String PPSERVED2;
	    	  private String DESCRIPTION1;
	    	  private String DESCRIPTION2;
	    	  private String DESCRIPTION3;
	    	  private String DESCRIPTION4;
	    	  private String DATEOFMAILING = null;
	    	  private String SERVICERESULTSINPUT;
	    	  private String LTSERVED1;
	    	  private String LTSERVED2;
	    	  private String NOTARYDATE = null;
	    	  private String LTADDLMAILS = null;
	    	  private String LTM1ADDRESS;
	    	  private String LTM1APT;
	    	  private String LTM1CITY;
	    	  private String LTM1FULLNAME;
	    	  private String LTM1STATE;
	    	  private String LTM1ZIP;
	    	  private String LTM2ADDRESS;
	    	  private String LTM2APT;
	    	  private String LTM2CITY;
	    	  private String LTM2FULLNAME;
	    	  private String LTM2STATE;
	    	  private String LTM2ZIP;
	    	  private String LTM3ADDRESS;
	    	  private String LTM3APT;
	    	  private String LTM3CITY;
	    	  private String LTM3FULLNAME;
	    	  private String LTM3STATE;
	    	  private String LTM3ZIP;
	    	  private String LTM4ADDRESS;
	    	  private String LTM4APT;
	    	  private String LTM4CITY;
	    	  private String LTM4FULLNAME;
	    	  private String LTM4STATE;
	    	  private String LTM4ZIP;
	    	  private String LTM5ADDRESS;
	    	  private String LTM5APT;
	    	  private String LTM5CITY;
	    	  private String LTM5FULLNAME;
	    	  private String LTM5STATE;
	    	  private String LTM5ZIP;
	    	  private String LTM6ADDRESS;
	    	  private String LTM6APT;
	    	  private String LTM6CITY;
	    	  private String LTM6FULLNAME;
	    	  private String LTM6STATE;
	    	  private String LTM6ZIP;
	    	  private String LTM7ADDRESS;
	    	  private String LTM7APT;
	    	  private String LTM7CITY;
	    	  private String LTM7FULLNAME;
	    	  private String LTM7STATE;
	    	  private String LTM7ZIP;
	    	  private String LTM8ADDRESS;
	    	  private String LTM8APT;
	    	  private String LTM8CITY;
	    	  private String LTM8FULLNAME;
	    	  private String LTM8STATE;
	    	  private String LTM8ZIP;
	    	  private String LTM9ADDRESS;
	    	  private String LTM9APT;
	    	  private String LTM9CITY;
	    	  private String LTM9FULLNAME;
	    	  private String LTM9STATE;
	    	  private String LTM9ZIP;
	    	  private String LTM10ADDRESS;
	    	  private String LTM10APT;
	    	  private String LTM10CITY;
	    	  private String LTM10FULLNAME;
	    	  private String LTM10STATE;
	    	  private String LTM10ZIP;


	    	 // Getter Methods 

	    	  public String get$id() {
	    	    return $id;
	    	  }

	    	  public String getLegalDeliveryID() {
	    	    return LegalDeliveryID;
	    	  }

	    	  public String getSERVERFNAME() {
	    	    return SERVERFNAME;
	    	  }

	    	  public String getSERVERLNAME() {
	    	    return SERVERLNAME;
	    	  }

	    	  public String getSERVERLNO() {
	    	    return SERVERLNO;
	    	  }

	    	  public String getSERVERCOUNTY() {
	    	    return SERVERCOUNTY;
	    	  }

	    	  public String getSERVERSTATE() {
	    	    return SERVERSTATE;
	    	  }

	    	  public String getSERVERCITY() {
	    	    return SERVERCITY;
	    	  }

	    	  public String getPPSERVED1() {
	    	    return PPSERVED1;
	    	  }

	    	  public String getPPSERVED2() {
	    	    return PPSERVED2;
	    	  }

	    	  public String getDESCRIPTION1() {
	    	    return DESCRIPTION1;
	    	  }

	    	  public String getDESCRIPTION2() {
	    	    return DESCRIPTION2;
	    	  }

	    	  public String getDESCRIPTION3() {
	    	    return DESCRIPTION3;
	    	  }

	    	  public String getDESCRIPTION4() {
	    	    return DESCRIPTION4;
	    	  }

	    	  public String getDATEOFMAILING() {
	    	    return DATEOFMAILING;
	    	  }

	    	  public String getSERVICERESULTSINPUT() {
	    	    return SERVICERESULTSINPUT;
	    	  }

	    	  public String getLTSERVED1() {
	    	    return LTSERVED1;
	    	  }

	    	  public String getLTSERVED2() {
	    	    return LTSERVED2;
	    	  }

	    	  public String getNOTARYDATE() {
	    	    return NOTARYDATE;
	    	  }

	    	  public String getLTADDLMAILS() {
	    	    return LTADDLMAILS;
	    	  }

	    	  public String getLTM1ADDRESS() {
	    	    return LTM1ADDRESS;
	    	  }

	    	  public String getLTM1APT() {
	    	    return LTM1APT;
	    	  }

	    	  public String getLTM1CITY() {
	    	    return LTM1CITY;
	    	  }

	    	  public String getLTM1FULLNAME() {
	    	    return LTM1FULLNAME;
	    	  }

	    	  public String getLTM1STATE() {
	    	    return LTM1STATE;
	    	  }

	    	  public String getLTM1ZIP() {
	    	    return LTM1ZIP;
	    	  }

	    	  public String getLTM2ADDRESS() {
	    	    return LTM2ADDRESS;
	    	  }

	    	  public String getLTM2APT() {
	    	    return LTM2APT;
	    	  }

	    	  public String getLTM2CITY() {
	    	    return LTM2CITY;
	    	  }

	    	  public String getLTM2FULLNAME() {
	    	    return LTM2FULLNAME;
	    	  }

	    	  public String getLTM2STATE() {
	    	    return LTM2STATE;
	    	  }

	    	  public String getLTM2ZIP() {
	    	    return LTM2ZIP;
	    	  }

	    	  public String getLTM3ADDRESS() {
	    	    return LTM3ADDRESS;
	    	  }

	    	  public String getLTM3APT() {
	    	    return LTM3APT;
	    	  }

	    	  public String getLTM3CITY() {
	    	    return LTM3CITY;
	    	  }

	    	  public String getLTM3FULLNAME() {
	    	    return LTM3FULLNAME;
	    	  }

	    	  public String getLTM3STATE() {
	    	    return LTM3STATE;
	    	  }

	    	  public String getLTM3ZIP() {
	    	    return LTM3ZIP;
	    	  }

	    	  public String getLTM4ADDRESS() {
	    	    return LTM4ADDRESS;
	    	  }

	    	  public String getLTM4APT() {
	    	    return LTM4APT;
	    	  }

	    	  public String getLTM4CITY() {
	    	    return LTM4CITY;
	    	  }

	    	  public String getLTM4FULLNAME() {
	    	    return LTM4FULLNAME;
	    	  }

	    	  public String getLTM4STATE() {
	    	    return LTM4STATE;
	    	  }

	    	  public String getLTM4ZIP() {
	    	    return LTM4ZIP;
	    	  }

	    	  public String getLTM5ADDRESS() {
	    	    return LTM5ADDRESS;
	    	  }

	    	  public String getLTM5APT() {
	    	    return LTM5APT;
	    	  }

	    	  public String getLTM5CITY() {
	    	    return LTM5CITY;
	    	  }

	    	  public String getLTM5FULLNAME() {
	    	    return LTM5FULLNAME;
	    	  }

	    	  public String getLTM5STATE() {
	    	    return LTM5STATE;
	    	  }

	    	  public String getLTM5ZIP() {
	    	    return LTM5ZIP;
	    	  }

	    	  public String getLTM6ADDRESS() {
	    	    return LTM6ADDRESS;
	    	  }

	    	  public String getLTM6APT() {
	    	    return LTM6APT;
	    	  }

	    	  public String getLTM6CITY() {
	    	    return LTM6CITY;
	    	  }

	    	  public String getLTM6FULLNAME() {
	    	    return LTM6FULLNAME;
	    	  }

	    	  public String getLTM6STATE() {
	    	    return LTM6STATE;
	    	  }

	    	  public String getLTM6ZIP() {
	    	    return LTM6ZIP;
	    	  }

	    	  public String getLTM7ADDRESS() {
	    	    return LTM7ADDRESS;
	    	  }

	    	  public String getLTM7APT() {
	    	    return LTM7APT;
	    	  }

	    	  public String getLTM7CITY() {
	    	    return LTM7CITY;
	    	  }

	    	  public String getLTM7FULLNAME() {
	    	    return LTM7FULLNAME;
	    	  }

	    	  public String getLTM7STATE() {
	    	    return LTM7STATE;
	    	  }

	    	  public String getLTM7ZIP() {
	    	    return LTM7ZIP;
	    	  }

	    	  public String getLTM8ADDRESS() {
	    	    return LTM8ADDRESS;
	    	  }

	    	  public String getLTM8APT() {
	    	    return LTM8APT;
	    	  }

	    	  public String getLTM8CITY() {
	    	    return LTM8CITY;
	    	  }

	    	  public String getLTM8FULLNAME() {
	    	    return LTM8FULLNAME;
	    	  }

	    	  public String getLTM8STATE() {
	    	    return LTM8STATE;
	    	  }

	    	  public String getLTM8ZIP() {
	    	    return LTM8ZIP;
	    	  }

	    	  public String getLTM9ADDRESS() {
	    	    return LTM9ADDRESS;
	    	  }

	    	  public String getLTM9APT() {
	    	    return LTM9APT;
	    	  }

	    	  public String getLTM9CITY() {
	    	    return LTM9CITY;
	    	  }

	    	  public String getLTM9FULLNAME() {
	    	    return LTM9FULLNAME;
	    	  }

	    	  public String getLTM9STATE() {
	    	    return LTM9STATE;
	    	  }

	    	  public String getLTM9ZIP() {
	    	    return LTM9ZIP;
	    	  }

	    	  public String getLTM10ADDRESS() {
	    	    return LTM10ADDRESS;
	    	  }

	    	  public String getLTM10APT() {
	    	    return LTM10APT;
	    	  }

	    	  public String getLTM10CITY() {
	    	    return LTM10CITY;
	    	  }

	    	  public String getLTM10FULLNAME() {
	    	    return LTM10FULLNAME;
	    	  }

	    	  public String getLTM10STATE() {
	    	    return LTM10STATE;
	    	  }

	    	  public String getLTM10ZIP() {
	    	    return LTM10ZIP;
	    	  }

	    	 // Setter Methods 

	    	  public void set$id( String $id ) {
	    	    this.$id = $id;
	    	  }

	    	  public void setLegalDeliveryID( String LegalDeliveryID ) {
	    	    this.LegalDeliveryID = LegalDeliveryID;
	    	  }

	    	  public void setSERVERFNAME( String SERVERFNAME ) {
	    	    this.SERVERFNAME = SERVERFNAME;
	    	  }

	    	  public void setSERVERLNAME( String SERVERLNAME ) {
	    	    this.SERVERLNAME = SERVERLNAME;
	    	  }

	    	  public void setSERVERLNO( String SERVERLNO ) {
	    	    this.SERVERLNO = SERVERLNO;
	    	  }

	    	  public void setSERVERCOUNTY( String SERVERCOUNTY ) {
	    	    this.SERVERCOUNTY = SERVERCOUNTY;
	    	  }

	    	  public void setSERVERSTATE( String SERVERSTATE ) {
	    	    this.SERVERSTATE = SERVERSTATE;
	    	  }

	    	  public void setSERVERCITY( String SERVERCITY ) {
	    	    this.SERVERCITY = SERVERCITY;
	    	  }

	    	  public void setPPSERVED1( String PPSERVED1 ) {
	    	    this.PPSERVED1 = PPSERVED1;
	    	  }

	    	  public void setPPSERVED2( String PPSERVED2 ) {
	    	    this.PPSERVED2 = PPSERVED2;
	    	  }

	    	  public void setDESCRIPTION1( String DESCRIPTION1 ) {
	    	    this.DESCRIPTION1 = DESCRIPTION1;
	    	  }

	    	  public void setDESCRIPTION2( String DESCRIPTION2 ) {
	    	    this.DESCRIPTION2 = DESCRIPTION2;
	    	  }

	    	  public void setDESCRIPTION3( String DESCRIPTION3 ) {
	    	    this.DESCRIPTION3 = DESCRIPTION3;
	    	  }

	    	  public void setDESCRIPTION4( String DESCRIPTION4 ) {
	    	    this.DESCRIPTION4 = DESCRIPTION4;
	    	  }

	    	  public void setDATEOFMAILING( String DATEOFMAILING ) {
	    	    this.DATEOFMAILING = DATEOFMAILING;
	    	  }

	    	  public void setSERVICERESULTSINPUT( String SERVICERESULTSINPUT ) {
	    	    this.SERVICERESULTSINPUT = SERVICERESULTSINPUT;
	    	  }

	    	  public void setLTSERVED1( String LTSERVED1 ) {
	    	    this.LTSERVED1 = LTSERVED1;
	    	  }

	    	  public void setLTSERVED2( String LTSERVED2 ) {
	    	    this.LTSERVED2 = LTSERVED2;
	    	  }

	    	  public void setNOTARYDATE( String NOTARYDATE ) {
	    	    this.NOTARYDATE = NOTARYDATE;
	    	  }

	    	  public void setLTADDLMAILS( String LTADDLMAILS ) {
	    	    this.LTADDLMAILS = LTADDLMAILS;
	    	  }

	    	  public void setLTM1ADDRESS( String LTM1ADDRESS ) {
	    	    this.LTM1ADDRESS = LTM1ADDRESS;
	    	  }

	    	  public void setLTM1APT( String LTM1APT ) {
	    	    this.LTM1APT = LTM1APT;
	    	  }

	    	  public void setLTM1CITY( String LTM1CITY ) {
	    	    this.LTM1CITY = LTM1CITY;
	    	  }

	    	  public void setLTM1FULLNAME( String LTM1FULLNAME ) {
	    	    this.LTM1FULLNAME = LTM1FULLNAME;
	    	  }

	    	  public void setLTM1STATE( String LTM1STATE ) {
	    	    this.LTM1STATE = LTM1STATE;
	    	  }

	    	  public void setLTM1ZIP( String LTM1ZIP ) {
	    	    this.LTM1ZIP = LTM1ZIP;
	    	  }

	    	  public void setLTM2ADDRESS( String LTM2ADDRESS ) {
	    	    this.LTM2ADDRESS = LTM2ADDRESS;
	    	  }

	    	  public void setLTM2APT( String LTM2APT ) {
	    	    this.LTM2APT = LTM2APT;
	    	  }

	    	  public void setLTM2CITY( String LTM2CITY ) {
	    	    this.LTM2CITY = LTM2CITY;
	    	  }

	    	  public void setLTM2FULLNAME( String LTM2FULLNAME ) {
	    	    this.LTM2FULLNAME = LTM2FULLNAME;
	    	  }

	    	  public void setLTM2STATE( String LTM2STATE ) {
	    	    this.LTM2STATE = LTM2STATE;
	    	  }

	    	  public void setLTM2ZIP( String LTM2ZIP ) {
	    	    this.LTM2ZIP = LTM2ZIP;
	    	  }

	    	  public void setLTM3ADDRESS( String LTM3ADDRESS ) {
	    	    this.LTM3ADDRESS = LTM3ADDRESS;
	    	  }

	    	  public void setLTM3APT( String LTM3APT ) {
	    	    this.LTM3APT = LTM3APT;
	    	  }

	    	  public void setLTM3CITY( String LTM3CITY ) {
	    	    this.LTM3CITY = LTM3CITY;
	    	  }

	    	  public void setLTM3FULLNAME( String LTM3FULLNAME ) {
	    	    this.LTM3FULLNAME = LTM3FULLNAME;
	    	  }

	    	  public void setLTM3STATE( String LTM3STATE ) {
	    	    this.LTM3STATE = LTM3STATE;
	    	  }

	    	  public void setLTM3ZIP( String LTM3ZIP ) {
	    	    this.LTM3ZIP = LTM3ZIP;
	    	  }

	    	  public void setLTM4ADDRESS( String LTM4ADDRESS ) {
	    	    this.LTM4ADDRESS = LTM4ADDRESS;
	    	  }

	    	  public void setLTM4APT( String LTM4APT ) {
	    	    this.LTM4APT = LTM4APT;
	    	  }

	    	  public void setLTM4CITY( String LTM4CITY ) {
	    	    this.LTM4CITY = LTM4CITY;
	    	  }

	    	  public void setLTM4FULLNAME( String LTM4FULLNAME ) {
	    	    this.LTM4FULLNAME = LTM4FULLNAME;
	    	  }

	    	  public void setLTM4STATE( String LTM4STATE ) {
	    	    this.LTM4STATE = LTM4STATE;
	    	  }

	    	  public void setLTM4ZIP( String LTM4ZIP ) {
	    	    this.LTM4ZIP = LTM4ZIP;
	    	  }

	    	  public void setLTM5ADDRESS( String LTM5ADDRESS ) {
	    	    this.LTM5ADDRESS = LTM5ADDRESS;
	    	  }

	    	  public void setLTM5APT( String LTM5APT ) {
	    	    this.LTM5APT = LTM5APT;
	    	  }

	    	  public void setLTM5CITY( String LTM5CITY ) {
	    	    this.LTM5CITY = LTM5CITY;
	    	  }

	    	  public void setLTM5FULLNAME( String LTM5FULLNAME ) {
	    	    this.LTM5FULLNAME = LTM5FULLNAME;
	    	  }

	    	  public void setLTM5STATE( String LTM5STATE ) {
	    	    this.LTM5STATE = LTM5STATE;
	    	  }

	    	  public void setLTM5ZIP( String LTM5ZIP ) {
	    	    this.LTM5ZIP = LTM5ZIP;
	    	  }

	    	  public void setLTM6ADDRESS( String LTM6ADDRESS ) {
	    	    this.LTM6ADDRESS = LTM6ADDRESS;
	    	  }

	    	  public void setLTM6APT( String LTM6APT ) {
	    	    this.LTM6APT = LTM6APT;
	    	  }

	    	  public void setLTM6CITY( String LTM6CITY ) {
	    	    this.LTM6CITY = LTM6CITY;
	    	  }

	    	  public void setLTM6FULLNAME( String LTM6FULLNAME ) {
	    	    this.LTM6FULLNAME = LTM6FULLNAME;
	    	  }

	    	  public void setLTM6STATE( String LTM6STATE ) {
	    	    this.LTM6STATE = LTM6STATE;
	    	  }

	    	  public void setLTM6ZIP( String LTM6ZIP ) {
	    	    this.LTM6ZIP = LTM6ZIP;
	    	  }

	    	  public void setLTM7ADDRESS( String LTM7ADDRESS ) {
	    	    this.LTM7ADDRESS = LTM7ADDRESS;
	    	  }

	    	  public void setLTM7APT( String LTM7APT ) {
	    	    this.LTM7APT = LTM7APT;
	    	  }

	    	  public void setLTM7CITY( String LTM7CITY ) {
	    	    this.LTM7CITY = LTM7CITY;
	    	  }

	    	  public void setLTM7FULLNAME( String LTM7FULLNAME ) {
	    	    this.LTM7FULLNAME = LTM7FULLNAME;
	    	  }

	    	  public void setLTM7STATE( String LTM7STATE ) {
	    	    this.LTM7STATE = LTM7STATE;
	    	  }

	    	  public void setLTM7ZIP( String LTM7ZIP ) {
	    	    this.LTM7ZIP = LTM7ZIP;
	    	  }

	    	  public void setLTM8ADDRESS( String LTM8ADDRESS ) {
	    	    this.LTM8ADDRESS = LTM8ADDRESS;
	    	  }

	    	  public void setLTM8APT( String LTM8APT ) {
	    	    this.LTM8APT = LTM8APT;
	    	  }

	    	  public void setLTM8CITY( String LTM8CITY ) {
	    	    this.LTM8CITY = LTM8CITY;
	    	  }

	    	  public void setLTM8FULLNAME( String LTM8FULLNAME ) {
	    	    this.LTM8FULLNAME = LTM8FULLNAME;
	    	  }

	    	  public void setLTM8STATE( String LTM8STATE ) {
	    	    this.LTM8STATE = LTM8STATE;
	    	  }

	    	  public void setLTM8ZIP( String LTM8ZIP ) {
	    	    this.LTM8ZIP = LTM8ZIP;
	    	  }

	    	  public void setLTM9ADDRESS( String LTM9ADDRESS ) {
	    	    this.LTM9ADDRESS = LTM9ADDRESS;
	    	  }

	    	  public void setLTM9APT( String LTM9APT ) {
	    	    this.LTM9APT = LTM9APT;
	    	  }

	    	  public void setLTM9CITY( String LTM9CITY ) {
	    	    this.LTM9CITY = LTM9CITY;
	    	  }

	    	  public void setLTM9FULLNAME( String LTM9FULLNAME ) {
	    	    this.LTM9FULLNAME = LTM9FULLNAME;
	    	  }

	    	  public void setLTM9STATE( String LTM9STATE ) {
	    	    this.LTM9STATE = LTM9STATE;
	    	  }

	    	  public void setLTM9ZIP( String LTM9ZIP ) {
	    	    this.LTM9ZIP = LTM9ZIP;
	    	  }

	    	  public void setLTM10ADDRESS( String LTM10ADDRESS ) {
	    	    this.LTM10ADDRESS = LTM10ADDRESS;
	    	  }

	    	  public void setLTM10APT( String LTM10APT ) {
	    	    this.LTM10APT = LTM10APT;
	    	  }

	    	  public void setLTM10CITY( String LTM10CITY ) {
	    	    this.LTM10CITY = LTM10CITY;
	    	  }

	    	  public void setLTM10FULLNAME( String LTM10FULLNAME ) {
	    	    this.LTM10FULLNAME = LTM10FULLNAME;
	    	  }

	    	  public void setLTM10STATE( String LTM10STATE ) {
	    	    this.LTM10STATE = LTM10STATE;
	    	  }

	    	  public void setLTM10ZIP( String LTM10ZIP ) {
	    	    this.LTM10ZIP = LTM10ZIP;
	    	  }
	    	}
	    class RelatedBusinesses
	    {
	    	 private String $id;
	    	 public ArrayList<String> $values = new ArrayList<String>();
	    	 
	    	 
	    	/*
	    	  public String getServiceResultDescription() {
	    	    return $id;
	    	  }
	    	
	    	  public void set$id( String $id ) {
	    	    this.$id = $id;
	    	  }   	*/
	    }  
	    class RelatedPersons
	    {
	    	 private String $id;
	    	 public ArrayList<String> $values = new ArrayList<String>();
	    	 // Getter Methods 
	    	  public String getServiceResultDescription() {
	    	    return $id;
	    	  }
	    	 // Setter Methods 
	    	  public void set$id( String $id ) {
	    	    this.$id = $id;
	    	  }   	
	    	
	    }
	    class Client
	    {
	    	
	    	 private String $id;
	    	  private String ClientID;
	    	  private String ClientCode;
	    	  private boolean IsActive;
	    	  private String ClientFullName;
	    	  private String ClientMI;
	    	  private String ClientAddress1;
	    	  private String ClientAddress2;
	    	  private String ClientCity;
	    	  private String ClientState;
	    	  private String ClientZIP;
	    	  private String ClientApt;
	    	  private String ClientPhone;
	    	  private String ClientFax;
	    	  private boolean ClientEnableCaseNavigation;
	    	  private String CreatedByUserId;
	    	  private String CreatedDate;
	    	  private String ModifiedByUserId;
	    	  private String ModifiedDate;
	    	  private String User = null;
	    	  private String User1 = null;


	    	 // Getter Methods 

	    	  public String get$id() {
	    	    return $id;
	    	  }

	    	  public String getClientID() {
	    	    return ClientID;
	    	  }

	    	  public String getClientCode() {
	    	    return ClientCode;
	    	  }

	    	  public boolean getIsActive() {
	    	    return IsActive;
	    	  }

	    	  public String getClientFullName() {
	    	    return ClientFullName;
	    	  }

	    	  public String getClientMI() {
	    	    return ClientMI;
	    	  }

	    	  public String getClientAddress1() {
	    	    return ClientAddress1;
	    	  }

	    	  public String getClientAddress2() {
	    	    return ClientAddress2;
	    	  }

	    	  public String getClientCity() {
	    	    return ClientCity;
	    	  }

	    	  public String getClientState() {
	    	    return ClientState;
	    	  }

	    	  public String getClientZIP() {
	    	    return ClientZIP;
	    	  }

	    	  public String getClientApt() {
	    	    return ClientApt;
	    	  }

	    	  public String getClientPhone() {
	    	    return ClientPhone;
	    	  }

	    	  public String getClientFax() {
	    	    return ClientFax;
	    	  }

	    	  public boolean getClientEnableCaseNavigation() {
	    	    return ClientEnableCaseNavigation;
	    	  }

	    	  public String getCreatedByUserId() {
	    	    return CreatedByUserId;
	    	  }

	    	  public String getCreatedDate() {
	    	    return CreatedDate;
	    	  }

	    	  public String getModifiedByUserId() {
	    	    return ModifiedByUserId;
	    	  }

	    	  public String getModifiedDate() {
	    	    return ModifiedDate;
	    	  }

	    	  public String getUser() {
	    	    return User;
	    	  }

	    	  public String getUser1() {
	    	    return User1;
	    	  }

	    	 // Setter Methods 

	    	  public void set$id( String $id ) {
	    	    this.$id = $id;
	    	  }

	    	  public void setClientID( String ClientID ) {
	    	    this.ClientID = ClientID;
	    	  }

	    	  public void setClientCode( String ClientCode ) {
	    	    this.ClientCode = ClientCode;
	    	  }

	    	  public void setIsActive( boolean IsActive ) {
	    	    this.IsActive = IsActive;
	    	  }

	    	  public void setClientFullName( String ClientFullName ) {
	    	    this.ClientFullName = ClientFullName;
	    	  }

	    	  public void setClientMI( String ClientMI ) {
	    	    this.ClientMI = ClientMI;
	    	  }

	    	  public void setClientAddress1( String ClientAddress1 ) {
	    	    this.ClientAddress1 = ClientAddress1;
	    	  }

	    	  public void setClientAddress2( String ClientAddress2 ) {
	    	    this.ClientAddress2 = ClientAddress2;
	    	  }

	    	  public void setClientCity( String ClientCity ) {
	    	    this.ClientCity = ClientCity;
	    	  }

	    	  public void setClientState( String ClientState ) {
	    	    this.ClientState = ClientState;
	    	  }

	    	  public void setClientZIP( String ClientZIP ) {
	    	    this.ClientZIP = ClientZIP;
	    	  }

	    	  public void setClientApt( String ClientApt ) {
	    	    this.ClientApt = ClientApt;
	    	  }

	    	  public void setClientPhone( String ClientPhone ) {
	    	    this.ClientPhone = ClientPhone;
	    	  }

	    	  public void setClientFax( String ClientFax ) {
	    	    this.ClientFax = ClientFax;
	    	  }

	    	  public void setClientEnableCaseNavigation( boolean ClientEnableCaseNavigation ) {
	    	    this.ClientEnableCaseNavigation = ClientEnableCaseNavigation;
	    	  }

	    	  public void setCreatedByUserId( String CreatedByUserId ) {
	    	    this.CreatedByUserId = CreatedByUserId;
	    	  }

	    	  public void setCreatedDate( String CreatedDate ) {
	    	    this.CreatedDate = CreatedDate;
	    	  }

	    	  public void setModifiedByUserId( String ModifiedByUserId ) {
	    	    this.ModifiedByUserId = ModifiedByUserId;
	    	  }

	    	  public void setModifiedDate( String ModifiedDate ) {
	    	    this.ModifiedDate = ModifiedDate;
	    	  }

	    	  public void setUser( String User ) {
	    	    this.User = User;
	    	  }

	    	  public void setUser1( String User1 ) {
	    	    this.User1 = User1;
	    	  }
	    	}
	    
	    
	    
}
