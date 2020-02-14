package com.lucasmourao.baobhapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.services.CommentService;;

@RestController
@RequestMapping(value = "/comments")
public class CommentResource {

	@Autowired
	private CommentService commentService;
	
	@GetMapping
	public ResponseEntity<List<Comment>> findAll(){
		List<Comment> list = commentService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Comment> findById(@PathVariable Long id){
		Comment comment = commentService.findById(id);
		return ResponseEntity.ok().body(comment);
	}
}
