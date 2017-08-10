package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
public class BatchController 
{
	@Autowired
	BatchService batchService;
	
	@Autowired
	UsersService usersService;

	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getBatchAll(HttpServletRequest request)
	{
		
		return batchService.getBatchAll();
	}
	
	@RequestMapping(value = "Past", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getPastBatches(HttpServletRequest request)
	{
		List<Batch> batches = batchService.getBatchByTrainer(usersService.findUserByEmail(request.getParameter("email")));
		List<Batch> pastBatches = new ArrayList<Batch>();
		for(Batch b : batches){
			if(new Timestamp(System.currentTimeMillis()).after(b.getEndDate())){
				pastBatches.add(b);
			}
		}
		return pastBatches;
	}
	
	@RequestMapping(value = "Future", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getFutureBatches(HttpServletRequest request)
	{
		List<Batch> batches = batchService.getBatchByTrainer(usersService.findUserByEmail(request.getParameter("email")));
		List<Batch> futureBatches = new ArrayList<Batch>();
		for(Batch b : batches){
			if(new Timestamp(System.currentTimeMillis()).before(b.getStartDate())){
				futureBatches.add(b);
			}
		}
		return futureBatches;
	}
	
	@RequestMapping(value = "InProgress", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Batch getBatchInProgress(HttpServletRequest request)
	{
		List<Batch> batches = batchService.getBatchByTrainer(usersService.findUserByEmail(request.getParameter("email")));
		Batch BatchInProgress = null;
		Timestamp t = new Timestamp(System.currentTimeMillis());
		for(Batch b : batches){
			
			if(t.after(b.getStartDate()) && t.before(b.getEndDate())){
				BatchInProgress = b;
				break;
			}
		}
		return BatchInProgress;
	}
	
	
	@RequestMapping(value="Edit", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void updateUser(@RequestBody String jsonObject, HttpSession session) {
		Batch currentBatch = null;
		System.out.println("jsonObject: " + jsonObject);
		try {
			currentBatch = new ObjectMapper().readValue(jsonObject, Batch.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		batchService.addOrUpdateBatch(currentBatch);
	}
	
	@RequestMapping(value = "ById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Batch getBatchById(HttpServletRequest request){
		return batchService.getBatchById( Integer.parseInt(request.getParameter("batchId")) );

	}
}
