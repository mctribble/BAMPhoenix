package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Subtopic;
import com.bam.bean.SubtopicStatus;
import com.bam.bean.TopicName;
import com.bam.bean.TopicWeek;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;

@RestController
@RequestMapping(value = "/api/v1/Calendar/")
public class CalendarController {
	
	private final static String batchID = "batchId";
	
	@Autowired
	SubtopicService subtopicService;

	@Autowired
	TopicService topicService;

	@RequestMapping(value = "Subtopics", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Subtopic> getSubtopicsByBatch(HttpServletRequest request) {
	System.out.println("CalendarController - getSubtopicsByBatch()");

		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(batchID) );
		
		//Retrieve and return users in a batch from the database

		return subtopicService.getSubtopicByBatchId(batchId);
	}
	
	@RequestMapping(value="SubtopicsPag", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Subtopic> getSubTopicsByBatchPag(HttpServletRequest request){
		System.out.println("CalendarController - getSubTopicsByBatchPag");
		int batchId = Integer.parseInt( request.getParameter(batchID) );
		System.out.println("batchId: " + batchId);
		System.out.println(subtopicService.findByBatchId(batchId, new PageRequest(0,2)));
		return subtopicService.findByBatchId(batchId, new PageRequest(0,2));
	}
//	@RequestMapping(value="SubtopicsPag", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	public List<Subtopic> getTopicsByBatchPag(HttpServletRequest request, Pageable pageable, Model model){
//		
//		int batchId = Integer.parseInt( request.getParameter(batchID) );
//		
//		Page<Subtopic> subtopic = this.subtopicService.findAll(pageable);
//		
//		model.addAttribute("subtopic", subtopic.getContent());
//		
//		float numOfPages = subtopic.getTotalPages();
//		
//		model.addAttribute("maxPages", numOfPages);
//		
//		return subtopicService.getSubtopicByBatchId(batchId);
//	}

	@RequestMapping(value = "Topics", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<TopicWeek> getTopicsByBatch(HttpServletRequest request) {
		System.out.println("CalendarController - getTopicsByBatch()");

		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(batchID) );
		
		//Retrieve and return users in a batch from the database
		return topicService.getTopicByBatchId(batchId);
	}
	
	@RequestMapping(value="DateUpdate", method=RequestMethod.GET, produces="application/json")
	public void changeTopicDate(HttpServletRequest request) throws ParseException {
		// Get the batch id from the request
		String subtopicName = request.getParameter("subtopicId");

		int batchId = Integer.parseInt( request.getParameter(batchID) );
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		Subtopic sub;
		for (int i = 0; i < topics.size(); i++) {
			if (topics.get(i).getSubtopicName().getName().equals(subtopicName)) {
				sub = topics.get(i);
				Long newDate = Long.valueOf(request.getParameter("date")) + 46800000;
				sub.setSubtopicDate(new Timestamp(newDate));

				// Update topic in the database
				subtopicService.updateSubtopic(sub);
				break;
			}
		}
	}

	
	@RequestMapping(value="StatusUpdate", method=RequestMethod.GET, produces="application/json")
	public void updateTopicStatus(HttpServletRequest request) throws ParseException {
		// Get the batch id from the request
		String subtopicName = request.getParameter("subtopicId");

		int batchId = Integer.parseInt( request.getParameter(batchID) );
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		Subtopic sub;
		SubtopicStatus status = subtopicService.getStatus(request.getParameter("status"));
		for (int i = 0; i < topics.size(); i++) {
			if (topics.get(i).getSubtopicName().getName().equals(subtopicName)) {
				sub = topics.get(i);
				sub.setStatus(status);

				// Update topic in the database
				subtopicService.updateSubtopic(sub);
				break;
			}
		}
	}

	@RequestMapping(value="AddTopics", method=RequestMethod.POST, produces="application/json")
	public void addTopics(@RequestBody String jsonObject, HttpSession session) {
		List<TopicName> topicsFromStub = null;

		ObjectMapper mapper = new ObjectMapper();
		try {

			topicsFromStub = mapper.readValue(jsonObject, mapper.getTypeFactory().constructCollectionType(List.class, TopicName.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<TopicName> allTopicsInBAM = topicService.getTopics();
		for (int i = 0; i < topicsFromStub.size(); i++) {
			boolean found = false;
			for (int j = 0; j < allTopicsInBAM.size(); j++) {
				if (topicsFromStub.get(i).getName().equals(allTopicsInBAM.get(j).getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				topicService.addOrUpdateTopicName(topicsFromStub.get(i));
			}
		}
	}
}
