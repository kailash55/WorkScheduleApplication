package com.scheduler.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.apiPayloads.requests.AddToDoTaskRequest;
import com.scheduler.models.ToDoTask;
import com.scheduler.services.ToDoService;

@RestController
@RequestMapping("/todo-notes")
public class ToDoNotesController {

	@Autowired
	ToDoService todoService;
	
	@GetMapping
	public ResponseEntity<?> getToDos()
	{
		 List<ToDoTask> tasks = todoService.getAllPendingTasks();
		 return ResponseEntity.ok(tasks);
	}
	
	@PostMapping
	public ResponseEntity<?> addTask(@RequestBody AddToDoTaskRequest toDoTaskRequest)
	{
		Long todoId = todoService.addNewTask(toDoTaskRequest.getTask());
		return ResponseEntity.ok(todoId);
	}
	
	@PatchMapping("/{id}/complete")
	public ResponseEntity<?> markComplete(@PathVariable("id") Long id)
	{
		todoService.markTaskAsDone(id);
		return ResponseEntity.ok("task marked as completed");
	}
}
