package com.example.server.exception;

public class ValidationException extends RuntimeException {

	public ValidationException(String message) {
		super(message);
	}
}
