package com.bam.service.unit;

import com.bam.bean.*;
import com.bam.repository.*;
import com.bam.service.SubtopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

public class SubtopicServiceTest
{
    @Mock private SubtopicRepository        subtopicRepository;
    @Mock private BatchRepository           batchRepository;
    @Mock private SubtopicNameRepository    subtopicNameRepository;
    @Mock private SubtopicStatusRepository  subtopicStatusRepository;
    @Mock private SubtopicTypeRepository    subtopicTypeRepository;

    @InjectMocks
    private SubtopicService subtopicService;

    private BamUser            testBatchCreator;
    private Batch              testBatch;
    private BatchType          testBatchType;
    private TopicName          testTopicName;
    private SubtopicType       testSubtopicType;
    private SubtopicName       testSubtopicName1;
    private SubtopicName       testSubtopicName2;
    private SubtopicName       testSubtopicName3;
    private List<SubtopicName> testSubtopicNames;
    private SubtopicStatus     testSubtopicStatusPending;
    private Subtopic           testSubtopic1;
    private Subtopic           testSubtopic2;
    private Subtopic           testSubtopic3;
    private List<Subtopic>     testSubtopics;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        testBatchCreator = new BamUser(
                1,                                         //userId
                "August", null, "Duet",      //names
                "AugustDuet@fakemail.com", "password", //login info
                2,                                           //role
                null,                                       //current batch (associates only!)
                "555-5555-5555", null, null,   //contact info
                null,                                       //for password resets
                1                                     //ID of the same user in assignForce
        );

        testBatchType = new BatchType(1, "Test", 10);

        testBatch = new Batch
                (
                        1,                                                                          //ID
                        "Test1",                                                                 //name
                        new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
                        new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
                        testBatchCreator,                                                              //trainer for the batch
                        testBatchType                                                                  //batch type
                );

        //subtopic names
        testTopicName = new TopicName(1, "unit testing");
        testSubtopicType = new SubtopicType(1, "testing subtopic");
        testSubtopicName1 = new SubtopicName(1, "Test topic 1", testTopicName, testSubtopicType);
        testSubtopicName2 = new SubtopicName(2, "Test topic 2", testTopicName, testSubtopicType);
        testSubtopicName3 = new SubtopicName(3, "Test topic 3", testTopicName, testSubtopicType);
        testSubtopicNames = new ArrayList<>();
        testSubtopicNames.add(testSubtopicName1);
        testSubtopicNames.add(testSubtopicName2);
        testSubtopicNames.add(testSubtopicName3);

        //subtopics
        testSubtopic1 = new Subtopic(testSubtopicName1, testBatch, testSubtopicStatusPending, new Timestamp(2015, 1, 15, 0, 0, 0, 0));
        testSubtopic2 = new Subtopic(testSubtopicName2, testBatch, testSubtopicStatusPending, new Timestamp(2015, 1, 16, 0, 0, 0, 0));
        testSubtopic3 = new Subtopic(testSubtopicName3, testBatch, testSubtopicStatusPending, new Timestamp(2015, 1, 17, 0, 0, 0, 0));
        testSubtopics = new ArrayList<>();
        testSubtopics.add(testSubtopic1);
        testSubtopics.add(testSubtopic2);
        testSubtopics.add(testSubtopic3);

        //subtopic status
        testSubtopicStatusPending = new SubtopicStatus(1, "Pending");

        //mock the calls
        when(batchRepository.findById(testBatch.getId())).thenReturn(testBatch);

        when(subtopicNameRepository.findById(testSubtopicName1.getId())).thenReturn(testSubtopicName1);
        when(subtopicNameRepository.findById(testSubtopicName2.getId())).thenReturn(testSubtopicName2);
        when(subtopicNameRepository.findById(testSubtopicName3.getId())).thenReturn(testSubtopicName3);
        when(subtopicNameRepository.findByName(testSubtopicName1.getName())).thenReturn(testSubtopicName1);
        when(subtopicNameRepository.findByName(testSubtopicName2.getName())).thenReturn(testSubtopicName2);
        when(subtopicNameRepository.findByName(testSubtopicName3.getName())).thenReturn(testSubtopicName3);
        when(subtopicNameRepository.findAll()).thenReturn(testSubtopicNames);
        when(subtopicNameRepository.save(any(SubtopicName.class))).thenReturn(null); //I believe this is the default behavior, but I would rather specify it explicitly

        when(subtopicStatusRepository.findById(1)).thenReturn(testSubtopicStatusPending);
        when(subtopicStatusRepository.findByName(testSubtopicStatusPending.getName())).thenReturn(testSubtopicStatusPending);

        when(subtopicRepository.findByBatch(testBatch)).thenReturn(testSubtopics);
        when(subtopicRepository.findByBatch(testBatch, new PageRequest(1, 10))).thenReturn(testSubtopics);
        when(subtopicRepository.countSubtopicsByBatchId(testBatch.getId())).thenReturn((long)testSubtopics.size());
        when(subtopicRepository.findAll()).thenReturn(testSubtopics);
        when(subtopicRepository.save(any(Subtopic.class))).thenReturn(null); //I believe this is the default behavior, but I would rather specify it explicitly

        when(subtopicTypeRepository.findById(testSubtopicType.getId())).thenReturn(testSubtopicType);
    }

    //happy path
    @Test
    public void addSubtopic() throws Exception
    {
        subtopicService.addSubtopic(testSubtopicName1.getId(), testBatch.getId());
        subtopicService.addSubtopic(testSubtopicName2.getId(), testBatch.getId());
        subtopicService.addSubtopic(testSubtopicName3.getId(), testBatch.getId());
    }

    //bad subtopic name id
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addSubtopicBadName() throws Exception
    {
        subtopicService.addSubtopic(0, testBatch.getId());
    }

    //bad batch id
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addSubtopicBadBatch() throws Exception
    {
        subtopicService.addSubtopic(testSubtopicName1.getId(), testBatch.getId());
    }

    //happy path
    @Test
    public void getSubtopicByBatch() throws Exception
    {
        List<Subtopic> result = subtopicService.getSubtopicByBatch(testBatch); //the result must...
        assertThat(result, notNullValue());                                    //not be null
        assertThat(result, hasSize(testSubtopics.size()));                     //be the expected size
        assertThat(result, containsInAnyOrder(testSubtopics.toArray()));       //contain all expected items
    }

    //null batch should return empty list
    @Test
    public void getSubtopicByBatchNullBatch() throws Exception
    {
        assertThat(subtopicService.getSubtopicByBatch(null), allOf(notNullValue(), hasSize(0)));
    }

    //happy path
    @Test
    public void getSubtopicByBatchId() throws Exception
    {
        List<Subtopic> result = subtopicService.getSubtopicByBatchId(testBatch.getId()); //the result must...
        assertThat(result, notNullValue());                                              //not be null
        assertThat(result, hasSize(testSubtopics.size()));                               //be the expected size
        assertThat(result, containsInAnyOrder(testSubtopics.toArray()));                 //contain all expected items
    }

    //bad id should return empty list
    @Test
    public void getSubtopicByBatchIdBadId() throws Exception
    {
        assertThat(subtopicService.getSubtopicByBatchId(0), allOf(notNullValue(), hasSize(0)));
    }

    //happy path
    @Test
    public void updateSubtopic() throws Exception
    {
        subtopicService.updateSubtopic(testSubtopic1);
    }

    //update null
    @Test (expected = NullPointerException.class)
    public void updateSubtopicNull() throws Exception
    {
        subtopicService.updateSubtopic(null);
    }

    //happy path
    @Test
    public void getStatus() throws Exception
    {
        assertThat(subtopicService.getStatus(testSubtopicStatusPending.getName()), is(testSubtopicStatusPending));
    }

    //non-existent status should return null
    @Test
    public void getStatusBadName() throws Exception
    {
        assertThat(subtopicService.getStatus("Not an actual status name"), nullValue());
    }

    //happy path
    @Test
    public void getNumberOfSubtopics() throws Exception
    {
        assertThat( subtopicService.getNumberOfSubtopics(testBatch.getId()).intValue(), equalTo(testSubtopics.size()));
    }

    //bad batch id
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void getNumberOfSubtopicsBadId() throws Exception
    {
        subtopicService.getNumberOfSubtopics(0);
    }

    //happy path
    @Test
    public void getAllSubtopics() throws Exception
    {
        List<SubtopicName> result = subtopicService.getAllSubtopics();       //the result must...
        assertThat(result, notNullValue());                                  //not be null
        assertThat(result, hasSize(testSubtopicNames.size()));               //be the expected size
        assertThat(result, containsInAnyOrder(testSubtopicNames.toArray())); //contain all expected items
    }

    //happy path
    @Test
    public void getSubtopics() throws Exception
    {
        List<Subtopic> result = subtopicService.getSubtopics();          //the result must...
        assertThat(result, notNullValue());                              //not be null
        assertThat(result, hasSize(testSubtopics.size()));               //be the expected size
        assertThat(result, containsInAnyOrder(testSubtopics.toArray())); //contain all expected items
    }

    //happy path
    @Test
    public void findByBatchId() throws Exception
    {
        List<Subtopic> result = subtopicService.findByBatchId(testBatch.getId(), new PageRequest(1, 10)); //the result must...
        assertThat(result, notNullValue());                                              //not be null
        assertThat(result, hasSize(testSubtopics.size()));                               //be the expected size
        assertThat(result, containsInAnyOrder(testSubtopics.toArray()));                 //contain all expected items
    }

    //happy path
    @Test
    public void getSubtopicName() throws Exception
    {
        assertThat(subtopicService.getSubtopicName(testSubtopicName1.getName()), is(testSubtopicName1));
        assertThat(subtopicService.getSubtopicName(testSubtopicName2.getName()), is(testSubtopicName2));
        assertThat(subtopicService.getSubtopicName(testSubtopicName3.getName()), is(testSubtopicName3));
    }

    //happy path
    @Test
    public void getSubtopicType() throws Exception
    {
        assertThat(subtopicService.getSubtopicType(testSubtopicType.getId()), is(testSubtopicType));
    }

    //bad id should return null
    @Test
    public void getSubtopicTypeBadId() throws Exception
    {
        assertThat(subtopicService.getSubtopicType(0), nullValue());
    }

    //happy path
    @Test
    public void addOrUpdateSubtopicName() throws Exception
    {
        subtopicService.addOrUpdateSubtopicName(testSubtopicName1);
    }

    //update null should throw exception
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void addOrUpdateSubtopicNameBadId() throws Exception
    {
        subtopicService.addOrUpdateSubtopicName(null);
    }

}