package com.scheduler.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.models.AssignedShift;
import com.scheduler.models.Employee;
import com.scheduler.models.EmployeeAvailibility;
import com.scheduler.models.Position;
import com.scheduler.models.Shift;
import com.scheduler.repository.AssignedShiftRepository;
import com.scheduler.repository.EmployeeAvailabilityRepository;
import com.scheduler.repository.EmployeeRepository;
import com.scheduler.repository.ShiftRepository;
import com.scheduler.util.date.DateOperationsUtil;

@Service
public class AutoScheduleService {
	
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
	EmployeeAvailabilityRepository employeeAvailibilityRepo;
	
	@Autowired
	ShiftRepository shiftRepo;
	
	@Autowired
	AssignedShiftRepository assignedShiftRepo;
	
	@Autowired
	AuditableEntityService auditableEntityService;
	
	@Autowired
	OrganizationSpecificEntityService orgSpecificEntityService;
	
	@Autowired
	UserService userService;
	
	public void autoScheduleAll(Date weekStartDate, Date weekEndDate)
	{
		List<Employee> employees = employeeRepo.findAllByOrganizationId(userService.getUsersOrganization().getId());
		
		for(int i=0; i<7; i++)
		{
			Date currentDate = DateOperationsUtil.addDaysToDate(i, weekStartDate);
			for(Employee employee : employees)
			{
				autoScheduleForEmployeeForDate(employee, currentDate);
			}
		}
		
	}
	
	public void autoScheduleForEmployeeForDate(Employee employee, Date date)
	{
		List<EmployeeAvailibility> empAvailibilities =  employeeAvailibilityRepo.findByDateBetweenAndEmployeeId(date, date, employee.getId());
		//List<Assigne>
		Position empPosition = employee.getPositions().get(0);
		
		List<Shift> shifts = shiftRepo.findAllByPositionId(empPosition.getId());
		
		
		
		for(EmployeeAvailibility availibility : empAvailibilities)
		{
			Time availableStartTime = new Time(availibility.getStartTimeHour(), availibility.getStartTimeMinute());
			Time availableEndTime = new Time(availibility.getEndTimeHour(), availibility.getEndTimeMinute());
			
			Integer availibilityDuration = Time.calculateDuration(availableStartTime, availableEndTime);
			
			Shift shiftToBeAssigned = null;
			for(Shift shift: shifts)
			{
				Time shiftStartTime = new Time(shift.getStartTimeHour(), shift.getStartTimeMinute());
				Time shiftEndTime = new Time(shift.getEndTimeHour(), shift.getEndTimeMinute());
				
				Integer minimumDurationDifference = Integer.MAX_VALUE;
				
				if(shiftStartTime.isGreaterThanOrEqual(availableStartTime)
				   && shiftEndTime.isLessThanOrEqual(availableEndTime))
				{
					Integer shiftDuration = Time.calculateDuration(shiftStartTime, shiftEndTime);
					if(Math.abs(shiftDuration - availibilityDuration) < minimumDurationDifference)
					{
						shiftToBeAssigned = shift;
					}
					
				}
			}
			
			if(shiftToBeAssigned != null)
			{
				AssignedShift assignedShift = assignedShiftRepo.findByEmployeeIdAndShiftIdAndDate(employee.getId(), shiftToBeAssigned.getId(), date);
				if(assignedShift == null)
				{
					assignedShift = new AssignedShift();
					assignedShift.setDate(date);
					assignedShift.setEmployee(employee);
					assignedShift.setShift(shiftToBeAssigned);
					orgSpecificEntityService.setOrganizationSpecificFields(assignedShift);
					auditableEntityService.setAuditableFields(assignedShift, true);
					
					assignedShiftRepo.save(assignedShift);
				}
			}
		}
		
	}
}

class Time
{
	public Integer timeHour;
	public Integer timeMinute;
	
	
	public Time(Integer timeHour, Integer timeMinute)
	{
		this.timeHour = timeHour;
		this.timeMinute = timeMinute;
	}
	
	public boolean isLessThan(Time time)
	{
		Integer timeInMinutes = (timeHour * 60) + timeMinute;
		Integer timeToCompareInMinutes = (time.timeHour * 60) + time.timeMinute;
		
		return timeInMinutes < timeToCompareInMinutes;
	}
	
	public boolean isLessThanOrEqual(Time time)
	{
		Integer timeInMinutes = (timeHour * 60) + timeMinute;
		Integer timeToCompareInMinutes = (time.timeHour * 60) + time.timeMinute;
		
		return timeInMinutes <= timeToCompareInMinutes;
	}
	
	public boolean isGreaterThan(Time time)
	{
		Integer timeInMinutes = (timeHour * 60) + timeMinute;
		Integer timeToCompareInMinutes = (time.timeHour * 60) + time.timeMinute;
		
		return timeInMinutes > timeToCompareInMinutes;
	}
	
	public boolean isGreaterThanOrEqual(Time time)
	{
		Integer timeInMinutes = (timeHour * 60) + timeMinute;
		Integer timeToCompareInMinutes = (time.timeHour * 60) + time.timeMinute;
		
		return timeInMinutes >= timeToCompareInMinutes;
	}
	
	public Integer timeInMinutes()
	{
		return (this.timeHour * 60) + this.timeMinute;
	}
	
	public static Integer calculateDuration(Time time1, Time time2)
	{
		return Math.abs(time1.timeInMinutes() - time2.timeInMinutes());
	}
}
