package com.inventory.exception;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class ProductNotFoundException extends BusinessException{
	
	public ProductNotFoundException(String message) {
		super(message);
		
		}

}
