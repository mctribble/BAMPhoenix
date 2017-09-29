package com.bam.dto.unit;

import com.bam.dto.DaysDTO;
import org.junit.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSettersFor;
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
    }
    //PASS: Ensures all properties of the DTO have valid ToString
    @Test
    public void toStringShouldWorkForEachState(){
        assertThat(DaysDTO.class, hasValidBeanToStringFor("subtopics"));
    }
}
