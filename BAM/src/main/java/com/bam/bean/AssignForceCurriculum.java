package com.bam.bean;

public class AssignForceCurriculum {
	
	private int currId;
	private String name;
	
	public AssignForceCurriculum() {}

	public AssignForceCurriculum(int currId, String name) {
		super();
		this.currId = currId;
		this.name = name;
	}

	public int getCurrId() {
		return currId;
	}

	public void setCurrId(int currId) {
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
