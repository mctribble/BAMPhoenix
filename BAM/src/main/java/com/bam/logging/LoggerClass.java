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

import com.splunk.logging.SplunkCimLogEvent;

@Aspect
@Component
public class LoggerClass {

  // Created Logger for Intercepting Methods and logging that Information

  private static final Logger logger = LogManager.getRootLogger();
  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
  private static Long ServiceCalls = 0L;
  private static Long ControllerCalls = 0L;

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
  public Object interceptService(ProceedingJoinPoint pjp) throws Exception {
    // return to always return join point objects so they are not consumed
    Object proceedObj = null;
    SplunkCimLogEvent event = new SplunkCimLogEvent("service", Long.toString(++ServiceCalls));
    event.addField("time", simpleDateFormat.format(new Date(System.currentTimeMillis())));
    event.addField("signature", pjp.getSignature().getDeclaringTypeName() + " -> " + pjp.getSignature().getName());
    event.addField("args", Arrays.toString(pjp.getArgs()));

    try {
      proceedObj = pjp.proceed();
      event.addField("return", proceedObj);
      logger.info("{" + event + "}");
    } catch (Throwable e) {
      event.addThrowableWithStacktrace(e);
      logger.error("{" + event + "}");
    }

    return proceedObj;
  }

  @Around("execution(* com.bam.controller.*.*(..))")
  public Object interceptController(ProceedingJoinPoint pjp) throws Exception {
    Object proceedObj = null;
    SplunkCimLogEvent event = new SplunkCimLogEvent("controller",  Long.toString(++ControllerCalls));
    event.addField("time", simpleDateFormat.format(new Date(System.currentTimeMillis())));
    event.addField("signature", pjp.getSignature().getDeclaringTypeName() + " -> " + pjp.getSignature().getName());
    event.addField("args", Arrays.toString(pjp.getArgs()));

    try {
      proceedObj = pjp.proceed();
      event.addField("return", proceedObj);
      logger.info("{" + event + "}");
    } catch (Throwable e) {
      event.addThrowableWithStacktrace(e);
      logger.error("{" + event + "}");
    }

    return proceedObj;
  }

}