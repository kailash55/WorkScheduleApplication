package com.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>{

}
