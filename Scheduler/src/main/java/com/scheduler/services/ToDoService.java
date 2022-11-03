package com.scheduler.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.scheduler.models.ToDoTask;
import com.scheduler.repository.ToDoRepository;

@Service
public class ToDoService {

	@Autowired
	ToDoRepository todoRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrganizationSpecificEntityService organizationSpecificEntityService;
	
	public Long addNewTask(String task)
	{
		ToDoTask taskEntity = new ToDoTask();
		taskEntity.setTodoText(task);
		taskEntity.setCompleted(false);
		
		organizationSpecificEntityService.setOrganizationSpecificFields(taskEntity);
		taskEntity.setCreatedBy(userService.getLoggedInUser());
		taskEntity.setCreatedDateTime(new Date());
		
		todoRepo.save(taskEntity);
		return taskEntity.getId();
	}
	
	public void markTaskAsDone(Long taskId)
	{
		ToDoTask taskEntity = todoRepo.getById(taskId);
		taskEntity.setCompleted(true);
		todoRepo.save(taskEntity);
	}
	
	public List<ToDoTask> getAllPendingTasks()
	{
		List<ToDoTask> todoTasks = todoRepo.findAllByIsCompleted(false);
		return todoTasks;
	}
}
