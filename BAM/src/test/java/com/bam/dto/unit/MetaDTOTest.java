package com.bam.dto.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import com.bam.bean.Curriculum;
import org.junit.Test;

import com.bam.dto.MetaDTO;

/**
 * @author Tom Scheffer, Christopher Hake
 * Testing the Meta DTO's getter and setters, no-args constructor
 * and toString method using JUnit
 */
public class MetaDTOTest {

	//PASS: Ensures the DTO has a working no-args constructor.
	@Test
	public void shouldHaveANoArgsConstructor(){
		assertThat(MetaDTO.class, hasValidBeanConstructor());
	}
	
	//PASS: Ensures all states of the DTO have valid Getters and Setters
	@Test
	public void gettersAndSettersShouldWorkForEachState(){
		assertThat(MetaDTO.class, hasValidGettersAndSettersFor("curriculum"));
	}
	
	//PASS: Ensures all properties of the DTO have valid ToString
	@Test
	public void toStringShouldWorkForEachState(){
		assertThat(MetaDTO.class, hasValidBeanToStringFor("curriculum"));
	}

	//test parameterized constructor
	@Test
	public void paramConstructor() throws Exception
	{
		Curriculum testCurriculum = new Curriculum();
		testCurriculum.setCurriculumId(1);
		Curriculum testCurriculum2 = new Curriculum();
		testCurriculum2.setCurriculumId(2);

		assertThat(new MetaDTO(testCurriculum).getCurriculum(), is(testCurriculum));
		assertThat(new MetaDTO(testCurriculum2).getCurriculum(), is(testCurriculum2));
		assertThat(new MetaDTO(null).getCurriculum(), nullValue());
	}
}
