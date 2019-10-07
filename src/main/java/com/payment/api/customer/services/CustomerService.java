package com.payment.api.customer.services;

import com.payment.api.customer.v1.dto.CustomerDto;

public interface CustomerService {
	
	String generateUniqueCustomerId();

	CustomerDto create(CustomerDto customerDto);
	
	void findByCustomerIdAndUpdateCustomerLoginId(String customerId, String customerLoginId);
	
	CustomerDto findByCustomerId(String customerId);
	
	CustomerDto update(String customerId, CustomerDto customerDto);
	
	void delete(String customerId);
}
