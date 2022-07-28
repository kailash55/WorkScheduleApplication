package com.scheduler.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.AssignedShift;

@Repository
public interface AssignedShiftRepository extends JpaRepository<AssignedShift, Long>{

	public AssignedShift findByEmployeeIdAndShiftIdAndDate(Long empId, Long shiftId, Date date);
	public List<AssignedShift> findAllByEmployeeIdAndDate(Long empId, Date date);
}
