package com.scheduler.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.models.AssignedShift;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.models.Shift;
import com.scheduler.repository.AssignedShiftRepository;
import com.scheduler.repository.EmployeeAvailabilityRepository;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.repository.ShiftRepository;
import com.scheduler.util.date.WeekUtil;

@Service
public class AssignedShiftService {
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	ShiftRepository shiftRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssignedShiftRepository assignedShiftRepository;
	
	@Autowired
	EmployeeAvailabilityRepository employeeAvailibilityRepo;
	
	@Autowired
	OrganizationSpecificEntityService organizationSpecificEntityService;

	public List<Long> assignShifts(AssignShiftRequest assignShiftRequst) throws Exception
	{
		List<Long> savedIds = new ArrayList();
		
		for(Integer day: assignShiftRequst.getDays())
		{
			validateIfSlotIsAvailable(day, assignShiftRequst);
			validateIfAlreadyAssinged(day, assignShiftRequst);
			validateIfWorkingHrsIsOverFlowed(day, assignShiftRequst);
			Date date = WeekUtil.getDateFromDayForCurrentWeek(day);
			
			AssignedShift assignedShift = new AssignedShift();
			assignedShift.setDate(date);
			assignedShift.setEmployee(empRepo.getById(assignShiftRequst.getEmployeeId()));
			assignedShift.setShift(shiftRepo.getById(assignShiftRequst.getShiftId()));
			
			organizationSpecificEntityService.setOrganizationSpecificFields(assignedShift);
			assignedShift.setCreatedBy(userService.getLoggedInUser());
			assignedShift.setCreatedDateTime(new Date());
			
			assignedShiftRepository.save(assignedShift);
			savedIds.add(assignedShift.getId());
		}
		
		return savedIds;
	}

	private void validateIfWorkingHrsIsOverFlowed(Integer day, AssignShiftRequest assignShiftRequst) throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void validateIfAlreadyAssinged(Integer day, AssignShiftRequest assignShiftRequst) throws Exception {
		// TODO Auto-generated method stub
		Date date = WeekUtil.getDateFromDayForCurrentWeek(day);
		List<AssignedShift> assignedShifts 
			= assignedShiftRepository.findAllByEmployeeIdAndDate(assignShiftRequst.getEmployeeId(), date);
		
		List<Integer> shiftTimingsInMinutes = new ArrayList<Integer>();
		for(AssignedShift assignedShift : assignedShifts)
		{
			Integer shiftStartTimeInMinutes = (assignedShift.getShift().getStartTimeHour() * 60) 
											+ (assignedShift.getShift().getStartTimeMinute());
			Integer shiftEndTimeInMinutes = (assignedShift.getShift().getEndTimeHour() * 60) 
										    +(assignedShift.getShift().getEndTimeMinute());
			shiftTimingsInMinutes.add(shiftStartTimeInMinutes);
			shiftTimingsInMinutes.add(shiftEndTimeInMinutes);
		}
		
		Collections.sort(shiftTimingsInMinutes);
		
		Shift shiftToAssign = shiftRepo.getById(assignShiftRequst.getShiftId());
		Integer startTimeToBeAssigned = (shiftToAssign.getStartTimeHour()*60) + (shiftToAssign.getStartTimeMinute());
		Integer endTimeToBeAssigned = (shiftToAssign.getEndTimeHour()*60) + (shiftToAssign.getEndTimeMinute());
		
		if(shiftTimingsInMinutes.size()==0)
			return;
		
		if(endTimeToBeAssigned <= shiftTimingsInMinutes.get(0))
			return;
		
		if(startTimeToBeAssigned >= shiftTimingsInMinutes.get(shiftTimingsInMinutes.size()-1))
			return;
		
		for(int i=1; i<shiftTimingsInMinutes.size()-2; i=i+2)
		{
			if(startTimeToBeAssigned >= shiftTimingsInMinutes.get(i) && endTimeToBeAssigned <= shiftTimingsInMinutes.get(i+1))
			{
				return;
			}
		}
		
		throw new Exception("Slot not available to assign");
	}

	private void validateIfSlotIsAvailable(Integer day, AssignShiftRequest assignShiftRequst) throws Exception {
		// TODO Auto-generated method stub
		Date date = WeekUtil.getDateFromDayForCurrentWeek(day);
		List<EmployeeAvailibility> empAvailibilities =  employeeAvailibilityRepo.findByDateBetweenAndEmployeeId(date, date, assignShiftRequst.getEmployeeId());
		
		Shift shiftToAssign = shiftRepo.getById(assignShiftRequst.getShiftId());
		
		Time shiftStartTime = new Time(shiftToAssign.getStartTimeHour(), shiftToAssign.getStartTimeMinute());
		Time shiftEndTime = new Time(shiftToAssign.getEndTimeHour(), shiftToAssign.getEndTimeMinute());
		for(EmployeeAvailibility availibility : empAvailibilities)
		{
			Time availableStartTime = new Time(availibility.getStartTimeHour(), availibility.getStartTimeMinute());
			Time availableEndTime = new Time(availibility.getEndTimeHour(), availibility.getEndTimeMinute());
			
			if(shiftStartTime.isGreaterThanOrEqual(availableStartTime)
				&& shiftEndTime.isLessThanOrEqual(availableEndTime))
			{
				return;
			}
		}
		throw new Exception("Availability not found");
	}
}
