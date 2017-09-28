package com.bam.service.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.repository.BatchRepository;
import com.bam.repository.BatchTypeRepository;
import com.bam.service.BatchService;

/**
 * Author: Sarah Kummerfeldt
 * 	Test cases of the BatchService class.
 */
public class BatchServiceTest {
	
	@Mock
	BatchRepository batchRepository;
	
	@Mock
	BatchTypeRepository batchTypeRepository;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(BatchService.class).build();
	}
	
	@Test
	public void addOrUpdateBatchTest(){
		Batch batch = mock(Batch.class);
		Batch batchEntity = batchRepository.save(batch);
		batch.setId(263);
		
		Mockito.when(batchRepository.save(any(Batch.class))).thenReturn(batchEntity);
		
	}
	
	/**
	 *  Testing of the BatchService - getBatchByIdTest()
	 *  	returns a batch that matches the id
	 */
	@Test
	public void getBatchByIdTest() {		
		Batch returnBatch = new Batch();
		returnBatch.setId(263);
		
		Integer id = 263;
		Integer batchId = returnBatch.getId();
		assertThat(batchId, is(id));
		
	}

	/**
	 *  Testing of the BatchService - getBatchAllTest()
	 *  	returns all batches and tests that list is not null
	 */
	@Test
	public void getBatchAllTest(){
		List<Batch> returnBatches = (List<Batch>) batchRepository.findAll();
		
		assertNotNull(returnBatches);
		assertEquals(returnBatches, new ArrayList<Batch>());
	}

	/**
	 *  Testing of the BatchService - getBatchByTrainerTest()
	 *  	Checks if list created from trainer is null or returns data
	 */
	@Test
	public void getBatchByTrainerTest(){
		BamUser trainer = new BamUser();
		List<Batch> returnTrainerBatch = (List<Batch>) batchRepository.findByTrainer(trainer);
		assertNotNull(returnTrainerBatch);
		assertEquals(returnTrainerBatch, new ArrayList<Batch>());
	}

	/**
	 *  Testing of the BatchService - getAllBatchTypesTest()
	 *  	Checks for a list of all batch types
	 */
	@Test
	public void getAllBatchTypesTest(){
		List<BatchType> returnBatchType = (List<BatchType>) batchTypeRepository.findAll();
		
		assertNotNull(returnBatchType);
		assertEquals(returnBatchType, new ArrayList<BatchType>());
	}
		
}
