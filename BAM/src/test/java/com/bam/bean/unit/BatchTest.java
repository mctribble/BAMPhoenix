package com.bam.bean.unit;
import com.bam.bean.Batch;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


import org.junit.Test;

/** 
 * @author Ramses
 * Testing the Batch Bean's getter and setter, no-args constructor
 * and toString method using JUnit.
 */
public class BatchTest {

	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Batch.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(Batch.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(Batch.class, hasValidBeanToString());
    }
    
}
