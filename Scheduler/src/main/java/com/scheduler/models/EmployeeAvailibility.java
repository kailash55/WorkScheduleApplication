package com.scheduler.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="employee_availability")
@NamedQuery(name = "EmployeeAvailibility.findByDateBewteen",
query = "select u from EmployeeAvailibility u where u.date >= ?1 and u.date <= ?2")
public class EmployeeAvailibility extends OrganizationSpecificEntity{
	
	private Date date;
	private Integer startTimeHour;
	private Integer startTimeMinute;
	private Integer endTimeHour;
	private Integer endTimeMinute;
	
	@ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;
}
