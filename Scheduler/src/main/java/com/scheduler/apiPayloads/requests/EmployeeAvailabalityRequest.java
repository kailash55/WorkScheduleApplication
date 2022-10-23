package com.scheduler.apiPayloads.requests;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeAvailabalityRequest {
	
	private Long employeeId;
	private List<Integer> days;
	private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;

}
