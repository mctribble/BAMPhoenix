package com.bam.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.Batch;
import java.lang.Integer;
import java.util.List;
import java.lang.String;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer>
{
	public Batch findById(Integer id);
	
	
}
