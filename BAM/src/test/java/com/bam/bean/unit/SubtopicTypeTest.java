package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.SubtopicType;

/**
 * @author Ramses
 * Testing the SubtopicType Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class SubtopicTypeTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(SubtopicType.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(SubtopicType.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(SubtopicType.class, hasValidBeanToStringFor("id"));
        assertThat(SubtopicType.class, hasValidBeanToStringFor("name"));
    }
	
}
