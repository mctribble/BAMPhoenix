package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.AssignForceCurriculum;

/**
 * @author Ramses, Christopher Hake
 * Testing the AssignForceCurriculum Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class AssignForceCurriculumTest {

	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(AssignForceCurriculum.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(AssignForceCurriculum.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(AssignForceCurriculum.class, hasValidBeanToStringFor("currId"));
        assertThat(AssignForceCurriculum.class, hasValidBeanToStringFor("name"));
    }

    @Test
    public void testParameterizedConstructor()
    {
        AssignForceCurriculum test = new AssignForceCurriculum(1, "test");
        assertThat(test.getCurrId(), equalTo(1));
        assertThat(test.getName(), equalTo("test"));
    }
	
}
