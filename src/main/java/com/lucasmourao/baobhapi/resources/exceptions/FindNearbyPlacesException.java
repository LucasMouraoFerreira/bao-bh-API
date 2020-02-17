package com.lucasmourao.baobhapi.resources.exceptions;

public class FindNearbyPlacesException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public FindNearbyPlacesException(String msg) {
		super(msg);
	}
}
