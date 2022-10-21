package com.scheduler.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table(name="application_errors")
@Data
public class ApplicationError {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // database id
	
	@Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @NotNull
    private Date loggedAt;

    private String message;

    private String stackTrance;
}
