package com.revature.assignForceDto;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="curriculum")
public class Curriculum {

	@Id
	@Column(name="curr_id")
	private Integer currId;
	@Column(name="curr_name")
	private String name;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CURRICULUM_SKILL", joinColumns = { @JoinColumn(name = "CURRICULUM") }, inverseJoinColumns = { @JoinColumn(name = "SKILL") })
	private Collection<Skill> skills;

	public Curriculum() {}

	public Curriculum(Integer currId, String name, Collection<Skill> skills) {
		super();
		this.currId = currId;
		this.name = name;
		this.skills = skills;
	}

	public Integer getCurrId() {
		return currId;
	}

	public void setCurrId(Integer currId) {
		this.currId = currId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Collection<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Curriculum [currId=" + currId + ", name=" + name + ", skills=" + skills + "]";
	}


	
	
	
	
}
