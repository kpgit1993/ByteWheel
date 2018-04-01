package org.bytemark.bytewheel.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.BookingDao;
import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingDaoImpl implements BookingDao {
	
	@Autowired
	BookingRepository bookingRepository;
	
	public List<Booking> findAllBookings() {
		return bookingRepository.findAll();
	}

	public Booking findBookingByBookingId(Long bookingId) {
		return bookingRepository.getOne(bookingId);
	}
	
	public synchronized Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	public synchronized Booking updateBooking(Booking bookingModel) {
		return bookingRepository.save(bookingModel);
	}

	public List<Booking> findBookingByEmailId(String emailId) {
		return bookingRepository.findBookingByEmailId(emailId);
	}

	public List<Booking> findBookingByBillingDate(Date date) {
		return bookingRepository.findBookingByBillingDate(date);
	}

	public List<Booking> findBookingByBillingDateForEmailId(String emailId, Date date) {
		return bookingRepository.findBookingByBillingDateForEmailId(emailId, date);
	}

}
