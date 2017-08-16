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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JoinColumn(name = "curriculum_Subtopic_Name_Id", referencedColumnName = "Subtopic_Name_Id")
	@NotEmpty(message="Curriculum Subtopic Name cannot be empty")
	private SubtopicName curriculum_Subtopic_Name_Id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "curriculum_Subtopic_Cur_Id", referencedColumnName = "Curriculum_Id")
	@NotEmpty(message ="Curriculum cannot be empty")
	private Curriculum curriculum;
	
	@Column(name = "Curriculum_Week")
	@NotEmpty(message="Curriculum Week cannot be empty")
	private int curriculumSubtopic_Week;
	
	@Column(name = "Curriculum_Day")
	@NotEmpty(message ="Curriculum Day cannot be empty")
	private int curriculumSubtopic_Day;
	
	public CurriculumSubtopic(){}

	public CurriculumSubtopic(int curriculumSubtopic_Id, SubtopicName curriculumSubtopic_Name_Id,
			Curriculum curriculumSubtopic_Curriculum_ID, int curriculumSubtopic_Week, int curriculumSubtopic_Day) {
		super();
		this.curriculumSubtopic_Id = curriculumSubtopic_Id;
		this.curriculum_Subtopic_Name_Id = curriculumSubtopic_Name_Id;
		this.curriculum = curriculumSubtopic_Curriculum_ID;
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
		return curriculum_Subtopic_Name_Id;
	}

	public void setCurriculumSubtopic_Name_Id(SubtopicName curriculumSubtopic_Name_Id) {
		this.curriculum_Subtopic_Name_Id = curriculumSubtopic_Name_Id;
	}

	@JsonIgnore
	public Curriculum getCurriculumSubtopic_Curriculum_ID() {
		return curriculum;
	}

	public void setCurriculumSubtopic_Curriculum_ID(Curriculum curriculumSubtopic_Curriculum_ID) {
		this.curriculum = curriculumSubtopic_Curriculum_ID;
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
				+ curriculum_Subtopic_Name_Id + ", curriculumSubtopic_Curriculum_ID=" + curriculum
				+ ", curriculumSubtopic_Week=" + curriculumSubtopic_Week + ", curriculumSubtopic_Day="
				+ curriculumSubtopic_Day + "]";
	}
	
	
	
}
