package com.revature.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CURRICULUMS")
public class Curriculum {

	@Id
	@Column(name="CURRICULUM_ID")
	private Integer curriculumId;
	
	@Column(name="C_NAME")
	private String name;
	
	public Curriculum() {
		
	}

	public Integer getCurriculum_id() {
		return curriculumId;
	}

	public void setCurriculum_id(Integer curriculum_id) {
		this.curriculumId = curriculum_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Curriculum [curriculum_id=" + curriculumId + ", name=" + name + "]";
	}
}
