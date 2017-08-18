package com.bam.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bam.bean.AssignForceBatch;
import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;

@Service("assignForceSyncService")
@Transactional
public class AssignForceSyncService {
	
	@Resource(name="batchesService")
	BatchService bservice;
	
	@Resource(name="userDetailsService")
	UsersDetailsService uservice;
	
	static RestTemplate restTemplate = new RestTemplate();
	static String baseUrl = "http://assignforce.revaturelabs.com/api/v2/";
	static ResponseEntity<List<AssignForceBatch>> batches = restTemplate.exchange(baseUrl + "batch/",HttpMethod.GET,null, new ParameterizedTypeReference<List<AssignForceBatch>>(){});
	
	public void assignForceSync() {
	
		AssignForceBatch batch = new AssignForceBatch();
		Batch curr_batch = new Batch();
		BatchType type = new BatchType();
		BamUser bammy = new BamUser();
		
		// Cycling through all batches received from AssignForce.
		for(int i = 0; i < batches.getBody().size(); i++) {
			
			// Current cycle's batch defined from batches Response Entity.
			batch = (AssignForceBatch) batches.getBody().get(i);
			
			if(batch.getTrainer() != null) {
				
				if(batches.getBody().get(i).getCurriculum() != null) {
					type.setId(batch.getCurriculum().getCurrId());
					type.setLength(10);
					type.setName(batches.getBody().get(i).getCurriculum().getName());
				}
				curr_batch.setName(batch.getName());
				curr_batch.setStartDate(batch.getStartDate());
				curr_batch.setEndDate(batch.getEndDate());
				curr_batch.setId(batch.getID());
				curr_batch.setType(type);
				
				String first_name = batch.getTrainer().getFirstName();
				String last_name = batch.getTrainer().getLastName();
				
				List<BamUser> BAMtrainers = uservice.getByFNameAndLName(first_name, last_name);
	
				
				if(!BAMtrainers.isEmpty() && BAMtrainers.get(0).getRole() == 2) {
					bammy = BAMtrainers.get(0);
					bammy.setAssignForce_ID(batch.getTrainer().getTrainerId());
					curr_batch.setTrainer(bammy);
				}else{
					curr_batch.setTrainer(null);
				}
				
				bservice.addOrUpdateBatch(curr_batch);
				
			}
		
		}
	}

}
