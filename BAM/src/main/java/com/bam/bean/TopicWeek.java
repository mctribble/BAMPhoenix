package com.bam.bean;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "Topic_Week")
public class TopicWeek {

	@Id
	@Column(name = "Week_ID")
	@SequenceGenerator(name = "Week_ID_SEQ", sequenceName = "Week_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Week_ID_SEQ")
	private Integer id;
 
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "Topic_Name_ID", referencedColumnName = "Topic_ID")
	@Autowired
	private TopicName topic;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "Topic_Batch_ID", referencedColumnName = "Batch_ID")
	@Autowired
	private Batch batch;

	@Column(name = "Topic_Week_Number")
	private Integer weekNumber;

	public TopicWeek() {
		super();
	}

	public TopicWeek(Integer id, TopicName topic, Batch batch, Integer weekNumber) {
		super();
		this.id = id;
		this.topic = topic;
		this.batch = batch;
		this.weekNumber = weekNumber;
	}

	public TopicWeek(TopicName topic, Batch batch, Integer weekNumber) {
		super();
		this.topic = topic;
		this.batch = batch;
		this.weekNumber = weekNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TopicName getTopic() {
		return topic;
	}

	public void setTopic(TopicName topic) {
		this.topic = topic;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	@Override
	public String toString() {
		return "TopicWeek [id=" + id + ", topic=" + topic + ", batch=" + batch + ", weekNumber=" + weekNumber + "]";
	}

}
