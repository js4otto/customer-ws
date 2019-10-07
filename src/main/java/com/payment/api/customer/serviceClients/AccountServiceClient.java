package com.payment.api.customer.serviceClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.payment.api.customer.v1.dto.AccountDto;
import com.payment.api.customer.v1.dto.ResponseDto;
import com.payment.api.customer.validators.OnUpdate;

@FeignClient(name = "accounts-ws", primary = false, qualifier = "accountServiceClient", fallback = AccountServiceClientFallback.class)
public interface AccountServiceClient {

	@GetMapping("/accounts/customers/{customerId}")
	public ResponseEntity<ResponseDto<AccountDto>> findAccountsByCustomerId(
			@PathVariable String customerId, 
			@RequestParam("page") int page, 
			@RequestParam("size") int size);
	
	@PostMapping("/accounts/customers/{customerId}/{accountId}")
	public ResponseEntity<ResponseDto<AccountDto>> update(@PathVariable String customerId, @PathVariable String accountId, @Validated({ OnUpdate.class })@RequestBody AccountDto accountDto);
	
	@DeleteMapping("/accounts/customers/{customerId}")
	public @ResponseBody void delete(@PathVariable String customerId);
	
	@DeleteMapping("/accounts/customers/{customerId}/{accountId}")
	public @ResponseBody void delete(@PathVariable String customerId, @PathVariable String accountId);
}