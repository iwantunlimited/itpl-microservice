package io.itpl.microservice.exceptions;

public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5406959854102197594L;

	public ApiException(String message) {
		super(message);
	}
}
