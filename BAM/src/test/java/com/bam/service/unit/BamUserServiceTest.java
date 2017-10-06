package com.bam.service.unit;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.repository.BamUserRepository;
import com.bam.repository.BatchRepository;
import com.bam.service.BamUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BamUserServiceTest
{
    @Mock
    private BamUserRepository userRepository;

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private BamUserService userService;

    //test data
    private BamUser testTrainer;
    private BatchType testBatchType;
    private Batch testBatch;
    private BamUser testAssociate1;
    private BamUser testAssociate2;
    private BamUser testAssociate3;
    private List<BamUser> testUsersInBatch;
    private List<BamUser> testUsersNotInBatch;
    private List<BamUser> testUsersAssociates;
    private List<BamUser> testUsersTrainers;
    private List<BamUser> testUsersAll;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        //trainer
        //associates
        testTrainer = new BamUser(
                1,                                         //userId
                "August", null, "Duet",      //names
                "AugustDuet@fakemail.com", "password", //login info
                2,                                           //role
                null,                                       //current batch (associates only!)
                "555-5555-5555", null, null,   //contact info
                null,                                       //for password resets
                1                                     //ID of the same user in assignForce
        );

        //batch type
        testBatchType = new BatchType(1, "TEST", 10);

        //batch
        testBatch = new Batch
                (
                        1,                                                                          //ID
                        "Test",                                                                  //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        testTrainer,                                                                   //trainer for the batch
                        testBatchType                                                                  //batch type
                );

        //associates
        testAssociate1 = new BamUser
                (
                        2,                                      //userId
                        "John", null, "Doe",      //names
                        "JohnDoe@fakemail.com", "password",  //login info
                        1,                                         //role
                        testBatch,                                     //current batch (associates only!)
                        "555-5555-5556", null, null, //contact info
                        null,                                     //for password resets
                        2                                   //ID of the same user in assignForce
                );

        testAssociate2 = new BamUser
                (
                        3,                                      //userId
                        "Jane", null, "Doe",      //names
                        "JaneDoe@fakemail.com", "password",  //login info
                        1,                                         //role
                        testBatch,                                     //current batch (associates only!)
                        "555-5555-5557", "555-5555-5558", null, //contact info
                        null,                                     //for password resets
                        3                                   //ID of the same user in assignForce
                );

        testAssociate3 = new BamUser
                (
                        4,                                      //userId
                        "Some", "Fake", "Name",    //names
                        "SomeName@someMail.ws", "pa$$w0rd",  //login info
                        1,                                         //role
                        null,                                     //current batch (associates only!)
                        "555-5555-5559", null, null, //contact info
                        null,                                     //for password resets
                        4                                   //ID of the same user in assignForce
                );

        testUsersInBatch = new ArrayList<>();
        testUsersInBatch.add(testAssociate1);
        testUsersInBatch.add(testAssociate2);

        testUsersNotInBatch = new ArrayList<>();
        testUsersNotInBatch.add(testAssociate3);
        testUsersNotInBatch.add(testTrainer);

        testUsersAssociates = new ArrayList<>();
        testUsersAssociates.add(testAssociate1);
        testUsersAssociates.add(testAssociate2);
        testUsersAssociates.add(testAssociate3);

        testUsersTrainers = new ArrayList<>();
        testUsersTrainers.add(testTrainer);

        testUsersAll = new ArrayList<>();
        testUsersAll.add(testAssociate1);
        testUsersAll.add(testAssociate2);
        testUsersAll.add(testAssociate3);
        testUsersAll.add(testTrainer);

        when(batchRepository.findById(testBatch.getId())).thenReturn(testBatch);

        when(userRepository.findByUserId(testAssociate1.getUserId())).thenReturn(testAssociate1);
        when(userRepository.findByUserId(testAssociate2.getUserId())).thenReturn(testAssociate2);
        when(userRepository.findByUserId(testAssociate3.getUserId())).thenReturn(testAssociate3);
        when(userRepository.findByUserId(testTrainer.getUserId())).thenReturn(testTrainer);
        when(userRepository.findByEmail(testAssociate1.getEmail())).thenReturn(testAssociate1);
        when(userRepository.findByEmail(testAssociate2.getEmail())).thenReturn(testAssociate2);
        when(userRepository.findByEmail(testAssociate3.getEmail())).thenReturn(testAssociate3);
        when(userRepository.findByEmail(testTrainer.getEmail())).thenReturn(testTrainer);
        when(userRepository.findByBatch(testBatch)).thenReturn(testUsersInBatch);
        when(userRepository.findByBatch(null)).thenReturn(testUsersNotInBatch);
        when(userRepository.findByRole(1)).thenReturn(testUsersAssociates);
        when(userRepository.findByRole(2)).thenReturn(testUsersTrainers);
        when(userRepository.findAll()).thenReturn(testUsersAll);
        when(userRepository.findByFNameAndLName(testAssociate1.getfName(), testAssociate1.getlName())).thenAnswer(invocationOnMock -> { List<BamUser> l = new ArrayList<>(); l.add(testAssociate1); return l; }); //this just creates a list with only one user: testAssociate1.
    }

    //happy path
    @Test
    public void addOrUpdateUser() throws Exception
    {
        userService.addOrUpdateUser(testAssociate1);
    }

    //save null should throw an exception
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addOrUpdateUserNull() throws Exception
    {
        userService.addOrUpdateUser(null);
    }

    //happy path
    @Test
    public void findAllUsers() throws Exception
    {
        List<BamUser> result = userService.findAllUsers();    //the result must...
        assertThat(result, notNullValue());                   //not be null
        assertThat(result, hasSize(testUsersAll.size()));     //have the expected number of items
        assertThat(result, containsInAnyOrder(testUsersAll.toArray())); //contain all expected items
    }

    //happy path
    @Test
    public void findByRole() throws Exception
    {
        List<BamUser> result = userService.findByRole(1);            //the result must...
        assertThat(result, notNullValue());                          //not be null
        assertThat(result, hasSize(testUsersAssociates.size()));     //have the expected number of items
        assertThat(result, containsInAnyOrder(testUsersAssociates.toArray())); //contain all expected items

        result = userService.findByRole(2);                        //the result must...
        assertThat(result, notNullValue());                        //not be null
        assertThat(result, hasSize(testUsersTrainers.size()));     //have the expected number of items
        assertThat(result, containsInAnyOrder(testUsersTrainers.toArray())); //contain all expected items
    }

    //bad role should return empty list
    @Test
    public void findByRoleBadRole() throws Exception
    {
        List<BamUser> result = userService.findByRole(0);
        assertThat(result, notNullValue());
        assertThat(result, hasSize(0));
    }

    //happy path
    @Test
    public void findUserById() throws Exception
    {
        assertThat(userService.findUserById(testAssociate1.getUserId()), is(testAssociate1));
        assertThat(userService.findUserById(testAssociate2.getUserId()), is(testAssociate2));
        assertThat(userService.findUserById(testAssociate3.getUserId()), is(testAssociate3));
        assertThat(userService.findUserById(testTrainer.getUserId()), is(testTrainer));
    }

    //bad id
    @Test
    public void findUserByIdBadId() throws Exception
    {
        assertThat(userService.findUserById(0), nullValue());
    }

    //happy path
    @Test
    public void findUserByEmail() throws Exception
    {
        assertThat(userService.findUserByEmail(testAssociate1.getEmail()), is(testAssociate1));
        assertThat(userService.findUserByEmail(testAssociate2.getEmail()), is(testAssociate2));
        assertThat(userService.findUserByEmail(testAssociate3.getEmail()), is(testAssociate3));
        assertThat(userService.findUserByEmail(testTrainer.getEmail()), is(testTrainer));
    }

    //bad id
    @Test
    public void findUserByEmailBadEmail() throws Exception
    {
        assertThat(userService.findUserByEmail("not a real email"), nullValue());
    }

    //happy path
    @Test
    public void findUsersInBatch() throws Exception
    {
        List<BamUser> result = userService.findUsersInBatch(testBatch.getId());

        assertNotNull(result); //result must not be null

        //all returned users must be associates
        for (BamUser u : result)
            if (u.getRole() != 1)
                fail();

        //given the data above, there should be two returned
        assertEquals(2, result.size());

        //everything returned should be in the original list
        assertThat(testUsersInBatch, containsInAnyOrder(result.toArray()));
    }

    @Test
    public void findUsersNotInBatch() throws Exception
    {
        List<BamUser> result = userService.findUsersNotInBatch();

        assertNotNull(result); //result must not be null

        //all returned users must be associates
        for (BamUser u : result)
            if (u.getRole() != 1)
                fail();

        //given the data above, there should be one returned
        assertEquals(1, result.size());

        //everything returned should be in the original list
        assertThat(testUsersNotInBatch, containsInAnyOrder(result.toArray()));
    }

    //make sure an exception is thrown if this gets null: it shouldnt pretend to succeed.
    @Test(expected = NullPointerException.class)
    public void recoverENullArgs()
    {
        userService.recoverE(null, "swordfish");
    }

    //make sure an exception is thrown if this user's email is invalid: it shouldn't pretend to succeed.
    @Test (expected = Exception.class)
    public void recoverEBadEmail()
    {
        String goodEmail = testAssociate1.getEmail();
        String badEmail = "this is not an email!";
        testAssociate1.setEmail(badEmail);
        try
        {
            userService.recoverE(testAssociate1, "swordfish");
        }
        finally
        {
            testAssociate1.setEmail(goodEmail);
        }
    }

    //happy path
    @Test
    public void getByFNameAndLName() throws Exception
    {
        List<BamUser> result = userService.getByFNameAndLName(testAssociate1.getfName(), testAssociate1.getlName());
        assertThat(result, notNullValue());
        assertThat(result, hasSize(1));
        assertThat(result, contains(testAssociate1));
    }
}