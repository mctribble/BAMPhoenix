package com.bam.bean.unit;
import com.bam.bean.BamUser;
import com.bam.bean.Batch;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.bam.bean.BatchType;
import org.junit.Test;

import java.sql.Timestamp;

/** 
 * @author Ramses, Christopher Hake
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

    @Test
    public void testParameterizedConstructors()
    {
        Integer   id        = 1;
        String    name      = "name";
        Timestamp startDate = new Timestamp(2015, 1, 1, 1, 1, 1, 1);
        Timestamp endDate   = new Timestamp(2015, 2, 2, 2, 2, 2, 2);
        BamUser   trainer   = new BamUser();
        BatchType type      = new BatchType(1, "test", 10);

        Batch testBatch1 = new Batch(name, startDate, endDate, trainer, type);
        Batch testBatch2 = new Batch(id, name, startDate, endDate, trainer, type);

        //test1
        assertEquals(name,      testBatch1.getName());
        assertEquals(startDate, testBatch1.getStartDate());
        assertEquals(endDate,   testBatch1.getEndDate());
        assertEquals(trainer,   testBatch1.getTrainer());
        assertEquals(type,      testBatch1.getType());

        //test2
        assertEquals(id,        testBatch2.getId());
        assertEquals(name,      testBatch2.getName());
        assertEquals(startDate, testBatch2.getStartDate());
        assertEquals(endDate,   testBatch2.getEndDate());
        assertEquals(trainer,   testBatch2.getTrainer());
        assertEquals(type,      testBatch2.getType());
    }
}
