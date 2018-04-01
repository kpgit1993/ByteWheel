package org.bytemark.bytewheel.service.interfaces;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.model.Car;


public interface CarService {

	Car addCar(Car car, String categoryType);
	
	List<Car> getAllCars();
	
	List<Car> getCarsByCarCategory(String categoryType);
	
	List<Car> getAvailableCarsByDate(Date requestDate, Date date);
	
	List<Car> getAvailableCarsByDateAndCategory(Date requestDate, Date date, String categoryType);
	
	Car updateCar(Car car);
	
	boolean deleteCarByCarId(Long carId);

	Car getCarByCarName(String carName);

}
