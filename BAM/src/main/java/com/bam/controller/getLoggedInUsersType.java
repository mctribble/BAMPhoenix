package com.bam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
@Controller
@RequestMapping("/getLoggedInType.do")
public class getLoggedInUsersType {


		@RequestMapping(method = RequestMethod.GET)
		@ResponseBody
		public String getLoggedIn(ModelMap map, HttpServletRequest req) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			String json = "";
			try {
				// converts user authorities to json, gets the one at index 0 (the user can only have one)
				json = mapper.writeValueAsString(user.getAuthorities().toArray()[0]);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}
			System.out.println(json);
			return json;

		}
	}