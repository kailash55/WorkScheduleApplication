package com.scheduler.apiPayloads.requests;

import lombok.Data;

@Data
public class ShiftSaveRequest {
	private Long id;
	private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;
    private String notes;
    private Long positionId;
}
