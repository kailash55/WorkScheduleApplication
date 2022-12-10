package com.scheduler.mappers;

import com.scheduler.apiPayloads.responses.EmployeeAssignedShiftResponse;
import com.scheduler.apiPayloads.responses.EmployeeAvailabilityResponse;
import com.scheduler.models.AssignedShift;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.util.date.ConversionUtil;

public class EmployeeAssignedShiftResponseMapper {
	public static EmployeeAssignedShiftResponse map(AssignedShift assignedShift)
	{
		EmployeeAssignedShiftResponse res = new EmployeeAssignedShiftResponse();
		res.setDate(ConversionUtil.toString(assignedShift.getDate()));
		res.setStartTimeHour(assignedShift.getShift().getStartTimeHour());
		res.setStartTimeMinute(assignedShift.getShift().getStartTimeMinute());
		res.setEndTimeHour(assignedShift.getShift().getEndTimeHour());
		res.setEndTimeMinute(assignedShift.getShift().getEndTimeMinute());
		res.setPosition(assignedShift.getShift().getPosition().getName());
		return res;
	}
}
