package com.bam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.TopicName;
import com.bam.service.TopicService;

@RestController
@RequestMapping(value = "/api/v1/Topic/")
public class TopicController {
  @Autowired
  TopicService topicService;

  @RequestMapping(value = "Add", method = RequestMethod.POST)
  public void addTopicName(HttpServletRequest request) {
    TopicName topic = new TopicName();
    topic.setName(request.getParameter("name"));
    topicService.addOrUpdateTopicName(topic);
  }

}
