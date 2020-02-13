package com.lucasmourao.baobhapi.entities;

import java.io.Serializable;

public class Place implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private String address;
	private String[] businessHours = new String[7];
	private Double avgScore;

	public Place() {
	}

	public Place(Long id, String name, String description, String address, String[] businessHours, Double avgScore) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.avgScore = avgScore;
		this.businessHours = businessHours;			
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

	public String[] getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String[] businessHours) {
		this.businessHours = businessHours;
	}

	public Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
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
