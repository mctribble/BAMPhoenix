package com.bam.dto;

import java.util.Arrays;

import com.bam.bean.SubtopicName;

public class DaysDTO {

	private SubtopicName[] subtopics;
	
	public DaysDTO(){
		//Empty because of No Args
		
	}

	public SubtopicName[] getSubtopics() {
		return subtopics;
	}

	public void setSubtopics(SubtopicName[] subtopics) {
		this.subtopics = subtopics;
	}

	@Override
	public String toString() {
		return "DaysDTO [subtopics=" + Arrays.toString(subtopics) + "]";
	}
	
}
