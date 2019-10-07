package com.payment.api.customer.facade;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payment.api.customer.constants.Code;
import com.payment.api.customer.constants.Status;
import com.payment.api.customer.serviceClients.AccountServiceClient;
import com.payment.api.customer.serviceClients.CustomerLoginServiceClient;
import com.payment.api.customer.services.CustomerService;
import com.payment.api.customer.v1.dto.AccountDto;
import com.payment.api.customer.v1.dto.CustomerDto;
import com.payment.api.customer.v1.dto.CustomerLoginDto;
import com.payment.api.customer.v1.dto.ResponseDto;

@Component
public class CustomerFacadeImpl implements CustomerFacade{

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerLoginServiceClient customerLoginServiceClient;
	@Autowired
	private AccountServiceClient accountServiceClient;
	
	/**
	 * Creates a new Customer, if successful
	 * makes a call to Customer Login micro_service
	 * and then creates login details for the customer
	 * @param customerDto
	 * @return
	 */
	@Override
	public CustomerDto create(CustomerDto customerDto) {
		CustomerDto cus = null;
		String customerId = customerService.generateUniqueCustomerId();
		customerDto.setCustomerId(customerId);
		customerDto.getCustomerLogin().setCustomerId(customerId);
		List<CustomerLoginDto> clList = customerLoginServiceClient.create(customerDto.getCustomerLogin()).getBody().getData();
		if (!clList.isEmpty() && clList.size() > 0) {
			CustomerLoginDto customerLoginDto = clList.get(0);

			cus = customerService.create(customerDto);
			customerDto.getCustomerLogin().setCustomerId(cus.getCustomerId());
			customerLoginDto.setCustomerId(cus.getCustomerId());
			customerService.findByCustomerIdAndUpdateCustomerLoginId(cus.getCustomerId(), customerLoginDto.getCustomerId());
			cus.setCustomerLoginId(customerLoginDto.getCustomerLoginId());
			cus.getCustomerLogin().setUsername(null);
			cus.getCustomerLogin().setPassword(null);
		}
		
		return cus;
	}
	/**
	 * Finds a customer by their customerId, then
	 * makes a call to accounts micro_service which in
	 * turns returns the available accounts of the customer
	 * @param customerId
	 * @param page
	 * @param size
	 * @return 
	 */
	@Override
	public ResponseDto<CustomerDto> findAccountsByCustomerId(String customerId, int page, int size) {
		CustomerDto customerDto = customerService.findByCustomerId(customerId);
		ResponseDto<AccountDto> accFeed = accountServiceClient.findAccountsByCustomerId(customerId, page, size).getBody();
		
		ResponseDto<CustomerDto> response = new ResponseDto<>();
		if (!accFeed.getData().isEmpty() && accFeed.getData().size() > 0) {
			response.setPage(accFeed.getPage());
			response.setSize(accFeed.getSize());
			response.setTotal(accFeed.getTotal());
			
			customerDto.setAccounts(accFeed.getData());
		}
		customerDto.setCustomerLogin(null);
		response.setCode(Code.OK);
		response.setStatus(Status.SUCCESS);
		response.setMessage("Operation was successful");
		response.setData(Collections.singletonList(customerDto));
		return response;
	}
	
	@Override
	public void delete(String customerId) {
		
		accountServiceClient.delete(customerId);
		
		customerLoginServiceClient.delete(customerId);
		
		customerService.delete(customerId);
	}
	
	@Override
	public CustomerDto update(String customerId, String accountId, AccountDto accountDto) {
		CustomerDto customerDto = customerService.findByCustomerId(customerId);
		List<AccountDto> accList = accountServiceClient.update(customerId, accountId, accountDto).getBody().getData();
		if (!accList.isEmpty() && accList.size() > 0) {
			customerDto.setAccounts(accList);
		}
		return customerDto;
	}

}
