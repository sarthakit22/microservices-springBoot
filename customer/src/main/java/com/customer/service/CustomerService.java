package com.customer.service;

import java.util.Map;

import com.customer.entity.CustomerEntity;

public interface CustomerService {

	public Map<String, Object> addCustomer(CustomerEntity cust);

	public Map<String, Object> getAllCustomers();

	public Map<String, Object> getCustomerById(Integer custId);

	public Map<String, Object> updateCustomer(CustomerEntity cust);

	public Map<String, Object> deleteCustomer(Integer custId);
}
