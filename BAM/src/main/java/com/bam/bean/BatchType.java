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
@Table(name = "Batch_Type")
public class BatchType {

	@Id
	@Column(name = "Batch_Type_ID")
	@SequenceGenerator(name = "BATCH_TYPE_ID_SEQ", sequenceName = "BATCH_TYPE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_TYPE_ID_SEQ")
	private Integer id;

	@Column(name = "Batch_Type_Name")
	private String name;

	@Column(name = "Batch_Type_Length") // For now, this defaults to 10 and can't be changed.
	private Integer length = 10; // In the future, this field can be editable.

	public BatchType() {
		super();
	}

	public BatchType(Integer id, String name, Integer length) {
		super();
		this.id = id;
		this.name = name;
		this.length = length;
	}

	public BatchType(String name, Integer length) {
		super();
		this.name = name;
		this.length = length;
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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "BatchType [id=" + id + ", name=" + name + ", length=" + length + "]";
	}

}
