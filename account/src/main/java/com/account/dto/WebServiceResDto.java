package com.account.dto;

import org.springframework.http.HttpStatus;

import com.account.entity.CustomerEntity;

import lombok.Data;

@Data
public class WebServiceResDto {
	
	private int statusCode;
	private HttpStatus status;
	private String message;
	private CustomerEntity data;
}
