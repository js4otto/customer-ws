package com.payment.api.customer.serviceClients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.payment.api.customer.v1.dto.AccountDto;
import com.payment.api.customer.v1.dto.ResponseDto;

@Component
public class AccountServiceClientFallback implements AccountServiceClient{

	@Override
	public ResponseEntity<ResponseDto<AccountDto>> findAccountsByCustomerId(String customerId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String customerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String customerId, String account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseEntity<ResponseDto<AccountDto>> update(String customerId, String accountId, AccountDto accountDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
