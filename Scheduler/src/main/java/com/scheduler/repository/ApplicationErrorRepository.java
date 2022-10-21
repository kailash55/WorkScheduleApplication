package com.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.AssignedShift;

@Repository
public interface ApplicationErrorRepository extends JpaRepository<AssignedShift, Long>{

}
