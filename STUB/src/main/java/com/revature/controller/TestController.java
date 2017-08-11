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
import com.revature.bean.Topic;
import com.revature.service.Service;

//Test controller REST mapping
@RestController
@RequestMapping(value="/")
public class TestController {
	
	//autowired service class
	@Autowired
	Service s;
	
	//Test mapping that returns all batches based on a hardcoded email
	@RequestMapping(value="allB", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Batch> getAllBatches(){
		//returns list of batches
		return s.getBatchByTrainer("Jonathan@mail.com");
	}
	
	//test mapping that returns a single batch based on an id from GET
	@RequestMapping(value="singleB", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Batch getOneBatch(HttpServletRequest request){
		//System.out.println("hello");
		Integer id = Integer.parseInt(request.getParameter("id"));
		return s.getBatchById(id);
	}
	
	//test mapping that returns all topics based on a hard coded curriculum
	@RequestMapping(value="allT", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Topic> getAllTopics(){
		return s.getTopicsByCurriculum((s.getBatchById(1)).getCurriculum());
	}
	

}
