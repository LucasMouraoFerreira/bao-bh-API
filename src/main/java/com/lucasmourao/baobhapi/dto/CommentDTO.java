package com.lucasmourao.baobhapi.dto;

import java.io.Serializable;
import java.time.Instant;

import com.lucasmourao.baobhapi.entities.Comment;

public class CommentDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String author;
	private String text;
	private String placeName;
	private Instant moment;
	private Double rating;
	
	public CommentDTO() {}
	
	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.author = comment.getAuthor();
		this.text = comment.getText();
		this.moment = comment.getMoment();
		this.placeName = comment.getPlace().getName();
		this.rating = comment.getRating();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	
	
}
