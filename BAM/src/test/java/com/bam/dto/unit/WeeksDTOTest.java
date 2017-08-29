package com.bam.dto.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.bam.dto.WeeksDTO;

/**
 * @author Tom Scheffer
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
}
