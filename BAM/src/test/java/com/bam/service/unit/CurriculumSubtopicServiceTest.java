package com.bam.service.unit;

import com.bam.bean.*;
import com.bam.repository.CurriculumSubtopicRepository;
import com.bam.service.CurriculumSubtopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class CurriculumSubtopicServiceTest
{
    @Mock
    private CurriculumSubtopicRepository curriculumSubtopicRepository;

    @InjectMocks
    private CurriculumSubtopicService curriculumSubtopicService;

    //test data
    private BamUser testCreator;
    private Curriculum testCurriculum;
    private TopicName testTopicName;
    private SubtopicType testSubtopicType;
    private SubtopicName testSubtopicName1;
    private SubtopicName testSubtopicName2;
    private SubtopicName testSubtopicName3;
    private List<SubtopicName> testSubtopicNames;
    private SubtopicStatus testPendingStatus;
    private CurriculumSubtopic testSubtopic1;
    private CurriculumSubtopic testSubtopic2;
    private CurriculumSubtopic testSubtopic3;
    private List<CurriculumSubtopic> testSubtopics;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        //a user to serve as the creator for the curriculums
        testCreator = new BamUser(
                1,                                         //userId
                "August", null, "Duet",      //names
                "AugustDuet@fakemail.com", "password", //login info
                2,                                           //role
                null,                                       //current batch (associates only!)
                "555-5555-5555", null, null,   //contact info
                "changeMe",                                       //for password resets
                1                                     //ID of the same user in assignForce
        );

        //curriculum
        testCurriculum = new Curriculum();
        testCurriculum.setCurriculumId(1);
        testCurriculum.setIsMaster(0); //this field is an int, but every usage I found just checks for 0 or 1.
        testCurriculum.setCurriculumCreator(testCreator);
        testCurriculum.setCurriculumdateCreated("01/15/201");
        testCurriculum.setCurriculumName("Test Name 1");
        testCurriculum.setCurriculumNumberOfWeeks(6);
        testCurriculum.setCurriculumVersion(1);
        testCurriculum.setCurriculumModifier(null);

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
        testSubtopic1 = new CurriculumSubtopic(1, testSubtopicName1, testCurriculum, 1, 1);
        testSubtopic2 = new CurriculumSubtopic(2, testSubtopicName2, testCurriculum, 2, 2);
        testSubtopic3 = new CurriculumSubtopic(3, testSubtopicName3, testCurriculum, 3, 3);
        testPendingStatus = new SubtopicStatus(1, "Pending");
        testSubtopics = new ArrayList<>();
        testSubtopics.add(testSubtopic1);
        testSubtopics.add(testSubtopic2);
        testSubtopics.add(testSubtopic3);

        when(curriculumSubtopicRepository.findByCurriculum(testCurriculum)).thenReturn(testSubtopics);
        when(curriculumSubtopicRepository.save(any(CurriculumSubtopic.class))).thenReturn(null); //I think this is default behavior anyway, but I would rather specify it explicitly
    }

    //happy path
    @Test
    public void getCurriculumSubtopicForCurriculum() throws Exception
    {
        List<CurriculumSubtopic> result = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(testCurriculum); //the result must...
        assertThat(result, notNullValue());                                                                             //not be null
        assertThat(result, hasSize(testSubtopics.size()));                                                              //have the expected number of items
        assertThat(result, containsInAnyOrder(testSubtopics.toArray()));                                                //contain all expected items
    }

    //null arg
    @Test
    public void getCurriculumSubtopicForCurriculumNullArg() throws Exception
    {
        List<CurriculumSubtopic> result = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(null); //the result must...
        assertThat(result, notNullValue());                                                                     //not be null
        assertThat(result, hasSize(0));                                                                         //and be empty
    }

    //nonexistant arg
    @Test
    public void getCurriculumSubtopicForCurriculumBadArg() throws Exception
    {
        Curriculum badCurriculum = new Curriculum();
        badCurriculum.setCurriculumId(0);

        List<CurriculumSubtopic> result = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(badCurriculum); //the result must...
        assertThat(result, notNullValue());                                                                            //not be null
        assertThat(result, hasSize(0));                                                                                //and be empty
    }

    //happy path
    @Test
    public void saveCurriculumSubtopic() throws Exception
    {
        curriculumSubtopicService.saveCurriculumSubtopic(testSubtopic1);
    }

    //save null should throw an exception
    @Test (expected = RuntimeException.class) //realistically this should probably be either NullPointerException or IllegalArgumentException, but any runtimeException will do
    public void saveNullCurriculumSubtopic() throws Exception
    {
        curriculumSubtopicService.saveCurriculumSubtopic(null);
    }
}