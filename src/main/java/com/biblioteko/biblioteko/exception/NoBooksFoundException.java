package com.biblioteko.biblioteko.exception;

public class NoBooksFoundException extends Exception {

	public NoBooksFoundException() { }

	public NoBooksFoundException(String message) {
		super(message);
	}

}
