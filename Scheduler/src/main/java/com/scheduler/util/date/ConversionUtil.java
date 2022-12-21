package com.scheduler.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ConversionUtil {
	public static Date localDateToDate(LocalDate localDate)
	{
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return date;
	}
	
	public static LocalDate dateToLocalDate(Date date)
	{
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}
	
	public static String toString(Date date)
	{ 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String strDate = dateFormat.format(date); 
		return strDate;
	}
	
	public static String toString_ddMMyyyyy(Date date)
	{ 
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		String strDate = dateFormat.format(date); 
		return strDate;
	}
	
	public static LocalDate fromStringToLocalDate(String strDate)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
		LocalDate dateTime = LocalDate.parse(strDate, formatter);
		return dateTime;
	}
	
	public static Date fromStringToDate(String strDate)
	{
		return localDateToDate(fromStringToLocalDate(strDate));
	}
}	
