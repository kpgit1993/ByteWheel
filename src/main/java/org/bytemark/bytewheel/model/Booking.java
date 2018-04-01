package org.bytemark.bytewheel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "BOOKING")
public class Booking implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOKING_ID")
	private Long bookingId;

	@Column(name = "BOOKING_START_DATE")
	@NotNull
	private Date bookingStartDate;

	@Column(name = "BOOKING_END_DATE")
	@NotNull
	private Date bookingEndDate;

	@Column(name = "BILL_DATE")
	private Date billingDate;

	/**
	 * @JsonProperty is used to prevent chaining reference fall out error
	 *               WRITE_ONLY access type is used to unmarshalling the input
	 *               json data to store in the object, but the data stored to
	 *               this field will not be marshalled back to JSON.
	 */

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(targetEntity = Car.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CAR_ID", nullable = false, referencedColumnName = "CAR_ID")
	private Car car;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BOOKING_STATUS")
	@Nullable
	private String status;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	/*
	 * public Date getBookingDate() { return bookingDate; }
	 * 
	 * public void setBookingDate(Date bookingDate) { this.bookingDate =
	 * bookingDate; }
	 */

	public Car getCar() {
		return car;
	}

	public Date getBookingStartDate() {
		return bookingStartDate;
	}

	public void setBookingStartDate(Date bookingStartDate) {
		this.bookingStartDate = bookingStartDate;
	}

	public Date getBookingEndDate() {
		return bookingEndDate;
	}

	public void setBookingEndDate(Date bookingEndDate) {
		this.bookingEndDate = bookingEndDate;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "[bookingId: " + this.bookingId + ", billingDate: " + this.billingDate
				+ ", bookingStartDate: " + bookingStartDate
				+ ", bookingEndDate: " + bookingEndDate + ", bookingStatus: "
				+ this.status + ", car: " + this.car + ", customer: "
				+ this.customer + "]";
	}
}
