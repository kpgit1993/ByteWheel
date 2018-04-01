package org.bytemark.bytewheel.service;

import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.CustomerDao;
import org.bytemark.bytewheel.model.Customer;
import org.bytemark.bytewheel.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Kaustab Paul 
 * 		   The service layer used to manage the business
 *         transaction related to customers under the control of Spring
 *         framework. The methods are required to bound a existing or a newly
 *         created transaction, All the transactions will read the committed
 *         data from the database.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public Customer addCustomer(Customer customer) {
		return customerDao.addCustomer(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerDao.findAllCustomers();
	}

	public Customer getCustomerByCustomerId(Long customerId) {
		if(customerId <=0 ){
			throw new IllegalArgumentException("Invalid search request will customer id: "+customerId);
		}
		Customer customer = customerDao.findCustomerByCustomerId(customerId);
		if(customer == null){
			throw new ResourceNotFoundException("No customer was found with customer id: "+customerId);
		}
		return customer;
	}

	public Customer getCustomerByCustomerEmailId(String emailId) {
		if(emailId ==null || emailId.isEmpty()){
			throw new IllegalArgumentException("Invalid search request will customer id: "+emailId);
		}
		Customer customer = customerDao.findCustomerByCustomerEmailId(emailId);
		if(customer == null || customer.getCustomerId() == null){
			throw new ResourceNotFoundException("No customer was found with email id: "+emailId);
		}
		return customer; 
	}

}
