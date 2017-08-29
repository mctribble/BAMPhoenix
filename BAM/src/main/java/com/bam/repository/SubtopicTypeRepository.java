package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.SubtopicType;
@Repository
public interface SubtopicTypeRepository extends JpaRepository<SubtopicType, Integer> {
	public SubtopicType findById(Integer type);
}
