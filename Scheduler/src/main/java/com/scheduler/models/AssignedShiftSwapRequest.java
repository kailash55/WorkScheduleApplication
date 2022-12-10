package com.scheduler.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="assigned_shift_swap_requests")
@Data
public class AssignedShiftSwapRequest extends OrganizationSpecificEntity{
	
	@ManyToOne
    @JoinColumn(name = "assignedShiftId", referencedColumnName = "id")
    private AssignedShift assignedShift;
	
	@ManyToOne
	@JoinColumn(name = "fromEmployeeId", referencedColumnName="id")
	private Employee fromEmployee;
	
	@ManyToOne
	@JoinColumn(name = "toEmployeeId", referencedColumnName="id")
	private Employee toEmployee;
	
	@Enumerated(EnumType.STRING)
    private SwapRequestStatus status;
}
