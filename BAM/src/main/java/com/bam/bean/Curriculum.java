package com.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private int curriculum_Id;
	
	@Column(name= "Curriculum_name")
	@NotEmpty(message = "Curriculum name cannot be empty")
	private String curriculum_Name;
	
	@Column(name ="Curriculum_version")
	private int curriculum_Version;
	
	@Column(name ="Curriculum_Creator")
	@NotEmpty(message = "Curriculum Creator cannot be empty")
	private String curriculum_Creator;
	
	@Column(name = "Curriculum_Modifier")
	@NotEmpty(message = "Curriculum Modifier cannot be empty")
	private String curriculum_Modifier;
	
	@Column(name = "Curriculum_Date_Created")
	@NotEmpty(message = "Curriculum Date Created cannot be empty")
	private String curriculum_dateCreated;
	
	@Column(name = "Curriculum_Number_Of_Weeks")
	@NotEmpty(message = "Curriculum Number of weeks cannot be empty")
	private int curriculum_Number_Of_Weeks;
}
