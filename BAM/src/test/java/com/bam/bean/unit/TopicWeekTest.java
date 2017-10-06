package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.bam.bean.Batch;
import com.bam.bean.TopicName;
import org.junit.Test;

import com.bam.bean.TopicWeek;

/**
 * @author Ramses
 * Testing the TopicWeek Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class TopicWeekTest {
	
	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(TopicWeek.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(TopicWeek.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(TopicWeek.class, hasValidBeanToStringFor("id"));
        assertThat(TopicWeek.class, hasValidBeanToStringFor("topic"));
        assertThat(TopicWeek.class, hasValidBeanToStringFor("batch"));
        assertThat(TopicWeek.class, hasValidBeanToStringFor("weekNumber"));
    }

    @Test
    public void testParameterizedConstructors()
    {
        TopicName testName = new TopicName("teset");
        Batch testBatch = new Batch();
        TopicWeek testWeek1 = new TopicWeek(1, testName, testBatch, 2);
        TopicWeek testWeek2 = new TopicWeek(testName, testBatch, 2);

        assertEquals(new Integer(1), testWeek1.getId());
        assert(testName.equals(testWeek1.getTopic()) && testName.equals(testWeek2.getTopic()));
        assert(testBatch.equals(testWeek1.getBatch()) && testBatch.equals(testWeek2.getBatch()));
        assertEquals(2, testWeek1.getWeekNumber(), testWeek2.getWeekNumber());
    }
}