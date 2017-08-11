package com.bam.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bam.bean.BamUser;
import com.bam.repository.BamUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("restAuthenticationSuccessHandler")
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private BamUserRepository userService;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		//Retrieve user object
		BamUser user = userService.findByEmail(authentication.getName());
		//Check for successful login in console
		System.out.println("SUCCESS");
		//Set the appropriate content type
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		//Write object to the response writer object
		writer.write(mapper.writeValueAsString(user));
		//Send the success response
		response.setStatus(HttpServletResponse.SC_OK);
		//Empty and close the stream
		writer.flush();
		writer.close();
	}
}
