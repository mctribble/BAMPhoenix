package com.revature.assignForceDto;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Batch")
public class AForceBatch {
	
	@Column(name="batch_name")
	private String name;
	@Column(name="start_date")
	private Timestamp startDate;
	@Column(name="end_date")
	private Timestamp endDate;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="curriculum")
	private Curriculum curriculum;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="batch_status")
	private BatchStatus batchStatus;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="trainer")
	private Trainer trainer;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="batch_location")
	private Location batchLocation;
	@Id
	@Column(name="batch_id")
	private Integer ID;
	
	public AForceBatch() {}
	
	public AForceBatch(String name, Timestamp startDate, Timestamp endDate, Curriculum curriculum, BatchStatus batchStatus,
			Trainer trainer, Location batchLocation, Integer iD) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.curriculum = curriculum;
		this.batchStatus = batchStatus;
		this.trainer = trainer;
		this.batchLocation = batchLocation;
		ID = iD;
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

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public BatchStatus getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(BatchStatus batchStatus) {
		this.batchStatus = batchStatus;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public Location getBatchLocation() {
		return batchLocation;
	}

	public void setBatchLocation(Location batchLocation) {
		this.batchLocation = batchLocation;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return "Batch [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", curriculum="
				+ curriculum + ", batchStatus=" + batchStatus + ", trainer=" + trainer + ", batchLocation="
				+ batchLocation + ", ID=" + ID + "]";
	}
	

}
