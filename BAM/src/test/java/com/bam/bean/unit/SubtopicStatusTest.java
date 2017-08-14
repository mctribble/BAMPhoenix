package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.SubtopicStatus;

/**
 * @author Ramses
 * Testing the SubtopicStatus Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class SubtopicStatusTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(SubtopicStatus.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(SubtopicStatus.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(SubtopicStatus.class, hasValidBeanToStringFor("id"));
        assertThat(SubtopicStatus.class, hasValidBeanToStringFor("name"));
    }
	
}
