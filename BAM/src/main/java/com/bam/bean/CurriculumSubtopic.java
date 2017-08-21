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
@Table(name = "Curriculum_Subtopic")
public class CurriculumSubtopic {
	
	@Id
	@Column(name = "Curriculum_Subtopic_Id")
	@SequenceGenerator(name = "Curriculum_Subtopic_ID_SEQ", sequenceName = "Curriculum_Subtopic_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_Subtopic_ID_SEQ")
	private int curriculumSubtopicId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Subtopic_Name_Id", referencedColumnName = "Subtopic_Name_Id")
	@NotEmpty(message="Curriculum Subtopic Name cannot be empty")
	private SubtopicName curriculumSubtopicNameId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Curriculum_Id", referencedColumnName = "Curriculum_Id")
	@NotEmpty(message="Curriculum cannot be empty")
	private Curriculum curriculumSubtopicCurriculumID;
	
	@Column(name= "Curriculum_Week")
	@NotEmpty(message="Curriculum Week cannot be empty")
	private int curriculumSubtopicWeek;
	
	@Column(name= "Curriculum_Day")
	@NotEmpty(message="Curriculum Dawy cannot be empty")
	private int curriculumSubtopicDay;
	
	public CurriculumSubtopic(){}

	public CurriculumSubtopic(int curriculumSubtopicId, SubtopicName curriculumSubtopicNameId,
			Curriculum curriculumSubtopicCurriculumID, int curriculumSubtopicWeek, int curriculumSubtopicDay) {
		super();
		this.curriculumSubtopicId = curriculumSubtopicId;
		this.curriculumSubtopicNameId = curriculumSubtopicNameId;
		this.curriculumSubtopicCurriculumID = curriculumSubtopicCurriculumID;
		this.curriculumSubtopicWeek = curriculumSubtopicWeek;
		this.curriculumSubtopicDay = curriculumSubtopicDay;
	}

	public int getCurriculumSubtopicId() {
		return curriculumSubtopicId;
	}

	public void setCurriculumSubtopicId(int curriculumSubtopicId) {
		this.curriculumSubtopicId = curriculumSubtopicId;
	}

	public SubtopicName getCurriculumSubtopicNameId() {
		return curriculumSubtopicNameId;
	}

	public void setCurriculumSubtopicNameId(SubtopicName curriculumSubtopicNameId) {
		this.curriculumSubtopicNameId = curriculumSubtopicNameId;
	}

	public Curriculum getCurriculumSubtopicCurriculumID() {
		return curriculumSubtopicCurriculumID;
	}

	public void setCurriculumSubtopicCurriculumID(Curriculum curriculumSubtopicCurriculumID) {
		this.curriculumSubtopicCurriculumID = curriculumSubtopicCurriculumID;
	}

	public int getCurriculumSubtopicWeek() {
		return curriculumSubtopicWeek;
	}

	public void setCurriculumSubtopicWeek(int curriculumSubtopicWeek) {
		this.curriculumSubtopicWeek = curriculumSubtopicWeek;
	}

	public int getCurriculumSubtopicDay() {
		return curriculumSubtopicDay;
	}

	public void setCurriculumSubtopicDay(int curriculumSubtopicDay) {
		this.curriculumSubtopicDay = curriculumSubtopicDay;
	}

	@Override
	public String toString() {
		return "CurriculumSubtopic [curriculumSubtopicId=" + curriculumSubtopicId + ", curriculumSubtopicNameId="
				+ curriculumSubtopicNameId + ", curriculumSubtopicCurriculumID=" + curriculumSubtopicCurriculumID
				+ ", curriculumSubtopicWeek=" + curriculumSubtopicWeek + ", curriculumSubtopicDay="
				+ curriculumSubtopicDay + "]";
	}

	
	
	
	
}
