package com.inventory.exception;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class InsufficientStockException extends BusinessException{
	
	public InsufficientStockException(String message) {
		super(message);
		
		}

}
