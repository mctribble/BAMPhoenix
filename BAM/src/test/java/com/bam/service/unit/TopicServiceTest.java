package com.bam.service.unit;

import com.bam.bean.*;
import com.bam.repository.BatchRepository;
import com.bam.repository.TopicNameRepository;
import com.bam.repository.TopicWeekRepository;
import com.bam.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TopicServiceTest
{
    @Mock
    private TopicWeekRepository topicWeekRepository;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private TopicNameRepository topicNameRepository;

    @InjectMocks
    private TopicService topicService;

    //test data
    private BamUser testUser;
    private BatchType testBatchType;
    private Batch testBatch;
    private TopicName testTopicName;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        testUser = new BamUser(
                1,                                         //userId
                "August", null, "Duet",      //names
                "AugustDuet@fakemail.com", "password", //login info
                2,                                           //role
                null,                                       //current batch (associates only!)
                "555-5555-5555", null, null,   //contact info
                null,                                       //for password resets
                1                                     //ID of the same user in assignForce
        );

        testBatchType = new BatchType(1, "Test Type", 10);

        testBatch = new Batch
                (
                        1,                                                                          //ID
                        "Test1",                                                                 //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        testUser,                                                              //trainer for the batch
                        testBatchType                                                                  //batch type
                );

        testTopicName = new TopicName(1, "unit testing");

        //mock responses
        when(batchRepository.findById(testBatch.getId())).thenReturn(testBatch);

        when(topicNameRepository.findById(testTopicName.getId())).thenReturn(testTopicName);

        when(topicWeekRepository.save(any(TopicWeek.class))).thenReturn(null); //I think this is the default behavior, but I would rather specify it explicitly
    }

    //happy path
    @Test
    public void addTopic() throws Exception
    {
        topicService.addTopic(testTopicName.getId(), testBatch.getId(), 1);
    }

    //bad topic name
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addTopicBadName() throws Exception
    {
        topicService.addTopic(0, testBatch.getId(), 1);
    }

    //bad batch
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addTopicBadId() throws Exception
    {
        topicService.addTopic(testTopicName.getId(), 0, 1);
    }

    //bad week
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addTopicBadWeek() throws Exception
    {
        topicService.addTopic(testTopicName.getId(), testBatch.getId(), -1);
    }

    //happy path
    @Test
    public void getTopicByBatch() throws Exception
    {
        
    }

    @Test
    public void getTopicByBatchId() throws Exception
    {
    }

    @Test
    public void getTopics() throws Exception
    {
    }

    @Test
    public void addOrUpdateTopicName() throws Exception
    {
    }

    @Test
    public void getTopicName() throws Exception
    {
    }

}