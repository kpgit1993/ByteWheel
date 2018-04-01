package org.bytemark.bytewheel.rest;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.service.interfaces.CarCategoryService;
import org.bytemark.bytewheel.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/car-category-service")
public class CarCategoryController {
	
	@Autowired
	private CarCategoryService carCategoryService;
	
	@PostMapping("/car-category/add")
	public ResponseEntity<CarCategory> addCarCategory(@RequestBody CarCategory carCategory) {
		// check if the category already exists in the databse
		try{
			getCarCategoriesbyCategoryType(carCategory.getCategoryType());
			throw new RuntimeException("Enetring duplicate category in the database is not allowed");
		}catch(ResourceNotFoundException e){
			LoggerUtil.info("Category type not exist in db...");
		}
		CarCategory addedCarCategory = carCategoryService.addCarCategory(carCategory);
		return new ResponseEntity<CarCategory>(addedCarCategory, HttpStatus.CREATED);
	}

	@GetMapping("/car-categories")
	public ResponseEntity<List<CarCategory>> getAllCarCategories() {
		List<CarCategory> carCategoryList = carCategoryService.getAllCarCategories();
		return new ResponseEntity<List<CarCategory>>(carCategoryList, HttpStatus.OK);
	}
	
	@GetMapping("/car-categories/category/by-id/{carCategoryId}")
	public ResponseEntity<CarCategory> getCarCategoriesbyCarId(@PathVariable("carCategoryId") Long carCategoryId) {
		CarCategory carCategory = carCategoryService.getCarCategoriesbyCategoryId(carCategoryId);
		return new ResponseEntity<CarCategory>(carCategory, HttpStatus.OK);
	}

	@GetMapping("/car-categories/category/by-type/{carCategoryType}")
	public ResponseEntity<CarCategory> getCarCategoriesbyCategoryType(@PathVariable("carCategoryType") String categoryType) {
		CarCategory carCategory = carCategoryService.getCarCategoriesbyCategoryType(categoryType);
		return new ResponseEntity<CarCategory>(carCategory, HttpStatus.OK);
	}

	@PutMapping("/car-categories/category")
	public ResponseEntity<CarCategory> updateCarCategory(CarCategory carCategory) {
		CarCategory updatedCarCategory = carCategoryService.updateCarCategory(carCategory);
		return new ResponseEntity<CarCategory>(carCategory, HttpStatus.OK);
	}

	@DeleteMapping("/car-categories/category/by-id/{carCategoryId}")
	public ResponseEntity deleteCarCategoryByCategoryId(@PathVariable("carCategoryId") Long carCategoryId) {
		boolean isDeleted = carCategoryService.deleteCarCategoryByCategoryId(carCategoryId);
		return new ResponseEntity(isDeleted, HttpStatus.NO_CONTENT);
		
	}

	@DeleteMapping("/car-categories/category/by-type/{carCategoryType}")
	public ResponseEntity deleteCarCategoryByCategoryType(@PathVariable("carCategoryType") String carCategoryType) {
		boolean isDeleted =  carCategoryService.deleteCarCategoryByCategoryType(carCategoryType);
		return new ResponseEntity(isDeleted, HttpStatus.NO_CONTENT);
	}
	
}
