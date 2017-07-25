package com.bam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.beans.Users;
import com.bam.services.UsersService;

@RestController
@RequestMapping(value="/Users/")
public class TestController {

	
	@Autowired
	UsersService ts;
	
	@RequestMapping(value="all", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Users> getAllUsers(){
		return ts.findAllUsers();
	}
}