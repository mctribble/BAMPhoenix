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
import com.revature.service.Service;

@RestController
@RequestMapping(value="/batch/")
public class BatchController {
	
	@Autowired
	Service s;
	
	@RequestMapping(value="trainer", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Batch> getAllBatches(HttpServletRequest request){
		return s.getBatchByTrainer(request.getParameter("email"));
	}
	
	/*@RequestMapping(value="cur", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Curriculum getCurriculum(HttpServletRequest request){
		System.out.println("hello");
		Integer id = Integer.parseInt(request.getParameter("id"));
		return s.getCurriculumById(id);
	}*/
	
}
