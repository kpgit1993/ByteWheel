package org.bytemark.bytewheel.service.interfaces;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.Customer;

/**
 * 
 * @author: Kaustab Paul
 * 
 *         The Spring team's recommendation is that you only annotate concrete
 *         classes with the @Transactional annotation, as opposed to annotating
 *         interfaces. You certainly can place the @Transactional annotation on
 *         an interface (or an interface method), but this will only work as you
 *         would expect it to if you are using interface-based proxies. The fact
 *         that annotations are not inherited means that if you are using
 *         class-based proxies then the transaction settings will not be
 *         recognised by the class-based proxying infrastructure and the object
 *         will not be wrapped in a transactional proxy (which would be
 *         decidedly bad). So please do take the Spring team's advice and only
 *         annotate concrete classes (and the methods of concrete classes) with
 *         the @Transactional annotation.
 */
public interface BookingService {
	
	Booking createBooking(Booking bookingModel, String inputCarId, String inputCarName, String inputEmailId);

	List<Booking> getAllBookings();

	Booking getBookingByBookingId(Long bookingId);

	List<Booking> getBookingByEmailId(String emailId);
	
	List<Booking> getBookingByBillingDate(java.util.Date date);

	// Booking modify is not possible. Cancel and then add
	// E.g: Railway/flight reservation system
	// ResponseEntity<Booking> modifyBooking(Long bookingId);
	
}
