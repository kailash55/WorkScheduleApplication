package com.scheduler.apiPayloads.responses;

import java.util.HashMap;

import lombok.Data;

@Data
public class WeeklyResourceResponse {
	
	private Long empId;
	private String empName;
	private String designation;
	private Integer totalWorkingHours;
	private Integer assignedHours;
	private Integer workCompletedHours;
	private HashMap<Integer, ResourceAvailibilityResponse> weeklyAvailibilitySlots;
}
