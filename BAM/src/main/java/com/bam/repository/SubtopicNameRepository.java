package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.SubtopicName;


@Repository
public interface SubtopicNameRepository extends JpaRepository<SubtopicName, Integer> {
	public SubtopicName findById(Integer id);
}
