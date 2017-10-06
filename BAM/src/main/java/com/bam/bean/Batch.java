package com.bam.bean;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "batches")
@ApiModel("Batch")
public class Batch {

	@Id
	@Column(name = "Batch_ID")
	@SequenceGenerator(name = "BATCH_ID_SEQ", sequenceName = "BATCH_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_SEQ")
	@ApiModelProperty(notes = "Batch id")
	private Integer id;

	@Column(name = "Batch_Name")
	@NotEmpty(message = "Batch name cannot be empty")
	@ApiModelProperty(notes = "Name")
	private String name;

	@Column(name = "Start_Date")
	@NotNull(message = "Start date cannot be empty")
	@ApiModelProperty(notes = "Start date")
	private Timestamp startDate;

	@Column(name = "End_Date")
	@NotNull(message = "End date cannot be empty")
	@ApiModelProperty(notes = "End date")
	private Timestamp endDate;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Trainer_ID", referencedColumnName = "User_Id")
	@Autowired
	@ApiModelProperty(notes = "Trainer assigned to batch")
	private BamUser trainer;
	
		/*
		 * The trainer of a batch is stored in the trainer field.  It is vitally
		 * important that if a user is a trainer, that the batch ID of the user's
		 * table is empty.  All information about a trainer's batch is stored in
		 * this table. 
		 */

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Batch_Type_ID", referencedColumnName = "Batch_Type_ID")
	@Autowired
	@ApiModelProperty(notes = "Batch type")
	private BatchType type;

	public Batch() {
		super();
	}

	public Batch(Integer id, String name, Timestamp startDate, Timestamp endDate, BamUser trainer, BatchType type) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainer = trainer;
		this.type = type;
	}

	public Batch(String name, Timestamp startDate, Timestamp endDate, BamUser trainer, BatchType type) {
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

	public BamUser getTrainer() {
		return trainer;
	}

	public void setTrainer(BamUser trainer) {
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
