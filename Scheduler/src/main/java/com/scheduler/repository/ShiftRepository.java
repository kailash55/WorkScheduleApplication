package com.scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long>{

	public List<Shift> findAllByPositionId(Long positionId);
}
