package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.bam.bean.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
	public List<Curriculum> findAll();
	public Curriculum findById(Integer id);
	public List<Curriculum> findByCurriculumName(String name);
}
