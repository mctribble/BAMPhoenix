package com.bam.exception;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bam.logging.LoggerClass;

public class CustomException extends Exception {

  private static final long serialVersionUID = 8632434538067813982L;
  private static final Logger logger = LogManager.getLogger(LoggerClass.class);

  public CustomException(Throwable message) {
    super(message);
  }

  public CustomException(MessagingException e) {
    logger.error(e);
  }

  public CustomException(String string) {
    super(string);
  }

}