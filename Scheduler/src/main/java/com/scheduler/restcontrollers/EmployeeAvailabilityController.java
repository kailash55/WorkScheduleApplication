package com.scheduler.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.EmployeeAvailabalityRequest;
import com.scheduler.services.EmployeeAvailabilityService;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/employee-availablility")
public class EmployeeAvailabilityController {
	
	@Autowired
	EmployeeAvailabilityService employeeAvailabilityService;
	
	@PostMapping
	public ResponseEntity<?> addEmployeeAvailability(@RequestBody EmployeeAvailabalityRequest employeeAvailabilityRequest)
	{
		try
		{
			List<Long> ids = employeeAvailabilityService.addEmployeeAvailability(employeeAvailabilityRequest);
			return ResponseEntity.ok(ids);
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
