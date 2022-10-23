package com.scheduler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scheduler.apiPayloads.requests.SavePositionRequest;
import com.scheduler.apiPayloads.responses.PositionListItem;
import com.scheduler.mappers.PositionResponseMapper;
import com.scheduler.models.Position;
import com.scheduler.repository.PositionRepository;
import com.scheduler.repository.UserRepository;

@Service
public class PositionService {

	@Autowired
	PositionRepository positionRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;
	
	public Long savePostion(SavePositionRequest savePositionRequest)
	{
		Position position = new Position();
		position.setName(savePositionRequest.getName());
		
		
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		position.setCreatedBy(userRepo.findByUsername(userDetails.getUsername()).get());
		position.setCreatedDateTime(new Date());
		position
			.setOrganization
			(userRepo.findByUsername
					(userDetails.getUsername()).get().getOrganization());
		
		positionRepo.save(position);
		return position.getId();
	}
	
	
	public List<PositionListItem> getPositionsList()
	{
		Long organizationId = userService.getUsersOrganization().getId();
		List<Position> positionEntityList = positionRepo.findAllByOrganizationId(organizationId);
		
		List<PositionListItem> positionListResponse 
		= new PositionResponseMapper().map(positionEntityList);
		
		return positionListResponse;
	}
}
