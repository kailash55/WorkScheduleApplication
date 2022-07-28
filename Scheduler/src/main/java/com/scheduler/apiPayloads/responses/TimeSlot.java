package com.scheduler.apiPayloads.responses;

import lombok.Data;

@Data
public class TimeSlot {
	private Integer startTimeHour;
	private Integer startTimeMinute;
	private Integer endTimeHour;
	private Integer endTimeMinute;
}
