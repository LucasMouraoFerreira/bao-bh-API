package com.lucasmourao.baobhapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucasmourao.baobhapi.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query("SELECT c FROM Comment c where UPPER(c.author) like UPPER(concat('%', :text,'%'))")
	Page<Comment> findByAuthorName(@Param("text") String text, Pageable pageable);
}
