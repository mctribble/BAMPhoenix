package com.bam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Users;
import com.bam.dao.UsersRepository;

@Transactional
public class UsersService {

	@Autowired
	UsersRepository dao;
	
	public List<Users> findAllUsers(){
		return dao.findAll();
		
	}
	
	public Users findUserByEmail(String email){
		return dao.findByEmail(email);
	}
	
	
}
