package com.biblioteko.biblioteko.exception;

public class UnauthenticatedUserException extends Exception {

	public UnauthenticatedUserException() { }

	public UnauthenticatedUserException(String message) {
		super(message);
	}

}
