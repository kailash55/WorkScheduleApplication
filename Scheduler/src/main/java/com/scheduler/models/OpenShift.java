package com.scheduler.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="open_shifts")
@Data
public class OpenShift extends OrganizationSpecificEntity{
	
	private Date date;
	
	private Integer status;  // 0-Created, 1-Claimed, 2-Approved
	
	@ManyToOne
    @JoinColumn(name = "shiftId", referencedColumnName = "id")
    private Shift shift;
}
