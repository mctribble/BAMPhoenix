package com.revature.assignForceDto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unavailable")
public class Unavailable {
	
	@Column(name="start_date")
	private Timestamp startDate;
	@Column(name="end_date")
	private Timestamp endDate;
	@Id
	@Column(name="unavailable_id")
	private Integer id;
	
	public Unavailable() {}

	public Unavailable(Timestamp startDate, Timestamp endDate, Integer id) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.id = id;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Unavailable [startDate=" + startDate + ", endDate=" + endDate + ", id=" + id + "]";
	}
	
	

}
