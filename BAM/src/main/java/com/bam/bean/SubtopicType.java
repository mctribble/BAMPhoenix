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
@Table(name = "Subtopic_Type")
public class SubtopicType {

	@Id
	@Column(name = "Type_ID")
	@SequenceGenerator(name = "Subtopic_Type_ID_SEQ", sequenceName = "Subtopic_Type_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Subtopic_Type_ID_SEQ")
	private Integer id;

	@Column(name = "Type_Name")
	private String name;

	public SubtopicType() {
		//Empty Because no args
	}

	public SubtopicType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicType(String name) {
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
		return "SubtopicType [id=" + id + ", name=" + name + "]";
	}

}
