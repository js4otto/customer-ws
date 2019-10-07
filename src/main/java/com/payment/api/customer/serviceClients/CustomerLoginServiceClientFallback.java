package com.payment.api.customer.serviceClients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.payment.api.customer.v1.dto.CustomerLoginDto;
import com.payment.api.customer.v1.dto.ResponseDto;

@Component
public class CustomerLoginServiceClientFallback implements CustomerLoginServiceClient {

	@Override
	public ResponseEntity<ResponseDto<CustomerLoginDto>> create(CustomerLoginDto customerLoginDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String customerId) {
		// TODO Auto-generated method stub
		
	}

}
