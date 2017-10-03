package com.bam.service.unit;

import com.bam.bean.BamUser;
import com.bam.bean.Curriculum;
import com.bam.repository.CurriculumRepository;
import com.bam.service.CurriculumService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CurriculumServiceTest
{
    @Mock
    private CurriculumRepository curriculumRepository;

    @InjectMocks
    private CurriculumService curriculumService;

    //test data
    private BamUser testCreator;
    private Curriculum testCurriculum1;
    private Curriculum testCurriculum2;
    private Curriculum testCurriculum3;
    private List<Curriculum> testCurriculums;
    private List<Curriculum> testCurriculumsName1;
    private List<Curriculum> testCurriculumsName2;

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
                null,                                       //for password resets
                1                                     //ID of the same user in assignForce
        );

        //curriculums
        testCurriculum1 = new Curriculum();
        testCurriculum1.setCurriculumId(1);
        testCurriculum1.setIsMaster(0); //this field is an int, but every usage I found just checks for 0 or 1.
        testCurriculum1.setCurriculumCreator(testCreator);
        testCurriculum1.setCurriculumdateCreated("01/15/201");
        testCurriculum1.setCurriculumName("Test Name 1");
        testCurriculum1.setCurriculumNumberOfWeeks(6);
        testCurriculum1.setCurriculumVersion(1);
        testCurriculum1.setCurriculumModifier(null);

        testCurriculum2 = new Curriculum();
        testCurriculum2.setCurriculumId(2);
        testCurriculum2.setIsMaster(0); //this field is an int, but every usage I found just checks for 0 or 1.
        testCurriculum2.setCurriculumCreator(testCreator);
        testCurriculum2.setCurriculumdateCreated("02/15/201");
        testCurriculum2.setCurriculumName("Test Name 1");
        testCurriculum2.setCurriculumNumberOfWeeks(6);
        testCurriculum2.setCurriculumVersion(1);
        testCurriculum2.setCurriculumModifier(null);

        testCurriculum3 = new Curriculum();
        testCurriculum3.setCurriculumId(3);
        testCurriculum3.setIsMaster(0); //this field is an int, but every usage I found just checks for 0 or 1.
        testCurriculum3.setCurriculumCreator(testCreator);
        testCurriculum3.setCurriculumdateCreated("03/15/201");
        testCurriculum3.setCurriculumName("Test Name 2");
        testCurriculum3.setCurriculumNumberOfWeeks(6);
        testCurriculum3.setCurriculumVersion(1);
        testCurriculum3.setCurriculumModifier(null);

        //lists
        testCurriculums = new ArrayList<>();
        testCurriculums.add(testCurriculum1);
        testCurriculums.add(testCurriculum2);
        testCurriculums.add(testCurriculum3);

        testCurriculumsName1 = new ArrayList<>();
        testCurriculumsName1.add(testCurriculum1);
        testCurriculumsName1.add(testCurriculum2);

        testCurriculumsName2 = new ArrayList<>();
        testCurriculumsName2.add(testCurriculum3);

        //setup the mocks
        when(curriculumRepository.findAll()).thenReturn(testCurriculums);
        when(curriculumRepository.findById(testCurriculum1.getCurriculumId())).thenReturn(testCurriculum1);
        when(curriculumRepository.findById(testCurriculum2.getCurriculumId())).thenReturn(testCurriculum2);
        when(curriculumRepository.findById(testCurriculum3.getCurriculumId())).thenReturn(testCurriculum3);
        when(curriculumRepository.findByCurriculumName(testCurriculum1.getCurriculumName())).thenReturn(testCurriculumsName1);
        when(curriculumRepository.findByCurriculumName(testCurriculum3.getCurriculumName())).thenReturn(testCurriculumsName2);
        when(curriculumRepository.save(Matchers.any(Curriculum.class))).thenReturn(null); //I believe this is default behavior anyway, but I would rather specify it explicitly
    }

    //happy path
    @Test
    public void getAllCurriculum() throws Exception
    {
        List<Curriculum> result = curriculumService.getAllCurriculum();    //the result must...
        assertThat(result, notNullValue());                                //not be null
        assertThat(result, hasSize(testCurriculums.size()));               //be the expected size
        assertThat(result, containsInAnyOrder(testCurriculums.toArray())); //contain all expected items
    }

    //happy path
    @Test
    public void getCuricullumById() throws Exception
    {
        Curriculum result = curriculumService.getCuricullumById(testCurriculum1.getCurriculumId());
        assertThat(result, equalTo(testCurriculum1));

        result = curriculumService.getCuricullumById(testCurriculum2.getCurriculumId());
        assertThat(result, equalTo(testCurriculum2));

        result = curriculumService.getCuricullumById(testCurriculum3.getCurriculumId());
        assertThat(result, equalTo(testCurriculum3));
    }

    //no curriculum with that id should return null
    @Test
    public void getCuricullumByIdBadId() throws Exception
    {
        assertThat(curriculumService.getCuricullumById(0), nullValue());
    }

    //happy path
    @Test
    public void save() throws Exception
    {
        curriculumService.save(testCurriculum1);
    }

    //save null should throw an exception instead of pretending to succeed
    @Test (expected = RuntimeException.class) //realistically it should probably be either NullPointerException or IllegalArgumentException, but any RuntimeException will do
    public void saveNull() throws Exception
    {
        curriculumService.save(null);
    }

    //happy path
    @Test
    public void findAllCurriculumByName() throws Exception
    {
        //curriculums 1 and 2 have the name "Test Name 1", and we expect both on this list
        List<Curriculum> result = curriculumService.findAllCurriculumByName(testCurriculum1.getCurriculumName()); //the result must...
        assertThat(result, notNullValue());                                                                       //not be null
        assertThat(result, hasSize(testCurriculumsName1.size()));                                                 //be the expected size
        assertThat(result, containsInAnyOrder(testCurriculumsName1.toArray()));                                   //contain all expected items

        //curriculum 3 has the name "Test Name 2", and we expect just that one on this list
        result = curriculumService.findAllCurriculumByName(testCurriculum3.getCurriculumName()); //the result must...
        assertThat(result, notNullValue());                                                      //not be null
        assertThat(result, hasSize(testCurriculumsName2.size()));                                //be the expected size
        assertThat(result, containsInAnyOrder(testCurriculumsName2.toArray()));                  //contain all expected items
    }

    //null arg should return empty list
    @Test
    public void findAllCurriculumByNameNull() throws Exception
    {
        assertThat(curriculumService.findAllCurriculumByName(null), hasSize(0));
    }

    //empty list if no curriculums by that name
    @Test
    public void findAllCurriculumByNameNonExistant() throws Exception
    {
        assertThat(curriculumService.findAllCurriculumByName("Not an actual name"), hasSize(0));
    }
}