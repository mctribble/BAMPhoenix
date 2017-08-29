package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.bam.bean.SubtopicStatus;

@Repository
public interface SubtopicStatusRepository extends JpaRepository<SubtopicStatus, Integer> {
	public SubtopicStatus findById(Integer id);
	public SubtopicStatus findByName(String name);
}