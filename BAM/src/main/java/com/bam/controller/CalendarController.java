package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Subtopic;
import com.bam.beans.SubtopicStatus;
import com.bam.beans.TopicName;
import com.bam.beans.TopicWeek;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;

@RestController
@RequestMapping(value="/api/v1/Calendar/")
public class CalendarController {
	
	private final String BATCH_ID = "batchId";
	
	@Autowired
	SubtopicService subtopicService;
	
	@Autowired
	TopicService topicService;
	
	@RequestMapping(value="Subtopics", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Subtopic> getSubtopicsByBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
		
		//Retrieve and return users in a batch from the database
		return subtopicService.getSubtopicByBatchId(batchId);
	}
	
	@RequestMapping(value="Topics", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<TopicWeek> getTopicsByBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
		
		//Retrieve and return users in a batch from the database
		return topicService.getTopicByBatchId(batchId);
	}
	
	@RequestMapping(value="DateUpdate", method=RequestMethod.GET, produces="application/json")
	public void changeTopicDate(HttpServletRequest request) throws ParseException {
		//Get the batch id from the request
		String subtopicName = request.getParameter("subtopicId");
		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		Subtopic sub;
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
	
	@RequestMapping(value="StatusUpdate", method=RequestMethod.GET, produces="application/json")
	public void updateTopicStatus(HttpServletRequest request) throws ParseException {
		//Get the batch id from the request
		String subtopicName = request.getParameter("subtopicId");
		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		Subtopic sub;
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
	
	
	@RequestMapping(value="AddTopics", method=RequestMethod.POST, produces="application/json")
	public void addTopics(@RequestBody String jsonObject, HttpSession session) throws NullPointerException {
		List<TopicName> topicsFromStub = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			topicsFromStub = mapper.readValue(jsonObject, mapper.getTypeFactory().constructCollectionType(List.class, TopicName.class));
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
