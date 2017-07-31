package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.beans.TopicName;
import com.bam.beans.TopicWeek;
import com.bam.dao.BatchRepository;
import com.bam.dao.TopicNameRepository;
import com.bam.dao.TopicWeekRepository;

@Transactional
public class TopicService {

	@Autowired
	TopicWeekRepository topicRep;
	
	@Autowired
	BatchRepository batchRep;
	
	@Autowired
	TopicNameRepository tNameRep;
	
	public void addTopic(int topicNameId, int batch, int weekNumber){
		TopicWeek topic = new TopicWeek();
		Batch b = new Batch();
		TopicName topicName = new TopicName();
		
		b = batchRep.findById(batch);
		topicName = tNameRep.findById(topicNameId);
		
		topic.setBatch(b);
		topic.setTopic(topicName);
		topic.setWeekNumber(weekNumber);
		
		topicRep.save(topic);
	}
	
	public List<TopicWeek> getTopicByBatch(Batch batch) {
		return topicRep.findByBatch(batch);
	}
	
	public List<TopicWeek> getTopicByBatchId(int batchId) {
		return topicRep.findByBatch(batchRep.findById(batchId));
	}
	
}
