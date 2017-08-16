package com.bam.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "Curriculum")
public class Curriculum {
	
	@Id
	@Column(name = "Curriculum_Id")
	@SequenceGenerator(name = "Curriculum_ID_SEQ", sequenceName = "Curriculum_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_ID_SEQ")
	private Integer id;
	
	@Column(name= "Curriculum_name")
	@NotEmpty(message = "Curriculum name cannot be empty")
	private String curriculum_Name;
	
	@Column(name ="Curriculum_version")
	private int curriculum_Version;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Curriculum_Creator", referencedColumnName = "User_Id")
	private BamUser curriculum_Creator;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Curriculum_Modifier", referencedColumnName = "User_Id")
	private BamUser curriculum_Modifier;
	
	@Column(name = "Curriculum_Date_Created")
	@NotEmpty(message = "Curriculum Date Created cannot be empty")
	private String curriculum_dateCreated;
	
	@Column(name = "Curriculum_Number_Of_Weeks")
	@NotEmpty(message = "Curriculum Number of weeks cannot be empty")
	private int curriculum_Number_Of_Weeks;
	
	public Curriculum(){
		//No-args constructor
	}

	public Integer getCurriculum_Id() {
		return id;
	}

	public void setCurriculum_Id(Integer curriculum_Id) {
		this.id = curriculum_Id;
	}

	public String getCurriculum_Name() {
		return curriculum_Name;
	}

	public void setCurriculum_Name(String curriculum_Name) {
		this.curriculum_Name = curriculum_Name;
	}

	public int getCurriculum_Version() {
		return curriculum_Version;
	}

	public void setCurriculum_Version(int curriculum_Version) {
		this.curriculum_Version = curriculum_Version;
	}

	public BamUser getCurriculum_Creator() {
		return curriculum_Creator;
	}

	public void setCurriculum_Creator(BamUser curriculum_Creator) {
		this.curriculum_Creator = curriculum_Creator;
	}

	public BamUser getCurriculum_Modifier() {
		return curriculum_Modifier;
	}

	public void setCurriculum_Modifier(BamUser curriculum_Modifier) {
		this.curriculum_Modifier = curriculum_Modifier;
	}

	public String getCurriculum_dateCreated() {
		return curriculum_dateCreated;
	}

	public void setCurriculum_dateCreated(String curriculum_dateCreated) {
		this.curriculum_dateCreated = curriculum_dateCreated;
	}

	public int getCurriculum_Number_Of_Weeks() {
		return curriculum_Number_Of_Weeks;
	}

	public void setCurriculum_Number_Of_Weeks(int curriculum_Number_Of_Weeks) {
		this.curriculum_Number_Of_Weeks = curriculum_Number_Of_Weeks;
	}

	@Override
	public String toString() {
		return "Curriculum [curriculum_Id=" + id + ", curriculum_Name=" + curriculum_Name
				+ ", curriculum_Version=" + curriculum_Version + ", curriculum_Creator=" + curriculum_Creator
				+ ", curriculum_Modifier=" + curriculum_Modifier + ", curriculum_dateCreated=" + curriculum_dateCreated
				+ ", curriculum_Number_Of_Weeks=" + curriculum_Number_Of_Weeks + "]";
	}
}