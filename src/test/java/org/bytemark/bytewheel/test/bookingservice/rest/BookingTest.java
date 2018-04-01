package org.bytemark.bytewheel.test.bookingservice.rest;

import java.util.Calendar;
import java.util.Date;
import junit.framework.Assert;
import org.bytemark.bytewheel.BytewheelApplication;
import org.bytemark.bytewheel.model.Booking;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.model.Customer;
import org.bytemark.bytewheel.test.util.JsonConverter;
import org.bytemark.bytewheel.test.util.UriUtil;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BytewheelApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookingTest {

	public static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

	private final String TEST_EMAIL = "kaustab.paul93@gmail.com";
	private final String TEST_CAR = "TEST_CAR";
	private final String TEST_CATEGORY = "TEST_CATEGORY";
	private final String SERVER_USER_NAME = "admin";
	private final String SERVER_PASSWORD = "admin_secret";
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private HttpHeaders headers = new HttpHeaders();

	private Date getTomorrowDate(int n) {
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, n);
		Date tomorrow = calendar.getTime();
		return tomorrow;
	}

	@Before
	public void createCarsForBooking() throws JsonProcessingException {

		// Check if car category exists in the category table
		ResponseEntity<String> responseListCategoryEntity = testRestTemplate.getForEntity(
			UriUtil.getURI(port, "/api/car-category-service/car-categories/category/by-type/TEST_CATEGORY"),
					String.class);
		logger.info("List Category: ============ " + responseListCategoryEntity);		
			
		// Add category if not exists
		if(responseListCategoryEntity.getStatusCode() == HttpStatus.NOT_FOUND){
			logger.info("Adding Category... "+TEST_CATEGORY);
			HttpEntity<String> categoryEntity = new HttpEntity<String>(headers);
			ResponseEntity<String> responseCategoryEntity = testRestTemplate
				.exchange(UriUtil.getURI(port, "/api/car-category-service/car-category/add"),
					HttpMethod.POST, new HttpEntity<CarCategory>(new CarCategory(TEST_CATEGORY, 20f), headers), 
						String.class);
			logger.info("Added Category: ============ "	+ responseCategoryEntity);
			logger.info("Added Category: ============ "	+ JsonConverter.mapToJson(responseCategoryEntity));
			Assert.assertTrue(responseCategoryEntity.getStatusCode() == HttpStatus.CREATED);
		}else
		{
			logger.info(TEST_CATEGORY+" already exists...");
		}
		

		
		// Check if car is in the database	
		ResponseEntity<String> responseCarEntity = testRestTemplate
			.exchange(UriUtil.getURI(port, "/api/car-service/cars/car?carName="+TEST_CAR),
					HttpMethod.GET, new HttpEntity<Car>(new Car(), headers), String.class);
		logger.info("List Car in Category: ============ " + responseCarEntity);
		logger.info("List Car in Category: ============ " + JsonConverter.mapToJson(responseCarEntity));
			
		// Adding car if not found
		if(responseCarEntity.getStatusCode() == HttpStatus.NOT_FOUND){
			logger.info("Adding Car TEST_CAR IN... TEST_CATEGORY");
			responseCarEntity = testRestTemplate.exchange(
				UriUtil.getURI(port, "/api/car-service/cars/car/add?categoryType="+TEST_CATEGORY),
					HttpMethod.POST, new HttpEntity<Car>(new Car(TEST_CAR, 2)), String.class);
			logger.info("Added CAR: ============ "	+ responseCarEntity);
			logger.info("Added CAR: ============ "	+ JsonConverter.mapToJson(responseCarEntity));
			Assert.assertTrue(responseCarEntity.getStatusCode() == HttpStatus.CREATED);
		}else{
			logger.info("TEST_CAR already exists in TEST_CAATEGORY.... ");
		}

		// validate if the car was created
		responseCarEntity = testRestTemplate.exchange(
				UriUtil.getURI(port, "/api/car-service/cars/car?carName="+TEST_CAR),
				HttpMethod.GET, new HttpEntity<Car>(new Car(), headers), String.class);
		logger.info("List Car in Category: ============ " + responseCarEntity);
		logger.info("List Car in Category: ============ " + JsonConverter.mapToJson(responseCarEntity));
		Assert.assertTrue(responseCarEntity.getStatusCode() == HttpStatus.OK);
		
		logger.info("END of initial configuration,,,,");
	}

	@After
	public void removeCarCategory() throws JsonProcessingException {
		// delete the category (this will delete all the cars under this
		// category in cascade)
		testRestTemplate.delete(UriUtil
			.getURI(port, "/api/car-category-service/car-categories/category/by-type/"+TEST_CATEGORY));

		// validate if category was deleted
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> responseListCategoryEntity = testRestTemplate.exchange(
			UriUtil.getURI(port, "/api/car-category-service/car-categories/category/by-type/"+TEST_CATEGORY),
				HttpMethod.GET, entity, String.class);
		logger.info("Delete Category: ============ " + responseListCategoryEntity);
		logger.info("Delete Category: ============ " + JsonConverter.mapToJson(responseListCategoryEntity));
		Assert.assertTrue(responseListCategoryEntity.getStatusCode() != HttpStatus.NOT_FOUND);

	/*	testRestTemplate.delete(UriUtil
			.getURI(port, "/api/car-category-service/car-categories/cars/car/"+{carId"+TEST_CAR));
*/
		
		// check the car if deleted
		HttpEntity<String> carEntity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> responseCarEntity = testRestTemplate.exchange(
			UriUtil.getURI(port, "/api/car-service/cars/car?carName="+TEST_CAR),
				HttpMethod.GET, carEntity, String.class);
		logger.info("List Car in Category: ============ " + responseCarEntity);
		logger.info("List Car in Category: ============ " + JsonConverter.mapToJson(responseCarEntity));
		Assert.assertTrue(responseCarEntity.getStatusCode() != HttpStatus.NOT_FOUND);

	}

	@Test
	public void testCreateBooking() throws Exception {

		logger.info("Starting booking process for TEST_CAR of category TEST_CATEGORY.........................");
		
		Booking booking = new Booking();
		Customer customer = new Customer("kaustab.paul93@gmail.com");
		
		// Get the car object from the database that has car name as TEST_CAR
		logger.info("Get the car by car name.......");
		ResponseEntity<Car> responseCarEntity = testRestTemplate.exchange(
				UriUtil.getURI(port, "/api/car-service/cars/car?carName="+TEST_CAR),
					HttpMethod.GET, new HttpEntity<Car>(new Car()) , Car.class);
		
		logger.info("CAR FOR BOOKING: ============ " + responseCarEntity);
		logger.info("CAR FOR BOOKING: ============ " + JsonConverter.mapToJson(responseCarEntity));
		Assert.assertTrue(responseCarEntity.getStatusCode() == HttpStatus.OK);

		booking.setBookingStartDate(getTomorrowDate(2));
		booking.setBookingEndDate(getTomorrowDate(5));
		
		booking.setCar(responseCarEntity.getBody());
		booking.setCustomer(customer);

		HttpEntity<Booking> entity = new HttpEntity<Booking>(booking, headers);

		logger.info("GOR CAR ==== "+responseCarEntity.getBody());
		logger.info("SENDING BOOKING PAYLOAD === "+booking);
		
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(
				UriUtil.getURI(port, "/api/booking-service/bookings/booking/add?carName=TEST_CAR&emailId="+TEST_EMAIL),
				HttpMethod.POST, entity, String.class);

		String jsonResponse = JsonConverter.mapToJson(responseEntity);
		logger.info("Add Booking Response ====================== "+jsonResponse);
		
	}

}
