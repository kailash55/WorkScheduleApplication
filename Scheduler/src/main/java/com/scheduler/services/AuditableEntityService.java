package com.scheduler.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scheduler.models.Auditable;
import com.scheduler.repository.UserRepository;

@Service
public class AuditableEntityService {

	@Autowired
	UserRepository userRepo;
	
	public Auditable setAuditableFields(Auditable entity, boolean isNew)
	{
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(isNew)
		{
			entity.setCreatedBy(userRepo.findByUsername(userDetails.getUsername()).get());
			entity.setCreatedDateTime(new Date());
		}
		else
		{
			entity.setLastModifiedBy(userRepo.findByUsername(userDetails.getUsername()).get());
			entity.setUpdatedDateTime(new Date());
		}
		
		
		return entity;
	}
}
