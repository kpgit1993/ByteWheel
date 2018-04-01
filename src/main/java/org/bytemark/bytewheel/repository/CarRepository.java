package org.bytemark.bytewheel.repository;

import java.util.Date;
import java.util.List;

import org.bytemark.bytewheel.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CarRepository extends JpaRepository<Car, Long> {
	
	@Query("SELECT c FROM Car c join fetch c.carCategory cty WHERE LOWER(cty.categoryType) = LOWER(:categoryType)")
    public List<Car> findCarsByCarCategoryType(@Param("categoryType") String categoryType);

	@Query("select b.car, count(*) from Booking b "+
			"where b.status <> 'CANCELLED' "+ 
			"and ( "+
					"(b.bookingStartDate < (:startDate) and b.bookingEndDate > (:endDate)) "+
					"or (b.bookingStartDate < (:startDate) and b.bookingEndDate < (:endDate)) "+
					"or (b.bookingStartDate > (:startDate) and b.bookingEndDate < (:endDate)) "+
					"or (b.bookingStartDate < (:endDate) and b.bookingEndDate > (:endDate)) "+
				") group by b.car.carId")
	public List<Object[]> getBookedCarsByBetweenDates(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate);
	
	@Query("select b.car, count(*) from Booking b "+
			"where b.status <> 'CANCELLED' "+ 
			"and b.car.carCategory.categoryType = (:categoryType) "+
			"and ( "+
					"(b.bookingStartDate < (:startDate) and b.bookingEndDate > (:endDate)) "+
					"or (b.bookingStartDate < (:startDate) and b.bookingEndDate < (:endDate)) "+
					"or (b.bookingStartDate > (:startDate) and b.bookingEndDate < (:endDate)) "+
					"or (b.bookingStartDate < (:endDate) and b.bookingEndDate > (:endDate)) "+
				") group by b.car.carId")
	public List<Object[]> getBookedCarsBetweenDatesByCategory(
			@Param("startDate") Date requestDate,
			@Param("endDate") Date endDate, 
			@Param("categoryType") String categoryType);

	@Query("select count(*) from Booking b where b.car.carId=(:carId) and "
			+ "b.billingDate=(:requestDate) and b.status <> 'CANCELLED'")
	public Integer getBookedCarCountsForGivenDateByCarId(@Param("carId") Long carId, 
			@Param("requestDate") Date requestDate);

	@Query("select c from Car c where c.carName=(:carName)")
	public Car getCarByCarName(@Param("carName") String carName);
	
}
