package com.bam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.TopicName;
import com.bam.service.TopicService;
import com.bam.service.UsersDetailsService;

@RestController

@RequestMapping(value = "/api/v1/Topic")

public class TopicController {

	

	@Autowired

	UsersDetailsService userService;

	

	@Autowired

	TopicService topicService;

	

	@RequestMapping(value="AddTopics", method=RequestMethod.POST, produces="application/json")

	public void addTopicName(@RequestBody String name){ //HttpSession session??

	TopicName topic = new TopicName();

		if(userService.findByRole(2) != null){

			topic.setName(name);

			topicService.addOrUpdateTopicName(topic);

		} else {

			throw new IllegalArgumentException("Not authorized");

		}	

		

		

		

		

		

	}

	

	



}