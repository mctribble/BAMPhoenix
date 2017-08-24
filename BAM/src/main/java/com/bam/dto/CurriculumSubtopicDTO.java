package com.bam.dto;

import java.util.Arrays;

public class CurriculumSubtopicDTO {

	private MetaDTO meta;
	
	private WeeksDTO [] weeks;
	
	public CurriculumSubtopicDTO(){
		//Empty Because of No Args.
		
	}

	public MetaDTO getMeta() {
		return meta;
	}

	public void setMeta(MetaDTO meta) {
		this.meta = meta;
	}

	public WeeksDTO[] getWeeks() {
		return weeks;
	}

	public void setWeeks(WeeksDTO[] weeks) {
		this.weeks = weeks;
	}

	@Override
	public String toString() {
		return "CurriculumSubtopicDTO [meta=" + meta + ", weeks=" + Arrays.toString(weeks) + "]";
	}
	
	//private Meta object
		//Curriculum Object
	
	//private Weeks object array
		//private Days object array [each represent weeks]
			//private Subtopics object that has subtopics field array of int [each array index represents days]
	
}
