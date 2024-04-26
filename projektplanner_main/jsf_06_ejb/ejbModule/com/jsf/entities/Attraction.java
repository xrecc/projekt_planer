package com.jsf.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.jsf.entities.Attraction;

/**
 * The persistent class for the attractions database table.
 * 
 */
@Entity
@Table(name="attraction")
@NamedQuery(name="Attraction.findAll", query="SELECT a FROM Attraction a")
public class Attraction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="attractions_id")
	private Integer attractionsId;

	private String city;

	private String country;

	@Column(name="is_available")
	private String isAvailable;

	private String name;

	private Double price;

	@Column(name="type_of_attraction")
	private String typeOfAttraction;

	//bi-directional many-to-one association to Trip
	@ManyToOne
	@JoinColumn(name="trips_trips_id")
	private Trip trip;

	public Attraction() {
	}

	public Integer getAttractionsId() {
		return this.attractionsId;
	}

	public void setAttractionsId(Integer attractionsId) {
		this.attractionsId = attractionsId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTypeOfAttraction() {
		return this.typeOfAttraction;
	}

	public void setTypeOfAttraction(String typeOfAttraction) {
		this.typeOfAttraction = typeOfAttraction;
	}

	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

}