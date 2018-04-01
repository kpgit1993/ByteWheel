package org.bytemark.bytewheel.dao;

import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.CustomerDao;
import org.bytemark.bytewheel.model.Customer;
import org.bytemark.bytewheel.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	CustomerRepository customerRepository;
	
	/**
	 * Insert a new customer 
	 **/
	public Customer addCustomer(Customer customer){
		return customerRepository.save(customer);
	}
	
	/**
	 * get all customers 
	 **/
	public List<Customer> findAllCustomers(){
		return customerRepository.findAll();
	}
	
	/**
	 * get customer by customerId
	 **/
	public Customer findCustomerByCustomerId(Long customerId){
		return customerRepository.getOne(customerId);
	}
	
	/**
	 * get customer by emailId (unique for a customer)
	 **/
	public Customer findCustomerByCustomerEmailId(String emailId){
		return customerRepository.findCustomerByCustomerEmailId(emailId);
	}
	
}
