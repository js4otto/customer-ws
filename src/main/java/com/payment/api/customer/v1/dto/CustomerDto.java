package com.payment.api.customer.v1.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.payment.api.customer.validators.OnCreate;

import lombok.Data;

@Data
public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 7588475601951361747L;
	private String customerId;
	@NotNull(groups = { OnCreate.class }, message = "Customer Name cannot be null")
	@NotEmpty(message = "Customer Name cannot be empty")
	private String customerName;
	private String otherDetails;
	private String customerLoginId;
	
	@Valid
	@NotNull(groups = { OnCreate.class }, message = "Login Details cannot be null")
	private CustomerLoginDto customerLogin;
	
	private List<AccountDto> accounts;

}
