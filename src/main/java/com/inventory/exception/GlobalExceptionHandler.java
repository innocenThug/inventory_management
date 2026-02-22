package com.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.inventory.dto.ProductMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestControllerAdvice(basePackages = "com.inventory.controller.api")
@AllArgsConstructor
public class GlobalExceptionHandler {
	
	private final ProductMapper mapper;
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(ProductNotFoundException ex, HttpServletRequest request) {
		return mapper.builder(ex.getMessage(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
		return mapper.builder(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(DuplicateProductException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicate(DuplicateProductException ex, HttpServletRequest request) {
		return mapper.builder(ex.getMessage(), HttpStatus.CONFLICT, request);
	
		}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ApiErrorResponse> handleInsufficient(InsufficientStockException ex, HttpServletRequest request) {
		return mapper.builder(ex.getMessage(), HttpStatus.CONTENT_TOO_LARGE, request);
	
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusExp(BusinessException ex, HttpServletRequest request) {
		return mapper.builder(ex.getMessage(), HttpStatus.FORBIDDEN, request);
		
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
		return mapper.builder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
}
	
}
