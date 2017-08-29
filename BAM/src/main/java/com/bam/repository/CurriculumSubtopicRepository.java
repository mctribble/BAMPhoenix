package com.bam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Curriculum;
import com.bam.bean.CurriculumSubtopic;

@Repository
public interface CurriculumSubtopicRepository extends JpaRepository<CurriculumSubtopic, Integer> {
	public List<CurriculumSubtopic> findAll();
	public List<CurriculumSubtopic> findByCurriculum(Curriculum c);
}
