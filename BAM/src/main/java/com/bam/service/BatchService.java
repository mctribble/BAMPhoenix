package com.bam.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.repository.BatchRepository;
import com.bam.repository.BatchTypeRepository;
 
@Service
@Transactional
public class BatchService {
	@Autowired
	BatchRepository batchRepository;

	@Autowired
	BatchTypeRepository batchTypeRepository;
	
	public void addOrUpdateBatch(Batch b) {
		batchRepository.save(b);
	}

	public Batch getBatchById(Integer id) {
		LogManager.getLogger(BatchService.class).fatal(batchRepository);
		return batchRepository.findById(id);
	}

	public List<Batch> getBatchAll() {
		return batchRepository.findAll();
	}

	public List<Batch> getBatchByTrainer(BamUser trainer) {
		return batchRepository.findByTrainer(trainer);
	}
	
	public List<BatchType> getAllBatchTypes() {
		return batchTypeRepository.findAll();
	}
	
}
