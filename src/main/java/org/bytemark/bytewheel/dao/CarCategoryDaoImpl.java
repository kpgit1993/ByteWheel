package org.bytemark.bytewheel.dao;

import java.util.List;

import org.bytemark.bytewheel.dao.interfaces.CarCategoryDao;
import org.bytemark.bytewheel.model.CarCategory;
import org.bytemark.bytewheel.repository.CarCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarCategoryDaoImpl implements CarCategoryDao {

	@Autowired
	private CarCategoryRepository carCategoryRepository;

	@Autowired
	private IdGeneratorDao idGeneratorDao;

	/**
	 * Insert a car category
	 **/
	public CarCategory addCarCategory(CarCategory carCategory) {
		/**
		 * To generate sequence by trigger un-comment the following and comment
		 * the @GeneratedValue in CarCategory
		 */
		// Long categoryId = idGeneratorDao.getCarCategoryId();
		// carCategory.setCategoryId(categoryId);
		// Logger.info(carCategory);
		return carCategoryRepository.save(carCategory);
	}

	/**
	 * get all car categories
	 **/
	public List<CarCategory> findAllCarCategory() {
		return carCategoryRepository.findAll();
	}

	/**
	 * get car category by categoryId
	 **/
	public CarCategory findCarCategoryByCategoryId(Long categoryId) {
		return carCategoryRepository.getOne(categoryId);
	}

	public CarCategory findCarCategoryByCategoryType(String categoryType) {
		return carCategoryRepository
				.findCarCategoryByCategoryType(categoryType);
	}

	public synchronized CarCategory updateCarCategory(CarCategory carCategory) {
		return carCategoryRepository.save(carCategory);
	}

	public boolean deleteCarCategory(CarCategory carCategory) {
		carCategoryRepository.delete(carCategory);
		if(findCarCategoryByCategoryId(carCategory.getCategoryId())==null){
			return true;
		}else{
			return false;
		}
	}

	public void deleteAllCarCategory() {
		carCategoryRepository.deleteAll();
	}
}
