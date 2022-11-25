package com.scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scheduler.models.Shift;
import com.scheduler.models.ToDoTask;

public interface ToDoRepository extends JpaRepository<ToDoTask, Long>{

	List<ToDoTask> findAllByIsCompleted(boolean b);
	
}
