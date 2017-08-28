package com.revature.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerClass {
	
	
	final static Logger logger = Logger.getLogger(LoggerClass.class);

	@Pointcut("execution(public * find*(..))")
	public void logFind() {
		
	}
	
	@After("logFind()")
	public void createSuccess() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); 
		logger.info("data request made at " + sdf.format(new Date(System.currentTimeMillis())));
	}
	
}
