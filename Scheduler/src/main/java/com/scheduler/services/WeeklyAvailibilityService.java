package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.responses.ResourceAvailibilityResponse;
import com.scheduler.apiPayloads.responses.ShiftListResponseItem;
import com.scheduler.apiPayloads.responses.TimeSlot;
import com.scheduler.apiPayloads.responses.WeeklyResourceResponse;
import com.scheduler.models.AssignedShift;
import com.scheduler.models.Employee;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.repository.AssignedShiftRepository;
import com.scheduler.repository.EmployeeAvailabilityRepository;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.util.date.ConversionUtil;
import com.scheduler.util.date.DateOperationsUtil;
import com.scheduler.util.date.WeekUtil;


@Service
public class WeeklyAvailibilityService {
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
	EmployeeAvailabilityRepository employeeAvailibilityRepository;
	
	@Autowired
	AssignedShiftRepository assignedShiftRepo;
	
	public List<WeeklyResourceResponse> getEmployeesAvailibiltyForCurrentWeek()
	{
		List<WeeklyResourceResponse> resourcesAvail = new ArrayList<WeeklyResourceResponse>();
		List<Employee> employees = employeeRepo.findAll();
		
		Date weekStartDate = ConversionUtil.localDateToDate(WeekUtil.getStartDateOfCurrentWeek()); 
		Date weekEndDate = ConversionUtil.localDateToDate(WeekUtil.getEndDateOfCurrentWeek()); 
		List<EmployeeAvailibility> empAvailibilites = employeeAvailibilityRepository.findByDateBewteen(weekStartDate, weekEndDate);
		
		
		for(Employee employee: employees)
		{
			WeeklyResourceResponse weeklyResource = new WeeklyResourceResponse();
			weeklyResource.setEmpId(employee.getId());
			weeklyResource.setEmpName(employee.getFirstName() + " " + employee.getLastName());
			weeklyResource.setTotalWorkingHours(employee.getWeeklyWorkingHours());
			Integer assignedWorkHrs = 0;
			
			HashMap<Integer, ResourceAvailibilityResponse> weeklyAvailibilitySlots = new HashMap<Integer, ResourceAvailibilityResponse>();
			for(int i=1; i<=7; i++)
			{
				Date currentDate = DateOperationsUtil.addDaysToDate(i-1, weekStartDate);
				ResourceAvailibilityResponse resourceDayAvailibility = new ResourceAvailibilityResponse();
				resourceDayAvailibility.setWeekDay(i);
				resourceDayAvailibility.setDate(ConversionUtil.toString(currentDate));
				resourceDayAvailibility.setOnLeave(false);
				List<EmployeeAvailibility> empAvailibilitesOnDay = empAvailibilites
						.stream()
						.filter(x-> 
									(new Date(x.getDate().getTime()).equals(currentDate)) 
									&& x.getEmployee().getId().equals(employee.getId()))
									.collect(Collectors.toList());
				
				
				List<TimeSlot> availableTimeSlotsOnDay = new ArrayList<TimeSlot>();
				for(EmployeeAvailibility availibility : empAvailibilitesOnDay)
				{
					TimeSlot timeSlot = new TimeSlot();
					timeSlot.setStartTimeHour(availibility.getStartTimeHour());
					timeSlot.setStartTimeMinute(availibility.getStartTimeMinute());
					timeSlot.setEndTimeHour(availibility.getEndTimeHour());
					timeSlot.setEndTimeMinute(availibility.getEndTimeMinute());
					availableTimeSlotsOnDay.add(timeSlot);
				}
				resourceDayAvailibility.setAvailableSlots(availableTimeSlotsOnDay);
				
				
				List<ShiftListResponseItem> assignedShiftResponseList = new ArrayList<ShiftListResponseItem>();
				List<AssignedShift> assignedShifts = assignedShiftRepo.findAllByEmployeeIdAndDate(employee.getId(), currentDate);
				for(AssignedShift assignedShift : assignedShifts)
				{
					ShiftListResponseItem assignedShiftResponseItem = new ShiftListResponseItem();
					assignedShiftResponseItem.setId(assignedShift.getShift().getId());
					assignedShiftResponseItem.setStartTimeHour(assignedShift.getShift().getStartTimeHour());
					assignedShiftResponseItem.setEndTimeHour(assignedShift.getShift().getEndTimeHour());
					assignedShiftResponseItem.setStartTimeMinute(assignedShift.getShift().getStartTimeMinute());
					assignedShiftResponseItem.setEndTimeMinute(assignedShift.getShift().getEndTimeMinute());
					assignedShiftResponseItem.setNotes(assignedShift.getShift().getNotes());
					assignedShiftResponseItem.setPositionId(assignedShift.getShift().getPosition().getId());
					assignedShiftResponseItem.setPositionName(assignedShift.getShift().getPosition().getName());
					
					Time shiftStartTime = new Time(assignedShift.getShift().getStartTimeHour(), assignedShift.getShift().getStartTimeMinute());
					Time shiftEndTime = new Time(assignedShift.getShift().getEndTimeHour(), assignedShift.getShift().getEndTimeMinute());
					Integer shiftDuration = Time.calculateDuration(shiftStartTime, shiftEndTime);
					assignedWorkHrs += shiftDuration;
					assignedShiftResponseList.add(assignedShiftResponseItem);
					
				}
				resourceDayAvailibility.setAssignedShifts(assignedShiftResponseList);
				
				weeklyAvailibilitySlots.put(i, resourceDayAvailibility);
			}
			weeklyResource.setWeeklyAvailibilitySlots(weeklyAvailibilitySlots);
			weeklyResource.setAssignedHours(assignedWorkHrs/60);
			resourcesAvail.add(weeklyResource);
			
			
		}
		
		return resourcesAvail;
	}
}

