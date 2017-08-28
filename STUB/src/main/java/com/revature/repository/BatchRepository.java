package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bean.Batch;

import java.lang.Integer;
import java.util.List;
import java.lang.String;

//DAO that interacts with an RDS
//Used for batch queries
@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
	
	//gets a batch by id
	public Batch findByBatchId(Integer batchid);

	//gets batches for a specific trainer
	public List<Batch> findByTrainerEmail(String traineremail);
}
