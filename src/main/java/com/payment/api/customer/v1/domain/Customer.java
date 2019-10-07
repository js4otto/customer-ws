package com.payment.api.customer.v1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CUSTOMER")
@Data
public class Customer {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String customerName;
	@Column(nullable = false, unique = true)
	private String customerId;
	@Column()
	private String otherDetails;
	@Column()
	private String customerLoginId;

}
