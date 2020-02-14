package com.lucasmourao.baobhapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmourao.baobhapi.entities.Comment;
import com.lucasmourao.baobhapi.repositories.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public List<Comment> findAll(){
		return commentRepository.findAll();
	}
	
	public Comment findById(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		return comment.get(); 
	}
	
}