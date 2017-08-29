package com.bam.bean.dto.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.dto.CurriculumSubtopicDTO;

/** 
 * @author Roktim
 * Testing the CurriculimDTO getter and setter, no-args constructor
 * and toString method using JUnit.
 */
public class CurriculumSubtopicDTOTest {
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(CurriculumSubtopicDTO.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(CurriculumSubtopicDTO.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(CurriculumSubtopicDTO.class, hasValidBeanToString());
        

}
}
