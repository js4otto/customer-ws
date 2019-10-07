package com.payment.api.customer.v1.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.payment.api.customer.validators.OnCreate;

import lombok.Data;

@Data
public class CustomerLoginDto implements Serializable {

	private static final long serialVersionUID = -4338606291318624412L;
	private String customerLoginId;
	@NotNull(groups = { OnCreate.class }, message = "Username cannot be null")
	@NotEmpty(groups = { OnCreate.class }, message = "Username cannot be empty")
	private String username;
	@NotNull(groups = { OnCreate.class }, message = "Password cannot be null")
	@NotEmpty(groups = { OnCreate.class }, message = "Password cannot be empty")
	private String password;
	private boolean isEnabled;
	private String customerId;
}
