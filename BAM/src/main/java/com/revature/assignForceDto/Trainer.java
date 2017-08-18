package com.revature.assignForceDto;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="trainer")
public class Trainer {
	
	@Id
	@Column(name="trainer_id")
	private Integer trainerId;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "TRAINER_UNAVAILABLE", joinColumns = { @JoinColumn(name = "TRAINER") }, inverseJoinColumns = { @JoinColumn(name = "UNAVAILABLE") })
	private Collection<Unavailable> unavailable;
	@Column(name="active")
	private boolean active;
	
	public Trainer() {}
	
	public Trainer(Integer trainerId, String firstName, String lastName, Collection<Unavailable> unavailable,
			boolean active) {
		super();
		this.trainerId = trainerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.unavailable = unavailable;
		this.active = active;
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

	public Collection<Unavailable> getUnavailabilities() {
		return unavailable;
	}

	public void setUnavailabilities(Collection<Unavailable> unavailabilities) {
		this.unavailable = unavailabilities;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Trainer [trainerId=" + trainerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", unavailable=" + unavailable + ", active=" + active + "]";
	}
	

}
