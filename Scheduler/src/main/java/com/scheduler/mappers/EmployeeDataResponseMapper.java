package com.scheduler.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.scheduler.apiPayloads.responses.EmployeeDataResponse;
import com.scheduler.models.Employee;

public class EmployeeDataResponseMapper {
	
	public EmployeeDataResponse map(Employee employee)
	{
		EmployeeDataResponse response = new EmployeeDataResponse();
		response.setId(employee.getId());
		response.setEmail(employee.getEmail());
		response.setFirstName(employee.getFirstName());
		response.setLastName(employee.getLastName());
		response.setMobileNumber(employee.getMobileNumber());
		response.setUsername(employee.getUser().getUsername());
		response.setWeeklyWorkingHours(employee.getWeeklyWorkingHours());
		response.setPositions(new PositionResponseMapper().map(employee.getPositions()));
		
		return response;
	}
	
	public List<EmployeeDataResponse> map(List<Employee> employees)
	{
		return employees.stream().map(this::map).collect(Collectors.toList());
	}
} 
