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
@RequestMapping(value="/api/v1/Users/")
public class UserController {
	@Autowired
	UsersService userService;
	
	@Autowired
	BatchService batchService;
	
	@RequestMapping(value="All", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllUsers(){
		return userService.findAllUsers();
	}
	
	@RequestMapping(value="AllTrainers", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllTrainers(){
		return userService.findByRole(2);
	}
	
	@RequestMapping(value="AllAssociates", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllAssociates(){
		return userService.findByRole(1);
	}
	
	@RequestMapping(value="InBatch", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Drop", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> dropUserFromBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		Users user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();
		
		//Drop user from the batch
		user.setBatch(null);
		user.setRole(0);
		userService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}
	
	@RequestMapping(value="Update", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public void updateUser(@RequestBody String jsonObject, HttpSession session) {
		System.out.println("Enter update controller");
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
	
	@RequestMapping(value="Register", method=RequestMethod.POST, produces="application/json")
	public void addUser(@RequestBody Users currentUser, HttpSession session) throws Exception {
	
		if(userService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			userService.addOrUpdateUser(currentUser);
		} else {
			throw new Exception("Email exists in database");
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
		Users userNewPass = null;
		try {
			userNewPass = new ObjectMapper().readValue(jsonObject, Users.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Users currentUser = userService.findUserByEmail(userNewPass.getEmail());
		if(currentUser.getPwd().equals(userNewPass.getPwd())){
			currentUser.setPwd(userNewPass.getPwd2());
			userService.addOrUpdateUser(currentUser);
		}else{
			throw new Exception("Wrong password, password not changed");
		}
	}
	
	@RequestMapping(value="Remove", method=RequestMethod.POST, produces="application/json")
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
	
	@RequestMapping(value="Add", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public List<Users> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		Users user = userService.findUserById( userId );
		
		user.setBatch(batchService.getBatchById(batchId));
		
		userService.addOrUpdateUser(user);
		
		return userService.findUsersNotInBatch();
	}
	
	@RequestMapping(value="NotInABatch", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersNotInBatch(HttpServletRequest request) {
		return userService.findUsersNotInBatch();
	}
	
}
