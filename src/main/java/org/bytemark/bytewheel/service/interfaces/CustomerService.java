package org.bytemark.bytewheel.service.interfaces;

import java.util.List;

import org.bytemark.bytewheel.model.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer);
	
	List<Customer> getAllCustomers();
	
	Customer getCustomerByCustomerId(Long customerId);
	
	Customer getCustomerByCustomerEmailId(String emailId);
	
}
