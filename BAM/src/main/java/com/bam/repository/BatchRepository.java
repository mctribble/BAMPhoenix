package com.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.BamUser;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
	public Batch findById(Integer id);
	public List<Batch> findAll();
	public List<Batch> findByTrainer(BamUser trainer);
}
