package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.AddOpenShiftRequest;
import com.scheduler.apiPayloads.requests.AssignShiftRequest;
import com.scheduler.models.AssignedShift;
import com.scheduler.models.OpenShift;
import com.scheduler.repository.AssignedShiftRepository;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.repository.OpenShiftRepository;
import com.scheduler.repository.ShiftRepository;
import com.scheduler.util.date.WeekUtil;

@Service
public class OpenShiftService {
	
	@Autowired
	ShiftRepository shiftRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OpenShiftRepository openShiftRepository;
	
	@Autowired
	OrganizationSpecificEntityService organizationSpecificEntityService;

	public List<Long> addOpenShift(AddOpenShiftRequest openShiftRequest)
	{
		List<Long> savedIds = new ArrayList();
		
		for(Integer day: openShiftRequest.getDays())
		{
			Date date = WeekUtil.getDateFromDayForCurrentWeek(day);
			
			OpenShift openShift = new OpenShift();
			openShift.setDate(date);
			openShift.setShift(shiftRepo.getById(openShiftRequest.getShiftId()));
			
			organizationSpecificEntityService.setOrganizationSpecificFields(openShift);
			openShift.setCreatedBy(userService.getLoggedInUser());
			openShift.setCreatedDateTime(new Date());
			
			openShiftRepository.save(openShift);
			savedIds.add(openShift.getId());
		}
		
		return savedIds;
	}
}
