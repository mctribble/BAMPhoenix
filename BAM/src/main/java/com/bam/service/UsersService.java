package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Batch;
import com.bam.beans.Users;
import com.bam.dao.BatchRepository;
import com.bam.dao.UsersRepository;

@Transactional
public class UsersService {

	@Autowired
	UsersRepository dao;
	
	@Autowired
	BatchRepository bdao;
	
	public void addUser(Users user){
		dao.save(user);
	}
	
	public List<Users> findAllUsers(){
		return dao.findAll();
		
	}
	
	public Users findUserByEmail(String email){
		return dao.findByEmail(email);
	}
	
	public List<Users> findUsersInBatch(int batchId){
		Batch batch = bdao.findById(batchId);
		return dao.findByBatch(batch);
	}
	
	
}
