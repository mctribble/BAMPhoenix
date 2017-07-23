package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Batch;
import com.revature.beans.Curriculum;
import com.revature.beans.Topic;
import com.revature.service.Service;

@RestController
@RequestMapping(value="/Topic/")
public class CurriculumController {
	
	@Autowired
	Service s;
	
	@RequestMapping(value="Curriculum", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Curriculum> getAllTopics(HttpServletRequest request){
		
		return  s.getCurriculumById(Integer.parseInt(request.getParameter("id")));
		// s.getTopicsByCurriculum(c);
		
	}
}
