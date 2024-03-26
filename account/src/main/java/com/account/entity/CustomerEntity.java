package com.account.entity;

import lombok.Data;

@Data
public class CustomerEntity {

	private int custId;

	private String name;

	private String gender;

	private String dob;

	private String city;

	private String mobileNo;

	private String accType;
}
