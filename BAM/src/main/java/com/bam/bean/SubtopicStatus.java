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
@Table(name = "Subtopic_Status")
public class SubtopicStatus {
	
	@Id
	@Column(name="Status_ID")
	@SequenceGenerator(name = "Status_ID_SEQ", sequenceName = "Status_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Status_ID_SEQ")
	private Integer id;
	
	@Column(name="Status_Name")
	private String name;

	public SubtopicStatus() {
		super();
	}

	public SubtopicStatus(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicStatus(String name) {
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
		return "SubtopicStatus [id=" + id + ", name=" + name + "]";
	}
	
	
}
