package org.bytemark.bytewheel.service;

import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.CarCategoryDao;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.service.interfaces.CarCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Kaustab Paul 
 * 		   The service layer used to manage the business
 *         transaction related to car category under the control of Spring
 *         framework. The methods are required to bound a existing or a newly
 *         created transaction, All the transactions will read the committed
 *         data from the database.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class CarCategoryServiceImpl implements CarCategoryService {

	@Autowired
	private CarCategoryDao carCategoryDao;

	/**
	 * Method is used to add a car category The cars under the added category
	 * can be null, and can be added later on
	 */
	public CarCategory addCarCategory(CarCategory carCategory) {
		if (carCategory == null) {
			throw new NullPointerException(
					"Invalid car category add request with category Null");
		}
		return carCategoryDao.addCarCategory(carCategory);
	}

	/**
	 * List details for all car categories
	 */
	public List<CarCategory> getAllCarCategories() {
		return carCategoryDao.findAllCarCategory();
	}

	/**
	 * Method is used to get the details of a car category by it's id
	 */
	public CarCategory getCarCategoriesbyCategoryId(Long categoryId) {
		if (categoryId <= 0) {
			throw new IllegalArgumentException(
					"Search request with invalid categry id: " + categoryId);
		}
		CarCategory carCategory = carCategoryDao.findCarCategoryByCategoryId(categoryId);
		if(carCategory==null || carCategory.getCategoryId()==null){
			throw new ResourceNotFoundException("Car category not found for category id: "+categoryId);
		}
		return carCategory;
	}

	/**
	 * Method is used to get the details of a car category by it's name
	 */
	public CarCategory getCarCategoriesbyCategoryType(String categoryType) {
		if (categoryType == null || categoryType.isEmpty()) {
			throw new IllegalArgumentException(
					"search category with invalid category type: "
							+ categoryType);
		}
		CarCategory carCategory = carCategoryDao.findCarCategoryByCategoryType(categoryType);
		if(carCategory==null || carCategory.getCategoryId()==null){
			throw new ResourceNotFoundException("Car category not found for category type: "+categoryType);
		}
		return carCategory;
	}

	/**
	 * Method is used to update a car category
	 */
	public CarCategory updateCarCategory(CarCategory carCategory) {
		if (carCategory == null) {
			throw new NullPointerException(
					"Invalid update category request with category = null");
		}
		return carCategoryDao.updateCarCategory(carCategory);
	}

	/**
	 * Method is used to delete a car category by it's id
	 */
	public boolean deleteCarCategoryByCategoryId(Long carCategoryId) {
		if (carCategoryId <= 0) {
			throw new IllegalArgumentException(
					"Delete request for invalid category id: " + carCategoryId);
		}
		CarCategory carCategory = carCategoryDao
				.findCarCategoryByCategoryId(carCategoryId);
		return carCategoryDao.deleteCarCategory(carCategory);

	}

	/**
	 * Method is used to delete a car category by it's name
	 */
	public boolean deleteCarCategoryByCategoryType(String carCategoryType) {
		if (carCategoryType == null || carCategoryType.isEmpty()) {
			throw new IllegalArgumentException(
					"Delete request for invalid car category type: "
							+ carCategoryType);
		}
		CarCategory carCategory = carCategoryDao
				.findCarCategoryByCategoryType(carCategoryType);
		return carCategoryDao.deleteCarCategory(carCategory);
	}

	/**
	 * Method is used to delete all the car categories
	 */
	public void deleteAllCarCategory() {
		carCategoryDao.deleteAllCarCategory();
	}
}
