package com.bam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Curriculum;
import com.bam.bean.CurriculumSubtopic;
import com.bam.bean.SubtopicName;
import com.bam.service.CurriculumService;
import com.bam.service.CurriculumSubtopicService;
import com.bam.service.SubtopicService;

@RestController
@RequestMapping(value = "/api/v1/Curriculum/")
public class CurriculumController {

	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	CurriculumSubtopicService curriculumSubtopicService;
	
	@Autowired
	SubtopicService subtopicService;
	
	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Curriculum> getAllCurriculum(){
		return curriculumService.getAllCurriculum();
	}
	
	@RequestMapping(value = "GetCurriculum", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Curriculum getCurriculumById(HttpServletRequest request){
		int curriculumId = Integer.parseInt(request.getParameter("curriculumId"));
		return curriculumService.getCuricullumById(curriculumId);
	}
	
	@RequestMapping(value = "Schedule", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CurriculumSubtopic> getAllCurriculumSchedules(HttpServletRequest request){
		Curriculum c = new Curriculum();
		c.setCurriculum_Id(Integer.parseInt(request.getParameter("curriculumId")));
		return curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
	}
	
	public List<SubtopicName> getTopicPool(){
		return subtopicService.getAllSubtopics();
	}
}
