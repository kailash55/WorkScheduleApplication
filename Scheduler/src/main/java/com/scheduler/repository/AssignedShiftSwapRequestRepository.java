package com.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.AssignedShift;
import com.scheduler.models.AssignedShiftSwapRequest;

@Repository
public interface AssignedShiftSwapRequestRepository extends JpaRepository<AssignedShiftSwapRequest, Long>{

}
