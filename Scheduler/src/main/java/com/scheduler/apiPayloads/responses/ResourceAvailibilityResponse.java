package com.scheduler.apiPayloads.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResourceAvailibilityResponse {

	private Integer weekDay;
	private String date;
	private Boolean onLeave;
	private List<TimeSlot> availableSlots;
	private List<ShiftListResponseItem> assignedShifts;
	
	public ResourceAvailibilityResponse()
	{
		availableSlots = new ArrayList<TimeSlot>();
		assignedShifts = new ArrayList<ShiftListResponseItem>();
	}
}
