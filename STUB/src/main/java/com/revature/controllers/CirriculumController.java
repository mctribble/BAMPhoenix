package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Batch;
import com.revature.service.Service;

@RestController
@RequestMapping(value="/Topic/")
public class CirriculumController {
	
	@Autowired
	Service s;
	
	@RequestMapping(value="Cirriculum", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Batch> getAllBatches(HttpServletRequest request){
		return null;
		//return s.getTopicsByCurriculum(request.getParameter("id"));
	}
}
