package com.example.thymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(BaseErrorController.class);

	@ExceptionHandler(Exception.class)
	public String exception(Exception e){
		logger.warn("GlobalDefaultExceptionHandler:",e);
		return "error";
	}
}