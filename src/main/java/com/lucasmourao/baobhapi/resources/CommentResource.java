package com.lucasmourao.baobhapi.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasmourao.baobhapi.dto.CommentDTO;
import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.resources.util.URL;
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
	public ResponseEntity<Page<CommentDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<CommentDTO> list = commentService.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CommentDTO> findById(@PathVariable Long id) {
		CommentDTO comment = new CommentDTO(commentService.findById(id));
		return ResponseEntity.ok().body(comment);
	}

	@PostMapping(value = "/addComment/{place_id}")
	public ResponseEntity<CommentDTO> insert(@PathVariable Long place_id, @RequestBody Comment comment) {
		Place place = placeService.findById(place_id);
		comment = commentService.insert(comment, place);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new CommentDTO(comment));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		commentService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CommentDTO> update(@PathVariable Long id, @RequestBody Comment comment) {
		comment = commentService.update(id, comment);
		return ResponseEntity.ok().body(new CommentDTO(comment));
	}

	@GetMapping(value = "/authorsearch")
	public ResponseEntity<Page<CommentDTO>> findByAuthor(
			@RequestParam(value = "author", defaultValue = "") String author,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit) {
		
		author = URL.decodeParam(author);
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<CommentDTO> comments = commentService.findByAuthorName(author, pageable);
		return ResponseEntity.ok().body(comments);
	}

}
