package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bam.bean.Curriculum;
import com.bam.bean.CurriculumSubtopic;
import com.bam.repository.CurriculumSubtopicRepository;

@Service
@Transactional
public class CurriculumSubtopicService {

	@Autowired
	CurriculumSubtopicRepository curriculumSubtopic;
	
	public List<CurriculumSubtopic> getCurriculumSubtopicForCurriculum(Curriculum c){
		return curriculumSubtopic.findByCurriculum(c);
	}
	
	public void saveCurriculumSubtopic(CurriculumSubtopic cs){
		curriculumSubtopic.save(cs);
	}
	
}
