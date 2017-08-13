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

//Set up REST controller mapping
@RestController
@RequestMapping(value="/Topic/")
public class CurriculumController {
	
	//Autowire in service class to make DAO calls
	@Autowired
	Service s;
	
	//Mapping for the call to get a set of topics for the curriculum id passed in through GET
	@RequestMapping(value="Curriculum", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Topic> getAllTopics(HttpServletRequest request){
		//Gets Curriculum from the id passed in through the GET request
		Curriculum c = (s.getCurriculumById(Integer.parseInt(request.getParameter("id")))).get(0);
		
		//Returns the set of topics that the curriculum uses
		return s.getTopicsByCurriculum(c);
	}
}
