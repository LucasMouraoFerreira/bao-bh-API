package com.lucasmourao.baobhapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.repositories.PlaceRepository;
import com.lucasmourao.baobhapi.services.exceptions.DatabaseException;
import com.lucasmourao.baobhapi.services.exceptions.ResourceNotFoundException;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository placeRepository;

	public List<Place> findAll() {
		return placeRepository.findAll();
	}

	public Place findById(Long id) {
		Optional<Place> place = placeRepository.findById(id);
		return place.orElseThrow(()-> new ResourceNotFoundException(id));
	}

	public Place insert(Place place) {
		return placeRepository.save(place);
	}

	public void delete(Long id) {
		try {
			placeRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}		
	}

	public Place update(Long id, Place place) {
		Optional<Place> entity = placeRepository.findById(id);
		updateData(entity.orElseThrow(()-> new ResourceNotFoundException(id)), place);
		return placeRepository.save(entity.get());
	}

	private void updateData(Place entity, Place place) {
		if (place.getName() != null) {
			entity.setName(place.getName());
		}
		if (place.getDescription() != null) {
			entity.setDescription(place.getDescription());
		}
		if (place.getAddress() != null) {
			entity.setAddress(place.getAddress());
		}
		entity.setRegion(place.getRegion());
		if (place.getBusinessHours() != null) {
			entity.setBusinessHours(place.getBusinessHours());
		}
		if (place.getLatitude() != null) {
			entity.setLatitude(place.getLatitude());
		}
		if (place.getLongitude() != null) {
			entity.setLongitude(place.getLongitude());
		}
	}
}
