package com.scheduler.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="positions")
@Data
public class Position extends OrganizationSpecificEntity{
    private String name;
}
