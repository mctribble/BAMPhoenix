package com.bam.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;
import com.bam.bean.SubtopicName;
import com.bam.bean.SubtopicStatus;
import com.bam.repository.BatchRepository;
import com.bam.repository.SubtopicNameRepository;
import com.bam.repository.SubtopicRepository;
import com.bam.repository.SubtopicStatusRepository;

@Transactional
public class SubtopicService {

	@Autowired
	SubtopicRepository subtopicRepository;
	
	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	SubtopicNameRepository subtopicNameRepository;
	
	@Autowired
	SubtopicStatusRepository subtopicStatusRepository;
	
	public void addSubtopic(int subtopic, int batch){
		Subtopic s = new Subtopic();
		Batch b;
		SubtopicName st;
		SubtopicStatus ss;
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = dateFormat.parse("23/09/2017");
		} catch (Exception e) {
			e.printStackTrace();
		}
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		
		b = batchRepository.findById(batch);
		st = subtopicNameRepository.findById(subtopic);
		ss = subtopicStatusRepository.findById(1);

		s.setBatch(b);
		s.setSubtopicName(st);
		s.setStatus(ss);
		s.setSubtopicDate(ts);
		
		subtopicRepository.save(s);
	}

	public List<Subtopic> getSubtopicByBatch(Batch batch) {
		return subtopicRepository.findByBatch(batch);
	}

	public List<Subtopic> getSubtopicByBatchId(int batchId) {
		return subtopicRepository.findByBatch(batchRepository.findById(batchId));
	}

	/**
	 * 
	 * @param topic
	 * Persisting subtopic to database.
	 * To handle timezone offset, before submission to DB, 
	 * adding offset to date and updating date.
	 * 
	 * @author Samuel Louis-Pierre, Avant Mathur
	 */
	public void updateSubtopic(Subtopic subtopic) {
		Long newDate = subtopic.getSubtopicDate().getTime() + 46800000;
		subtopic.setSubtopicDate(new Timestamp(newDate));
		
		subtopicRepository.save(subtopic);
	}

	public SubtopicStatus getStatus(String name) {
		return subtopicStatusRepository.findByName(name);
	}

	/**
	 * Service method to return the number of Subtopics by matching their ids with
	 * the batchId.
	 * 
	 * @param batchId(int)
	 * @return number(long) of Subtopics
	 * 
	 * @author Michael Garza, Gary LaMountain
	 */
	public Long getNumberOfSubtopics(int batchId){
		return subtopicRepository.countSubtopicsByBatchId(batchId);
  }

	public List<SubtopicName> getAllSubtopics(){
		return subtopicNameRepository.findAll();
	}

	/**
	 * Service method to return the pages of json information to the FullCalendar API. 
	 * This is hard coded until the FullCalendar API is set up for getting pages of 
	 * json sub-topics.
	 * 
	 * @param batchId
	 * @param pageRequest
	 * @return
	 * 
	 * Authors: Michael Garza
	 * 			Gary LaMountain
	 */
	public List<Subtopic> findByBatchId(int batchId, PageRequest pageRequest) {
		return subtopicRepository.findByBatch(batchRepository.findById(batchId), pageRequest);
    }
}