package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.SubtopicName;

/**
 * @author Ramses
 * Testing the SubtopicName Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class SubtopicNameTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(SubtopicName.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(SubtopicName.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(SubtopicName.class, hasValidBeanToStringFor("id"));
        assertThat(SubtopicName.class, hasValidBeanToStringFor("name"));
        assertThat(SubtopicName.class, hasValidBeanToStringFor("topic"));
    }
	
}
