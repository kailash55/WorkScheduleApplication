package com.scheduler.apiPayloads.requests;

import lombok.Data;

@Data
public class AutoScheduleRequest {
	private String weekStartDate;
	private String weekEndDate;
}
