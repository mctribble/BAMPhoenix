package com.bam.service.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bam.repository.SubtopicNameRepository;
import com.bam.repository.SubtopicRepository;
import com.bam.service.SubtopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.repository.BatchRepository;
import com.bam.repository.BatchTypeRepository;
import com.bam.service.BatchService;

/**
 * Author: Christopher Hake
 * 	Test cases of the BatchService class.
 */
public class BatchServiceTest
{
	@Mock
	private BatchRepository batchRepository;

	@Mock
	private BatchTypeRepository batchTypeRepository;

	@Mock
	private SubtopicRepository subtopicRepository;

	@Mock
	private SubtopicNameRepository subtopicNameRepository;

	@Mock
	private SubtopicService subtopicService;

	@InjectMocks
	private BatchService batchService;

	//test data
	private BamUser testTrainer1;
	private BamUser testTrainer2;
	private BatchType testBatchType1;
	private BatchType testBatchType2;
	private Batch testBatch1;
	private Batch testBatch2;
	private List<Batch> allBatches;
	private List<Batch> batchesWithTrainer1;
	private List<BatchType> allBatchTypes;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);

		//trainer
		//associates
		testTrainer1 = new BamUser(
				1,                                         //userId
				"August", null, "Duet",      //names
				"AugustDuet@fakemail.com", "password", //login info
				2,                                           //role
				null,                                       //current batch (associates only!)
				"555-5555-5555", null, null,   //contact info
				null,                                       //for password resets
				1                                     //ID of the same user in assignForce
		);

		testTrainer2 = new BamUser(
				2,                                         //userId
				"Some", "Other", "Trainer",  //names
				"someTrainer@fakemail.ws", "password", //login info
				2,                                           //role
				null,                                       //current batch (associates only!)
				"555-5555-5556", null, null,   //contact info
				null,                                       //for password resets
				2                                     //ID of the same user in assignForce
		);

		//batch types
		testBatchType1 = new BatchType(1, "TEST1", 10);
		testBatchType2 = new BatchType(2, "TEST2", 10);

		//batch
		testBatch1 = new Batch
				(
						1,                                                                          //ID
						"Test1",                                                                  //name
						new Timestamp(2015, 1, 1, 0, 0, 0, 0), //start date
						new Timestamp(2015, 4, 1, 0, 0, 0, 0), //end date
						testTrainer1,                                                                   //trainer for the batch
						testBatchType1                                                                  //batch type
				);

		testBatch2 = new Batch
				(
						2,                                                                          //ID
						"Test2",                                                                  //name
						new Timestamp(2015, 5, 1, 0, 0, 0, 0), //start date
						new Timestamp(2015, 8, 1, 0, 0, 0, 0), //end date
						testTrainer2,                                                                   //trainer for the batch
						testBatchType1                                                                  //batch type
				);

		allBatches = new ArrayList<>();
		allBatches.add(testBatch1);
		allBatches.add(testBatch2);

		batchesWithTrainer1 = new ArrayList<>();
		batchesWithTrainer1.add(testBatch1);

		when(batchRepository.findById(testBatch1.getId())).thenReturn(testBatch1);
		when(batchRepository.findById(testBatch2.getId())).thenReturn(testBatch2);
		when(batchRepository.findAll()).thenReturn(allBatches);
		when(batchRepository.findByTrainer(testTrainer1)).thenReturn(batchesWithTrainer1);
		when(batchTypeRepository.findAll()).thenReturn(allBatchTypes);
	}

	//cant update null
	@Test (expected = IllegalArgumentException.class)
	public void ExceptionUpdatingNullBatch()
	{
		batchService.addOrUpdateBatch(null);
	}

	//happy path
	@Test
	public void getBatchByIdTest()
	{
		assertThat(batchService.getBatchById(testBatch1.getId()), is(testBatch1));
	}

	//happy bath
	@Test
	public void getBatchAllTest()
	{
		List<Batch> result = batchRepository.findAll();		//the result must...
		assertThat(result, notNullValue());					//not be null
		assertThat(result, hasSize(allBatches.size()));		//have the expected number of items
		assertThat(result, containsInAnyOrder(allBatches)); //have all the expected items
	}

	//happy path
	@Test
	public void getBatchByTrainerTest()
	{
		List<Batch> result = batchRepository.findByTrainer(testTrainer1); //the result must...
		assertThat(result, notNullValue());								  //not be null
		assertThat(result, hasSize(1));									  //have the expected number of items
		assertThat(result, contains(testBatch1)); 			  			  //have the first test batch
		assertThat(result, not(contains(testBatch2)));				  	  //but not the second
	}

	//happy path
	@Test
	public void getAllBatchTypesTest(){
		List<Batch> result = batchRepository.findByTrainer(testTrainer1); //the result must...
		assertThat(result, notNullValue());								  //not be null
		assertThat(result, hasSize(2));									  //have the expected number of items
		assertThat(result, containsInAnyOrder(allBatchTypes)); 			  //have all expected items
	}

	
}
