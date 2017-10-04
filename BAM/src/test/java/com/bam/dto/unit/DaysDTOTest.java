package com.bam.dto.unit;

import com.bam.bean.SubtopicName;
import com.bam.dto.DaysDTO;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSettersFor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DaysDTOTest {
    //PASS: Ensures the DTO has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor(){
        assertThat(DaysDTO.class, hasValidBeanConstructor());
    }
    //PASS: Ensures all states of the DTO have valid Getters and Setters
    @Test
    public void gettersAndSettersShouldWorkForEachState(){
        assertThat(DaysDTO.class, hasValidGettersAndSettersFor());
        DaysDTO test = new DaysDTO();
        SubtopicName[] testDays = {new SubtopicName(), new SubtopicName()};
        test.setSubtopics(testDays);
        assertThat(test.getSubtopics(), is(testDays));
    }
    //PASS: Ensures all properties of the DTO have valid ToString
    @Test
    public void toStringShouldWorkForEachState(){
        assertThat(DaysDTO.class, hasValidBeanToStringFor("subtopics"));
    }
}
