package com.payment.api.customer.serviceClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.payment.api.customer.v1.dto.CustomerLoginDto;
import com.payment.api.customer.v1.dto.ResponseDto;

@FeignClient(name = "customerlogin-ws", primary = false, qualifier = "customerLoginServiceClient", fallback = CustomerLoginServiceClientFallback.class)
public interface CustomerLoginServiceClient {

	@PostMapping("/customerLogin")
	public ResponseEntity<ResponseDto<CustomerLoginDto>> create(@RequestBody CustomerLoginDto customerLoginDto);
	
	@DeleteMapping("/customerLogin/{customerId}")
	public void delete(@PathVariable String customerId);
}