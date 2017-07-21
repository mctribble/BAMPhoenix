package com.bam.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "batches")
public class Batches {
	
	@Id
	@Column(name="Batch_ID")
	private Integer id;
	
	@Column(name="Batch_Name")
	private String name;
	
	@Column(name="Start_Date")
	private Timestamp startDate;
	
	@Column(name="End_Date")
	private Timestamp endDate;
	
	@Column(name="Trainer_ID")
	private Users trainer;
	
	@Column(name="Batch_Type_ID")
	private Batch_Type type;
}
