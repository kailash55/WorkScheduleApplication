package com.scheduler.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.services.WeeklyAvailibilityService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/weeklyresources")
public class WeeklyResourcesController {

	@Autowired
	WeeklyAvailibilityService weeklyAvailabilityService;
	
	@GetMapping
	public ResponseEntity<?> get()
	{
		return ResponseEntity.ok(weeklyAvailabilityService.getEmployeesAvailibiltyForCurrentWeek());
	}
}
