package org.bytemark.bytewheel.repository;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("select b from Booking b where b.customer.email=(:emailId)")
	public List<Booking> findBookingByEmailId(@Param("emailId") String emailId);

	@Query("select b from Booking b where b.billingDate=(:bookingDate)")
	public List<Booking> findBookingByBillingDate(@Param("bookingDate") Date date);

	@Query("select b from Booking b where b.customer.email=(:emailId) and b.billingDate=(:bookingDate)")
	public List<Booking> findBookingByBillingDateForEmailId(
			@Param("emailId") String emailId, 
			@Param("bookingDate") Date date);

}
