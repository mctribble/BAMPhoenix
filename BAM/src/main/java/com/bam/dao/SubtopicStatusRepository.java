package com.bam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.SubtopicStatus;
import java.lang.String;
import java.util.List;

@Repository
public interface SubtopicStatusRepository extends JpaRepository<SubtopicStatus, Integer> {
	public SubtopicStatus findById(Integer id);

	public SubtopicStatus findByName(String name);
}
