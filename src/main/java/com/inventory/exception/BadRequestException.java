package com.inventory.exception;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class BadRequestException extends BusinessException {

	public BadRequestException(String message) {
		super(message);
		
		}
}
