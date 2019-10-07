package com.payment.api.customer.v1.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.api.customer.constants.Status;
import com.payment.api.customer.facade.CustomerFacade;
import com.payment.api.customer.services.CustomerService;
import com.payment.api.customer.v1.dto.AccountDto;
import com.payment.api.customer.v1.dto.CustomerDto;
import com.payment.api.customer.v1.dto.ResponseDto;
import com.payment.api.customer.validators.OnCreate;
import com.payment.api.customer.validators.OnUpdate;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerFacade customerFacade;
	@Autowired
	private CustomerService customerService;
	
	/**
	 * Finds a Customer By their customer id
	 * attribute in the database. If record
	 * does not exist throws Business Exception
	 * @param customerId
	 * @return
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<ResponseDto<CustomerDto>> findByCustomerId(@PathVariable String customerId) {
		List<CustomerDto> data = Collections.singletonList(customerService.findByCustomerId(customerId));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<CustomerDto>(Status.SUCCESS, String.valueOf(HttpStatus.OK.value()), data, Status.SUCCESS_MESSAGE, 0, 0, 0L));
	}
	
	/**
	 * Gets all account of the attached customer
	 * @param customerId
	 * @return
	 */
	@GetMapping("/{customerId}/accounts")
	public ResponseEntity<ResponseDto<CustomerDto>> findAccountsByCustomerId(
			@PathVariable String customerId, 
			@RequestParam("page") int page, 
			@RequestParam("size") int size) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(customerFacade.findAccountsByCustomerId(customerId, page, size));
	}
	
	/**
	 *  Creates a new Customer and if the request
	 *  is successful makes a call to CustomerLogin
	 *  micro_service to do the same
	 * @param customerDto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ResponseDto<CustomerDto>> create(@Validated({ OnCreate.class }) @RequestBody CustomerDto customerDto) {
		
		List<CustomerDto> data = Collections.singletonList(customerFacade.create(customerDto));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<CustomerDto>(Status.SUCCESS, String.valueOf(HttpStatus.OK.value()), data, Status.SUCCESS_MESSAGE, 0, 0, 0L));
	}
	
	@PatchMapping("/{customerId}/accounts/{accountId}")
	public ResponseEntity<ResponseDto<CustomerDto>> update(
			@PathVariable String customerId, 
			@PathVariable String accountId, 
			@RequestBody AccountDto accountDto) {
		List<CustomerDto> data = Collections.singletonList(customerFacade.update(customerId, accountId, accountDto));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<CustomerDto>(Status.SUCCESS, String.valueOf(HttpStatus.OK.value()), data, Status.SUCCESS_MESSAGE, 0, 0, 0L));
	}
	
	/**
	 * Updates a customer record based
	 * on their customer id. Update 
	 * validation is different from
	 * creation validation by flags
	 * @param customerId
	 * @param customerDto
	 * @return
	 */
	@PatchMapping("/{customerId}")
	public ResponseEntity<ResponseDto<CustomerDto>> update(@PathVariable String customerId, @Validated({ OnUpdate.class }) @RequestBody CustomerDto customerDto) {
		List<CustomerDto> data = Collections.singletonList(customerService.update(customerId, customerDto));
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<CustomerDto>(Status.SUCCESS, String.valueOf(HttpStatus.OK.value()), data, Status.SUCCESS_MESSAGE, 0, 0, 0L));
	}
	
	/**
	 * Deletes a Customer which in turns makes
	 * a call to Customer Login micro_service and Account
	 * micro_service and deletes any record associated to
	 * the deleted customer
	 * @param customerId
	 * @return
	 */
	@DeleteMapping("/{customerId}")
	public @ResponseBody void delete(@PathVariable String customerId) {
		customerFacade.delete(customerId);
	}
}
