package com.bam.bean;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.bam.logging.LoggerClass;

public class CustomException extends Throwable {


	private static final long serialVersionUID = 8632434538067813982L;
	private static final Logger logger = Logger.getLogger(LoggerClass.class);

	public CustomException(String message){
		super(message);
	}
	public CustomException(MessagingException e) {
		logger.error(e);
		
	}
		
	}
	
	
