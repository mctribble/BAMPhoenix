package com.bam.controller;

/**
 * 
 */

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Subtopic;
import com.bam.service.SubtopicService;

@RestController
@RequestMapping(value="/api/v1/Subtopic/")
public class SubtopicAddController {
	
	@Autowired
	SubtopicService subserv;
	
	/**
	 * 
	 * @param jsonObj
	 * 
	 * REST controller to add existing subtopic to specified batch
	 */
	
	@RequestMapping(value="addSubtopic", method=RequestMethod.POST, produces="application/json")
	public void addSubtopic(@RequestBody String jsonObj){

		Subtopic st = null;
		try {
			st = new ObjectMapper().readValue(jsonObj, Subtopic.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		subserv.updateSubtopic(st);
	}
}