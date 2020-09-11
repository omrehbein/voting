package br.com.omr.voting.infrastructure.exceptionhandler;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;

public class AppValidationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 499398346088685252L;
	
	private static final Logger LOGGER = Logger.getLogger( AppValidationRuntimeException.class.getName() );

	private static HttpStatus httpStatusResponse;
	
	public AppValidationRuntimeException(String error) 
	{
		super(error);
		this.httpStatusResponse = HttpStatus.INTERNAL_SERVER_ERROR;
		LOGGER.severe(this.getMessage());	
	}
	
	public AppValidationRuntimeException(String error, HttpStatus httpStatusResponse) 
	{
		super(error);
		this.httpStatusResponse = httpStatusResponse;
		LOGGER.severe(this.getMessage());	
	}

	public HttpStatus getHttpStatusResponse() {
		return this.httpStatusResponse;
	}
	
	

}
