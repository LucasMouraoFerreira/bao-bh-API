package com.lucasmourao.baobhapi.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lucasmourao.baobhapi.dto.CommentDTO;
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

	public Page<CommentDTO> findAll(Pageable pageable) {
		return commentRepository.findAll(pageable).map(x->new CommentDTO(x));
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

	public void delete(Long id) {
		try {
			Optional<Comment> comment = commentRepository.findById(id);
			placeService.deletedCommentUpdateAvgRating(comment.orElseThrow(() -> new ResourceNotFoundException(id)),
					comment.orElseThrow(() -> new ResourceNotFoundException(id)).getPlace());
			commentRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public Comment update(Long id, Comment comment) {
		Optional<Comment> entity = commentRepository.findById(id);
		updateData(entity.orElseThrow(() -> new ResourceNotFoundException(id)), comment);
		return commentRepository.save(entity.get());
	}

	private void updateData(Comment entity, Comment comment) {
		if (comment.getAuthor() != null) {
			entity.setAuthor(comment.getAuthor());
		}
		if (comment.getText() != null) {
			entity.setText(comment.getText());
		}
		if (comment.getRating() != null) {
			if (entity.getRating() == null) {
				placeService.newAvgRating(comment, entity.getPlace());
			} else {
				placeService.updateAvgRating(comment, entity, entity.getPlace());
			}
			entity.setRating(comment.getRating());
		}
	}

	public Page<CommentDTO> findByAuthorName(String author, Pageable pageable) {
		return commentRepository.findByAuthorName(author, pageable).map(x -> new CommentDTO(x));
	}

}
