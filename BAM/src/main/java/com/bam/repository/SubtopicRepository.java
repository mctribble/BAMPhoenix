package com.bam.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;

import java.util.List;

@Repository
public interface SubtopicRepository extends JpaRepository<Subtopic, Integer> {
	List<Subtopic> findByBatch(Batch batch);
	
	/**
	 * This repository will most likely need the @Query to do get specific dates.
	 * 
	 * @param batch
	 * @param pageable
	 * @return
	 * 
	 * 
	 * Authors: Michael Garza
	 * 			Gary LaMountain
	 */
	List<Subtopic> findByBatch(Batch batch, Pageable pageable);
}