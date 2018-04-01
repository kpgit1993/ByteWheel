package org.bytemark.bytewheel.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(
		name="CUSTOMER"
		//,
		//uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL"})
		)
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	@Column(name="EMAIL")
	@NotNull
	private String email;

	@OneToMany(targetEntity=Booking.class, mappedBy="customer", 
			cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Booking> bookingSet;
	
	public Customer(){
		// no-arg constructor
	}
	
	public Customer(String email){
		this.email = email;
	}
	
	public Set<Booking> getBookingSet() {
		return bookingSet;
	}

	public void setBookingSet(Set<Booking> bookingSet) {
		this.bookingSet = bookingSet;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString(){
		return "[customerId"+this.customerId+","
				+ "email: "+this.email;
	}
}
