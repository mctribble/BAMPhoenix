package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "Topic_Name")
public class TopicName {

	@Id
	@Column(name = "Topic_ID")
	@SequenceGenerator(name = "Topic_ID_SEQ", sequenceName = "Topic_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Topic_ID_SEQ")
	private Integer id;

	@Column(name = "Topic_Name")
	private String name;

	public TopicName() {
		super();
	}

	public TopicName(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public TopicName(String name) {
		super();
		this.name = name;
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

	@Override
	public String toString() {
		return "TopicName [id=" + id + ", name=" + name + "]";
	}

}
