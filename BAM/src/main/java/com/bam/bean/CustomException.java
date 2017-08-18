package com.bam.bean;

import javax.mail.MessagingException;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8632434538067813982L;
	public CustomException(String message){
		super(message);
	}
	public CustomException(MessagingException e) {
		e.printStackTrace();
		
	}
		// TODO Auto-generated constructor stub
	}
	
	
