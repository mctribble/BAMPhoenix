package com.bam.dto;

import java.util.Arrays;

public class WeeksDTO {

	private DaysDTO[] days;
	
	public WeeksDTO(){
		//Empty because of NoArgs.
		
	}

	public WeeksDTO(DaysDTO[] days) {
		super();
		this.days = days;
	}

	public DaysDTO[] getDays() {
		return days;
	}

	public void setDays(DaysDTO[] days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "WeeksDTO [days=" + Arrays.toString(days) + "]";
	}
	
}
