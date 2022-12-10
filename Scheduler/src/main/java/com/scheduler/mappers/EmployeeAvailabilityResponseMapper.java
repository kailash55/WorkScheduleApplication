package com.scheduler.mappers;

import com.scheduler.apiPayloads.responses.EmployeeAvailabilityResponse;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.util.date.ConversionUtil;

public class EmployeeAvailabilityResponseMapper {
	public static EmployeeAvailabilityResponse getEmployeeAvailabilityResponse(EmployeeAvailibility empAvailibility)
	{
		EmployeeAvailabilityResponse res = new EmployeeAvailabilityResponse();
		res.setDate(ConversionUtil.toString(empAvailibility.getDate()));
		res.setStartTimeHour(empAvailibility.getStartTimeHour());
		res.setStartTimeMinute(empAvailibility.getStartTimeMinute());
		res.setEndTimeHour(empAvailibility.getEndTimeHour());
		res.setEndTimeMinute(empAvailibility.getEndTimeMinute());
		return res;
	}
}
