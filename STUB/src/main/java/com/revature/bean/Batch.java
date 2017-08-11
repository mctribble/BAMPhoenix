package com.revature.bean;

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
@Table(name="BATCHES")
public class Batch {
	
	@Id
	@Column(name="BATCH_ID")
	private Integer batchId;
	
	@Column(name="BATCH_NAME")
	private String name;
	
	@Column(name="TRAINER_ID")
	private String trainerEmail;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="CURRICULUM", referencedColumnName = "CURRICULUM_ID")
	private Curriculum curriculum;
	
	//Batch Start Date
	@Column(name="START_DATE")
	private Timestamp startDate;
	
	//Batch End Date
	@Column(name="END_DATE")
	private Timestamp endDate;
	
	public Batch() {
		
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrainerEmail() {
		return trainerEmail;
	}

	public void setTrainerEmail(String trainerEmail) {
		this.trainerEmail = trainerEmail;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
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

	@Override
	public String toString() {
		return "Batch [batchId=" + batchId + ", name=" + name + ", trainerEmail=" + trainerEmail + ", curriculum="
				+ curriculum + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
