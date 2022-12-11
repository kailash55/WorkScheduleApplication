package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.EmployeeAddAvailabilityMobileRequest;
import com.scheduler.apiPayloads.requests.EmployeeAvailabalityRequest;
import com.scheduler.apiPayloads.responses.EmployeeAvailabilityResponse;
import com.scheduler.mappers.EmployeeAvailabilityResponseMapper;
import com.scheduler.models.Employee;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.repository.EmployeeAvailabilityRepository;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.util.date.ConversionUtil;
import com.scheduler.util.date.DateUtil;
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
			EmployeeAvailabalityRequest empAvailabilityRequest) throws Exception
	{
		List<Long> savedIds = new ArrayList<Long>();
		for(Integer day : empAvailabilityRequest.getDays())
		{
			Date date = WeekUtil.getDateFromDayForCurrentWeek(day);
			validateIfSlotAlreadyExist(date, empAvailabilityRequest);
			validateIfAvailableHrsOverflowed(date, empAvailabilityRequest);
			
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

	
	public List<EmployeeAvailabilityResponse> getAvailabilityForEmployee(Long empId)
	{
		Date currentDate = DateUtil.getCurrenDate();
		return employeeAvailabilityRepository
		.findByEmployeeIdAndDateGreaterThanEqual(empId, currentDate)
		.stream()
		.map(EmployeeAvailabilityResponseMapper::getEmployeeAvailabilityResponse)
		.collect(Collectors.toList());
	}

	private void validateIfAvailableHrsOverflowed(Date date, EmployeeAvailabalityRequest empAvailabilityRequest) throws Exception {
		// TODO Auto-generated method stub
		Date weekStartDate = ConversionUtil.localDateToDate(WeekUtil.getStartDateOfCurrentWeek());
		Date weekEndDate = ConversionUtil.localDateToDate(WeekUtil.getEndDateOfCurrentWeek());
		
		List<EmployeeAvailibility> empAvailibilities =  employeeAvailabilityRepository.findByDateBetweenAndEmployeeId(weekStartDate, weekEndDate, empAvailabilityRequest.getEmployeeId());
		
		Integer totalMinutes = 0;
		for(int i=0; i<empAvailibilities.size(); i++)
		{
			Time availStartTime = new Time(empAvailibilities.get(i).getStartTimeHour(), empAvailibilities.get(i).getStartTimeMinute());
			Time availEndTime = new Time(empAvailibilities.get(i).getEndTimeHour(), empAvailibilities.get(i).getEndTimeMinute());
			totalMinutes += Time.calculateDuration(availStartTime, availEndTime);
		}
		
		Time availStartTime = new Time(empAvailabilityRequest.getStartTimeHour(), empAvailabilityRequest.getStartTimeMinute());
		Time availEndTime = new Time(empAvailabilityRequest.getEndTimeHour(), empAvailabilityRequest.getEndTimeMinute());
		Integer duration = Time.calculateDuration(availStartTime, availEndTime);
		totalMinutes += duration;
		
		Employee emp = empRepo.getById(empAvailabilityRequest.getEmployeeId());
		
		if(totalMinutes > (emp.getWeeklyWorkingHours() * 60))
		{
			throw new Exception("Could not add availablity more than "+ emp.getWeeklyWorkingHours() +"hrs for a week.");
		}
	}

 
	private void validateIfSlotAlreadyExist(Date date, EmployeeAvailabalityRequest empAvailabilityRequest) throws Exception {
		// TODO Auto-generated method stub
		List<EmployeeAvailibility> empAvailibilities =  employeeAvailabilityRepository.findByDateBetweenAndEmployeeId(date, date, empAvailabilityRequest.getEmployeeId());
		
		Time availStartTime = new Time(empAvailabilityRequest.getStartTimeHour(), empAvailabilityRequest.getStartTimeMinute());
		Time availEndTime = new Time(empAvailabilityRequest.getEndTimeHour(), empAvailabilityRequest.getEndTimeMinute());
		for(EmployeeAvailibility empAvailability : empAvailibilities)
		{
			Time existingStartTime = new Time(empAvailability.getStartTimeHour(), empAvailability.getStartTimeMinute());
			Time existingEndTime = new Time(empAvailability.getEndTimeHour(), empAvailability.getEndTimeMinute());
			if(!(availStartTime.isGreaterThanOrEqual(existingEndTime)
			 ||availEndTime.isLessThanOrEqual(existingStartTime)))
			{
				throw new Exception("Slot already exists");
			}
		}
	}

	public void deleteAvailability(Long availabilityId) {
		// TODO Auto-generated method stub
		employeeAvailabilityRepository.deleteById(availabilityId);
	}


	public List<Long> addEmployeeAvailabilityMobile(EmployeeAddAvailabilityMobileRequest empAvailabilityRequest) {
		// TODO Auto-generated method stub
		List<Long> savedIds = new ArrayList<Long>();
		for(String strDate : empAvailabilityRequest.getDates())
		{
			Date date = ConversionUtil.fromStringToDate(strDate);
			
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
