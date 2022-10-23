package com.scheduler.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class WeekUtil {
	
	public static LocalDate getStartDateOfCurrentWeek()
	{
		LocalDate today = LocalDate.now();

	    // Go backward to get Monday
	    LocalDate sunday = today;
	    while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
	    	sunday = sunday.minusDays(1);
	    }
	    
	    return sunday;
	}
	
	public static LocalDate getEndDateOfCurrentWeek()
	{
		LocalDate today = LocalDate.now();

	    // Go backward to get Monday
	    LocalDate saturday = today;
	    while (saturday.getDayOfWeek() != DayOfWeek.SATURDAY) {
	    	saturday = saturday.plusDays(1);
	    }
	    
	    return saturday;
	}
	
	public static LocalDate getLocalDateFromDayForCurrentWeek(Integer day)
	{
		LocalDate startDateOfCurrentWeek = getStartDateOfCurrentWeek();
		return DateOperationsUtil.addDaysToDate(day-1, startDateOfCurrentWeek);
	}
	
	public static Date getDateFromDayForCurrentWeek(Integer day)
	{
		LocalDate startDateOfCurrentWeek = getStartDateOfCurrentWeek();
		return ConversionUtil.localDateToDate(DateOperationsUtil.addDaysToDate(day-1, startDateOfCurrentWeek));
	}

}
