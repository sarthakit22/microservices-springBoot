package com.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {

	@Id
	@GeneratedValue(generator = "customer_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "customer_gen", sequenceName = "customer_custid_seq", allocationSize = 1)
	@Column(name = "custid")
	private int custId;

	@Column(name = "name")
	private String name;

	@Column(name = "gender")
	private String gender;

	@Size(min = 0, max = 10, message = "DOB length should be 10")
	@Column(name = "dob")
	private String dob;

	@Column(name = "city")
	private String city;

	@Size(min = 0, max = 10, message = "Mobile Number length should be 10")
	@Column(name = "mob")
	private String mobileNo;

	@Column(name = "acctyp")
	private String accType;
}
