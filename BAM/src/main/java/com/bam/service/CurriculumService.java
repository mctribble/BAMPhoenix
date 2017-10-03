package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bam.bean.Curriculum;
import com.bam.repository.CurriculumRepository;
 
@Service
public class CurriculumService {

	@Autowired
	CurriculumRepository curriculumRepository;
	
	public List<Curriculum> getAllCurriculum(){
		List<Curriculum> curriculumList =  curriculumRepository.findAll();
		//obfuscate password
		for(Curriculum element : curriculumList){
			element.getCurriculumCreator().setPwd("");
			element.getCurriculumCreator().setPwd2("");
			if(element.getCurriculumModifier() != null)
			{
				element.getCurriculumModifier().setPwd("");
				element.getCurriculumModifier().setPwd2("");
			}
		}
		return curriculumList;
	}
	
	public Curriculum getCuricullumById(Integer id){
		//obfuscate password
		Curriculum curriculum = curriculumRepository.findById(id);
		curriculum.getCurriculumCreator().setPwd("");
		curriculum.getCurriculumCreator().setPwd2("");
		if(curriculum.getCurriculumModifier() != null)
		{
			curriculum.getCurriculumModifier().setPwd("");
			curriculum.getCurriculumModifier().setPwd2("");
		}
		return curriculum;
	}
	
	public void save(Curriculum c){
		curriculumRepository.save(c);
	}
	
	public List<Curriculum> findAllCurriculumByName(String name){
		return curriculumRepository.findByCurriculumName(name);
	}
}
