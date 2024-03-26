package com.account.controller;

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

import com.account.dto.ApiResponseDto;
import com.account.entity.AccountEntity;
import com.account.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accService;

	@PostMapping("/add-account")
	public ApiResponseDto addAccount(@RequestBody AccountEntity account) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = accService.addAccount(account);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("acc"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("acc")) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		return apiResBuilder.build();
	}

	@PutMapping("/add-amount")
	public ApiResponseDto addAmtToBank(@RequestParam Integer accId, @RequestParam Long amount) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = accService.addAmtToBank(accId, amount);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("acc"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("acc")) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		return apiResBuilder.build();
	}

	@DeleteMapping("/delete-account")
	public ApiResponseDto deleteAccount(@RequestParam Integer accId) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = accService.deleteAccount(accId);
		apiResBuilder.withMessage(map.get("msg")).withData("");
		apiResBuilder.withStatus("1".equals(map.get("msgCd")) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
		return apiResBuilder.build();
	}

	@GetMapping("/get-account")
	public ApiResponseDto getAccountById(@RequestParam Integer accId) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = accService.getAccountById(accId);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("acc"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("acc")) ? HttpStatus.OK : HttpStatus.NO_CONTENT);
		return apiResBuilder.build();
	}

	@PutMapping("/withdraw-amount")
	public ApiResponseDto withdrawAmtFromBank(@RequestParam Integer accId, @RequestParam Long amount) {
		ApiResponseDto.ApiResponseDtoBuilder apiResBuilder = new ApiResponseDto.ApiResponseDtoBuilder();
		Map<String, Object> map = accService.withdrawAmtFromBank(accId, amount);
		apiResBuilder.withMessage(map.get("msg")).withData(map.get("acc"));
		apiResBuilder.withStatus(!ObjectUtils.isEmpty(map.get("acc")) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
		return apiResBuilder.build();
	}
}
