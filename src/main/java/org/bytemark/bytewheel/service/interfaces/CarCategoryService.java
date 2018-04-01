package org.bytemark.bytewheel.service.interfaces;

import java.util.List;
import org.bytemark.bytewheel.model.CarCategory;


public interface CarCategoryService {

	CarCategory addCarCategory(CarCategory carCategory);
	
	List<CarCategory> getAllCarCategories();
	
	CarCategory getCarCategoriesbyCategoryId(Long carId);
	
	CarCategory getCarCategoriesbyCategoryType(String categoryType);
	
	CarCategory updateCarCategory(CarCategory carCategory);
	
	boolean deleteCarCategoryByCategoryId(Long carCategoryId);
	
	boolean deleteCarCategoryByCategoryType(String carCategoryType);

	void deleteAllCarCategory();
			
}
