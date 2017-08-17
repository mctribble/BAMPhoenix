package com.bam.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.bean.Batch;
import com.bam.repository.BatchRepository;
import com.bam.bean.BamUser;

@Transactional
public class BatchService {
	
	@Autowired
	BatchRepository batchRepository;

	public void addOrUpdateBatch(Batch b) {
	  batchRepository.save(b);
	}

	public Batch getBatchById(Integer id) {
		Logger.getLogger(BatchService.class).fatal(batchRepository);
		System.out.println(batchRepository);
		return batchRepository.findById(id);
	}

	public List<Batch> getBatchAll() {

		return batchRepository.findAll();
	}

	public List<Batch> getBatchByTrainer(BamUser trainer) {
		return batchRepository.findByTrainer(trainer);
	}
}
