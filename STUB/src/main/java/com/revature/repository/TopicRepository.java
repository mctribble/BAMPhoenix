package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bean.Curriculum;
import com.revature.bean.Topic;

import java.util.List;

//DAO that interacts with an RDS
//Used for batch queries
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer>{
	
	//Gets a list of topics that a curriculum has
	public List<Topic> findByCurriculum(Curriculum curriculum);
}
