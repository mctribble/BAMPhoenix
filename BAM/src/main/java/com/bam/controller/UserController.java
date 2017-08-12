package com.bam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping(value = "/api/v1/Users/")
public class UserController {
	
	private final String userID = "userId";
	private final String batchID = "batchId";
	
	@Autowired

	BamUserService bamUserService;


	@Autowired
	BatchService batchService;

	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllUsers(){
		return bamUserService.findAllUsers();
	}

	@RequestMapping(value = "AllTrainers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllTrainers(){
		return bamUserService.findByRole(2);
	}

	@RequestMapping(value = "AllAssociates", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllAssociates(){
		return bamUserService.findByRole(1);
	}

	@RequestMapping(value = "InBatch", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(batchID) );
		
		//Retrieve and return users in a batch from the database
		return bamUserService.findUsersInBatch(batchId);
	}

	@RequestMapping(value = "Drop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BamUser> dropUserFromBatch(HttpServletRequest request) {
		//Get the user id from the request

		int userId = Integer.parseInt( request.getParameter(USER_ID) );
		BamUser user = bamUserService.findUserById( userId );

		int batchId = user.getBatch().getId();

		// Drop user from the batch
		user.setBatch(null);
		user.setRole(0);
		bamUserService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return bamUserService.findUsersInBatch(batchId);
	}

	
	@RequestMapping(value="Update", method=RequestMethod.POST, produces="application/json")
	public void updateUser(@RequestBody BamUser currentUser) {
		BamUser user = bamUserService.findUserByEmail(currentUser.getEmail());
		currentUser.setPwd(user.getPwd());
		bamUserService.addOrUpdateUser(currentUser);
	}
	
	@RequestMapping(value="Register", method=RequestMethod.POST, produces="application/json")
	public void addUser(@RequestBody BamUser currentUser) throws Exception {
		if(bamUserService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			bamUserService.addOrUpdateUser(currentUser);
		} else {
			throw new IllegalArgumentException("Email exists in database");
		}	
	}

	/**
	 * @author Tom Scheffer
	 * @param jsonObject
	 *            - object being passed in
	 * @param session
	 *            - current session
	 * @throws Exception
	 *             - for when previous password is wrong
	 * @param jsonObject - object being passed in
	 * @throws Exception - for when previous password is wrong
	 * 
	 *             Updates the user's password from the update view. Updates
	 *             password to pwd2 when pwd equals their old pwd
	 */

	@RequestMapping(value="Reset", method=RequestMethod.POST, produces="application/java")
	public void resetPassword(@RequestBody BamUser userNewPass) throws Exception{
	  BamUser currentUser = bamUserService.findUserByEmail(userNewPass.getEmail());
		if (currentUser.getPwd().equals(userNewPass.getPwd())) {
			currentUser.setPwd(userNewPass.getPwd2());
			bamUserService.addOrUpdateUser(currentUser);

		}else{
			throw new IllegalArgumentException("Wrong password, password not changed");
		}
	}

	@RequestMapping(value = "Remove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BamUser> removeUser(HttpServletRequest request) {

		//Get the user id from the request

		int userId = Integer.parseInt( request.getParameter(USER_ID) );
		BamUser user = bamUserService.findUserById( userId );

		int batchId = user.getBatch().getId();

		// Set the user as inactive
		Batch b = null;
		user.setBatch(b);
		bamUserService.addOrUpdateUser(user);
		
		//Return users from batch without the user
		return bamUserService.findUsersInBatch(batchId);
	}

	@RequestMapping(value = "Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BamUser> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(userID) );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter(batchID) );
		
		BamUser user = bamUserService.findUserById( userId );
		
		user.setBatch(batchService.getBatchById(batchId));
		
		bamUserService.addOrUpdateUser(user);
		
		return bamUserService.findUsersNotInBatch();
	}

	@RequestMapping(value = "NotInABatch", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getUsersNotInBatch(HttpServletRequest request) {
		return bamUserService.findUsersNotInBatch();
	}

}
