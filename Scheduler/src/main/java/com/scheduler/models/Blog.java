package com.scheduler.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Entity
@Table(name="blogs")
@Data
public class Blog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // database id
	
	private String title;
	
	private String body;
	
	@Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date createdDateTime; 
}
