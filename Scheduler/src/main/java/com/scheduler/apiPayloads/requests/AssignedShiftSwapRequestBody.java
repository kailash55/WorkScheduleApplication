package com.scheduler.apiPayloads.requests;

import lombok.Data;

@Data
public class AssignedShiftSwapRequestBody {
	private Long assignedShiftId;
	private Long fromEmployeeId;
	private Long toEmployeeId;
}
