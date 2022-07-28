package com.scheduler.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="employees")
@Data
public class Employee extends OrganizationSpecificEntity{
    @NotBlank
    @NotNull
    private String firstName;

    private String lastName;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String mobileNumber;
    
    private Integer weeklyWorkingHours;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "employee_positions",
            joinColumns = @JoinColumn(name = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "positionId"))
    private List<Position> positions;
}
