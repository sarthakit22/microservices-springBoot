package com.account.service;

import java.util.Map;

import com.account.entity.AccountEntity;

public interface AccountService {

	Map<String, Object> addAccount(AccountEntity account);

	Map<String, Object> addAmtToBank(Integer accId, Long amount);

	Map<String, Object> deleteAccount(Integer accId);

	Map<String, Object> getAccountById(Integer accId);

	Map<String, Object> withdrawAmtFromBank(Integer accId, Long amount);

}
