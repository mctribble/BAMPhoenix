package com.revature.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.revature.bean.Curriculum;

@Entity
@Table(name="TOPICS")
public class Topic {
	
	@Id
	@Column(name="TOPIC_ID")
	private Integer topicId;
	
	@Column(name="TOPIC_NAME")
	private String name;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="CURRICULUM", referencedColumnName = "CURRICULUM_ID")
	private Curriculum curriculum;
	
	@Column(name="NUMBER_OF_DAYS")
	private Integer days;
	
	@Column(name="SEQUENCE_NUMBER")
	private Integer seq_number;
	
	public Topic() {
		
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getSeq_number() {
		return seq_number;
	}

	public void setSeq_number(Integer seq_number) {
		this.seq_number = seq_number;
	}

	@Override
	public String toString() {
		return "Topic [TopicId=" + topicId + ", name=" + name + ", curriculum=" + curriculum + ", days=" + days
				+ ", seq_number=" + seq_number + "]";
	}

}
