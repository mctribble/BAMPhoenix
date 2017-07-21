package com.bam.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "batches")
public class Batches {
	
	@Id
	@Column(name="Batch_ID")
	@SequenceGenerator(name = "BATCH_ID_SEQ", sequenceName = "BATCH_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_SEQ")
	private Integer id;
	
	@Column(name="Batch_Name")
	@NotEmpty(message="Batch name cannot be empty")
	private String name;
	
	@Column(name="Start_Date")
	@NotEmpty(message="Start date cannot be empty")
	private Timestamp startDate;
	
	@Column(name="End_Date")
	@NotEmpty(message="End date cannot be empty")
	private Timestamp endDate;
	
	@Column(name="Trainer_ID")
	@NotEmpty(message="Trainer ID cannot be empty")
	@Autowired
	private Users trainer;
	
	@Column(name="Batch_Type_ID")
	@NotEmpty(message="Batch type cannot be empty")
	@Autowired
	private BatchType type;

	public Batches() {
		super();
	}

	public Batches(Integer id, String name, Timestamp startDate, Timestamp endDate, Users trainer, BatchType type) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainer = trainer;
		this.type = type;
	}

	public Batches(String name, Timestamp startDate, Timestamp endDate, Users trainer, BatchType type) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainer = trainer;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Users getTrainer() {
		return trainer;
	}

	public void setTrainer(Users trainer) {
		this.trainer = trainer;
	}

	public BatchType getType() {
		return type;
	}

	public void setType(BatchType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Batches [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", trainer=" + trainer + ", type=" + type + "]";
	}
	
	
}
