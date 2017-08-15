package com.bam.repository;

import org.jboss.logging.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;

import java.util.List;

@Repository
public interface SubtopicRepository extends JpaRepository<Subtopic, Integer> {
	List<Subtopic> findByBatch(Batch batch);
	List<Subtopic> findByBatch(Batch batch, Pageable pageable);
	

//	@Query("SELECT t FROM Todo t WHERE " + "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
//			+ "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
//	List<Subtopic> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);

//	@Query("SELECT t FROM Todo t WHERE " + "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
//			+ "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
//	Page<Subtopic> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);

//	@Query("SELECT t FROM Todo t WHERE " + "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR "
//			+ "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
//	Slice<Subtopic> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);
}