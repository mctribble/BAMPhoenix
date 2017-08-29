package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bam.bean.AssignForceBatch;
import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;

/**
 * @author DillonT, GilB
 * assignForceSyncService pulls the most current information from AssignForce
 * and updates the BAM Batch, Users, and Batch_Type tables with the corresponding values.
 * It utilizes 3 Objects, AssignForceBatch, AssignForceCurriculum, and AssignForceTrainer.
 * These objects are used to hold data from a ResponseEntity, and are later used to 
 * set data in objects relevant to persistence in the BAM database.
 */
@Service
public class AssignForceSyncService {

  @Autowired
	BatchService bservice;
	
  @Autowired
	BamUserService uservice;
	
	static RestTemplate restTemplate = new RestTemplate();
	static String baseUrl = "http://assignforce.revaturelabs.com/api/v2/";
	
	// Makes a call to the AssignForce API and builds POJOs from the returned JSON String.
	static ResponseEntity<List<AssignForceBatch>> batches = restTemplate.exchange(baseUrl + "batch/",HttpMethod.GET,null, new ParameterizedTypeReference<List<AssignForceBatch>>(){});
	
	public void assignForceSync() {
		
		// Object to hold data from AssignForce
		AssignForceBatch batch;
		
		// Object which will be persisted; populated with AssignForceBatch data.
		Batch currentBatch = new Batch();
		
		// Object necessary for creation of batch; populated with AssignForceBatch data.
		BatchType type = new BatchType();
		
		// Object necessary for creation of batch; populated with AssignForceBatch data.
		BamUser bamUser;
		
		// Cycling through all batches received from AssignForce.
		for(int i = 0; i < batches.getBody().size(); i++) {
			
			// Current cycle's batch defined from batches Response Entity.
			batch = (AssignForceBatch) batches.getBody().get(i);
			
			// Filters our batches without trainers assigned.
			if(batch.getTrainer() != null) {
				
				// Null check for curricula, assignment of curriculum values to batch_type.
				if(batches.getBody().get(i).getCurriculum() != null) {
					type.setId(batch.getCurriculum().getCurrId());
					type.setLength(10);
					type.setName(batches.getBody().get(i).getCurriculum().getName());
				}
				// Batch populated with data from AssignForceBatch
				currentBatch.setName(batch.getName());
				currentBatch.setStartDate(batch.getStartDate());
				currentBatch.setEndDate(batch.getEndDate());
				currentBatch.setId(batch.getID());
				currentBatch.setType(type);
				
				
				// First and last names pulled from AssignForce trainer to search for current trainers.
				String firstName = batch.getTrainer().getFirstName();
				String lastName = batch.getTrainer().getLastName();
				
				// List comprised of trainers both in AssignForce and BAM
				List<BamUser> bamTrainers = uservice.getByFNameAndLName(firstName, lastName);
	
				// If List is not empty and user is a trainer, trainer is assigned to batch.
				if(!bamTrainers.isEmpty() && bamTrainers.get(0).getRole() == 2) {
					bamUser = bamTrainers.get(0);
					bamUser.setAssignForceID(batch.getTrainer().getTrainerId());
					currentBatch.setTrainer(bamUser);
				}
				
				// Batch is persisted, the batch will only be added if it doesn't already exist in db
				if(bservice.getBatchById(currentBatch.getId()) == null){
					bservice.addOrUpdateBatch(currentBatch);	
				}
			}
		}
	}
}
