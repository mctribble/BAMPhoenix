package com.bam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Subtopic;
import com.bam.bean.SubtopicName;
import com.bam.bean.SubtopicType;
import com.bam.bean.TopicName;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;
import com.bam.service.UsersDetailsService;

@RestController

@RequestMapping(value = "/api/v1/SubTopic")

public class SubTopicController {

	

	@Autowired

	UsersDetailsService usersService;

	

	@Autowired

	TopicService topicService;

	

	@Autowired

	SubtopicService subTopicService;

	

	@RequestMapping(value="AddSubtopics", method= RequestMethod.POST, produces="application/json" )

	public void addSubTopicName(@RequestBody String name, int id, int type){

		if(usersService.findByRole(2) != null){
		Subtopic topic = new Subtopic();

		SubtopicName sub = new SubtopicName();

		SubtopicType subtype = new SubtopicType();

		TopicName top = new TopicName();

		top.setId(id);

		sub.setName(name);

		subtype.setId(type);



		subTopicService.updateSubtopic(topic);

		

		}

	}

	



}