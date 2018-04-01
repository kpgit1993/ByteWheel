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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
		name="CAR"
		//uniqueConstraints = @UniqueConstraint(columnNames = "CAR_NAME")
	)
public class Car implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CAR_ID")
	private Long carId;
	
	@Column(name="CAR_NAME", unique=true)
	@NotNull
	private String carName;
	
	@Column(name="TOTAL_COUNT")
	@NotNull
	private Integer totalCount;
	
	/**
	 * @JsonIgnore to ignore the associated details in the json response during marshalling
	 * and to break the infinite json looping due to bi-directional association mapping of DAOs
	 */
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID")
	private CarCategory carCategory;
	
	
	//@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="car")
	private Set<Booking> bookingSet;
	
	
	public Car(){
		// no-arg constructor
	}

	public Car(String carName, int count){
		this.carName = carName;
		this.totalCount = count;
	}
	
	public Set<Booking> getBookingSet() {
		return bookingSet;
	}

	public void setBookingSet(Set<Booking> bookingSet) {
		this.bookingSet = bookingSet;
	}
	
	public CarCategory getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(CarCategory carCategory) {
		this.carCategory = carCategory;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	
	@Override
	public String toString(){
		return "[carId"+this.carId+","
				+ "carName: "+this.carName+","
				+ "totalCount:"+this.totalCount+""
				+ "category:"+this.carCategory+"]"
				;
	}
	
}
