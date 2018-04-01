package org.bytemark.bytewheel.util;

import java.text.SimpleDateFormat;

import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author: Kaustab Paul 
 * 			Gmail account is highly secured. So Email is blocked.
 *          The solution is to make the gmail account less secure: 
 *          Login to Gmail. Access
 *          https://www.google.com/settings/security/lesssecureapps 
 *          Select “Turn on”
 */
@Component("byteWheelMailSender")
public class MailSender {

	@Autowired
	JavaMailSender javaMailSender;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void sendMail(String from, String to, String subject, Booking booking) {

		// get booking details
		Long bookingId = booking.getBookingId();
		String bookingStartDate = new SimpleDateFormat("dd-MM-yyyy").format(booking.getBookingStartDate());
		String bookingEndDate = new SimpleDateFormat("dd-MM-yyyy").format(booking.getBookingEndDate());
		long totalDays = Math.abs(booking.getBookingEndDate().getTime() - booking.getBookingStartDate().getTime())/86400000;
		
		String bookingStatus = booking.getStatus();

		Car car = booking.getCar();
		Long carId = car.getCarId();
		String carName = car.getCarName();

		CarCategory carCategory = car.getCarCategory();
		String categoryType = carCategory.getCategoryType();
		float rent = carCategory.getDailyRentInDollar();

		Customer customer = booking.getCustomer();
		Long customerId = customer.getCustomerId();

		// create mail body
		String messageBody = "BOOKING_ID: " 
				+ bookingId + "\r\n"
				+ "START DATE: " + bookingStartDate + "\r\n" 
				+ "END DATE: " + bookingEndDate + "\r\n" 
				+ "TOTAL DQAYS: " + totalDays+"\r\n"
				+ "BOOKING STATUS: " + bookingStatus + "\r\n" 
				+ "CUSTOMER ID: " + customerId + "\r\n" 
				+ "EMAIL ID: " + to + "\r\n" 
				+ "CAR ID: " + carId
				+ "\r\n" + "CAR NAME: "	+ carName + "\r\n" 
				+ "CAR TYPE: " + categoryType + "\r\n" 
				+ "TOTAL RENT: " + (rent * totalDays);
		;

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(messageBody);

		logger.info("Sending Mail to: " + to);

		javaMailSender.send(mail);

		logger.info("Mail sent...");
	}
}
