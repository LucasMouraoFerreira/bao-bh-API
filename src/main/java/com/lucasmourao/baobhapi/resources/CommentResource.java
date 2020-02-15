package com.lucasmourao.baobhapi.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.baobhapi.dto.CommentDTO;
import com.lucasmourao.baobhapi.services.CommentService;;

@RestController
@RequestMapping(value = "/comments")
public class CommentResource {

	@Autowired
	private CommentService commentService;
	
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
}
