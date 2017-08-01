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

import com.bam.beans.Batch;
import com.bam.beans.Users;
import com.bam.service.BatchService;
import com.bam.service.UsersService;


@RestController
@RequestMapping(value="/Users/")
public class UserController {
	@Autowired
	UsersService userService;
	
	@Autowired
	BatchService batchService;
	
	@RequestMapping(value="All.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllUsers(){
		return userService.findAllUsers();
	}
	
	@RequestMapping(value="AllTrainers.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllTrainers(){
		return userService.findByRole(2);
	}
	
	@RequestMapping(value="AllAssociates.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllAssociates(){
		return userService.findByRole(1);
	}
	
	@RequestMapping(value="InBatch.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Drop.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> dropUserFromBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		Users user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Drop user from the batch
		user.setBatch(null);
		userService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Update.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void updateUser(@RequestBody String jsonObject, HttpSession session) {
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
		userService.addOrUpdateUser(currentUser);
	}
	
	@RequestMapping(value="Register.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void addUser(@RequestBody String jsonObject, HttpSession session) throws Exception {
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
	
		if(userService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			userService.addOrUpdateUser(currentUser);
		} else {
			Exception e = null;
			throw  e;
		}	
	}
	
	@RequestMapping(value="Remove.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> removeUser(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		Users user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Set the user as inactive
		Batch b = null;
		user.setBatch(b);
		userService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Add.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		/*BatchService batchService = new BatchService();*/
		
		Users user = userService.findUserById( userId );
		
		user.setBatch(batchService.getBatchById(batchId));
		
		userService.addOrUpdateUser(user);
		
		return userService.findUsersNotInBatch();
	}
	
	@RequestMapping(value="NotInABatch.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersNotInBatch(HttpServletRequest request) {
		return userService.findUsersNotInBatch();
	}
	
}
