package com.bam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Users;
import com.bam.service.UsersService;

@RestController
@RequestMapping(value="/Users/")
public class UserController {
	@Autowired
	UsersService userService;
	
	@RequestMapping(value="All.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllUsers(){
		return userService.findAllUsers();
	}
	
	@RequestMapping(value="InBatch.do", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getUsersInBatch(HttpServletRequest request) {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter("batchId") );
		
		//Retrieve and return users in a batch from the database
		return userService.findUsersInBatch(batchId);
	}
	
	public List<Users> dropUserFromBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter("userId") );
		
		
		Users u = userService.findUserById( userId );
		u.setBatch(null);
		userService.addOrUpdateUser(u);
		
		return null;
	}
	
}
