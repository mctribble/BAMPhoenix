package com.bam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.Batch;
import java.util.List;
import com.bam.beans.Users;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer>
{
	public Batch findById(Integer id);
	
	public List<Batch> findAll();
	
	public List<Batch> findByTrainer(Users trainer);
}
