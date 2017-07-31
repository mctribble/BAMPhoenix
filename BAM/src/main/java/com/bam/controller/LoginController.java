//package com.bam.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.bam.beans.Users;
//import com.bam.beans.Users.UserType;
//@Component
//@Controller
//@RequestMapping("/login.do")
//
//public class LoginController{
//
//	@RequestMapping(method = RequestMethod.GET)
//	public String loginGet(){
//		
//		return "login";
//		
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	
//	public String login(ModelMap map, HttpServletRequest req){
//		
//		return null;
//		
//		
//		/*
//		HttpSession session = req.getSession();
//		XmlWebApplicationContext c = new XmlWebApplicationContext();
//        c.setConfigLocation("/WEB-INF/spring-servlet.xml");
//        c.setServletContext(req.getServletContext());
//        c.refresh();
//        DaoInterface dao = (DaoInterface)c.getBean("daoHib");
//		BAMUser loggedInUser;
//		
//		String email = req.getParameter("email");
//		String password = req.getParameter("passwd");
//		loggedInUser = dao.getBAMUserByEmail(email);
//		String nextPage = "";
//		if (loggedInUser != null && loggedInUser.getPasswd().equals(password)){
//			session.setAttribute("loggedInUser", loggedInUser);
//			if (loggedInUser.getUserType().equals(UserType.Associate)){
//				nextPage = "associate";
//			}
//			else if (loggedInUser.getUserType().equals(UserType.Trainer)){
//				nextPage = "trainer";
//			}
//			else {
//				nextPage = "trainer";
//			}
//			nextPage += "-homepage";
//			
//		}
//		
//		else {
//			nextPage = "login";
//		}
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			
//			nextPage = mapper.writeValueAsString(nextPage);
//		} catch (JsonProcessingException e) {
//			
//			e.printStackTrace();
//		}
//		c.close();
//		System.out.println(nextPage);
//		return nextPage;*/
//	}
//
//}
