package org.bytemark.bytewheel.exception;

import java.io.Serializable;

public class BusinessException implements Serializable {

	private String date;
	private String errorMessage;
	
	public BusinessException(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	public BusinessException(String date, String errorMessage){
		this.errorMessage = errorMessage;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
