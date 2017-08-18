package com.revature.assignForceDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="skill")
public class Skill {
	
	@Id
	@Column(name="skill_id")
	private Integer skillId;
	@Column(name="skill_name")
	private String name;
	
	public Skill() {}

	public Skill(Integer skillId, String name) {
		super();
		this.skillId = skillId;
		this.name = name;
	}

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Skill [skillId=" + skillId + ", name=" + name + "]";
	}

	

	
	
	
}
