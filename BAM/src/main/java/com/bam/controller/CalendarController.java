package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Batch;
import com.bam.beans.Subtopic;
import com.bam.beans.SubtopicStatus;
import com.bam.beans.TopicName;
import com.bam.beans.TopicWeek;
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
		String subtopicName = request.getParameter("subtopicId");
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		Subtopic sub = new Subtopic();
		for (int i = 0; i < topics.size(); i++) {
			if (topics.get(i).getSubtopicName().getName().equals(subtopicName)){
				sub = topics.get(i);
				Long newDate = Long.valueOf(request.getParameter("date")) + 46800000;
				sub.setSubtopicDate(new Timestamp(newDate));
				
				//Update topic in the database
				subtopicService.updateSubtopic(sub);
				break;
			}
		}
	}
	
	@RequestMapping(value="StatusUpdate.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public void updateTopicStatus(HttpServletRequest request) throws ParseException {
		//Get the batch id from the request
		String subtopicName = request.getParameter("subtopicId");
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		Subtopic sub = new Subtopic();
		SubtopicStatus status = subtopicService.getStatus(request.getParameter("status"));
		for (int i = 0; i < topics.size(); i++) {
			if (topics.get(i).getSubtopicName().getName().equals(subtopicName)){
				sub = topics.get(i);
				sub.setStatus(status);
				
				//Update topic in the database
				subtopicService.updateSubtopic(sub);
				break;
			}
		}
	}
	
	
	@RequestMapping(value="AddTopics.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void addTopics(@RequestBody String jsonObject, HttpSession session) {
		List<TopicName> topicsFromStub = null;
		System.out.println("jsonObject: " + jsonObject);
		ObjectMapper mapper = new ObjectMapper();
		try {
			topicsFromStub = mapper.readValue(jsonObject, mapper.getTypeFactory().constructCollectionType(List.class, TopicName.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<TopicName> allTopicsInBAM = topicService.getTopics();
		for(int i=0; i<topicsFromStub.size(); i++) {
			boolean found = false;
			for(int j=0; j<allTopicsInBAM.size(); j++) {
				if(topicsFromStub.get(i).getName().equals(allTopicsInBAM.get(j).getName())) {
					found = true;
					break;
				}
			}
			if(!found) {
				topicService.addOrUpdateTopicName(topicsFromStub.get(i));
			}
		}
	}
}
