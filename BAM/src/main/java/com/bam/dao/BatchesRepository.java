package com.bam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.Batches;
import java.lang.Integer;
import java.util.List;
import java.lang.String;

@Repository
public interface BatchesRepository extends JpaRepository<Batches, Integer>
{
	public List<Batches> findById(Integer id);
	public List<Batches> findByName(String name);
	
}
