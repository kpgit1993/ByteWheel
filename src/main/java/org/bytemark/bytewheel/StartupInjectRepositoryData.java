package org.bytemark.bytewheel;

import javax.annotation.PostConstruct;

/*import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.service.interfaces.CarCategoryService;
import org.bytemark.bytewheel.service.interfaces.CarService;
import org.bytemark.bytewheel.util.LoggerUtil;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StartupInjectRepositoryData {

	/*@Autowired
	private CarCategoryService carCategoryService;

	@Autowired
	private CarService carService;

	/**
	 * The following method is called after the application is started... This
	 * will inject initial data in the database (category and car data) so that
	 * the user can start with initial activities ...
	 */
	
	/*
	@PostConstruct
	public void init() {

		LoggerUtil.info("Injecting data in DB");

		// clean up initial database
		try{
			carCategoryService.deleteAllCarCategory();
		}catch(Exception e){
			LoggerUtil.error(e.getMessage());
		}finally{
			LoggerUtil.info("Car Category database is cleaned...");
		}
		
		// Adding car category in the database
		CarCategory compact = new CarCategory("COMPACT", 20f);
		CarCategory full = new CarCategory("FULL", 30f);
		CarCategory large = new CarCategory("LARGE", 40f);
		CarCategory luxury = new CarCategory("LUXURY", 50f);

		carCategoryService.addCarCategory(compact);
		carCategoryService.addCarCategory(full);
		carCategoryService.addCarCategory(large);
		carCategoryService.addCarCategory(luxury);
		
		// Adding Cars in the database for each categories
		Car fordFiesta = new Car("Ford Fiesta", 2);
		Car fordFocus = new Car("Ford Focus", 2);
		carService.addCar(fordFiesta, "COMPACT");
		carService.addCar(fordFocus, "COMPACT");
		
		Car chevrolet = new Car("Chevrolet Malibu", 2);
		Car volkswagen = new Car("Volkswagen Jetta", 2);
		carService.addCar(chevrolet, "FULL");
		carService.addCar(volkswagen, "FULL");
		
		Car fordEdge = new Car("Ford Egde", 2);
		Car fordEscape = new Car("Ford Escape", 2);
		carService.addCar(fordEdge, "LARGE");
		carService.addCar(fordEscape, "LARGE");
		
		Car BMW328i = new Car("BMW 328i", 2);
		Car BMWX5 = new Car("BMW X5", 2);
		carService.addCar(BMW328i, "LUXURY");
		carService.addCar(BMWX5, "LUXURY");
		
		LoggerUtil.info("Injecting COMPLETED ---------");
	}
*/
}
