package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;;

@Component
@Aspect
public class CRMLoggingAspect {
	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {

	}

	// setup pointcut for service and dao
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {

	}

	@Pointcut("execution(* com.luv2code.springdemo.dao..*.*(..))")
	private void forDaoPackage() {

	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {

	}

	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint jointPoint) {

		// display method we are calling
		String method = jointPoint.getSignature().toShortString();
		logger.info("------> in @Before: calling method: " + method);

		// display the arguments to the method

		// get the arguments
		Object[] args = jointPoint.getArgs();

		for (Object arg : args) {
			logger.info("------> argument: " + arg);
		}

	}

	// add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint jointPoint, Object result) {
		String method = jointPoint.getSignature().toShortString();
		logger.info("------> in @AfterReturning: calling method: " + method);
	}

}
