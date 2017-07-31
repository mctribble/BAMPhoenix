package com.bam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.TopicName;

@Repository
public interface TopicNameRepository extends JpaRepository<TopicName, Integer> {
	public TopicName findById(Integer id);
}