package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.bam.bean.AssignForceBatch;


/**
 * @author Ramses
 * Testing the AssignForceBatch Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class AssignForceBatchTest {
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(AssignForceBatch.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(AssignForceBatch.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(AssignForceBatch.class, hasValidBeanToStringFor("name"));
        assertThat(AssignForceBatch.class, hasValidBeanToStringFor("startDate"));
        assertThat(AssignForceBatch.class, hasValidBeanToStringFor("endDate"));
        assertThat(AssignForceBatch.class, hasValidBeanToStringFor("curriculum"));
        assertThat(AssignForceBatch.class, hasValidBeanToStringFor("trainer"));
        assertThat(AssignForceBatch.class, hasValidBeanToStringFor("ID"));
    }
	
}
