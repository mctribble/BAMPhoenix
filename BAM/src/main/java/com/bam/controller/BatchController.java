package com.bam.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Batch;
import com.bam.service.BatchService;
import com.bam.service.UsersService;

@RestController
@RequestMapping(value = "/Batches/")
public class BatchController 
{
	@Autowired
	BatchService batchService;
	
	@Autowired
	UsersService usersService;
	
	//@RequestMapping(value = "All", method = RequestMethod.GET, headers = "Accept=application/json")
	@RequestMapping(value = "All.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getBatchAll(HttpServletRequest request)
	{
		System.out.println("in batches controller");
		return batchService.getBatchAll();
	}
	
	@RequestMapping(value = "Past.do", method = RequestMethod.GET, produces = "application/json")
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
	
	@RequestMapping(value = "Future.do", method = RequestMethod.GET, produces = "application/json")
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
	
	@RequestMapping(value = "ById.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Batch getBatchById(HttpServletRequest request){
		return batchService.getBatchById( Integer.parseInt(request.getParameter("batchId")) );
	}
}
