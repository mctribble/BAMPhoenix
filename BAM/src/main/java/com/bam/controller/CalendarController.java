package com.bam.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Subtopic;
import com.bam.beans.TopicWeek;
import com.bam.beans.Users;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;

@RestController
@RequestMapping(value="/Calendar/")
public class CalendarController {
	@Autowired
	SubtopicService subtopicService;
	
	@Autowired
	TopicService topicService;
	
	@RequestMapping(value="Subtopics.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Subtopic> getSubtopicsByBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return subtopicService.getSubtopicByBatchId(batchId);
	}
	
	@RequestMapping(value="Topics.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<TopicWeek> getTopicsByBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return topicService.getTopicByBatchId(batchId);
	}
	
	@RequestMapping(value="DateUpdate.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public void changeTopicDate(HttpServletRequest request) throws ParseException {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		List<Subtopic> topic = subtopicService.getSubtopicByBatchId(batchId);
		Long newDate = Long.valueOf(request.getParameter("date"));
		Subtopic sub = topic.get(0);
		sub.setSubtopicDate(new Timestamp(newDate));
		System.out.println(newDate);
		
		//Update topic in the database
		subtopicService.updateSubtopicDate(sub);
	}
}
