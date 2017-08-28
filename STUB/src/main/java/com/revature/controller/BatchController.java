package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bean.Batch;
import com.revature.bean.Curriculum;
import com.revature.service.Service;

//Set up REST Controller mapping
@RestController
@RequestMapping(value="/Batch/")
public class BatchController {
	
	//Autowired service class
	@Autowired
	Service s;
	
	//Mapping for GET Requests to return JSON to the client
	@RequestMapping(value="Trainer", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Batch> getAllBatches(HttpServletRequest request){
		//returns a list of batches that takes in the email of the trainer from GET
		return s.getBatchByTrainer(request.getParameter("email"));
	}
	
}
