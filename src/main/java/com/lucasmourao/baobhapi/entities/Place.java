package com.lucasmourao.baobhapi.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasmourao.baobhapi.entities.enums.Region;

@Entity
@Table(name="tb_place")
public class Place implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String address;
	private String businessHours;
	private Double avgRating;
	private Long numberOfRatings;
	private Double latitude;
	private Double longitude;
	
	private Integer region;
	
	@JsonIgnore
	@OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();
	
	public Place() {
	}

	public Place(Long id, String name, String description, String address, String businessHours, Double avgRating, Long numberOfRatings, Region region, Double latitude, Double longitude) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.avgRating = avgRating;
		this.businessHours = businessHours;	
		this.numberOfRatings = numberOfRatings;
		setRegion(region);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
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

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public Long getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(Long numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}
	
	public Region getRegion() {
		return Region.valueOf(region);
	}

	public void setRegion(Region region) {
		if(region != null) {
			this.region = region.getCode();
		}		
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	

}
