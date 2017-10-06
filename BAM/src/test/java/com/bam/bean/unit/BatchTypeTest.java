package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bam.bean.BatchType;

/**
 * @author Ramses, Christopher Hake
 * Testing the BatchType Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class BatchTypeTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(BatchType.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(BatchType.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(BatchType.class, hasValidBeanToString());
    }

    @Test
    public void testParameterizedConstructors()
    {
        BatchType test1 = new BatchType(1, "test", 10);
        BatchType test2 = new BatchType("test", 10);

        assertEquals(new Integer(1), test1.getId());
        assertEquals("test", test1.getName(), test2.getName());
        assertEquals(10, test1.getLength(), test2.getLength());
    }
}
