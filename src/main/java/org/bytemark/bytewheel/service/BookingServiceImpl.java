package org.bytemark.bytewheel.service;

import java.awt.CardLayout;
import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.constants.BookingStatus;
import org.bytemark.bytewheel.dao.interfaces.BookingDao;
import org.bytemark.bytewheel.dao.interfaces.CarDao;
import org.bytemark.bytewheel.dao.interfaces.CustomerDao;
import org.bytemark.bytewheel.exception.BusinessException;
import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.Customer;
import org.bytemark.bytewheel.service.interfaces.BookingService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.bytemark.bytewheel.util.MailSender;
import org.omg.CORBA.IMP_LIMIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author : Kaustab Paul 
 * 		   The service layer used to manage the business
 *         transaction related to booking process under the control of Spring
 *         framework. The methods are required to bound a existing or a newly
 *         created transaction, All the transactions will read the committed
 *         data from the database.
 */
@Service
@Transactional(
		propagation = Propagation.REQUIRED, 
		isolation = Isolation.READ_COMMITTED
	)
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private CarDao carDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	@Qualifier("byteWheelMailSender")
	private MailSender mailSender;

	@Value("${spring.mail.username}")
	String senderMailAddress;

	@Value("${spring.mail.password}")
	String mailSubject;

	
	/**
	 * The following method is used to book a car on a particular date The
	 * synchronized block in this method helps to book the process in thread
	 * safe fashion
	 */
	public Booking createBooking(Booking bookingModel, String inputCarId,
			String inputCarName, String inputEmailId) {

		if(bookingModel == null){
			throw new NullPointerException("Booking model found null");
		}
		
		// booking for past date is not allowed
		if(bookingModel.getBookingStartDate().before(new Date())){
			throw  new IllegalArgumentException("Booking for past date is not allowed: "
					+bookingModel.getBookingStartDate());
		}
		if(bookingModel.getBookingEndDate().before(bookingModel.getBookingStartDate())){
			throw  new IllegalArgumentException("Booking end date should be greater/equal to start date "
					+bookingModel.getBookingStartDate());
		}
		
		LoggerUtil.info("CAR TO BOOK === "+bookingModel.getCar());
		LoggerUtil.info("input carid: "+inputCarId);
		LoggerUtil.info("input carName: "+inputCarName);
		LoggerUtil.info("input emailid: "+inputEmailId);
		
		// get car object by carId of http session
		Car carModel = null;
		
		// get by name if name is not null
		Long carId;
		if(inputCarName != null){
			carModel = carDao.getCarByCarName(inputCarName);
			carId = carModel.getCarId();
		}else{
			// get by car Id if carId is not null
			carId = (inputCarId==null)?bookingModel.getCar().getCarId():Long.parseLong(inputCarId.trim());
			if (carId == null) {
				throw new NullPointerException("Seession do not contain carId data for booking");
			} else {
				LoggerUtil.info("Booking request for car Id = " + carId);
			}
			// get the car model 
			carModel = carDao.findCarByCarId(carId);
			LoggerUtil.info("Booking request for car = " + carModel);
		}
		
		// get customer object
		String emailId = (inputEmailId==null) ? bookingModel.getCustomer().getEmail() : inputEmailId;
		LoggerUtil.info("Booking for customer having email id: " + emailId);
		Customer customerModel = customerDao.findCustomerByCustomerEmailId(emailId);
		
		if (customerModel == null) {
			// new customer will not have record in the database
			LoggerUtil.info("New Customer detected with email id: " + emailId);
			Customer newCustomerModel = new Customer();
			newCustomerModel.setEmail(emailId);
			customerModel = customerDao.addCustomer(newCustomerModel);
		} else {
			LoggerUtil.info("Customer exists with email id: " + emailId);
		}
		LoggerUtil.info("Customer record: " + customerModel);

		// create booking object
		bookingModel.setCar(carModel);
		bookingModel.setCustomer(customerModel);
		bookingModel.setStatus(BookingStatus.BOOKED.toString());

		/**
		 * The following block is synchronized to make the booking process
		 * thread safe. Say, at time t1 there were only 1 BMW car was left. At
		 * this point 2 threads were spawn by 2 users to book the same car. The
		 * first thread that manages to acquire the lock on the object and will
		 * then check in the first step weather there is still BMW left on not.
		 * If so it will book the car in the next step successfully.
		 * 
		 * The synchronized keyword ensures that the block is executed by only 1
		 * thread at a time. Hence other threads will be waiting outside this
		 * method for booking. After thread 1 finishes booking there will be no
		 * BMW car left, and the second thread entering after Thread1 should not
		 * be able to book another BMW. In the step 1 thread 2 will check now
		 * that there is no BMW left for that day. Hence it will come out
		 * throwing exception and the booking for the second thread will be
		 * failed.
		 */
		Booking booking;

		synchronized (bookingModel) {

			// STEP 1 : Check the current availability for the cars
			// There is no chance of dirty read from the synchronized block as
			// all the threads will be executed one after another within this
			// block
			List<Car> carList = carDao.findAvailableCarsByDate(bookingModel.getBookingStartDate(), 
					bookingModel.getBookingEndDate());
			
			// STEP 2 : If the current car count is less than the total count
			// process the booking, fail otherwise
			boolean flag = false;
			
			for(Car car : carList){
				if(car.getCarId() == carId) {
					LoggerUtil.info("The car was found in the avaibility list for booking... ");
					bookingModel.setBillingDate(new Date());
					booking = bookingDao.createBooking(bookingModel);
					flag = true;
					break;
				}
			}
			
			if (flag) {
				LoggerUtil.info("Booking was made for the car...");
			} else {
				LoggerUtil.error("No available car to book for carId: " + carId + "... ");
				throw new RuntimeException("No car available for booking with carId:" + carId);
			}
		}

		// send email notification to the customer
		// String senderMailAddress = "fdtapan349paul@gmail.com";
		// String mailSubject = "ByteWheel Booking Invoce";
		mailSender.sendMail(senderMailAddress, bookingModel.getCustomer()
				.getEmail(), mailSubject, bookingModel);

		// update status of booking to confirmed
		bookingModel.setStatus(BookingStatus.CONFIRMED.toString());
		booking = updateBooking(bookingModel);

		return booking;
	}

	/**
	 * Method is used to get all the bookings 
	 */
	public List<Booking> getAllBookings() {
		List<Booking> bookingList = bookingDao.findAllBookings();
		if(bookingList==null || bookingList.isEmpty()){
			throw new ResourceNotFoundException("No books found... "+bookingList);
		}
		return bookingList;
	}

	/**
	 * Method is used to get booking details for a booking id
	 */
	public Booking getBookingByBookingId(Long bookingId) {
		if(bookingId < 0){
			throw new IllegalArgumentException("Search for invalid booking id: "+bookingId);
		}
		Booking booking = bookingDao.findBookingByBookingId(bookingId);
		if(booking == null || booking.getBookingId()==null){
			throw new ResourceNotFoundException("No books found... "+booking);
		}
		return booking;
	}

	/**
	 * Method is used to get booking by mail Id
	 */
	public List<Booking> getBookingByEmailId(String emailId) {
		if(emailId == null || emailId.isEmpty() || !emailId.contains("@") || !emailId.contains(".")){
			throw new IllegalArgumentException("Search for invalid email id: "+emailId);
		}
		List<Booking> bookingList =  bookingDao.findBookingByEmailId(emailId);
		if(bookingList == null || bookingList.isEmpty()){
			throw new ResourceNotFoundException("No books found... "+bookingList);
		}
		return bookingList;
	}

	/**
	 * Method is used to get booking details by date
	 */
	public List<Booking> getBookingByBillingDate(java.util.Date date) {
		if(date == null){
			throw new NullPointerException("Search for invalid date: "+date);
		}
		List<Booking> bookingList = bookingDao.findBookingByBillingDate(date);
		if(bookingList==null || bookingList.isEmpty()){
			throw new ResourceNotFoundException("No books found... "+bookingList);
		}
		return bookingList;
	}

	/**
	 * Method is used to get bookings for a mail id by date
	 */
	public List<Booking> findBookingByBillingDateForEmailId(String emailId, java.util.Date date) {
		if(date == null){
			throw new NullPointerException("Search for invalid date: "+date);
		}
		if(emailId == null || emailId.isEmpty() || !emailId.contains("@") || !emailId.contains(".")){
			throw new IllegalArgumentException("Search for invalid email id: "+emailId);
		}
		List<Booking> bookingList = bookingDao.findBookingByBillingDateForEmailId(emailId, date);
		if(bookingList==null || bookingList.isEmpty()){
			throw new ResourceNotFoundException("No books found... "+bookingList);
		}
		return bookingList;
	}

	/**
	 * The booking table records are not deleted, for cancellation the booking
	 * status will be updated as CANCELLED
	 */
	public Booking cancelBookingByBookingId(Long bookingId) {
		if(bookingId <= 0){
			throw new IllegalArgumentException("Invalid booking id: "+bookingId);
		}
		Booking booking = bookingDao.findBookingByBookingId(bookingId);
		booking.setStatus(BookingStatus.CANCELLED.toString());
		Booking cancelledBooking = updateBooking(booking);
		return cancelledBooking;
	}

	/**
	 * Method is used to update a booking
	 */
	public Booking updateBooking(Booking booking) {
		// cancel not allowed for past date booking
		if (booking.getBookingStartDate().before(new Date())) {
			throw new RuntimeException("Cancellation is not allowed for past bookings");
		}
		return bookingDao.updateBooking(booking);
	}

	
}
