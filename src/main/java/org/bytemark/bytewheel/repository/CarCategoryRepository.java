package org.bytemark.bytewheel.repository;

import org.bytemark.bytewheel.model.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarCategoryRepository extends JpaRepository<CarCategory, Long>{

	@Query("SELECT cc FROM CarCategory cc WHERE cc.categoryType=(:categoryType)")
	public CarCategory findCarCategoryByCategoryType(String categoryType);

}

