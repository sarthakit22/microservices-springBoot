package com.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
//	@GeneratedValue(generator = "account_gen", strategy = GenerationType.AUTO)
//	@SequenceGenerator(name = "account_gen", sequenceName = "account_accid_seq", allocationSize = 1)
	@Column(name = "accid")
	private int accId;

	@Column(name = "acctyp")
	private String accType;

	@Column(name = "ifsc")
	private String ifsc;

	@Column(name = "bal")
	private Long balance;

	@Transient
	private CustomerEntity customer;

	public AccountEntity() {

	}

	public AccountEntity(int accId, String accType, String ifsc, Long balance) {
		this.accId = accId;
		this.accType = accType;
		this.ifsc = ifsc;
		this.balance = balance;
	}

}
