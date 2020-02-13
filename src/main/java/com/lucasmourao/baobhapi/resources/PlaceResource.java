package com.lucasmourao.baobhapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.services.PlaceService;;

@RestController
@RequestMapping(value = "/places")
public class PlaceResource {

	@Autowired
	private PlaceService placeService;
	
	@GetMapping
	public ResponseEntity<List<Place>> findAll(){
		List<Place> list = placeService.findAll();
		return ResponseEntity.ok().body(list);
	}
}
