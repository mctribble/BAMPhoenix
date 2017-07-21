package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Curriculum;
import java.lang.Integer;
import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer>{
	public Curriculum findByCurriculum_id(Integer curriculum_id);
}
