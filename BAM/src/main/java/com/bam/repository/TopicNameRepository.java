package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.TopicName;
 
@Repository
public interface TopicNameRepository extends JpaRepository<TopicName, Integer> {
	public TopicName findById(Integer id);
}