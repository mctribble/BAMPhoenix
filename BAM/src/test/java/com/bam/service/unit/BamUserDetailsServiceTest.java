package com.bam.service.unit;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.repository.BamUserRepository;
import com.bam.service.BamUserDetailsService;
import com.bam.service.BamUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class BamUserDetailsServiceTest
{
    @Mock
    private BamUserService userService;

    @Mock
    private BamUserRepository bamUserRepository;

    @InjectMocks
    private BamUserDetailsService userDetailsService;

    //test data
    private BamUser testTrainer;
    private BatchType testBatchType;
    private Batch     testBatch;
    private BamUser testAssociate1;
    private BamUser testAssociate2;
    private BamUser testAssociate3;
    private List<BamUser> testUsersWithNoBatch;

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
                null,                                     //current batch (associates only!)
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

        testUsersWithNoBatch = new ArrayList<>();
        testUsersWithNoBatch.add(testTrainer);
        testUsersWithNoBatch.add(testAssociate2);
        testUsersWithNoBatch.add(testAssociate3);

        when(userService.findUserByEmail(testTrainer.getEmail())).thenReturn(testTrainer);
        when(userService.findUserByEmail(testAssociate1.getEmail())).thenReturn(testAssociate1);
        when(userService.findUserByEmail(testAssociate2.getEmail())).thenReturn(testAssociate2);
        when(userService.findUserByEmail(testAssociate3.getEmail())).thenReturn(testAssociate3);

        when(bamUserRepository.findByBatch(null)).thenReturn(testUsersWithNoBatch);
    }

    //happy path
    @Test
    public void findUsersNotInBatch() throws Exception
    {
        List<BamUser> result = userDetailsService.findUsersNotInBatch();

        assertNotNull(result); //result must not be null

        //all returned users must be associates
        for (BamUser u : result)
            if (u.getRole() != 1)
                fail();

        //given the data above, there should be two returned
        assert(result.size() == 2);
    }

    //happy path
    @Test
    public void loadUserByUsername() throws Exception
    {
        UserDetails result = userDetailsService.loadUserByUsername(testAssociate1.getEmail());

        //result should not be null
        assertNotNull(result);

        //user should have at least one authority
        assert(result.getAuthorities().isEmpty() == false);

        //assure properties that the user is given during service construction are maintained
        assert(result.isEnabled() && result.isAccountNonExpired() && result.isAccountNonLocked() && result.isCredentialsNonExpired());
    }

    //user doesnt exist
    @Test
    public void loadNonexistantUser() throws Exception
    {
        UserDetails result = userDetailsService.loadUserByUsername("not an email");
    }
}