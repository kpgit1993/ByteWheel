package org.bytemark.bytewheel.aspects.services;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutDescriptor {

	@Pointcut("execution(* org.bytemark.bytewheel.*.*.*(..))")
	public void genericPointcut() {}

	/*@Pointcut("within(* org.bytemark.bytewheel.rest.*(..))")
	public void webserviceMetadataPointcut() {}*/
}
