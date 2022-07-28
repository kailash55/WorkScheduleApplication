package com.scheduler.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // database id

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @NotNull
    private Date createdDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdByUserId", referencedColumnName = "id")
    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lastModifiedByUserId", referencedColumnName = "id")
    private User lastModifiedBy;
}
