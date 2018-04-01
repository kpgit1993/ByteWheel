package org.bytemark.bytewheel.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.CarDao;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.repository.CarRepository;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarDaoImpl implements CarDao {

	@Autowired
	CarRepository carRepository;
	
	/**
	 * Insert a new customer 
	 **/
	public synchronized Car addCar(Car car){
		return carRepository.save(car);
	}
	
	/**
	 * get all customers 
	 **/
	public List<Car> findAllCars(){
		return carRepository.findAll();
	}
	
	/**
	 * get customer by customerId
	 **/
	public Car findCarByCarId(Long carId){
		return carRepository.getOne(carId);
	}
	
	/**
	 * get customer by customerId
	 **/
	public List<Car> findCarsByCarCategory(String categoryType){
		LoggerUtil.info("Fetching data using categoryType = "+categoryType);
		return carRepository.findCarsByCarCategoryType(categoryType);
	}

	public List<Car> findAvailableCarsByDate(Date startDate, Date endDate) {
		 List<Object[]> bookedCarNumber = carRepository.getBookedCarsByBetweenDates(startDate, endDate);
		 LoggerUtil.info(bookedCarNumber.toString());
		 
		 List<Car> availableCars = getAvailableCars(bookedCarNumber, null);
		 LoggerUtil.info("AVAILABLE CARS ========== "+availableCars);
		 return availableCars;
	}

	public List<Car> getAvailableCarsByDateAndCategory(Date startDate, Date endDate, String categoryType) {
		List<Object[]> bookedCarNumber = carRepository.getBookedCarsBetweenDatesByCategory(startDate, endDate, categoryType);
		LoggerUtil.info(bookedCarNumber.toString());
		
		List<Car> availableCars = getAvailableCars(bookedCarNumber, categoryType);
		LoggerUtil.info("AVAILABLE CARS ========== "+availableCars);
		return availableCars;
	}
	
	
	private List<Car> getAvailableCars(List<Object[]> bookedCarNumber, String categoryType){
		
		List<Car> availableCars = new ArrayList<Car>(); 
		
		// get all cars
		List<Car> allCars;
		if(categoryType == null){
			allCars = findAllCars();
		}else{
			allCars = findCarsByCarCategory(categoryType);
		}
		LoggerUtil.info(allCars.toString());
		
		for(Car car : allCars){
			
			boolean flag = false;
			
			for(int i=0;i<bookedCarNumber.size();++i){
				Car c = (Car)bookedCarNumber.get(i)[0];
				int n = (Integer)bookedCarNumber.get(i)[1];
				// if the matched car found in the list check if no of bookings is less than the total stock
				if(car.getCarId() == c.getCarId()){
					if(car.getTotalCount() > n){
						LoggerUtil.info("Car is available for booking... "+car);
						availableCars.add(car);
						flag = true;
						break;
					}
				}
			}
			if(!flag){
				LoggerUtil.info("Car is available for booking... "+car+", No booking record found...");
				availableCars.add(car);
			}
		}
		
		return availableCars;
	}
	
	
	public synchronized Car updateCar(Car car) {
		return carRepository.save(car);
	}

	public boolean deleteCarByCarId(Long carId) {
		carRepository.deleteById(carId);
		if(findCarByCarId(carId)==null){
			return true;
		}
		return false;
	}

	public Car getCarByCarName(String carName) {
		return carRepository.getCarByCarName(carName);
	}

}
