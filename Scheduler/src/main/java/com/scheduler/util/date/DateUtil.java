package com.scheduler.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtil {

	public static Date getCurrenDate()
	{
		LocalDate now = LocalDate.now();  
		return ConversionUtil.localDateToDate(now);
	}
}
