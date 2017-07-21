package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Topic;
import com.revature.beans.Curriculum;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer>{
	public List<Topic> findByCurriculum(Curriculum curriculum);
}
