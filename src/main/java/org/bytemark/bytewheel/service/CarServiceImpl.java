package org.bytemark.bytewheel.service;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.BookingDao;
import org.bytemark.bytewheel.dao.interfaces.CarCategoryDao;
import org.bytemark.bytewheel.dao.interfaces.CarDao;
import org.bytemark.bytewheel.exception.BusinessException;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.service.interfaces.CarService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Kaustab Paul 
 * 		   The service layer used to manage the business
 *         transaction related to cars under the control of Spring
 *         framework. The methods are required to bound a existing or a newly
 *         created transaction, All the transactions will read the committed
 *         data from the database.
 */
@Service
@Transactional(
		propagation=Propagation.REQUIRED,
		isolation=Isolation.READ_COMMITTED
	)
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDao carDao;

	@Autowired
	private CarCategoryDao carCategoryDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	
	/**
	 * The method is used to add a car 
	 */
	public Car addCar(Car car, String categoryType) {
		if(car == null){
			throw new NullPointerException("Null value can't be saved for car");
		}
		if(categoryType == null || categoryType.isEmpty()){
			throw new IllegalArgumentException("Invalid category type for the car provided: "+categoryType);
		}
		CarCategory carCategory = carCategoryDao.findCarCategoryByCategoryType(categoryType);
		LoggerUtil.info("Car category for the provided car type = "+carCategory);
		if(carCategory==null){
			throw new IllegalArgumentException("Invalid category type found: "+categoryType);
		}
		car.setCarCategory(carCategory);
		Car addCar = carDao.addCar(car);
		return addCar;
	}

	/**
	 * Method is used to get all cars for all categories
	 */
	public List<Car> getAllCars() {
		return carDao.findAllCars();
	}

	/**
	 * Method is used to get all car details for a certain category
	 */
	public List<Car> getCarsByCarCategory(String categoryType) {
		if(categoryType == null || categoryType.isEmpty()){
			throw new IllegalArgumentException("Search car with Invalid category type: "+categoryType);
		}
		List<Car> carList = carDao.findCarsByCarCategory(categoryType);
		if(carList==null || carList.isEmpty()){
			throw new ResourceNotFoundException("Car category not found: "+categoryType);
		}
		return carList;
	}

	/**
	 * Method is used to get all cars available in a particular date
	 */
	public List<Car> getAvailableCarsByDate(Date startDate, Date endDate) {
		if(startDate == null || endDate == null){
			throw new NullPointerException("Invalid available car search request with null date value");
		}
		return carDao.findAvailableCarsByDate(startDate, endDate);
	}

	/**
	 * Method is used to get all cars available in a particular date of certain category
	 */
	public List<Car> getAvailableCarsByDateAndCategory(Date startDate, Date endDate, String categoryType) {
		if(startDate == null || endDate == null){
			throw new NullPointerException("Invalid available car search request with null date value");
		}
		if(categoryType == null || categoryType.isEmpty()){
			throw new IllegalArgumentException("Search car with Invalid category type: "+categoryType);
		}
		List<Car> carList = carDao.getAvailableCarsByDateAndCategory(startDate, endDate, categoryType);
		if(carList==null || carList.isEmpty()){
			throw new ResourceNotFoundException("Car category not found: "+carList);
		}
		return carList;
	}

	public Car updateCar(Car car) {
		if(car == null){
			throw new NullPointerException("Invalid update request with car value as null");
		}
		return carDao.updateCar(car);
	}

	public boolean deleteCarByCarId(Long carId) {
		if(carId <= 0){
			throw new IllegalArgumentException("Delete request for Invalid carId: "+carId);
		}
		return carDao.deleteCarByCarId(carId);
	}

	public Car getCarByCarName(String carName) {
		if(carName==null || carName.isEmpty()){
			throw new IllegalArgumentException("Seaqrch car request with inalid car name: "+carName);
		}
		Car car = carDao.getCarByCarName(carName);
		if(car == null || car.getCarId()==null){
			throw new ResourceNotFoundException("Car not found with carName: "+carName);
		}
		return car;
	}

}
