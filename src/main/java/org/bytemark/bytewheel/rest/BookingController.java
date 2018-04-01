package org.bytemark.bytewheel.rest;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.constants.BookingStatus;
import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.service.interfaces.BookingService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/booking-service")
public class BookingController {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookingService bookingService;
	
	/**
	 * The backend server should not store any session data for the client. 
	 * Otherwise it will consume the backend server's memory for each threads which will 
	 * ultimately slow down the server at the pick hours when many users try to access the application. 
	 * In order to improve the performance the session data is to be managed on the client side.
	 * Being stateless REST web server can't keep track of the client's state. 
	 * Session data are to be passed from the client side for each REST calls as per requirement.
	 * The server will authenticate and will check authorization for the each calls made. 
	 * @throws Exception 
	 */
	@PostMapping("/bookings/booking/add")
	public ResponseEntity<Booking> createBooking(@RequestBody Booking bookingModel,
			@RequestParam(value="carId", required=false) String carId, 
			@RequestParam(value="carName", required=false) String carName,
			@RequestParam(value="emailId", required=false) String emailId) {
		LoggerUtil.info("BOOKING PAYLOAD = "+bookingModel);
		LoggerUtil.info("BOOKING carId = "+carId);
		LoggerUtil.info("BOOKING carName = "+carName);
		LoggerUtil.info("BOOKING emilId = "+emailId);
		Booking booking = bookingService.createBooking(bookingModel, carId, carName, emailId);
		return new ResponseEntity<Booking>(booking, HttpStatus.CREATED);
	}
	
	@GetMapping("/bookings")
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> bookingList = bookingService.getAllBookings();
		return new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);
	}
	
	@GetMapping("/bookings/booking/by-id/{bookingId}")
	public ResponseEntity<Booking> getBookingByBookingId(@PathVariable("bookingId") Long bookingId) {
		LoggerUtil.info("Booking details for bookingId = "+bookingId);
		Booking booking = bookingService.getBookingByBookingId(bookingId);
		return new ResponseEntity<Booking>(booking, HttpStatus.OK);
	}
	
	@GetMapping("/bookings/by-email-id")
	public ResponseEntity<List<Booking>> getBookingByEmailId(@RequestParam("emailId") String emailId) {
		LoggerUtil.info("Get Booking By Customer emailId = "+emailId);
		List<Booking> bookingList = bookingService.getBookingByEmailId(emailId);
		return new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);
	}

	@GetMapping("/bookings/by-date/{date}")
	public ResponseEntity<List<Booking>> getBookingByBillingDate(@PathVariable("date") Date date) {
		LoggerUtil.info("Get Booking By booking date = "+date);
		List<Booking> bookingList = bookingService.getBookingByBillingDate(date);
		return new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);
	}

/*	@DeleteMapping("/bookings/booking/cancel/by-id/{bookingId}")
	public ResponseEntity<Booking> cancelBookingByBookingId(@PathVariable("bookingId") Long bookingId) {
		LoggerUtil.info("Cancel Booking By bookingId = "+bookingId);
		Booking booking = bookingService.c(bookingId);
		if(booking.getStatus().equalsIgnoreCase(BookingStatus.CANCELLED.toString())){
			return new ResponseEntity<Booking>(booking, HttpStatus.OK);
		}else{
			throw new IllegalStateException("Failed to cancell the booking. Expected: [CANCELLED], found: "+booking.getStatus());
		}
	}
*/	
}














