package com.scheduler.apiPayloads.requests;

import java.util.List;

import lombok.Data;

@Data
public class AddOpenShiftRequest {
	private List<Integer> days;
	private Long shiftId;
}
