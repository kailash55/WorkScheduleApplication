package com.scheduler.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@MappedSuperclass
public abstract class OrganizationSpecificEntity extends Auditable{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizationId", referencedColumnName = "id")
    @NotNull
    private Organization organization;
}
