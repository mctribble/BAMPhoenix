package com.bam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Batch;
import com.bam.service.BatchService;

@RestController
@RequestMapping(value = "/Batches/")
public class BatchController 
{
	@Autowired
	BatchService batchService;
	
	//@RequestMapping(value = "All", method = RequestMethod.GET, headers = "Accept=application/json")
	@RequestMapping(value = "All.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Batch> getBatchAll()
	{
		System.out.println("in batches controller");
		return batchService.getBatchAll();
	}
}
