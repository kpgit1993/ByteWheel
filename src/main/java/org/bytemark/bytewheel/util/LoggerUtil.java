package org.bytemark.bytewheel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerUtil {

	public static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

	public static void info(String message){
		logger.info(message);
	}
	
	public static void debug(String message){
		logger.debug(message);
	}
	
	public static void error(String message){
		logger.error(message);
	}
	
	public static void fine(String message){
		logger.trace(message);
	}
}
