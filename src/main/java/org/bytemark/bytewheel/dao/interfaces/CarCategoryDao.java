package org.bytemark.bytewheel.dao.interfaces;

import java.util.List;

import org.bytemark.bytewheel.model.CarCategory;


/**
 * @author : Kaustab Paul 
 * 		Useful for loose coupling the DAO layer with the other
 *      layers of the application
 */
public interface CarCategoryDao {

	CarCategory addCarCategory(CarCategory carCategory);
	
	List<CarCategory> findAllCarCategory();
	
	CarCategory findCarCategoryByCategoryId(Long categoryId);
	
	CarCategory findCarCategoryByCategoryType(String categoryType);

	CarCategory updateCarCategory(CarCategory carCategory);

	boolean deleteCarCategory(CarCategory carCategory);

	void deleteAllCarCategory();
	
}
