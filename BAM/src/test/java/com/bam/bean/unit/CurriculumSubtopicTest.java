package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.CurriculumSubtopic;
/**
 * @author Ramses
 * Testing the CurriculumSubtopic Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class CurriculumSubtopicTest {
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(CurriculumSubtopic.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(CurriculumSubtopic.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(CurriculumSubtopic.class, hasValidBeanToStringFor("curriculumSubtopicNameId"));
        assertThat(CurriculumSubtopic.class, hasValidBeanToStringFor("curriculumSubtopicWeek"));
        assertThat(CurriculumSubtopic.class, hasValidBeanToStringFor("curriculumSubtopicDay"));
    }
}
