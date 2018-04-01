package org.bytemark.bytewheel.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.bytemark.bytewheel.model.Car;
import org.bytemark.bytewheel.service.interfaces.CarService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/car-service")
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@PostMapping("/cars/car/add")
	public ResponseEntity<Car> addCar(@RequestBody Car car,
			@RequestParam("categoryType") String categoryType) throws Exception {
		LoggerUtil.info("Adding car: "+car+" for category: "+categoryType);
		LoggerUtil.info("CAR Payload: "+car);
		Car addCar = carService.addCar(car, categoryType);
		return new ResponseEntity<Car>(addCar, HttpStatus.CREATED);
	}
	
	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getAllCars() {
		List<Car> carList = carService.getAllCars();
		LoggerUtil.info("CARLIST = " + carList);
		return new ResponseEntity<List<Car>>(carList, HttpStatus.OK);
	}

	@GetMapping("/cars/category")
	public ResponseEntity<List<Car>> getCarsByCarCategory(
			@RequestParam("type") String categoryType) {
		LoggerUtil.info("CAR LIST FOR TYPE = " + categoryType);
		List<Car> carList = carService.getCarsByCarCategory(categoryType);
		return new ResponseEntity<List<Car>>(carList, HttpStatus.OK);
	}
	
	@GetMapping("/cars/car")
	public ResponseEntity<Car> getCarByCarName(@RequestParam("carName") String carName) {
		Car car = carService.getCarByCarName(carName);
		return new ResponseEntity<Car>(car, HttpStatus.OK);
	} 

	@GetMapping("/cars/available-cars/")
	public ResponseEntity<List<Car>> getAvailableCarsBetweenDates(
			@RequestParam("startDate") String startDate,
			@RequestParam("startDate") String endDate) throws ParseException {
		LoggerUtil.info("CAR LIST FOR booking start date: " + startDate+ ", end date: "+endDate);
		List<Car> carList = carService.getAvailableCarsByDate(
				new SimpleDateFormat("dd-MM-yyyy").parse(startDate),
				new SimpleDateFormat("dd-MM-yyyy").parse(endDate));
		return new ResponseEntity<List<Car>>(carList, HttpStatus.OK);
	}

	@GetMapping("/cars/available-cars/category/{categoryType}")
	public ResponseEntity<List<Car>> getAvailableCarsBetweenDatesByCategory(
			@PathVariable("categoryType") String categoryType,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) throws ParseException {
		
		LoggerUtil.info("CAR LIST FOR START DATE = " + startDate + ", END DATE: "
			+endDate+" CATEGORY = " + categoryType);
		
		List<Car> carList = carService.getAvailableCarsByDateAndCategory(
				new SimpleDateFormat("dd-MM-yyyy").parse(startDate), 
				new SimpleDateFormat("dd-MM-yyyy").parse(endDate), 
				categoryType);
		return new ResponseEntity<List<Car>>(carList, HttpStatus.OK);
	}

	@PutMapping("/cars/car")
	public ResponseEntity<Car> updateCar(@RequestBody Car car){
		LoggerUtil.info("CAR Payload: "+car);
		Car updatedCar = carService.updateCar(car);
		return new ResponseEntity<Car>(car, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/cars/car/{carId}")
	public ResponseEntity<Object> deleteCar(@PathVariable("carId") Long carId){
		LoggerUtil.info("Delete car request for carId: "+carId);
		boolean delete = carService.deleteCarByCarId(carId);
		if(delete){
			return new ResponseEntity<Object>(delete, HttpStatus.NO_CONTENT);
		}else{
			throw new IllegalStateException("Failed to delete car with car id: "+carId);
		}
	}
}
