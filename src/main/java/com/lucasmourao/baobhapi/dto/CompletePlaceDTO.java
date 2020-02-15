package com.lucasmourao.baobhapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lucasmourao.baobhapi.entities.Place;

public class CompletePlaceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private String address;
	private Double avgRating;
	private Long numberOfRatings;
	
	private List<String> bussinesHours = new ArrayList<>();

	private List<CommentDTO> comments = new ArrayList<>();

	public CompletePlaceDTO() {
	}

	public CompletePlaceDTO(Place place) {
		id = place.getId();
		name = place.getName();
		description = place.getDescription();
		address = place.getAddress();
		avgRating = place.getAvgRating();
		numberOfRatings = place.getNumberOfRatings();
		comments = place.getComments().stream().map(x -> new CommentDTO(x)).collect(Collectors.toList());
		setBussinesHours(place.getBusinessHours());
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

	public Long getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(Long numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public List<String> getBussinesHours() {
		return bussinesHours;
	}

	public void setBussinesHours(String bussinesHours) {
		String array[] = bussinesHours.split(";");
		for(String s : array) {
			this.bussinesHours.add(s);
		}
	}

}
