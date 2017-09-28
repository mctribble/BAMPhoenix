package com.bam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.exception.CustomException;
import com.bam.service.BamUserService;
import com.bam.service.BatchService;
import com.bam.service.PasswordGenerator;

@RestController
@RequestMapping(value = "/api/v1/Users/")
public class UserController {
	
	private static final String USERID = "userId";
	private static final String BATCHID = "batchId";

	@Autowired
	BamUserService userService;

	@Autowired
	BatchService batchService;

	@RequestMapping(value = "All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllUsers() {
		return userService.findAllUsers();
	}

	@RequestMapping(value = "AllTrainers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllTrainers() {
		return userService.findByRole(2);
	}

	@RequestMapping(value = "AllAssociates", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getAllAssociates() {
		return userService.findByRole(1);
	}

	@RequestMapping(value = "InBatch", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getUsersInBatch(HttpServletRequest request) {

		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(BATCHID) );
		
		//Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}

	@RequestMapping(value = "Drop", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BamUser> dropUserFromBatch(HttpServletRequest request) {

		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USERID) );
		BamUser user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();

		// Drop user from the batch
		user.setBatch(null);
		user.setRole(0);
		userService.addOrUpdateUser(user);

		// Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}

	
	@RequestMapping(value="Update", method=RequestMethod.POST, produces="application/json")
	public void updateUser(@RequestBody BamUser currentUser) {
		BamUser user = userService.findUserByEmail(currentUser.getEmail());
		currentUser.setPwd(user.getPwd());
		userService.addOrUpdateUser(currentUser);
	}
	
	@RequestMapping(value="Register", method=RequestMethod.POST, produces="application/json")
	public void addUser(@RequestBody BamUser currentUser) throws CustomException {
		if(userService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			String password = currentUser.getPwd();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			currentUser.setPwd(hashed);
			userService.addOrUpdateUser(currentUser);
		} else {
			throw new CustomException("Email exists in database");
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
	public void resetPassword(@RequestBody BamUser userNewPass) throws CustomException{
		BamUser currentUser = userService.findUserByEmail(userNewPass.getEmail());
		if(BCrypt.checkpw(userNewPass.getPwd(), currentUser.getPwd())){
			String hashed =  BCrypt.hashpw(userNewPass.getPwd2(), BCrypt.gensalt());
			currentUser.setPwd(hashed);
			userService.addOrUpdateUser(currentUser);
		}else{
			throw new CustomException("Wrong password, password not changed");
		}
	}

	@RequestMapping(value = "Remove", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BamUser> removeUser(HttpServletRequest request) {

		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USERID) );
		BamUser user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();

		// Set the user as inactive
		Batch b = null;
		user.setBatch(b);
		userService.addOrUpdateUser(user);

		// Return users from batch without the user
		return userService.findUsersInBatch(batchId);
	}

	@RequestMapping(value = "Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BamUser> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USERID) );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter(BATCHID) );
		
		BamUser user = userService.findUserById( userId );
		user.setBatch(batchService.getBatchById(batchId));
		userService.addOrUpdateUser(user);
		return userService.findUsersNotInBatch();
	}

	@RequestMapping(value = "NotInABatch", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BamUser> getUsersNotInBatch(HttpServletRequest request) {
		return userService.findUsersNotInBatch();
	}
	
	@RequestMapping(value = "Recovery", method = RequestMethod.POST, produces = "application/json")
    public void recoverPassword(@RequestBody String email) throws CustomException {
        // Lookup user in database by e-mail
        BamUser user = userService.findUserByEmail(email);
        if (user != null) {
        	String generate = PasswordGenerator.makePassword();
        	String hashed =  BCrypt.hashpw(generate, BCrypt.gensalt());
        	user.setPwd(hashed);
        	userService.addOrUpdateUser(user);
        	userService.recoverE(user, generate);
        } else { 
        	throw new CustomException("User does not exist in the system");
        }
    }


}