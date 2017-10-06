package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.bam.bean.SubtopicType;
import org.junit.Test;

import com.bam.bean.TopicName;

/**
 * @author Ramses, Christopher Hake
 * Testing the TopicName Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class TopicNameTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(TopicName.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(TopicName.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(TopicName.class, hasValidBeanToStringFor("id"));
        assertThat(TopicName.class, hasValidBeanToStringFor("name"));
    }

    @Test
    public void testParameterizedConstructors()
    {
        TopicName testName1 = new TopicName(1, "test");
        TopicName testName2 = new TopicName("test");

        assertEquals(new Integer(1), testName1.getId());
        assertEquals("test", testName1.getName(), testName2.getName());
    }
}
