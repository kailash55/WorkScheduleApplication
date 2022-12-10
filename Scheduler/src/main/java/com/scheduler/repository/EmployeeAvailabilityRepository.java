package com.scheduler.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.Employee;
import com.scheduler.models.EmployeeAvailibility;

@Repository
public interface EmployeeAvailabilityRepository extends JpaRepository<EmployeeAvailibility, Long>{

	public List<EmployeeAvailibility> findByDateBewteen(Date startDate, Date endDate);
	public List<EmployeeAvailibility> findByDateBetweenAndEmployeeId(Date startDate, Date endDate, Long empId);
	public List<EmployeeAvailibility> findByEmployeeIdAndDateGreaterThanEqual(Long empId, Date fromDate);
}
