package com.bam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.Batch;
import com.bam.beans.TopicWeek;

@Repository
public interface TopicWeekRepository extends JpaRepository<TopicWeek, Integer> {
	List<TopicWeek> findByBatch(Batch batch);
}