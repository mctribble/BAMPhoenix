package com.bam.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.beans.Users;
import com.bam.dao.BatchRepository;

@Transactional
public class BatchService {
	@Autowired
	BatchRepository bRep;

	public void addOrUpdateBatch(Batch b) {
		bRep.save(b);
	}

	public Batch getBatchById(Integer id) {
		Logger.getLogger(BatchService.class).fatal(bRep);
		System.out.println(bRep);
		return bRep.findById(id);
	}

	public List<Batch> getBatchAll() {

		return bRep.findAll();
	}

	public List<Batch> getBatchByTrainer(Users trainer) {
		return bRep.findByTrainer(trainer);
	}
}
