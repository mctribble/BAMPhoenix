package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Batch;
import com.bam.service.BatchService;
import com.bam.service.UsersService;

@RestController
@RequestMapping(value = "/api/v1/Batches/")
public class BatchController {

	
	private final String EMAIL = "email";
	
	@Autowired
	BatchService batchService;

	@Autowired
	UsersService usersService;

	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getBatchAll() {
		return batchService.getBatchAll();
	}

	@RequestMapping(value = "Past", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getPastBatches(HttpServletRequest request) {
		List<Batch> batches = batchService.getBatchByTrainer(usersService.findUserByEmail(request.getParameter(EMAIL)));
		List<Batch> pastBatches = new ArrayList<>();
		for(Batch b : batches){
			if(new Timestamp(System.currentTimeMillis()).after(b.getEndDate())){
				pastBatches.add(b);
			}
		}
		return pastBatches;
	}

	@RequestMapping(value = "Future", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getFutureBatches(HttpServletRequest request) {
		List<Batch> batches = batchService.getBatchByTrainer(usersService.findUserByEmail(request.getParameter(EMAIL)));
		List<Batch> futureBatches = new ArrayList<>();
		for(Batch b : batches){
			if(new Timestamp(System.currentTimeMillis()).before(b.getStartDate())){
				futureBatches.add(b);
			}
		}
		return futureBatches;
	}

	@RequestMapping(value = "InProgress", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Batch getBatchInProgress(HttpServletRequest request) {

		List<Batch> batches = batchService.getBatchByTrainer(usersService.findUserByEmail(request.getParameter(EMAIL)));
		Batch batchInProgress = null;
		Timestamp t = new Timestamp(System.currentTimeMillis());
		for(Batch b : batches){
			if(t.after(b.getStartDate()) && t.before(b.getEndDate())){
				batchInProgress = b;
				break;
			}
		}
		return batchInProgress;
	}
	
	
	@RequestMapping(value="Edit", method=RequestMethod.POST, produces="application/json")
	public void updateUser(@RequestBody String jsonObject) {
		Batch currentBatch = null;
		try {
			currentBatch = new ObjectMapper().readValue(jsonObject, Batch.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		batchService.addOrUpdateBatch(currentBatch);
	}

	@RequestMapping(value = "ById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Batch getBatchById(HttpServletRequest request) {

		return batchService.getBatchById( Integer.parseInt(request.getParameter("batchId")) );

	}
}
