package com.lucasmourao.baobhapi.dto;

import java.io.Serializable;

import com.lucasmourao.baobhapi.entities.Place;

public class SimplePlaceWithDistanceDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private String address;
	private Double avgRating;
	private Double userDistanceKm;

	public SimplePlaceWithDistanceDTO() {}

	public SimplePlaceWithDistanceDTO( Place place, Double userDistanceKm) {
		this.id = place.getId();
		this.name = place.getName();
		this.description = place.getDescription();
		this.address = place.getAddress();
		this.avgRating = place.getAvgRating();
		this.setUserDistanceKm(userDistanceKm);
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Double getAvgRating() {
		return avgRating;
	}


	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}

	public Double getUserDistanceKm() {
		return userDistanceKm;
	}

	public void setUserDistanceKm(Double userDistanceKm) {
		this.userDistanceKm = userDistanceKm;
	}	
	
}
