package com.scheduler.apiPayloads.responses;

import lombok.Data;

@Data
public class EmployeeAvailabilityResponse {
	private String date;
	private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;
}
