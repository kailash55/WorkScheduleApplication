package com.scheduler.apiPayloads.requests;

import java.util.List;

import lombok.Data;

@Data
public class AssignShiftRequest {
	
	private Long employeeId;
	private List<Integer> days;
	private Long shiftId;
}
