package com.scheduler.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="shifts")
@Data
public class Shift extends OrganizationSpecificEntity{
    private Integer startTimeHour;
    private Integer startTimeMinute;
    private Integer endTimeHour;
    private Integer endTimeMinute;
    private String notes;

    @OneToOne
    @JoinColumn(name = "positionId", referencedColumnName = "id")
    private Position position;
}
