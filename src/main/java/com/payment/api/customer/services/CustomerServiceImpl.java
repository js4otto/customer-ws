package com.payment.api.customer.services;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.api.customer.constants.Code;
import com.payment.api.customer.exceptions.BusinessException;
import com.payment.api.customer.repository.CustomerRepository;
import com.payment.api.customer.v1.domain.Customer;
import com.payment.api.customer.v1.dto.CustomerDto;
import com.payment.api.customer.v1.dto.ErrorDto;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public String generateUniqueCustomerId() {
		String customerId = UUID.randomUUID().toString();
		while(customerRepository.findByCustomerId(customerId).isPresent()) {
			customerId = UUID.randomUUID().toString();
		}
		return customerId;
	}
	
	@Override
	public CustomerDto create(CustomerDto customerDto) {
		try {
			Customer customer = new Customer();
			customer.setCustomerId(customerDto.getCustomerId());
			customer.setCustomerName(customerDto.getCustomerName());
			customerRepository.save(customer);
		} catch(Exception e) {
			throw e;
		}
		return customerDto;
	}

	@Override
	public void findByCustomerIdAndUpdateCustomerLoginId(String customerId, String customerLoginId) {
		try {
			Optional<Customer> cusOpl = customerRepository.findByCustomerId(customerId);
			if (cusOpl.isPresent()) {
				Customer customer = cusOpl.get();
				customer.setCustomerLoginId(customerLoginId);
				customerRepository.save(customer);
			}
		} catch(Exception e) {
			throw e;
		}
		
	}

	@Override
	public CustomerDto findByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		CustomerDto customerDto = null;
		try {
			Optional<Customer> cusOpl = customerRepository.findByCustomerId(customerId);
			if (!cusOpl.isPresent()) {
				throw new BusinessException(Collections.singletonList(new ErrorDto("customerId", "Customer Id does not exist", Code.BAD_REQUEST)));
			}
			ModelMapper modelMapper = new ModelMapper();
			customerDto = modelMapper.map(cusOpl.get(), CustomerDto.class);
		} catch (Exception e) {
			throw e;
		}
		return customerDto;
	}

	@Override
	public CustomerDto update(String customerId, CustomerDto customerDto) {
		try {
			Optional<Customer> cusOpl = customerRepository.findByCustomerId(customerId);
			if (!cusOpl.isPresent()) {
				throw new BusinessException(Collections.singletonList(new ErrorDto("customerId", "Customer Id does not exist", Code.BAD_REQUEST)));
			}
			Customer customer = cusOpl.get();
			if (!customerDto.getOtherDetails().isEmpty()) {
				customer.setOtherDetails(customerDto.getOtherDetails());
				customerRepository.save(customer);
			}
			ModelMapper modelMapper = new ModelMapper();
			customerDto = modelMapper.map(customer, CustomerDto.class);
		} catch (Exception e) {
			throw e;
		}
		return customerDto;
	}

	@Override
	public void delete(String customerId) {
		// TODO Auto-generated method stub
		Optional<Customer> cusOpl = customerRepository.findByCustomerId(customerId);
		if (!cusOpl.isPresent()) {
			throw new BusinessException(Collections.singletonList(new ErrorDto("customerId", "Customer Id does not exist", Code.BAD_REQUEST)));
		}
		customerRepository.delete(cusOpl.get());
	}

}
