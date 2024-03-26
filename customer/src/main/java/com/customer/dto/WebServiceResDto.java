package com.customer.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class WebServiceResDto {

	private int statusCode;
	private HttpStatus status;
	private String message;
	private Object data;
}
