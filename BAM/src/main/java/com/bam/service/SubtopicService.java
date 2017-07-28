package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.beans.Subtopic;
import com.bam.beans.Users;
import com.bam.dao.SubtopicRepository;

@Transactional
public class SubtopicService {

	@Autowired
	SubtopicRepository subTopRep;
	
	public void addSubtopic(Subtopic s){
		subTopRep.save(s);
	}
	
	public List<Subtopic> getSubtopicByBatch(Batch batch) {
		return subTopRep.findByBatch(batch);
	}
	
}
