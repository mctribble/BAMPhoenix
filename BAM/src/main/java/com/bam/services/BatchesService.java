package com.bam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batches;
import com.bam.dao.BatchesRepository;

@Transactional
public class BatchesService 
{	
	@Autowired
	BatchesRepository bRep;
	
	public List<Batches> getBatchById(Integer id)
	{
		return bRep.findById(id);
	}
}
