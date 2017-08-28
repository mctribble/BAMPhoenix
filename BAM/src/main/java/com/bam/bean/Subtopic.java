package com.bam.bean;

import java.sql.Timestamp;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "SUBTOPIC")
@Component
public class Subtopic {

	@Id
	@Column(name = "Subtopic_Id")
	@SequenceGenerator(name = "SUBTOPIC_SEQ", sequenceName = "SUBTOPIC_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBTOPIC_SEQ")
	private int subtopicId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBTOPIC_NAME_ID", referencedColumnName = "SUBTOPIC_NAME_ID")
	@Autowired
	private SubtopicName subtopicName;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBTOPIC_BATCH_ID", referencedColumnName = "BATCH_ID")
	@Autowired
	private Batch batch;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBTOPIC_STATUS_ID", referencedColumnName = "STATUS_ID")
	@Autowired
	private SubtopicStatus status;

	@Column(name = "Subtopic_Date")
	private Timestamp subtopicDate;

	public Subtopic() {
		super();
	}

	public Subtopic(SubtopicName subtopicName, Batch batch, SubtopicStatus status, Timestamp subtopicDate) {
		super();
		this.subtopicName = subtopicName;
		this.batch = batch;
		this.status = status;
		this.subtopicDate = subtopicDate;
	}

	public Subtopic(int subtopicId, SubtopicName subtopicName, Batch batch, SubtopicStatus status,
			Timestamp subtopicDate) {
		super();
		this.subtopicId = subtopicId;
		this.subtopicName = subtopicName;
		this.batch = batch;
		this.status = status;
		this.subtopicDate = subtopicDate;
	}

	public int getSubtopicId() {
		return subtopicId;
	}

	public void setSubtopicId(int subtopicId) {
		this.subtopicId = subtopicId;
	}

	public SubtopicName getSubtopicName() {
		return subtopicName;
	}

	public void setSubtopicName(SubtopicName subtopicName) {
		this.subtopicName = subtopicName;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public SubtopicStatus getStatus() {
		return status;
	}

	public void setStatus(SubtopicStatus status) {
		this.status = status;
	}

	public Timestamp getSubtopicDate() {
		return subtopicDate;
	}

	public void setSubtopicDate(Timestamp subtopicDate) {
		this.subtopicDate = subtopicDate;
	}

	@Override
	public String toString() {
		return "Subtopic [subtopicId=" + subtopicId + ", batch=" + batch + ", subtopicDate=" + subtopicDate + ", status=" + status +"]";
	}

}
