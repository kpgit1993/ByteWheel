package org.bytemark.bytewheel.dao.interfaces;

import java.util.List;

import org.bytemark.bytewheel.model.Customer;


/**
 * @author : Kaustab Paul 
 * 		Useful for loose coupling the DAO layer with the other
 *      layers of the application
 */
public interface CustomerDao {
	
	Customer addCustomer(Customer customer);
	
	List<Customer> findAllCustomers();
	
	Customer findCustomerByCustomerId(Long customerId);
	
	Customer findCustomerByCustomerEmailId(String emailId);

}
