package com.bam.bean;

public class AssignForceCurriculum {
	
	private Integer currId;
	private String name;
	
	public AssignForceCurriculum() {
		//Empty Because No Args
	}

	public AssignForceCurriculum(Integer currId, String name) {
		super();
		this.currId = currId;
		this.name = name;
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

	@Override
	public String toString() {
		return "AssignForceCurriculum [currId=" + currId + ", name=" + name + "]";
	}
	

}
