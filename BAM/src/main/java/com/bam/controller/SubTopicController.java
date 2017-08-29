package com.bam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.SubtopicName;
import com.bam.bean.SubtopicType;
import com.bam.bean.TopicName;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;

@RestController
@RequestMapping(value = "/api/v1/Subtopic/")
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

}
