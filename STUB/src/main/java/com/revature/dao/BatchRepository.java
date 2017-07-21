package com.revature.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Batch;
import java.lang.Integer;
import java.util.List;
import java.lang.String;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
	public Batch findByBatchId(Integer batchid);

	public List<Batch> findByTrainerEmail(String traineremail);
}
