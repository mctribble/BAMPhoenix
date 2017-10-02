package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import org.junit.Before;
import org.junit.Test;

import com.bam.bean.BamUser;

import java.sql.Timestamp;

/**
 * @author Ramses, Christopher Hake
 * Testing the BamUser Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class BamUserTest {

    //test data
    private BatchType testBatchType;
    private Batch     testBatch;

    @Before
    public void setUp() throws Exception
    {
        //batch type
        testBatchType = new BatchType(1, "TEST", 10);

        //batch
        testBatch = new Batch
                    (
                        1,                                                                          //ID
                        "Test",                                                                  //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        null,                                                                   //trainer for the batch
                        testBatchType                                                                  //batch type
                    );
    }

    //PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(BamUser.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(BamUser.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(BamUser.class, hasValidBeanToStringFor("userId"));
        assertThat(BamUser.class, hasValidBeanToStringFor("fName"));
        assertThat(BamUser.class, hasValidBeanToStringFor("mName"));
        assertThat(BamUser.class, hasValidBeanToStringFor("lName"));
        assertThat(BamUser.class, hasValidBeanToStringFor("email"));
        assertThat(BamUser.class, hasValidBeanToStringFor("pwd"));
        assertThat(BamUser.class, hasValidBeanToStringFor("role"));
        assertThat(BamUser.class, hasValidBeanToStringFor("phone"));
        assertThat(BamUser.class, hasValidBeanToStringFor("phone2"));
        assertThat(BamUser.class, hasValidBeanToStringFor("skype"));
        assertThat(BamUser.class, hasValidBeanToStringFor("pwd2"));
    }

    //known error condition: should not permit trainers to have a batch
    //(see comments in BamUser.java (near the Batch field) and Batch.java (near the trainer field)
    @Test (expected = IllegalStateException.class)
    public void cantMakeTrainerWithBatch()
    {
        BamUser testTrainer = new BamUser(
                4,                                      //userId
                "Some", "Fake", "Name",    //names
                "SomeName@someMail.ws", "pa$$w0rd",  //login info
                2,                                         //role
                testBatch,                                     //current batch (associates only!)
                "555-5555-5559", null, null, //contact info
                null,                                     //for password resets
                4                                   //ID of the same user in assignForce
        );
    }

    //known error condition: should not permit trainers to have a batch
    //(see comments in BamUser.java (near the Batch field) and Batch.java (near the trainer field)
    @Test (expected = IllegalStateException.class)
    public void cantSetTrainerBatch()
    {
        BamUser testTrainer = new BamUser(
                4,                                      //userId
                "Some", "Fake", "Name",    //names
                "SomeName@someMail.ws", "pa$$w0rd",  //login info
                2,                                         //role
                null,                                     //current batch (associates only!)
                "555-5555-5559", null, null, //contact info
                null,                                     //for password resets
                4                                   //ID of the same user in assignForce
        );

        testTrainer.setBatch(testBatch);
    }
}

