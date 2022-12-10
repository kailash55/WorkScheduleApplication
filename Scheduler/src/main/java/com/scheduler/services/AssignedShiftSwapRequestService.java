package com.scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.AssignedShiftSwapRequestBody;
import com.scheduler.models.AssignedShift;
import com.scheduler.models.AssignedShiftSwapRequest;
import com.scheduler.models.Employee;
import com.scheduler.models.SwapRequestStatus;
import com.scheduler.repository.AssignedShiftRepository;
import com.scheduler.repository.AssignedShiftSwapRequestRepository;
import com.scheduler.repository.EmployeeRepository;

@Service
public class AssignedShiftSwapRequestService {

	@Autowired
	AssignedShiftRepository assignedShiftRepo;
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Autowired
	AssignedShiftSwapRequestRepository swapRequestRepo;
	
	@Autowired
	OrganizationSpecificEntityService organizationSpecificEntityService;
	
	@Autowired
	AuditableEntityService auditableEntityService;
	
	public void raiseAssignedShiftSwapRequest(AssignedShiftSwapRequestBody assignedShiftSwapRequestBody)
	{
		AssignedShift assignedShift = assignedShiftRepo.findById(assignedShiftSwapRequestBody.getAssignedShiftId()).get();
		Employee fromEmployee = empRepo.findById(assignedShiftSwapRequestBody.getFromEmployeeId()).get();
		Employee toEmployee = empRepo.findById(assignedShiftSwapRequestBody.getToEmployeeId()).get();
		
		AssignedShiftSwapRequest swapRequest = new AssignedShiftSwapRequest();
		swapRequest.setAssignedShift(assignedShift);
		swapRequest.setFromEmployee(fromEmployee);
		swapRequest.setToEmployee(toEmployee);
		swapRequest.setStatus(SwapRequestStatus.REQUESTED);
		
		auditableEntityService.setAuditableFields(swapRequest, true);
		organizationSpecificEntityService.setOrganizationSpecificFields(swapRequest);
		
		swapRequestRepo.save(swapRequest);
	}
}
