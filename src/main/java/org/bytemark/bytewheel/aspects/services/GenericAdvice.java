package org.bytemark.bytewheel.aspects.services;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GenericAdvice {

	@Before(value = "PointcutDescriptor.genericPointcut()")
	@Order(2)
	public void executeBeforeAllMethods(JoinPoint joinPoint) {
		LoggerUtil.info("Starting => " + joinPoint.getTarget() + "-"
				+ joinPoint.getSignature().toLongString());
	}

	@AfterReturning(value = "PointcutDescriptor.genericPointcut()", returning = "retVal")
	public void executeAfterAllMethodsOnReturning(JoinPoint joinPoint,
			Object retVal) {
		LoggerUtil.info("Returning => " + joinPoint.getTarget() + "-"
				+ joinPoint.getSignature().toLongString()
				+ " [[ Return value: " + retVal + " ]]");
	}

	@AfterThrowing(value = "PointcutDescriptor.genericPointcut()", throwing = "ex")
	public void executeOnThrowing(JoinPoint joinPoint, Exception ex) {
		LoggerUtil.info("Throwing => " + joinPoint.getTarget() + "-"
				+ joinPoint.getSignature().toLongString()
				+ " [[ Exception message: " + ex.getMessage() + " ]]");
	}

	/*
	 * This is causing the error message to disappear as well as the error
	 * status
	 * 
	 * @Around(value = "PointcutDescriptor.genericPointcut()") public void
	 * executeAroundForAllMethods(ProceedingJoinPoint joinPoint) throws
	 * Throwable {
	 * 
	 * long startTime = System.currentTimeMillis();
	 * LoggerUtil.info("Starting => " + joinPoint.getTarget() + "-" +
	 * joinPoint.getSignature().toLongString());
	 * 
	 * joinPoint.proceed();
	 * 
	 * long endTime = System.currentTimeMillis(); long elapsedTime = endTime -
	 * startTime; LoggerUtil.info("Ending => " + joinPoint.getTarget() + "-"
	 * + joinPoint.getSignature().toLongString() + "[Time taken = " +
	 * elapsedTime + " ms ]"); }
	 */
}
