package com.bam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.TopicWeek;
 
@Repository
public interface TopicWeekRepository extends JpaRepository<TopicWeek, Integer> {
	List<TopicWeek> findByBatch(Batch batch);
}