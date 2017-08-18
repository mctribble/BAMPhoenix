package com.revature.assignForceDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="batch_status")
public class BatchStatus {
	
	@Id
	@Column(name="batch_status_id")
	private Integer batchStatusID;
	@Column(name="batch_status_name")
	private String batchStatusName;
	
	public BatchStatus() {}
	
	public BatchStatus(Integer batchStatusID, String batchStatusName) {
		super();
		this.batchStatusID = batchStatusID;
		this.batchStatusName = batchStatusName;
	}

	public Integer getBatchStatusID() {
		return batchStatusID;
	}

	public void setBatchStatusID(Integer batchStatusID) {
		this.batchStatusID = batchStatusID;
	}

	public String getBatchStatusName() {
		return batchStatusName;
	}

	public void setBatchStatusName(String batchStatusName) {
		this.batchStatusName = batchStatusName;
	}

	@Override
	public String toString() {
		return "BatchStatus [batchStatusID=" + batchStatusID + ", batchStatusName=" + batchStatusName + "]";
	}
	
	
	
	

}
