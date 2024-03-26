package com.account.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.account.constant.LogConstants;
import com.account.constant.MessageConstants;
import com.account.dto.WebServiceResDto;
import com.account.entity.AccountEntity;
import com.account.repository.AccountRepository;
import com.account.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accRepo;

	@Autowired
	private WebClient webClient;

	@Autowired
	private ModelMapper mapper;

	/*
	 * This method includes two primary validations: 1.If the customer is not
	 * present with account ID, it will trigger a message. 2.If an account already
	 * exists with the same account ID, it will trigger a message.
	 */
	@Override
	public Map<String, Object> addAccount(AccountEntity account) {
		log.info(LogConstants.ADD_ACCOUNT + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			if (!ObjectUtils.isEmpty(account) && account.getAccId() != 0) {
				WebServiceResDto response = getCustomerDetails(account.getAccId());
				AccountEntity accEntity = mapper.map(accRepo.findById(account.getAccId()), AccountEntity.class);
				if (response != null && ObjectUtils.isNotEmpty(response.getData()) && ObjectUtils.isEmpty(accEntity)) {
					accEntity = accRepo.saveAndFlush(account);
					accEntity.setCustomer(response.getData());
					map.put("acc", accRepo.saveAndFlush(account));
					map.put("msg", MessageConstants.ACCOUNT_ADDED + account.getAccId());
				} else {
					map.put("msg",
							ObjectUtils.isNotEmpty(accEntity)
									? MessageConstants.ACCOUNT_ALREADY_EXISTS + account.getAccId()
									: MessageConstants.PLEASE_ADD_CUSTOMER + account.getAccId());
				}
			} else {
				map.put("msg", MessageConstants.PLEASE_ENTER_ALL_DETAILS);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);
		}
		log.info(LogConstants.ADD_ACCOUNT + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> addAmtToBank(Integer accId, Long amount) {
		log.info(LogConstants.ADD_AMOUNT_TOBANK + LogConstants.STARTED);
		return addAndWithdrawAmount(accId, amount, "+");
	}

	@Override
	public Map<String, Object> deleteAccount(Integer accId) {
		log.info(LogConstants.DELETE_ACCOUNT + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			AccountEntity accEntity = mapper.map(accRepo.findById(accId), AccountEntity.class);
			if (accId != 0 && ObjectUtils.isNotEmpty(accEntity)) {
				accRepo.deleteById(accId);
				WebServiceResDto response = getCustomerDetails(accId);
				if (response != null && ObjectUtils.isNotEmpty(response.getData())) {
					map.put("msg", MessageConstants.ACCOUNT_CUSTOMER_DELETED);
					webClient.delete().uri("/delete-customer?custId=" + accId).retrieve()
							.bodyToMono(WebServiceResDto.class).block();
				} else {
					map.put("msg", MessageConstants.ACCOUNT_DELETED);
				}
				map.put("msgCd", "0");
			} else {
				map.put("msgCd", "1");
				map.put("msg", accId != 0 ? MessageConstants.ACCOUNT_NOT_EXISTS + accId
						: MessageConstants.PLEASE_ENTER_VALID_ACC_ID);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);

		}
		log.info(LogConstants.DELETE_ACCOUNT + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> getAccountById(Integer accId) {
		log.info(LogConstants.GET_ACCOUNT_BY_ID + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			if (accId != 0) {
				AccountEntity accEntity = mapper.map(accRepo.findById(accId), AccountEntity.class);
				if (ObjectUtils.isNotEmpty(accEntity)) {
					accEntity.setCustomer(getCustomerDetails(accId).getData());
					map.put("acc", accEntity);
					map.put("msg", MessageConstants.ACCOUNT_DETAILS + accId);
				} else {
					map.put("msg", MessageConstants.ACCOUNT_DOES_NOT_EXISTS + accId);
				}
			} else {
				map.put("msg", MessageConstants.PLEASE_ENTER_VALID_ACC_ID);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);
		}
		log.info(LogConstants.GET_ACCOUNT_BY_ID + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> withdrawAmtFromBank(Integer accId, Long amount) {
		log.info(LogConstants.WITHDRAW_AMOUNT_FROMBANK + LogConstants.STARTED);
		return addAndWithdrawAmount(accId, amount, "-");
	}

	/*
	 * Common method for accessing customer information.
	 */
	private WebServiceResDto getCustomerDetails(int accId) {
		return webClient.get().uri("/get-customer?custId=" + accId).retrieve().bodyToMono(WebServiceResDto.class)
				.block();
	}

	/*
	 * Common method for depositing and withdrawing funds from a bank account.
	 */
	public Map<String, Object> addAndWithdrawAmount(Integer accId, Long amount, String operator) {
		log.info(LogConstants.ADD_AND_WITHDRAW + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			AccountEntity accEntity = mapper.map(accRepo.findById(accId), AccountEntity.class);
			if (accId != 0 && ObjectUtils.isNotEmpty(accEntity)) {
				AccountEntity acc = "+".equals(operator)
						? new AccountEntity(accId, accEntity.getAccType(), accEntity.getIfsc(),
								accEntity.getBalance() + amount)
						: new AccountEntity(accId, accEntity.getAccType(), accEntity.getIfsc(),
								accEntity.getBalance() - amount);
				acc = accRepo.saveAndFlush(acc);
				acc.setCustomer(getCustomerDetails(accId).getData());
				map.put("acc", acc);
				map.put("msg", "+".equals(operator) ? MessageConstants.AMOUNT_ADDED + amount
						: MessageConstants.AMOUNT_DEBITED + amount);
			} else {
				map.put("msg", accId != 0 ? MessageConstants.ACCOUNT_NOT_EXISTS + accId
						: MessageConstants.PLEASE_ENTER_VALID_ACC_ID);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);
		}
		log.info(LogConstants.ADD_AND_WITHDRAW + LogConstants.ENDS);
		return map;
	}

}
