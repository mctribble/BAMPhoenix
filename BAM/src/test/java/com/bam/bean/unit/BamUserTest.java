package com.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.google.code.beanmatchers.BeanMatchers;
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

    @Test
    public void testParameterizedConstructors() throws Exception
    {
        //params
        int userId = 2;
        String fName = "John", mName =  null, lName = "Doe";
        String email = "JohnDoe@fakemail.com", pwd = "password";
        int role = 1;
        Batch batch = new Batch();
        String phone = "555-5555-5556", phone2 = null, skype = null;
        String pwd2 = null;
        Integer AssignForceId = 2;

        //objects
        BamUser testUser1 = new BamUser(fName, mName, lName, email, pwd, role, batch, phone, phone2, skype, pwd2);
        BamUser testUser2 = new BamUser(userId, fName, mName, lName, email, pwd, role, batch, phone, phone2, skype, pwd2);
        BamUser testUser3 = new BamUser(userId, fName, mName, lName, email, pwd, role, batch, phone, phone2, skype, pwd2, AssignForceId);

        //test 1
        assertEquals(fName,         testUser1.getfName());
        assertEquals(mName,         testUser1.getmName());
        assertEquals(lName,         testUser1.getlName());
        assertEquals(email,         testUser1.getEmail());
        assertEquals(pwd,           testUser1.getPwd());
        assertEquals(role,          testUser1.getRole());
        assertEquals(batch,         testUser1.getBatch());
        assertEquals(phone,         testUser1.getPhone());
        assertEquals(phone2,        testUser1.getPhone2());
        assertEquals(skype,         testUser1.getSkype());
        assertEquals(pwd2,          testUser1.getPwd2());

        //test 2
        assertEquals(userId,        testUser2.getUserId());
        assertEquals(fName,         testUser2.getfName());
        assertEquals(mName,         testUser2.getmName());
        assertEquals(lName,         testUser2.getlName());
        assertEquals(email,         testUser2.getEmail());
        assertEquals(pwd,           testUser2.getPwd());
        assertEquals(role,          testUser2.getRole());
        assertEquals(batch,         testUser2.getBatch());
        assertEquals(phone,         testUser2.getPhone());
        assertEquals(phone2,        testUser2.getPhone2());
        assertEquals(skype,         testUser2.getSkype());
        assertEquals(pwd2,          testUser2.getPwd2());

        //test 3
        assertEquals(userId,        testUser3.getUserId());
        assertEquals(fName,         testUser3.getfName());
        assertEquals(mName,         testUser3.getmName());
        assertEquals(lName,         testUser3.getlName());
        assertEquals(email,         testUser3.getEmail());
        assertEquals(pwd,           testUser3.getPwd());
        assertEquals(role,          testUser3.getRole());
        assertEquals(batch,         testUser3.getBatch());
        assertEquals(phone,         testUser3.getPhone());
        assertEquals(phone2,        testUser3.getPhone2());
        assertEquals(skype,         testUser3.getSkype());
        assertEquals(pwd2,          testUser3.getPwd2());
        assertEquals(AssignForceId, testUser3.getAssignForceID());
    }
}

