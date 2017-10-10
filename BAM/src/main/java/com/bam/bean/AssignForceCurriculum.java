package com.bam.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("AssignForce Curriculum")
public class AssignForceCurriculum {
	@ApiModelProperty(notes = "AssignForce curriculum id")
	private Integer currId;
	@ApiModelProperty(notes = "Name")
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
