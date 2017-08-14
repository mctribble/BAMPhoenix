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
	
//	@RequestMapping(value="SubtopicsPag", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	public List<Subtopic> getTopicsByBatchPag(HttpServletRequest request, Pageable pageable, Model model){
//		System.out.println("Line 1");
//		int batchId = Integer.parseInt( request.getParameter(BATCH_ID) );
//		System.out.println("Line 2");
//		Page<Subtopic> subtopic = this.subtopicRepository.findAll(pageable);
//		System.out.println("Line 3");
//		model.addAttribute("subtopic", subtopic.getContent());
//		System.out.println("Line 4");
//		float numOfPages = subtopic.getTotalPages();
//		System.out.println("Line 5");
//		model.addAttribute("maxPages", numOfPages);
//		System.out.println("Line 6");
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
