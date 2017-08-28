package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.bean.Batch;
import com.revature.bean.Curriculum;
import com.revature.bean.Topic;
import com.revature.repository.BatchRepository;
import com.revature.repository.CurriculumRepository;
import com.revature.repository.TopicRepository;

public class Service {
	
	//Repositorys used to abstract dao calls
	@Autowired
	BatchRepository daob;
	
	@Autowired
	TopicRepository daot;
	
	@Autowired
	CurriculumRepository daoc;
	
	//Abstraction of the dao calls
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
