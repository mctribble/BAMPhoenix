package com.revature.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Curriculum;
import java.lang.Integer;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer>{
	public List<Curriculum> findByCurriculumId(Integer curriculumId);
}
