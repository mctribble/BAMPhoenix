package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Batch;
import com.revature.beans.Topic;
import com.revature.service.Service;

@RestController
@RequestMapping(value="/")
public class TestController {
	

	@Autowired
	Service s;
	
	@RequestMapping(value="allB", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Batch> getAllBatches(){
		return s.getBatchByTrainer("Jonathan@mail.com");
	}
	
	@RequestMapping(value="singleB", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Batch getOneBatch(){
		return s.getBatchById(1);
	}
	
	@RequestMapping(value="allT", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Topic> getAllTopics(){
		return s.getTopicsByCurriculum((s.getBatchById(1)).getCurriculum());
	}
	

}
