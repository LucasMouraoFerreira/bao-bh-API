package com.lucasmourao.baobhapi.services.exceptions;

public class UpdateAvgRatingException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UpdateAvgRatingException() {
		super("Rating value must be between 0.0 and 5.0");
	}
}
