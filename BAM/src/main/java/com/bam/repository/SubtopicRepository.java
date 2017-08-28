package com.bam.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;

@Repository
public interface SubtopicRepository extends JpaRepository<Subtopic, Integer> {
	List<Subtopic> findByBatch(Batch batch);
	
	List<Subtopic> findAll();

	/**
	 * Counts the number of subtopics in the database by matching it with the
	 * batchid.
	 * 
	 * 
	 * @param batchId
	 * @return number of Subtopics
	 * 
	 * @author Michael Garza, Gary LaMountain
	 */
	Long countSubtopicsByBatchId(int batchId);
	
	/**
	 * Generate a list of Subtopics by a Batch object and a specific
	 * page from a Pageable object.
	 * 
	 * @param batch
	 * @param pageable
	 * @return list of Subtopics
	 * 
	 * @author  Michael Garza, Gary LaMountain
	 */
	List<Subtopic> findByBatch(Batch batch, Pageable pageable);
}