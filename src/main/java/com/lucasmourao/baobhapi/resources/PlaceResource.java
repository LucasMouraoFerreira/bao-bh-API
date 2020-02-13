package com.lucasmourao.baobhapi.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.baobhapi.entities.Place;

@RestController
@RequestMapping(value = "/places")
public class PlaceResource {

	@GetMapping
	public ResponseEntity<Place> findAll(){
		String [] businessHours = {"Segunda: 8:00 às 17:00","Terça: 8:00 às 17:00","Quarta: 8:00 às 17:00",
				"Quinta: 8:00 às 17:00","Sexta: 8:00 às 17:00","Sabádo: 8:00 às 17:00",
				"Domingo: 8:00 às 17:00"};
		Place place = new Place(1L, "Zoologico", "Lugar para ver animais", "Rua Otacilio Negrão de Lima", businessHours, 4.5);
		return ResponseEntity.ok().body(place);
	}
}
