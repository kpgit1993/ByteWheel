package org.bytemark.bytewheel.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.bytemark.bytewheel.dao.IdGeneratorDao;

/**
 * 
 * @author		: 	Kaustab Paul
 * Class		:	CarCategory
 * Description	:	 
 * The class is will be used to map the CAR_CATEGORY table in the database. 
 * The table has one to many relationship with the Car table i.e a certain category of
 * this table will have mapping with one/more than one cars of the Car table. 
 * Daily rent of the car will vary from category to category
 * 
 */
@Entity
@Table(name = "CAR_CATEGORY")
public class CarCategory implements Serializable {

	@Id
	/**
	 * ManyToOne/OneToMany may give the following error:
	 * Spring boot BUG: One to Many relationship doesn't work and fails with the error: 
	 * org.h2.jdbc.JdbcSQLException: NULL not allowed for column "CATEGORY_ID"; SQL statement:
	 * As an workaround the id is to be assigned manually
	 * 
	 * Ref: https://github.com/alexbt/sample-spring-boot-data-jpa-embedded/issues/2 (OPEN)
	 * 
	 * WorkAround: 
	 * set the categoryId from the service Layer to generate Id through external trigger 
	 * and comment the @GeneratedValue annotation for the id
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
	
	@Column(name = "CATEGORY_TYPE", unique=true)
	@NotNull 
	private String categoryType;

	@Column(name = "DAILY_RENT_IN_DOLLAR")
	@NotNull
	private Float dailyRentInDollar;

	public CarCategory(){
		// no-arg constructor
	}
	
	public CarCategory(String categoryType, float dailyRentInDollar){
		this.categoryType = categoryType;
		this.dailyRentInDollar = dailyRentInDollar;
	}
	
	/**
	 * FetchType.EAGER will produce the JSON response along with the car details under each category
	 * CascadeType.ALL will delete all the car children if any category gets deleted 
	 */
	@OneToMany(targetEntity=Car.class, mappedBy="carCategory", 
			cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Car> carSet;

	public Set<Car> getCarSet() {
		return carSet;
	}

	public void setCarSet(Set<Car> carSet) {
		this.carSet = carSet;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Float getDailyRentInDollar() {
		return dailyRentInDollar;
	}

	public void setDailyRentInDollar(Float dailyRentInDollar) {
		this.dailyRentInDollar = dailyRentInDollar;
	}

	@Override
	public String toString() {
		return "[categoryId: " + this.categoryId + "," + "categoryType: "
				+ this.categoryType + "," + "dailyRentInDollar:"
				+ this.dailyRentInDollar + "]";
	}
}
