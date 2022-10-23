package com.scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.Position;
import com.scheduler.models.Shift;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long>{

	public List<Position> findAllByOrganizationId(Long organizationId);
}