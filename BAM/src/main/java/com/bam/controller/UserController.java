package com.bam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Batch;
import com.bam.bean.BamUser;
import com.bam.service.BatchService;
import com.bam.service.BamUserService;


@RestController
@RequestMapping(value="/api/v1/Users/")
public class UserController {
	@Autowired
	BamUserService bamUserService;
	
	@Autowired
	BatchService batchService;
	
	@RequestMapping(value="All", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<BamUser> getAllUsers(){
		return bamUserService.findAllUsers();
	}
	
	@RequestMapping(value="AllTrainers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<BamUser> getAllTrainers(){
		return bamUserService.findByRole(2);
	}
	
	@RequestMapping(value="AllAssociates", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<BamUser> getAllAssociates(){
		return bamUserService.findByRole(1);
	}
	
	@RequestMapping(value="InBatch", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<BamUser> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return bamUserService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Drop", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<BamUser> dropUserFromBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		BamUser user = bamUserService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Drop user from the batch
		user.setBatch(null);
		user.setRole(0);
		bamUserService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return bamUserService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Update", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void updateUser(@RequestBody String jsonObject, HttpSession session) {
		BamUser currentUser = null;
		System.out.println("jsonObject: " + jsonObject);
		try {
			currentUser = new ObjectMapper().readValue(jsonObject, BamUser.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(currentUser);
		bamUserService.addOrUpdateUser(currentUser);
	}
	
	@RequestMapping(value="Register", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void addUser(@RequestBody String jsonObject, HttpSession session) throws Exception {
		BamUser currentUser = null;
		System.out.println("jsonObject: " + jsonObject);
		try {
			currentUser = new ObjectMapper().readValue(jsonObject, BamUser.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(currentUser);
	
		if(bamUserService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			bamUserService.addOrUpdateUser(currentUser);
		} else {
			Exception e = null;
			throw  e;
		}	
	}

	/**
	 * @author Tom Scheffer
	 * @param jsonObject - object being passed in
	 * @param session - current session
	 * @throws Exception - for when previous password is wrong
	 * 
	 * 	Updates the user's password from the update view. Updates password to pwd2 when pwd equals their old pwd
	 */
	@RequestMapping(value="Reset", method=RequestMethod.POST, produces="application/java")
	@ResponseBody
	public void resetPassword(@RequestBody String jsonObject, HttpSession session) throws Exception{
		BamUser userNewPass = null;
		try {
			userNewPass = new ObjectMapper().readValue(jsonObject, BamUser.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BamUser currentUser = bamUserService.findUserByEmail(userNewPass.getEmail());
		if(currentUser.getPwd().equals(userNewPass.getPwd())){
			currentUser.setPwd(userNewPass.getPwd2());
			bamUserService.addOrUpdateUser(currentUser);
		}else{
			throw new Exception("Wrong password, password not changed");
		}
	}
	
	@RequestMapping(value="Remove", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<BamUser> removeUser(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		BamUser user = bamUserService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Set the user as inactive
		Batch b = null;
		user.setBatch(b);
		bamUserService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return bamUserService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Add", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<BamUser> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		BamUser user = bamUserService.findUserById( userId );
		
		user.setBatch(batchService.getBatchById(batchId));
		
		bamUserService.addOrUpdateUser(user);
		
		return bamUserService.findUsersNotInBatch();
	}
	
	@RequestMapping(value="NotInABatch", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<BamUser> getUsersNotInBatch(HttpServletRequest request) {
		return bamUserService.findUsersNotInBatch();
	}
	
}
