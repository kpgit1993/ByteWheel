package org.bytemark.bytewheel.rest;

import java.util.List;

import org.bytemark.bytewheel.model.Customer;
import org.bytemark.bytewheel.service.interfaces.CustomerService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer-service")
public class CustomerConstoller {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers/customer/add")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		LoggerUtil.info("CUSTOMER Payload: "+customer);
		Customer addedCustomer =  customerService.addCustomer(customer);
		return new ResponseEntity<Customer>(addedCustomer, HttpStatus.CREATED);
	}

	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer> customerList = customerService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);
	}
	
	@GetMapping("/customers/customer/by-id/{customerId}")
	public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable("customerId") Long customerId) {
		LoggerUtil.info("Get Customer For Customer Id "+customerId);
		Customer customer = customerService.getCustomerByCustomerId(customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@GetMapping("/customers/customer/by-mailid/{emailId}")
	public ResponseEntity<Customer> getCustomerByCustomerEmailId(String emailId) {
		LoggerUtil.info("Get Customer For Customer Email Id "+emailId);
		Customer customer = customerService.getCustomerByCustomerEmailId(emailId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
}
