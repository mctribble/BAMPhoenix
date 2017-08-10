
package com.bam.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
	
	public void addOrUpdateUser(Users user){
		dao.save(user);
		
	}
	
	public List<Users> findAllUsers(){
		return dao.findAll();
		
	}
	
	public List<Users> findByRole(int role){
		return dao.findByRole(role);
	}
	public Users findUserById(int userId){
		return dao.findByUserId(userId);
	}
	
	public Users findUserByEmail(String email){
		return dao.findByEmail(email);
	}
	
	public List<Users> findUsersInBatch(int batchId){
		//Get batch object by the id
		Batch batch = bdao.findById(batchId);
		//Return users in the batch
		return dao.findByBatch(batch);
	}
	
	public List<Users> findUsersNotInBatch(){
		//Return users in the batch with a null
		List<Users> users = dao.findByBatch(null);
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).getRole() != 1) {
				users.remove(i);
				i--;
			}
		}
		return users;
	}

	public Users parseUserFromJson(String jsonObject) {
		Users currentUser = null;
		System.out.println("jsonObject: " + jsonObject);
		try {
			currentUser = new ObjectMapper().readValue(jsonObject, Users.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(currentUser);
		
		return currentUser;
	}
	
}
