package com.bam.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "Topic_Week")
public class TopicWeek {
	
	@Id
	@Column(name="Week_ID")
	@SequenceGenerator(name = "Week_ID_SEQ", sequenceName = "Week_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Week_ID_SEQ")
	private Integer id;
	
	@Column(name="Topic_Name_ID")
	@Autowired
	private TopicName topicId;
	
	@Column(name="Topic_Batch_ID")
	@Autowired
	private Batches batchId;

	@Column(name="Topic_Week_Number")
	private Integer weekNumber;

	public TopicWeek() {
		super();
	}

	public TopicWeek(Integer id, TopicName topicId, Batches batchId, Integer weekNumber) {
		super();
		this.id = id;
		this.topicId = topicId;
		this.batchId = batchId;
		this.weekNumber = weekNumber;
	}

	public TopicWeek(TopicName topicId, Batches batchId, Integer weekNumber) {
		super();
		this.topicId = topicId;
		this.batchId = batchId;
		this.weekNumber = weekNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Autowired
	public TopicName getTopicId() {
		return topicId;
	}

	public void setTopicId(TopicName topicId) {
		this.topicId = topicId;
	}

	@Autowired
	public Batches getBatchId() {
		return batchId;
	}

	public void setBatchId(Batches batchId) {
		this.batchId = batchId;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	@Override
	public String toString() {
		return "TopicWeek [id=" + id + ", topicId=" + topicId + ", batchId=" + batchId + ", weekNumber=" + weekNumber
				+ "]";
	}
	
	
}
