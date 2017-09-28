package com.bam.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.bean.CurriculumSubtopic;
import com.bam.bean.Subtopic;
import com.bam.repository.BatchRepository;
import com.bam.repository.BatchTypeRepository;
import com.bam.repository.SubtopicNameRepository;
import com.bam.repository.SubtopicRepository;

@Service
public class BatchService {
	@Autowired
	BatchRepository batchRepository;

	@Autowired
	BatchTypeRepository batchTypeRepository;
	
	@Autowired
	SubtopicRepository subtopicRepository;
	
	@Autowired
	SubtopicNameRepository subtopicNameRepository;
	
	@Autowired
	SubtopicService subtopicService;
	
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
	
	/**
	 * Populates batch using a list of curriculum subtopics.
	 * 
	 * NOTE: This method assumes all batches start on a Monday.
	 * 
	 * @author DillonT
	 * @param currSubtopics
	 * @param batch
	 */
	public void addCurriculumSubtopicsToBatch(List<CurriculumSubtopic> currSubtopics, Batch batch){
		Calendar cal = Calendar.getInstance();
		
		for(CurriculumSubtopic cSTopic: currSubtopics){
			Subtopic sub = new Subtopic();
			
			//set name and batch using given information
			sub.setBatch(batch);
			sub.setSubtopicName((subtopicNameRepository.findById(cSTopic.getCurriculumSubtopicNameId().getId())));
			sub.setStatus(subtopicService.getStatus("Pending"));
			
			//Get the absolute day of batch that the subtopic should be added to
			int sDay = cSTopic.getCurriculumSubtopicDay();
			int sWeek = cSTopic.getCurriculumSubtopicWeek();
			int absoluteDayOfBatch = (sWeek-1)*7 + sDay - 1;
			
			//Set the subtopics date using the batches start date and the 
			//previously derived absolute day of batch.
			cal.setTime(batch.getStartDate());
			cal.add(Calendar.DAY_OF_WEEK, absoluteDayOfBatch);
			sub.setSubtopicDate(new Timestamp(cal.getTime().getTime()));
			
			subtopicRepository.save(sub);
		}
	}
}
