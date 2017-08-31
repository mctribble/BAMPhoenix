package com.bam.logging;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.bam.exception.CustomException;

@Aspect
@Component
public class LoggerClass {

  // Created Logger for Intercepting Methods and logging that Information 

  private static final Logger logger = LogManager.getRootLogger();

  /**
   * Logging Methods from the Batch service class
   * addOrUpdateBranch()
   * getBatchById()
   * getBatchAll()
   * getBatchByTrainer()
   * 
   * @author Jonathan Layssard and Troy King
   */
  @Around("execution(* com.bam.service.*.*(..))")
  public Object interceptService(ProceedingJoinPoint pjp) throws CustomException {
    // return to always return join point objects so they are not consumed
    Object proceedObj = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    String baseString = "(" + simpleDateFormat.format(new Date(System.currentTimeMillis())) + ")"
                        + ": " + pjp.getSignature().getDeclaringTypeName()
                        + " -> " + pjp.getSignature().getName()
                        + " " + Arrays.toString(pjp.getArgs())
                        + " => ";

    try {
      proceedObj = pjp.proceed();
      logger.info(baseString + proceedObj);
    } catch (Throwable e) {//NOSONAR
      logger.error(baseString + e);
    }

    return proceedObj;
  }

  @Around("execution(* com.bam.controller.*.*(..))")
  public Object interceptController(ProceedingJoinPoint pjp) throws CustomException {
    Object proceedObj = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    String baseString = "(" + simpleDateFormat.format(new Date(System.currentTimeMillis())) + ")"
                        + ": " + pjp.getSignature().getDeclaringTypeName()
                        + " -> " + pjp.getSignature().getName()
                        + " " + Arrays.toString(pjp.getArgs())
                        + " => ";

    try {
      proceedObj = pjp.proceed();
      logger.info(baseString + proceedObj);
    } catch (Throwable e) {//NOSONAR
      logger.error(baseString + e);
    }

    return proceedObj;
  }

}