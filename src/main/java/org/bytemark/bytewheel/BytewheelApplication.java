package org.bytemark.bytewheel;

import org.apache.catalina.core.ApplicationContext;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.service.CarCategoryServiceImpl;
import org.bytemark.bytewheel.service.CarServiceImpl;
import org.bytemark.bytewheel.service.interfaces.CarCategoryService;
import org.bytemark.bytewheel.service.interfaces.CarService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages={"org.bytemark.bytewheel"})
public class BytewheelApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(BytewheelApplication.class, args);

		LoggerUtil.info("CONTEXT IS UP AND RUNNING =================");
		
		CarCategoryService carCategoryService = context.getBean(CarCategoryServiceImpl.class);
		CarService carService = context.getBean(CarServiceImpl.class);
		
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
}
