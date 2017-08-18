package com.bam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.repository.BamUserRepository;
import com.bam.repository.BatchRepository;
import com.revature.assignForceDto.AForceBatch;


@Service
public class AssignForceService {
	
	@Autowired
	BamUserRepository bamUserRepo;
	
	@Autowired
	BatchRepository batchRepo;
	
	public void consumeAForce(){
		System.out.println("consuming assignforce");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<AForceBatch>> batchesEntity = restTemplate.exchange("http://assignforce.revaturelabs.com/api/v2/batch/", HttpMethod.GET, null, new ParameterizedTypeReference<List<AForceBatch>>(){});
		
		AForceBatch afBatch = new AForceBatch();
		
//		BamUser trainer = new BamUser();
//		BamUser tempUser = new BamUser();
		List<BamUser> trainers = new ArrayList<>();
		String trainerFName;
		String trainerLName;
		
		
		//cycles through each assignforce batch and persists the data to the database
		for(int i = 0; i < batchesEntity.getBody().size(); i++) {
			Batch bamBatch = new Batch();
			
			// Current cycle's batch defined from batches Response Entity.
			afBatch = (AForceBatch) batchesEntity.getBody().get(i);

			bamBatch.setEndDate(afBatch.getEndDate());
			bamBatch.setStartDate(afBatch.getEndDate());
			bamBatch.setName(afBatch.getName());
			bamBatch.setId(afBatch.getID());
			
			//assigning names to trainer
			if(afBatch.getTrainer() != null){
				trainerFName = afBatch.getTrainer().getFirstName();
				trainerLName = afBatch.getTrainer().getLastName();
			}else{
				trainerFName = "TBD";
				trainerLName = "TBD";
			}
			
			//Section for assigning trainer
			if(!bamUserRepo.findByFNameAndLName(trainerFName, trainerLName).isEmpty()){
				trainers = bamUserRepo.findByFNameAndLName(trainerFName, trainerLName);
			}
			
			for(BamUser t: trainers){
				if(t.getRole() == 2){
					bamBatch.setTrainer(t);
					System.out.println("Batch to persist: "+bamBatch);
				}
			}
		
			//Insert here
			
			
			trainers.clear();
			
			//persists the batch
//			batchRepo.save(bamBatch);
			
//			System.out.println("#"+i+" "+afBatch);
		}
	}
}
