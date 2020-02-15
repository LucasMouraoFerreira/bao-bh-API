package com.lucasmourao.baobhapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.repositories.PlaceRepository;

@Service
public class PlaceService {

	@Autowired
	private PlaceRepository placeRepository;
	
	public List<Place> findAll(){
		return placeRepository.findAll();
	}
	
	public Place findById(Long id) {
		Optional<Place> place = placeRepository.findById(id);
		return place.get(); 
	}
	
	public Place insert(Place place) {
		return placeRepository.save(place);
	}
	
}
