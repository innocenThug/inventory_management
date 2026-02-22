package com.inventory.exception;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
	
	public BusinessException(String message) {
	super(message);
	
	}
	
	public BusinessException()
	{
		super();
	}
	
}
