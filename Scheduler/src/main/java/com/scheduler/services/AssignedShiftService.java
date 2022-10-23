package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.models.AssignedShift;
import com.scheduler.repository.AssignedShiftRepository;
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
	OrganizationSpecificEntityService organizationSpecificEntityService;

	public List<Long> assignShifts(AssignShiftRequest assignShiftRequst)
	{
		List<Long> savedIds = new ArrayList();
		
		for(Integer day: assignShiftRequst.getDays())
		{
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
}
