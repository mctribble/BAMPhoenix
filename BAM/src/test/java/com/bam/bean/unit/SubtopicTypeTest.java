package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.bam.bean.SubtopicStatus;
import org.junit.Test;

import com.bam.bean.SubtopicType;

/**
 * @author Ramses, Christopher Hake
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

    @Test
    public void testParameterizedConstructors()
    {
        SubtopicType testType1 = new SubtopicType(1, "test");
        SubtopicType testType2 = new SubtopicType("test");

        assertEquals(new Integer(1), testType1.getId());
        assertEquals("test", testType1.getName(), testType2.getName());
    }
}
