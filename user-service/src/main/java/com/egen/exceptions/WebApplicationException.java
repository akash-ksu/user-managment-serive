package com.egen.exceptions;

import org.springframework.http.HttpStatus;

public class WebApplicationException extends RuntimeException {

	private String message;
	private HttpStatus status;

	public WebApplicationException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
