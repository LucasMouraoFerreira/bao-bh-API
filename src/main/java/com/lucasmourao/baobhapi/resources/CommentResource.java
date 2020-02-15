package com.lucasmourao.baobhapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmourao.baobhapi.dto.CommentDTO;
import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.services.CommentService;
import com.lucasmourao.baobhapi.services.PlaceService;;

@RestController
@RequestMapping(value = "/comments")
public class CommentResource {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PlaceService placeService;
	
	@GetMapping
	public ResponseEntity<List<CommentDTO>> findAll(){
		List<CommentDTO> list = commentService.findAll().stream().map(x->new CommentDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<CommentDTO> findById(@PathVariable Long id){
		CommentDTO comment = new CommentDTO(commentService.findById(id));
		return ResponseEntity.ok().body(comment);
	}
	
	@PostMapping(value="/{place_id}")
	public ResponseEntity<CommentDTO> insert(@PathVariable Long place_id, @RequestBody Comment comment){
		Place place = placeService.findById(place_id);
		comment = commentService.insert(comment, place);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
		return ResponseEntity.created(uri).body(new CommentDTO(comment));
	}
}
