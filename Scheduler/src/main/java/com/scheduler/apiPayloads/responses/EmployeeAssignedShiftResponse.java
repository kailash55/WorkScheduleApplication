package com.scheduler.apiPayloads.responses;

import lombok.Data;

@Data
public class EmployeeAssignedShiftResponse {
	private String date;
	private String position;
	private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;
}
