package com.inventory.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

	Date timeStamp;
	Integer status;
	String error;
	String message;
	String path;
}
