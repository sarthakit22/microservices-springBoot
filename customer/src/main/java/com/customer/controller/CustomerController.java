package com.customer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.dto.ApiResponseDto;
import com.customer.entity.CustomerEntity;
import com.customer.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService custService;

	@PostMapping("/add-customer")
	public ApiResponseDto addCustomer(@RequestBody CustomerEntity cust) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = custService.addCustomer(cust);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("cust"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("cust")) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		return apiResBuilder.build();
	}

	@GetMapping("/all-customers")
	public ApiResponseDto getAllCustomers() {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = custService.getAllCustomers();
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("cust"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("cust")) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
		return apiResBuilder.build();
	}

	@GetMapping("/get-customer")
	public ApiResponseDto getCustomerById(@RequestParam Integer custId) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = custService.getCustomerById(custId);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("cust"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("cust")) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
		return apiResBuilder.build();
	}

	@PutMapping("/update-customer")
	public ApiResponseDto updateCustomer(@RequestBody CustomerEntity cust) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = custService.updateCustomer(cust);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("cust"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("cust")) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		return apiResBuilder.build();
	}

	@DeleteMapping("/delete-customer")
	public ApiResponseDto deleteCustomer(@RequestParam Integer custId) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = custService.deleteCustomer(custId);
		apiResBuilder.withMessage(map.get("msg")).withData("");
		apiResBuilder.withStatus("1".equals(map.get("msgCd")) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
		return apiResBuilder.build();
	}
}
