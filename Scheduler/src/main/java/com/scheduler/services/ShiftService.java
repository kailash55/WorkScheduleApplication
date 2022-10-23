package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.ShiftSaveRequest;
import com.scheduler.apiPayloads.responses.ShiftListResponseItem;
import com.scheduler.models.Shift;
import com.scheduler.repository.PositionRepository;
import com.scheduler.repository.ShiftRepository;
import com.scheduler.repository.UserRepository;

@Service
public class ShiftService {

	@Autowired
	ShiftRepository shiftRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PositionRepository positionRepo;
	
	public Long saveShift(ShiftSaveRequest saveShiftRequest)
	{
		Shift shift = new Shift();
		shift.setStartTimeHour(saveShiftRequest.getStartTimeHour());
		shift.setStartTimeMinute(saveShiftRequest.getStartTimeMinute());
		shift.setEndTimeHour(saveShiftRequest.getEndTimeHour());
		shift.setEndTimeMinute(saveShiftRequest.getEndTimeMinute());
		shift.setNotes(saveShiftRequest.getNotes());
		shift.setPosition(positionRepo.findById(saveShiftRequest.getPositionId()).get());
		
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		shift.setCreatedBy(userRepo.findByUsername(userDetails.getUsername()).get());
		shift.setCreatedDateTime(new Date());
		shift
		.setOrganization
		(userRepo.findByUsername
				(userDetails.getUsername()).get().getOrganization());
		
		shiftRepo.save(shift);
		
		return shift.getId();
	}
	
	public List<ShiftListResponseItem> getAllShifts()
	{
		Long organizationId = userService.getUsersOrganization().getId();
		List<Shift> shiftEntityList = shiftRepo.findAllByOrganizationId(organizationId);
		List<ShiftListResponseItem> shiftListResponse = new ArrayList<ShiftListResponseItem>();
		
		for(Shift shift: shiftEntityList)
		{
			ShiftListResponseItem shiftEntityResponse = new ShiftListResponseItem();
			shiftEntityResponse.setId(shift.getId());
			shiftEntityResponse.setStartTimeHour(shift.getStartTimeHour());
			shiftEntityResponse.setStartTimeMinute(shift.getStartTimeMinute());
			shiftEntityResponse.setEndTimeHour(shift.getEndTimeHour());
			shiftEntityResponse.setEndTimeMinute(shift.getEndTimeMinute());
			shiftEntityResponse.setNotes(shift.getNotes());
			shiftEntityResponse.setPositionId(shift.getPosition().getId());
			shiftEntityResponse.setPositionName(shift.getPosition().getName());
			
			shiftListResponse.add(shiftEntityResponse);
		}
		
		return shiftListResponse;
	}
}
