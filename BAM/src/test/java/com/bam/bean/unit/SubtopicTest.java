package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.Subtopic;

/**
 * @author Ramses
 * Testing the Subtopic Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class SubtopicTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Subtopic.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(Subtopic.class, hasValidGettersAndSetters());
    }

    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(Subtopic.class, hasValidBeanToStringFor("subtopicId"));
        assertThat(Subtopic.class, hasValidBeanToStringFor("batch"));
        assertThat(Subtopic.class, hasValidBeanToStringFor("subtopicDate"));
    }
    
}
