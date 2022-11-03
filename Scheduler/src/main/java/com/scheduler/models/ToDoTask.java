package com.scheduler.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="to_do_tasks")
@Data
public class ToDoTask extends OrganizationSpecificEntity{
	
	String todoText;
	
	boolean isCompleted;
}
