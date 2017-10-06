package com.bam.dto.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import com.bam.bean.Curriculum;
import com.bam.dto.DaysDTO;
import com.bam.dto.MetaDTO;
import org.junit.Test;

import com.bam.dto.WeeksDTO;

/**
 * @author Tom Scheffer, Christopher Hake
 * Testing the Weeks DTO's getter and setters, no-args constructor
 * and toString method using JUnit
 */
public class WeeksDTOTest {

	//PASS: Ensures the DTO has a working no-args constructor.
	@Test
	public void shouldHaveANoArgsConstructor(){
		assertThat(WeeksDTO.class, hasValidBeanConstructor());
	}
	
	//PASS: Ensures all states of the DTO have valid Getters and Setters
	@Test
	public void gettersAndSettersShouldWorkForEachState(){
		assertThat(WeeksDTO.class, hasValidGettersAndSettersFor("days"));
	}
	
	//PASS: Ensures all properties of the DTO have valid ToString
	@Test
	public void toStringShouldWorkForEachState(){
		assertThat(WeeksDTO.class, hasValidBeanToStringFor("days"));
	}

	//test parameterized constructor
	@Test
	public void paramConstructor() throws Exception
	{
		DaysDTO[] testDays = {new DaysDTO(), new DaysDTO(), new DaysDTO()};
		DaysDTO[] testDays2 = {new DaysDTO(), new DaysDTO(), new DaysDTO()};

		assertThat(new WeeksDTO(testDays).getDays(), is(testDays));
		assertThat(new WeeksDTO(testDays2).getDays(), is(testDays2));
		assertThat(new WeeksDTO(null).getDays(), nullValue());
	}
}
