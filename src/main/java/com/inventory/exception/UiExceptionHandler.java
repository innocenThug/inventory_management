package com.inventory.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.inventory.controller.ui")
public class UiExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleNoResource(ProductNotFoundException ex, HttpServletRequest request, HttpServletResponse response, Model model) {
        return render(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request, response, model);
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleProductNotFound(BadRequestException ex, HttpServletRequest request, HttpServletResponse response, Model model) {
        return render(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request, response, model);
    }

    @ExceptionHandler(DuplicateProductException.class)
    public String handleDuplicate(DuplicateProductException ex, HttpServletRequest request, HttpServletResponse response, Model model) {
        return render(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request, response, model);
    }
    
    @ExceptionHandler(InsufficientStockException.class)
    public String handleInsufficientStock(InsufficientStockException ex, HttpServletRequest request, HttpServletResponse response, Model model) {
        return render(HttpStatus.CONTENT_TOO_LARGE, "Insufficient Stock", ex.getMessage(), request, response, model);
    }
    
    @ExceptionHandler(BusinessException.class)
    public String handleBusiness(BusinessException ex, HttpServletRequest request, HttpServletResponse response, Model model) {
        return render(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), request, response, model);
    }

    @ExceptionHandler(Exception.class)
    public String handleUiError(Exception ex, HttpServletRequest request, HttpServletResponse response, Model model) {
        return render(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), request, response, model);
    }

    private String render(HttpStatus status, String error, String message, HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setStatus(status.value());
    	String now = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	model.addAttribute("timeStamp", now);
        model.addAttribute("status", status.value());
        model.addAttribute("error", error);
        model.addAttribute("message", message != null ? message : "Unexpected error occurred.");
        model.addAttribute("path", request.getRequestURI());

        return "error";
    }
}