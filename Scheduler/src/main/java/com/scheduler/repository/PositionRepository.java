package com.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduler.models.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long>{

}