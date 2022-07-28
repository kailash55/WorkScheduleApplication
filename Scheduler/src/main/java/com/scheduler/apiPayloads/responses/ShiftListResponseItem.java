package com.scheduler.apiPayloads.responses;

import lombok.Data;

@Data
public class ShiftListResponseItem {
	private Long id;
	private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;
    private String notes;
    private Long positionId;
    private String positionName;
}
