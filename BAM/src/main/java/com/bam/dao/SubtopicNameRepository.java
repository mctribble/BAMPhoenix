package com.bam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.SubtopicName;

@Repository
public interface SubtopicNameRepository extends JpaRepository<SubtopicName, Integer> {
	public SubtopicName findById(Integer id);
}
