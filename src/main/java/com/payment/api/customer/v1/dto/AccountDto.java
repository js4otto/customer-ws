package com.payment.api.customer.v1.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AccountDto implements Serializable {

	private static final long serialVersionUID = -252903402440726845L;
	private String accountId;
	@NotNull(message = "Account Name cannot be null")
	@NotEmpty(message = "Account Name cannot be empty")
	private String accountName;
	@NotNull(message = "Current Balance cannot be null")
	@NotEmpty(message = "Current Balance cannot be empty")
	private String currentBalance;
	@NotNull(message = "Other Details cannot be null")
	@NotEmpty(message = "Other Details cannot be empty")
	private String otherDetails;
}
