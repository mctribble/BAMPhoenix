package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bean.Curriculum;

import java.lang.Integer;

//DAO that interacts with an RDS
//Used for batch queries
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer>{
	
	//gets curriculums by id (should only be one but it needs to return a list)
	public List<Curriculum> findByCurriculumId(Integer curriculumId);
}
