package org.bytemark.bytewheel.dao.interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.model.Car;

/**
 * @author : Kaustab Paul 
 * 		Useful for loose coupling the DAO layer with the other
 *      layers of the application
 */
public interface BookingDao {

	List<Booking> findAllBookings();

	Booking findBookingByBookingId(Long bookingId);

	Booking createBooking(Booking booking);

	Booking updateBooking(Booking bookingModel);

	List<Booking> findBookingByEmailId(String emailId);

	List<Booking> findBookingByBillingDate(Date date);

	List<Booking> findBookingByBillingDateForEmailId(String emailId, Date date);
}
