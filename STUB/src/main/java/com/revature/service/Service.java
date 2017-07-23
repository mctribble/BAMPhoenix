package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.beans.Batch;
import com.revature.beans.Curriculum;
import com.revature.beans.Topic;
import com.revature.dao.BatchRepository;
import com.revature.dao.CurriculumRepository;
import com.revature.dao.TopicRepository;

public class Service {
	
	@Autowired
	BatchRepository daob;
	
	@Autowired
	TopicRepository daot;
	
	@Autowired
	CurriculumRepository daoc;
	
	public Batch getBatchById(int id){
		return daob.findByBatchId(id);
	}
	
	public List<Batch> getBatchByTrainer(String email){
		System.out.println(email);
		return daob.findByTrainerEmail(email);
	}
	
	public List<Topic> getTopicsByCurriculum(Curriculum cu){
		return daot.findByCurriculum(cu);
	}
	
	public List<Curriculum> getCurriculumById(int id){
		return daoc.findByCurriculumId(id);
	}
}
