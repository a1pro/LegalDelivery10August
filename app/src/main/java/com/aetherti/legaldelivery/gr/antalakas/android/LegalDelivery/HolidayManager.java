package com.aetherti.legaldelivery.gr.antalakas.android.LegalDelivery;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class HolidayManager {
	
	public class Holiday {
		LocalDate holidayDate;
		String holidayDescription;
		
		public Holiday(LocalDate _holidayDate, String _holidayDescription) {
			holidayDate = _holidayDate;
			holidayDescription = _holidayDescription;
		}
	}
	
	List<Holiday> holidayList;
	
	public enum LegalHolidaysNYC {
		NEW_YEAR,
		LUTHER_KING,
		LINKOLN,
		WASHINGTON,
		MEMORIAL,
		INDEPENDENCE,
		LABOR,
		COLUMBUS,
		ELECTION,
		VETERANS,
		THANKSGIVING,
		CHRISTMAS	
	}
	
	public HolidayManager() {
		holidayList = new ArrayList<Holiday>();
	}
	
	public void AddYearToHolidayCalculations(int year) {
		CalculateHolidays(year);
	}
	
	public List<Holiday> GetHolidays() {
		return holidayList;
	}
	
	void CalculateHolidays(int year) {
		
		AddHolidayNYC(LegalHolidaysNYC.NEW_YEAR, year);
		AddHolidayNYC(LegalHolidaysNYC.LUTHER_KING, year);
		AddHolidayNYC(LegalHolidaysNYC.LINKOLN, year);
		AddHolidayNYC(LegalHolidaysNYC.WASHINGTON, year);
		AddHolidayNYC(LegalHolidaysNYC.MEMORIAL, year);
		AddHolidayNYC(LegalHolidaysNYC.INDEPENDENCE, year);
		AddHolidayNYC(LegalHolidaysNYC.LABOR, year);
		AddHolidayNYC(LegalHolidaysNYC.COLUMBUS, year);
		AddHolidayNYC(LegalHolidaysNYC.ELECTION, year);
		AddHolidayNYC(LegalHolidaysNYC.VETERANS, year);
		AddHolidayNYC(LegalHolidaysNYC.THANKSGIVING, year);
		AddHolidayNYC(LegalHolidaysNYC.CHRISTMAS, year);
	}
	
	// where nTh is the number of the occurence of the target day, i.e. given "the third Friday", nTh=3; given "the first Monday" nTh=1
	LocalDate CalculateHolidayFixedWeekDayRoamingWeek(int targetDay, int nTh, int month, int year) {
		
		int earliestDate = 1 + 7 * (nTh - 1);
		
		LocalDate localDate = new LocalDate(year, month, earliestDate);
		int weekDay = localDate.dayOfWeek().get();
		
		int offset;
		
		if( targetDay == weekDay ) 
			offset = 0;
		else{
		  if( targetDay < weekDay ) 
			  offset = targetDay + (7 - weekDay);
		  else 
			  offset = (targetDay + (7 - weekDay)) - 7;
		}
		LocalDate finalDate = new LocalDate(year, month, earliestDate + offset);
		return finalDate;
	}
	
	void AddHolidayNYC(LegalHolidaysNYC legalHolidays, int year) {
		LocalDate localDate;
		switch (legalHolidays) {
		
			case NEW_YEAR:
				localDate = new LocalDate(Integer.toString(year) + "-01-01");
				
				holidayList.add(new Holiday(localDate, "New Year�s Day"));
				
				if (localDate.dayOfWeek().get() == 6)
					holidayList.add(new Holiday(localDate.minusDays(1), "New Year�s Day Observance"));
				
				if (localDate.dayOfWeek().get() == 0)
					holidayList.add(new Holiday(localDate.plusDays(1), "New Year�s Day Observance"));
				
				if (localDate.dayOfWeek().get() == 7)
					holidayList.add(new Holiday(localDate.plusDays(1), "New Year�s Day Observance"));
				break;
		
			case LUTHER_KING:
				localDate = CalculateHolidayFixedWeekDayRoamingWeek(1, 3, 1, year);
				holidayList.add(new Holiday(localDate, "Martin Luther King's Birthday"));
				break;
				
			case LINKOLN:
				localDate = new LocalDate(Integer.toString(year) + "-02-12");
				holidayList.add(new Holiday(localDate, "Linkoln�s Birthday"));
				break;
				
			case WASHINGTON:
				localDate = new LocalDate(Integer.toString(year) + "-02-22");
				holidayList.add(new Holiday(localDate, "Washington�s Birthday"));
				break;
				
			case MEMORIAL:
				localDate = CalculateHolidayFixedWeekDayRoamingWeek(1, 4, 5, year);
				LocalDate nextMonday = localDate.plusDays(7);
				
				if (nextMonday.dayOfMonth().get() < localDate.dayOfMonth().get())
					holidayList.add(new Holiday(localDate, "Memorial Day"));
				else
					holidayList.add(new Holiday(nextMonday, "Memorial Day"));
				break;
				
			case INDEPENDENCE:
				localDate = new LocalDate(Integer.toString(year) + "-07-04");
				holidayList.add(new Holiday(localDate, "Independence Day"));
				break;
				
			case LABOR:
				localDate = CalculateHolidayFixedWeekDayRoamingWeek(1, 1, 9, year);
				holidayList.add(new Holiday(localDate, "Labor Day"));
				break;
				
			case COLUMBUS:
				localDate = CalculateHolidayFixedWeekDayRoamingWeek(1, 2, 10, year);
				holidayList.add(new Holiday(localDate, "Columbus Day"));
				break;
				
			case ELECTION:
				localDate = CalculateHolidayFixedWeekDayRoamingWeek(1, 1, 11, year);
				holidayList.add(new Holiday(localDate.plusDays(1), "Election Day"));
				break;
				
			case VETERANS:
				localDate = new LocalDate(Integer.toString(year) + "-11-11");
				holidayList.add(new Holiday(localDate, "Veterans Day"));
				break;
				
			case THANKSGIVING:
				localDate = CalculateHolidayFixedWeekDayRoamingWeek(4, 4, 11, year);
				holidayList.add(new Holiday(localDate, "Thanksgiving Day"));
				break;
				
			case CHRISTMAS:
				localDate = new LocalDate(Integer.toString(year) + "-12-25");
				
				holidayList.add(new Holiday(localDate, "Christmas Day"));
				
				int iDay = localDate.dayOfWeek().get();
				
				if (localDate.dayOfWeek().get() == 6)
					holidayList.add(new Holiday(localDate.minusDays(1), "Christmas Day Observance"));
				
				if (localDate.dayOfWeek().get() == 0)
					holidayList.add(new Holiday(localDate.plusDays(1), "Christmas Day Observance"));
				
				if (localDate.dayOfWeek().get() == 7)
					holidayList.add(new Holiday(localDate.plusDays(1), "Christmas Day Observance"));
				break;
		}
	}
}
