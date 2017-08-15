package com.bam.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "Curriculum_Subtopic")
public class CurriculumSubtopic {
	
	@Id
	@Column(name = "Curriculum_Subtopic_Id")
	@SequenceGenerator(name = "Curriculum_Subtopic_ID_SEQ", sequenceName = "Curriculum_Subtopic_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_Subtopic_ID_SEQ")
	private int curriculumSubtopic_Id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Subtopic_Name_Id", referencedColumnName = "Subtopic_Name_Id")
	@NotEmpty(message="Curriculum Subtopic Name cannot be empty")
	private SubtopicName curriculumSubtopic_Name_Id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Curriculum_Id", referencedColumnName = "Curriculum_Id")
	@NotEmpty(message="Curriculum cannot be empty")
	private Curriculum curriculumSubtopic_Curriculum_ID;
	
	@Column(name= "Curriculum_Week")
	@NotEmpty(message="Curriculum Week cannot be empty")
	private int curriculumSubtopic_Week;
	
	@Column(name= "Curriculum_Day")
	@NotEmpty(message="Curriculum Dawy cannot be empty")
	private int curriculumSubtopic_Day;
	
	public CurriculumSubtopic(){}

	public CurriculumSubtopic(int curriculumSubtopic_Id, SubtopicName curriculumSubtopic_Name_Id,
			Curriculum curriculumSubtopic_Curriculum_ID, int curriculumSubtopic_Week, int curriculumSubtopic_Day) {
		super();
		this.curriculumSubtopic_Id = curriculumSubtopic_Id;
		this.curriculumSubtopic_Name_Id = curriculumSubtopic_Name_Id;
		this.curriculumSubtopic_Curriculum_ID = curriculumSubtopic_Curriculum_ID;
		this.curriculumSubtopic_Week = curriculumSubtopic_Week;
		this.curriculumSubtopic_Day = curriculumSubtopic_Day;
	}

	public int getCurriculumSubtopic_Id() {
		return curriculumSubtopic_Id;
	}

	public void setCurriculumSubtopic_Id(int curriculumSubtopic_Id) {
		this.curriculumSubtopic_Id = curriculumSubtopic_Id;
	}

	public SubtopicName getCurriculumSubtopic_Name_Id() {
		return curriculumSubtopic_Name_Id;
	}

	public void setCurriculumSubtopic_Name_Id(SubtopicName curriculumSubtopic_Name_Id) {
		this.curriculumSubtopic_Name_Id = curriculumSubtopic_Name_Id;
	}

	public Curriculum getCurriculumSubtopic_Curriculum_ID() {
		return curriculumSubtopic_Curriculum_ID;
	}

	public void setCurriculumSubtopic_Curriculum_ID(Curriculum curriculumSubtopic_Curriculum_ID) {
		this.curriculumSubtopic_Curriculum_ID = curriculumSubtopic_Curriculum_ID;
	}

	public int getCurriculumSubtopic_Week() {
		return curriculumSubtopic_Week;
	}

	public void setCurriculumSubtopic_Week(int curriculumSubtopic_Week) {
		this.curriculumSubtopic_Week = curriculumSubtopic_Week;
	}

	public int getCurriculumSubtopic_Day() {
		return curriculumSubtopic_Day;
	}

	public void setCurriculumSubtopic_Day(int curriculumSubtopic_Day) {
		this.curriculumSubtopic_Day = curriculumSubtopic_Day;
	}

	@Override
	public String toString() {
		return "CurriculumSubtopic [curriculumSubtopic_Id=" + curriculumSubtopic_Id + ", curriculumSubtopic_Name_Id="
				+ curriculumSubtopic_Name_Id + ", curriculumSubtopic_Curriculum_ID=" + curriculumSubtopic_Curriculum_ID
				+ ", curriculumSubtopic_Week=" + curriculumSubtopic_Week + ", curriculumSubtopic_Day="
				+ curriculumSubtopic_Day + "]";
	}
	
	
	
}
