package com.payment.api.customer.facade;

import com.payment.api.customer.v1.dto.AccountDto;
import com.payment.api.customer.v1.dto.CustomerDto;
import com.payment.api.customer.v1.dto.ResponseDto;

public interface CustomerFacade {

	CustomerDto create(CustomerDto customerDto);
	
	ResponseDto<CustomerDto> findAccountsByCustomerId(String customerId, int page, int size);
	
	void delete(String customerId);
	
	CustomerDto update(String customerId, String accountId, AccountDto accountDto);
}
