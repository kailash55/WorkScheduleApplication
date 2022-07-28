package com.scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scheduler.models.OrganizationSpecificEntity;
import com.scheduler.repository.UserRepository;

@Service
public class OrganizationSpecificEntityService {

	@Autowired
	UserRepository userRepo;
	
	public OrganizationSpecificEntity setOrganizationSpecificFields(OrganizationSpecificEntity entity)
	{
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		entity
		.setOrganization
		(userRepo.findByUsername
				(userDetails.getUsername()).get().getOrganization());
		return entity;
	}
}
