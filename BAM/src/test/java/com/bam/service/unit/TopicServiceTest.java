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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
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
    private Batch testBatchWithTopics;
    private Batch testBatchEmpty;
    private TopicName testTopicName1;
    private TopicName testTopicName2;
    private TopicName testTopicName3;
    private TopicName testTopicName4;
    private List<TopicName> testTopicNames;
    private TopicWeek testTopicWeek1;
    private TopicWeek testTopicWeek2;
    private TopicWeek testTopicWeek3;
    private TopicWeek testTopicWeek4;
    private List<TopicWeek> topicWeeksInTestBatch;
    private List<TopicWeek> topicWeeksInNoBatch;

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

        testBatchWithTopics = new Batch
                (
                        1,                                                                          //ID
                        "Test1",                                                                 //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        testUser,                                                              //trainer for the batch
                        testBatchType                                                                  //batch type
                );

        testBatchEmpty = new Batch
                (
                        2,                                                                          //ID
                        "Test2",                                                                 //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        testUser,                                                              //trainer for the batch
                        testBatchType                                                                  //batch type
                );

        //topic names
        testTopicName1 = new TopicName(1, "test topic 1");
        testTopicName2 = new TopicName(2, "test topic 2");
        testTopicName3 = new TopicName(3, "test topic 3");
        testTopicName4 = new TopicName(4, "test topic 4");
        testTopicNames = new ArrayList<>();
        testTopicNames.add(testTopicName1);
        testTopicNames.add(testTopicName2);
        testTopicNames.add(testTopicName3);
        testTopicNames.add(testTopicName4);

        //topics
        testTopicWeek1 = new TopicWeek(1, testTopicName1, testBatchWithTopics, 1);
        testTopicWeek2 = new TopicWeek(2, testTopicName2, testBatchWithTopics, 2);
        testTopicWeek3 = new TopicWeek(3, testTopicName3, testBatchWithTopics, 3);
        topicWeeksInTestBatch = new ArrayList<>();
        topicWeeksInTestBatch.add(testTopicWeek1);
        topicWeeksInTestBatch.add(testTopicWeek2);
        topicWeeksInTestBatch.add(testTopicWeek3);

        testTopicWeek4 = new TopicWeek(4, testTopicName4, testBatchWithTopics, 4);
        topicWeeksInNoBatch = new ArrayList<>();
        topicWeeksInNoBatch.add(testTopicWeek4);

        //mock responses
        when(batchRepository.findById(testBatchWithTopics.getId())).thenReturn(testBatchWithTopics);

        when(topicNameRepository.findById(testTopicName1.getId())).thenReturn(testTopicName1);
        when(topicNameRepository.findById(testTopicName2.getId())).thenReturn(testTopicName2);
        when(topicNameRepository.findById(testTopicName3.getId())).thenReturn(testTopicName3);
        when(topicNameRepository.findById(testTopicName4.getId())).thenReturn(testTopicName4);
        when(topicNameRepository.findAll()).thenReturn(testTopicNames);
        when(topicNameRepository.save(any(TopicName.class))).thenReturn(null); //I think this is the default behavior, but I would rather specify it explicitly

        when(topicWeekRepository.findByBatch(testBatchWithTopics)).thenReturn(topicWeeksInTestBatch);
        when(topicWeekRepository.findByBatch(null)).thenReturn(topicWeeksInNoBatch);
        when(topicWeekRepository.save(any(TopicWeek.class))).thenReturn(null); //I think this is the default behavior, but I would rather specify it explicitly
    }

    //happy path
    @Test
    public void addTopic() throws Exception
    {
        topicService.addTopic(testTopicName1.getId(), testBatchWithTopics.getId(), 1);
    }

    //bad topic name
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addTopicBadName() throws Exception
    {
        topicService.addTopic(0, testBatchWithTopics.getId(), 1);
    }

    //bad batch
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addTopicBadId() throws Exception
    {
        topicService.addTopic(testTopicName1.getId(), 0, 1);
    }

    //bad week
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addTopicBadWeek() throws Exception
    {
        topicService.addTopic(testTopicName1.getId(), testBatchWithTopics.getId(), -1);
    }

    //happy path
    @Test
    public void getTopicByBatch() throws Exception
    {
        List<TopicWeek> result = topicService.getTopicByBatch(testBatchWithTopics); //the result must...
        assertThat(result, notNullValue());                                         //not be null
        assertThat(result, hasSize(topicWeeksInTestBatch.size()));                  //be the expected size
        assertThat(result, containsInAnyOrder(topicWeeksInTestBatch.toArray()));    //contain all expected items

        result = topicService.getTopicByBatch(null);                             //the result must...
        assertThat(result, notNullValue());                                      //not be null
        assertThat(result, hasSize(topicWeeksInNoBatch.size()));                 //be the expected size
        assertThat(result, containsInAnyOrder(topicWeeksInNoBatch.toArray()));   //contain all expected items
    }

    //happy path
    @Test
    public void getTopicByBatchId() throws Exception
    {
        List<TopicWeek> result = topicService.getTopicByBatchId(testBatchWithTopics.getId()); //the result must...
        assertThat(result, notNullValue());                                                   //not be null
        assertThat(result, hasSize(topicWeeksInTestBatch.size()));                            //be the expected size
        assertThat(result, containsInAnyOrder(topicWeeksInTestBatch.toArray()));              //contain all expected items

        result = topicService.getTopicByBatchId(0);                              //the result must...
        assertThat(result, notNullValue());                                      //not be null
        assertThat(result, hasSize(topicWeeksInNoBatch.size()));                 //be the expected size
        assertThat(result, containsInAnyOrder(topicWeeksInNoBatch.toArray()));   //contain all expected items
    }

    //happy path
    @Test
    public void getTopics() throws Exception
    {
        List<TopicName> result = topicService.getTopics();                //the result must...
        assertThat(result, notNullValue());                               //not be null
        assertThat(result, hasSize(testTopicNames.size()));               //be the expected size
        assertThat(result, containsInAnyOrder(testTopicNames.toArray())); //contain all expected items
    }

    //happy path
    @Test
    public void addOrUpdateTopicName() throws Exception
    {
        topicService.addOrUpdateTopicName(testTopicName1);
        topicService.addOrUpdateTopicName(testTopicName2);
        topicService.addOrUpdateTopicName(testTopicName3);
        topicService.addOrUpdateTopicName(testTopicName4);
    }

    //save null should throw an exception
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addOrUpdateTopicNameNull() throws Exception
    {
        topicService.addOrUpdateTopicName(null);
    }

    //happy path
    @Test
    public void getTopicName() throws Exception
    {
        assertThat(topicService.getTopicName(testTopicName1.getId()), is(testTopicName1));
        assertThat(topicService.getTopicName(testTopicName2.getId()), is(testTopicName2));
        assertThat(topicService.getTopicName(testTopicName3.getId()), is(testTopicName3));
        assertThat(topicService.getTopicName(testTopicName4.getId()), is(testTopicName4));
    }

}