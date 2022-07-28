package com.scheduler.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="assigned_shifts")
@Data
public class AssignedShift extends OrganizationSpecificEntity{
	
	private Date date;
	
	@ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;
	
	@ManyToOne
    @JoinColumn(name = "shiftId", referencedColumnName = "id")
    private Shift shift;
}
