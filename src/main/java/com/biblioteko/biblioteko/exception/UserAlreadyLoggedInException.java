package com.biblioteko.biblioteko.exception;

public class UserAlreadyLoggedInException extends Exception {

	public UserAlreadyLoggedInException() { }

	public UserAlreadyLoggedInException(String message) {
		super(message);
	}

}
