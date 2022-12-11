package com.scheduler.apiPayloads.requests;

import java.util.List;
import lombok.Data;

@Data
public class EmployeeAddAvailabilityMobileRequest {
	private Long employeeId;
	private List<String> dates;
	private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;
}
