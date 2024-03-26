package com.customer.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.customer.contant.LogConstants;
import com.customer.contant.MessageConstants;
import com.customer.dto.WebServiceResDto;
import com.customer.entity.CustomerEntity;
import com.customer.repository.CustomerRepo;
import com.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo custRepo;

	@Autowired
	private WebClient webClient;

	@Autowired
	private ModelMapper mapper;

	public Map<String, Object> addCustomer(CustomerEntity cust) {
		log.info(LogConstants.ADD_CUSTOMER + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			if (!ObjectUtils.isEmpty(cust) && cust.getDob().length() == 10 && cust.getMobileNo().length() == 10) {
				map.put("cust", custRepo.saveAndFlush(cust));
				map.put("msg", MessageConstants.CUSTOMER_ADDED);
			} else {
				map.put("msg", MessageConstants.LENGTH_10_VALIDATION);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);
		}
		log.info(LogConstants.ADD_CUSTOMER + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> getAllCustomers() {
		log.info(LogConstants.GET_ALL_CUSTOMERS + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			List<CustomerEntity> cust = custRepo.findAll();
			map.put("cust", cust);
			map.put("msg", cust.isEmpty() ? MessageConstants.NO_CUSTOMER : MessageConstants.ALL_CUSTOMERS);
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);

		}
		log.info(LogConstants.GET_ALL_CUSTOMERS + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> getCustomerById(Integer custId) {
		log.info(LogConstants.GET_CUSTOMER_BY_ID + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			if (custId != 0) {
				CustomerEntity cust = mapper.map(custRepo.findById(custId), CustomerEntity.class);
				map.put("cust", cust);
				map.put("msg", ObjectUtils.isEmpty(cust) ? MessageConstants.CUSTOMER_NOT_AVAILABLE + custId
						: MessageConstants.CUSTOMER + custId);
			} else {
				map.put("msg", MessageConstants.PLEASE_ENTER_VALID_CUST_ID);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);
		}
		log.info(LogConstants.GET_CUSTOMER_BY_ID + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> updateCustomer(CustomerEntity cust) {
		log.info(LogConstants.UPDATE_CUSTOMER + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			if (ObjectUtils.isNotEmpty(cust) && cust.getCustId() != 0) {
				if (ObjectUtils.isNotEmpty(getCustomerById(cust.getCustId()).get("cust"))) {
					if (cust.getDob().length() == 10 && cust.getMobileNo().length() == 10) {
						map.put("cust", custRepo.saveAndFlush(cust));
						map.put("msg", MessageConstants.CUSTOMER_UPDATED);
					} else {
						map.put("msg", MessageConstants.LENGTH_10_VALIDATION);
					}
				} else {
					map.put("msg", MessageConstants.CUSTOMER_NOT_AVAILABLE + cust.getCustId());
				}
			} else {
				map.put("msg", MessageConstants.PLEASE_ENTER_VALID_CUST_ID);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);
		}
		log.info(LogConstants.UPDATE_CUSTOMER + LogConstants.ENDS);
		return map;
	}

	@Override
	public Map<String, Object> deleteCustomer(Integer custId) {
		log.info(LogConstants.DELETE_CUSTOMER + LogConstants.STARTED);
		Map<String, Object> map = new HashMap<>();
		try {
			CustomerEntity cust = mapper.map(custRepo.findById(custId), CustomerEntity.class);
			if (custId != 0 && ObjectUtils.isNotEmpty(cust)) {
				custRepo.deleteById(custId);
				WebServiceResDto response = getAccountDetails(custId);
				if (response != null && ObjectUtils.isNotEmpty(response.getData())) {
					webClient.delete().uri("/delete-account?accId=" + custId).retrieve()
							.bodyToMono(WebServiceResDto.class).block();
					map.put("msg", MessageConstants.CUSTOMER_ACCOUNT_DELETED);
				} else {
					map.put("msg", MessageConstants.CUSTOMER_DELETED);
				}
				map.put("msgCd", "0");
			} else {
				map.put("msgCd", "1");
				map.put("msg", MessageConstants.PLEASE_ENTER_VALID_CUST_ID);
			}
		} catch (Exception ex) {
			log.error(LogConstants.Exception + ex.getMessage() + LogConstants.ENDS);

		}
		log.info(LogConstants.DELETE_CUSTOMER + LogConstants.ENDS);
		return map;
	}

	private WebServiceResDto getAccountDetails(int custId) {
		return webClient.get().uri("/get-account?accId=" + custId).retrieve().bodyToMono(WebServiceResDto.class)
				.block();
	}
}
