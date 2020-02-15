package com.lucasmourao.baobhapi.services.exceptions;

public class CommentInsertException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CommentInsertException() {
		super("Comment must contain at least the name of the author and the text or the rating");
	}
}
