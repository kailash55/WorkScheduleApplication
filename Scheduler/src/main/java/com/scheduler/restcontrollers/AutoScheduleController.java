package com.scheduler.restcontrollers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AutoScheduleRequest;
import com.scheduler.apiPayloads.requests.SaveEmployeeRequest;
import com.scheduler.services.AutoScheduleService;
import com.scheduler.util.date.ConversionUtil;
import com.scheduler.util.date.WeekUtil;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/autoschedule")
public class AutoScheduleController {

	@Autowired
	AutoScheduleService autoScheduleService;
	
	@PostMapping
	public ResponseEntity<?> post()
	{
		Date weekStartDate = ConversionUtil.localDateToDate(WeekUtil.getStartDateOfCurrentWeek());
		Date weekEndDate = ConversionUtil.localDateToDate(WeekUtil.getEndDateOfCurrentWeek());
		
		autoScheduleService.autoScheduleAll(weekStartDate, weekEndDate);
		return ResponseEntity.ok(null);
	}
}
