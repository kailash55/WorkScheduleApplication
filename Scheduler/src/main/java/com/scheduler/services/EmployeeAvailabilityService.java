package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.EmployeeAvailabalityRequest;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.repository.EmployeeAvailabilityRepository;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.util.date.ConversionUtil;
import com.scheduler.util.date.WeekUtil;

@Service
public class EmployeeAvailabilityService {
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	EmployeeAvailabilityRepository employeeAvailabilityRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrganizationSpecificEntityService organizationSpecificEntityService;
	
	
	public List<Long> addEmployeeAvailability(
			EmployeeAvailabalityRequest empAvailabilityRequest)
	{
		List<Long> savedIds = new ArrayList();
		for(Integer day : empAvailabilityRequest.getDays())
		{
			Date date = WeekUtil.getDateFromDayForCurrentWeek(day);
			
			EmployeeAvailibility empAvailability = new EmployeeAvailibility();
			empAvailability.setDate(date);
			empAvailability
			.setEmployee(empRepo.getById(empAvailabilityRequest.getEmployeeId()));
			empAvailability.setStartTimeHour(empAvailabilityRequest.getStartTimeHour());
			empAvailability.setStartTimeMinute(empAvailabilityRequest.getStartTimeMinute());
			empAvailability.setEndTimeHour(empAvailabilityRequest.getEndTimeHour());
			empAvailability.setEndTimeMinute(empAvailabilityRequest.getEndTimeMinute());
			empAvailability
			.setEmployee(empRepo.getById(empAvailabilityRequest.getEmployeeId()));
			organizationSpecificEntityService.setOrganizationSpecificFields(empAvailability);
			empAvailability.setCreatedBy(userService.getLoggedInUser());
			empAvailability.setCreatedDateTime(new Date());
			
			employeeAvailabilityRepository.save(empAvailability);
			savedIds.add(empAvailability.getId());
		}
		return savedIds;
	}

}
