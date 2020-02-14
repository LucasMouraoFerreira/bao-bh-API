package com.lucasmourao.baobhapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasmourao.baobhapi.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
		
}
