package com.lucasmourao.baobhapi.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.baobhapi.dto.CompletePlaceDTO;
import com.lucasmourao.baobhapi.dto.SimplePlaceDTO;
import com.lucasmourao.baobhapi.services.PlaceService;;

@RestController
@RequestMapping(value = "/places")
public class PlaceResource {

	@Autowired
	private PlaceService placeService;
	
	@GetMapping
	public ResponseEntity<List<SimplePlaceDTO>> findAll(){
		List<SimplePlaceDTO> list = placeService.findAll().stream().map(x-> new SimplePlaceDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<CompletePlaceDTO> findById(@PathVariable Long id){
		CompletePlaceDTO place = new CompletePlaceDTO(placeService.findById(id));
		return ResponseEntity.ok().body(place);
	}
}
