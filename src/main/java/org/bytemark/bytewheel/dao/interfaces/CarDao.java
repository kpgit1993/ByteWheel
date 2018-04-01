package org.bytemark.bytewheel.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.util.LoggerUtil;


/**
 * @author : Kaustab Paul 
 * 		Useful for loose coupling the DAO layer with the other
 *      layers of the application
 */
public interface CarDao {

	Car addCar(Car car);

	List<Car> findAllCars();
	
	Car findCarByCarId(Long carId);
	
	List<Car> findCarsByCarCategory(String categoryType);

	List<Car> findAvailableCarsByDate(Date startDate, Date endDate);
	
	List<Car> getAvailableCarsByDateAndCategory(Date startDate, Date endDate, String categoryType);
	
	Car updateCar(Car car);

	boolean deleteCarByCarId(Long carId);

	Car getCarByCarName(String carName);
	
}
