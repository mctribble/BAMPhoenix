package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;

import java.util.List;

@Repository
public interface SubtopicRepository extends JpaRepository<Subtopic, Integer> {
	List<Subtopic> findByBatch(Batch batch);
}