package com.bam.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

@Component
@ApiModel("AssignForce Trainer")
public class AssignForceTrainer {

	@ApiModelProperty(notes = "AssignForce trainer id")
	private Integer trainerId;
	@ApiModelProperty(notes = "First name")
	private String firstName;
	@ApiModelProperty(notes = "Last name")
	private String lastName;
	
	public AssignForceTrainer() {
		//Empty Because No Args
	}

	
	public AssignForceTrainer(Integer trainerId, String firstName, String lastName) {
		super();
		this.trainerId = trainerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "AssignForceTrainer [trainerId=" + trainerId + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
