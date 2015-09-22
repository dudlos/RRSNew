package com.dudlo.reservationsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomException extends RuntimeException {

	private Logger log = LoggerFactory.getLogger(CustomException.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String exceptionCode;
	
	public CustomException() {
		super();
		log.info("Default constructor");
	}

	public CustomException(String exceptionCode) {
		log.info("Exception Code: {}", exceptionCode);
		this.exceptionCode = exceptionCode;
	}
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	
}
