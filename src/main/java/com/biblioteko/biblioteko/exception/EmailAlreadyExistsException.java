package com.biblioteko.biblioteko.exception;

public class EmailAlreadyExistsException extends Exception {
	
	public EmailAlreadyExistsException() {
		super();
	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
	
	public String message() {
		return super.getMessage();
	}
}
