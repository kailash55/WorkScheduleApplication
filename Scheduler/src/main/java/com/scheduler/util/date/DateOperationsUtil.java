package com.scheduler.util.date;

import java.time.LocalDate;
import java.util.Date;

public class DateOperationsUtil {

	public static Date addDaysToDate(int numberOfDays, Date date)
	{
		LocalDate localDate = ConversionUtil.dateToLocalDate(date);
		LocalDate addedDate = localDate.plusDays(numberOfDays);
		return ConversionUtil.localDateToDate(addedDate);
	}
}
