package com.scheduler.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scheduler.models.AssignedShift;
import com.scheduler.models.OpenShift;

@Repository
public interface OpenShiftRepository extends JpaRepository<OpenShift, Long>{

	public OpenShift findByShiftIdAndDate(Long shiftId, Date date);
	
	public List<OpenShift> findAllByDate(Date date);
}
