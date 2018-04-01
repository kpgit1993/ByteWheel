package org.bytemark.bytewheel.exception;

import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.naming.AuthenticationException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
@RequestMapping
public class ExceptionMappers {

	private static String getCurrentDate(){
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		return  day+"-"+month+"-"+year+" "+hour+":"+minute+":"+second;
	}
	
	/**
	 * Generic Exception mapper - mas=ps to all exceptions if no other speific exception mapper is found
	 * Returns the error code 500 
	 */
	@ExceptionHandler({Exception.class})
	public ResponseEntity<BusinessException> handleException(Exception e) {
		e.printStackTrace();
		BusinessException ex = new BusinessException(ExceptionMappers.getCurrentDate(), e.getMessage());
		return new ResponseEntity<BusinessException>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * IllegalArgumentException for invalid input parametes passed to REST calls
	 */
	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<BusinessException> handleInvalidParameterException(Exception e) {
		e.printStackTrace();
		BusinessException ex = new BusinessException(ExceptionMappers.getCurrentDate(), e.getMessage());
		return new ResponseEntity<BusinessException>(ex, HttpStatus.PRECONDITION_FAILED);
	}
	
	/**
	 * TimeoutException for timeout (e.g: network issue)
	 */
	@ExceptionHandler({TimeoutException.class})
	public ResponseEntity<BusinessException> handleTimeoutException(Exception e) {
		e.printStackTrace();
		BusinessException ex = new BusinessException(ExceptionMappers.getCurrentDate(), e.getMessage());
		return new ResponseEntity<BusinessException>(ex, HttpStatus.REQUEST_TIMEOUT);
	}
	
	/**
	 * Security exceptions 
	 */
	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<BusinessException> handleAuthenticationException(Exception e) {
		e.printStackTrace();
		BusinessException ex = new BusinessException(ExceptionMappers.getCurrentDate(), e.getMessage());
		return new ResponseEntity<BusinessException>(ex, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<BusinessException> handleAuthorzationException(Exception e) {
		e.printStackTrace();
		BusinessException ex = new BusinessException(ExceptionMappers.getCurrentDate(), e.getMessage());
		return new ResponseEntity<BusinessException>(ex, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<BusinessException> handleNotFoundException(Exception e) {
		e.printStackTrace();
		BusinessException ex = new BusinessException(ExceptionMappers.getCurrentDate(), e.getMessage());
		return new ResponseEntity<BusinessException>(ex, HttpStatus.NOT_FOUND);
	}
}

