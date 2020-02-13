package com.lucasmourao.baobhapi.services;

import java.util.List;

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
	
}
