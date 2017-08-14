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

/**
 * 
 * @author Duncan Hayward
 * Retrieve user object
 * Check for successful login in console
 * Set the appropriate content type
 * Write object to the response writer object
 * Send the success response
 * Empty and close the stream
 *
 */
@Component("restAuthenticationSuccessHandler")
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private BamUserRepository userService;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		
		BamUser user = userService.findByEmail(authentication.getName());
		
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		writer.write(mapper.writeValueAsString(user));
		
		response.setStatus(HttpServletResponse.SC_OK);
		
		writer.flush();
		writer.close();
	}
}
