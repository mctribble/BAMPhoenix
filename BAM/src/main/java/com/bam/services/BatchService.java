package com.bam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.dao.BatchRepository;

@Transactional
public class BatchService 
{	
	@Autowired
	BatchRepository bRep;
	
	public Batch getBatchById(Integer id)
	{
		return bRep.findById(id);
	}
}
