package com.lucasmourao.baobhapi.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.entities.Place;
import com.lucasmourao.baobhapi.repositories.CommentRepository;
import com.lucasmourao.baobhapi.services.exceptions.CommentInsertException;
import com.lucasmourao.baobhapi.services.exceptions.ResourceNotFoundException;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PlaceService placeService;

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment findById(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		return comment.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Comment insert(Comment comment, Place place) {
		if (comment.getAuthor() == null || (comment.getRating() == null && comment.getText() == null)) {
			throw new CommentInsertException();
		}
		placeService.newAvgRating(comment, place);
		Comment aux = new Comment(null, Instant.now(), comment.getText(), comment.getAuthor(), place,
				comment.getRating());
		return commentRepository.save(aux);
	}

}
