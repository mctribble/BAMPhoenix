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
		//Empty Because No Args
	}

	public SubtopicType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicType(String name) {
		super();//NOSONAR
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
		return "SubtopicType [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
