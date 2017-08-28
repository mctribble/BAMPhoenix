package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bam.bean.Batch;
import com.bam.bean.TopicName;
import com.bam.bean.TopicWeek;
import com.bam.repository.BatchRepository;
import com.bam.repository.TopicNameRepository;
import com.bam.repository.TopicWeekRepository;
 
@Service
public class TopicService {

  @Autowired
  TopicWeekRepository topicWeekRepository;

  @Autowired
  BatchRepository batchRepistory;

  @Autowired
  TopicNameRepository topicNameRepository;

  public void addTopic(int topicNameId, int batch, int weekNumber) {
    TopicWeek topic = new TopicWeek();
    Batch b;
    TopicName topicName;

    b = batchRepistory.findById(batch);
    topicName = topicNameRepository.findById(topicNameId);

    topic.setBatch(b);
    topic.setTopic(topicName);
    topic.setWeekNumber(weekNumber);

    topicWeekRepository.save(topic);
  }

  public List<TopicWeek> getTopicByBatch(Batch batch) {
    return topicWeekRepository.findByBatch(batch);
  }

  public List<TopicWeek> getTopicByBatchId(int batchId) {
    return topicWeekRepository.findByBatch(batchRepistory.findById(batchId));
  }

  public List<TopicName> getTopics() {
    return topicNameRepository.findAll();
  }

  public void addOrUpdateTopicName(TopicName topic) {
    topicNameRepository.save(topic);
  }

  public TopicName getTopicName(int id) {
    return topicNameRepository.findById(id);
  }

}
