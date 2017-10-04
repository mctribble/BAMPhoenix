package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.bam.bean.AssignForceCurriculum;
import com.bam.bean.AssignForceTrainer;
import com.google.code.beanmatchers.BeanMatchers;
import org.junit.Test;

import com.bam.bean.AssignForceBatch;

import java.sql.Timestamp;


/**
 * @author Ramses, Christopher Hake
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

    @Test
    public void testParameterizedConstructor()
    {
        String name = "name";
        Timestamp startDate = new Timestamp(2015, 1, 1, 1, 1, 1, 1);
        Timestamp endDate = new Timestamp(2015, 2, 2, 2, 2, 2, 2);
        AssignForceCurriculum assignForceCurriculum = new AssignForceCurriculum();
        AssignForceTrainer assignForceTrainer = new AssignForceTrainer();
        Integer id = 1;

        AssignForceBatch test = new AssignForceBatch(name, startDate, endDate, assignForceCurriculum, assignForceTrainer, id);

        assertThat(test.getName(), equalTo(name));
        assertThat(test.getStartDate(), equalTo(startDate));
        assertThat(test.getEndDate(), equalTo(endDate));
        assertThat(test.getCurriculum(), equalTo(assignForceCurriculum));
        assertThat(test.getTrainer(), equalTo(assignForceTrainer));
        assertThat(test.getID(), equalTo(id));
    }
}
