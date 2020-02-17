package com.lucasmourao.baobhapi.resources.exceptions;

public class GeocodingApiCallException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public GeocodingApiCallException(String msg) {
		super(msg);
	}
}
