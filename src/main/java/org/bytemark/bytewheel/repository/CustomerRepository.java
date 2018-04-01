package org.bytemark.bytewheel.repository;

import org.bytemark.bytewheel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	@Query("select c from Customer c where email=(:emailId)")
	public Customer findCustomerByCustomerEmailId(@Param("emailId") String emailId);

}
