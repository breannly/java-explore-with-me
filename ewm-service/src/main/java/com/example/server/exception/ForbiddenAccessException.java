package com.example.server.exception;

public class ForbiddenAccessException extends RuntimeException {

	public ForbiddenAccessException(String message) {
		super(message);
	}
}
