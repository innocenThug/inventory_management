package com.inventory.exception;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class DuplicateProductException extends BusinessException{

	public DuplicateProductException(String message) {
		super(message);
		
		}
}
