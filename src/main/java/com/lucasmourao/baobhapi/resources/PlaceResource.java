package com.lucasmourao.baobhapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmourao.baobhapi.dto.CompletePlaceDTO;
import com.lucasmourao.baobhapi.dto.SimplePlaceDTO;
import com.lucasmourao.baobhapi.entities.Place;
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
	
	@PostMapping
	public ResponseEntity<Place> insert(@RequestBody Place place){
		place = placeService.insert(place);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(place.getId()).toUri();
		return ResponseEntity.created(uri).body(place);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		placeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Place> update(@PathVariable Long id, @RequestBody Place place){
		place = placeService.update(id, place);
		return ResponseEntity.ok().body(place);
	}
	
}
