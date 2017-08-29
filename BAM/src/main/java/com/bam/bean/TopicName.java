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
	@SequenceGenerator(name = "TOPIC_NAME_ID_SEQ", sequenceName = "TOPIC_NAME_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOPIC_NAME_ID_SEQ")
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
		super(); //NOSONAR
		this.name = name;
	}

	public Integer getId() {
		return id;
	}//NOSONAR

	public void setId(Integer id) {
		this.id = id;
	}//NOSONAR

	public String getName() {
		return name;
	}//NOSONAR

	public void setName(String name) {
		this.name = name;
	}//NOSONAR

	@Override
	public String toString() {
		return "TopicName [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
