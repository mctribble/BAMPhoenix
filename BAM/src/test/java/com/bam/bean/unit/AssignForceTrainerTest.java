package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.AssignForceTrainer;

/**
 * @author Ramses
 * Testing the AssignForceTrainer Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class AssignForceTrainerTest {


	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(AssignForceTrainer.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(AssignForceTrainer.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(AssignForceTrainer.class, hasValidBeanToStringFor("trainerId"));
        assertThat(AssignForceTrainer.class, hasValidBeanToStringFor("firstName"));
        assertThat(AssignForceTrainer.class, hasValidBeanToStringFor("lastName"));
    }
	
}
