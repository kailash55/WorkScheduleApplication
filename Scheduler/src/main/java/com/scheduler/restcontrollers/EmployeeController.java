package com.scheduler.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.SaveEmployeeRequest;
import com.scheduler.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService empService;
	
	@PostMapping
	public ResponseEntity<?> postEmployee(@RequestBody SaveEmployeeRequest saveEmployeeRequest)
	{
		Long empId = empService.saveEmployee(saveEmployeeRequest);
		return ResponseEntity.ok(empId);
	}
	
	@GetMapping
	public ResponseEntity<?> getEmployees()
	{
		return ResponseEntity.ok(empService.getAllEmployees());
	}
}
