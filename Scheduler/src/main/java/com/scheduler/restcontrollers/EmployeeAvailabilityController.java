package com.scheduler.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.EmployeeAvailabalityRequest;
import com.scheduler.services.EmployeeAvailabilityService;

@RestController
@RequestMapping("/employee-availablility")
public class EmployeeAvailabilityController {
	
	@Autowired
	EmployeeAvailabilityService employeeAvailabilityService;
	
	@PostMapping
	public ResponseEntity<?> addEmployeeAvailability(@RequestBody EmployeeAvailabalityRequest employeeAvailabilityRequest)
	{
		List<Long> ids = employeeAvailabilityService.addEmployeeAvailability(employeeAvailabilityRequest);
		return ResponseEntity.ok(ids);
	}

}
