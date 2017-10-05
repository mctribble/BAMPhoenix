package com.bam.controller;

import javax.servlet.http.HttpServletRequest;

import com.bam.bean.Subtopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.bam.bean.SubtopicName;
import com.bam.bean.SubtopicType;
import com.bam.bean.TopicName;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/api/v1/Subtopic/")
public class SubTopicController {
  @Autowired
  TopicService topicService;

  @Autowired
  SubtopicService subTopicService;

  @RequestMapping(value = "Add", method = RequestMethod.POST)
  public void addSubTopicName(HttpServletRequest request) {
    SubtopicType type = subTopicService.getSubtopicType(Integer.parseInt(request.getParameter("typeId")));
    TopicName topic = topicService.getTopicName(Integer.parseInt(request.getParameter("topicId")));
    SubtopicName subtopic = new SubtopicName(request.getParameter("subtopicName"), topic, type);
    subTopicService.addOrUpdateSubtopicName(subtopic);
  }

  @RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllSubTopics(){
    return subTopicService.getSubtopics();
  }
  
  @RequestMapping(value = "AllCompleted", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllCompletedSubtopics(){
	  return subTopicService.getSubtopicsByStatus("Completed");
  }
  
  @RequestMapping(value = "AllPending", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllPendingSubTopics(){
	  return subTopicService.getSubtopicsByStatus("Pending");
  }
  
  @RequestMapping(value = "AllMissed", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllMissedSubTopics(){
	  return subTopicService.getSubtopicsByStatus("Missed");
  }
  
  @RequestMapping(value = "AllCanceled", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllCancelledSubTopics(){
	  return subTopicService.getSubtopicsByStatus("Canceled");
  }
  
  @RequestMapping(value = "ByBatchId", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllSubtopicsByStatus(HttpServletRequest request){
	  return subTopicService.getSubtopicsByBatchId(Integer.parseInt(request.getParameter("batchId")));
  }
  
  @RequestMapping(value = "ByBatchCompleted", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllSubtopicsByBatchCompleted(HttpServletRequest request){
	  return subTopicService.getSubtopicsByBatchAndStatus(Integer.parseInt(request.getParameter("batchId")), "Completed");
  }
  
  @RequestMapping(value = "ByBatchPending", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllSubtopicsByBatchPending(HttpServletRequest request){
	  return subTopicService.getSubtopicsByBatchAndStatus(Integer.parseInt(request.getParameter("batchId")), "Pending");
  }
  
  @RequestMapping(value = "ByBatchMissed", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllSubtopicsByBatchMissed(HttpServletRequest request){
	  return subTopicService.getSubtopicsByBatchAndStatus(Integer.parseInt(request.getParameter("batchId")), "Missed");
  }
  
  @RequestMapping(value = "ByBatchCanceled", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getAllSubtopicsByBatchCancelled(HttpServletRequest request){
	  return subTopicService.getSubtopicsByBatchAndStatus(Integer.parseInt(request.getParameter("batchId")), "Canceled");
  }

}
