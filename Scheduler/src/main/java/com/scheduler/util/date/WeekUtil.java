package com.scheduler.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;

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

}
