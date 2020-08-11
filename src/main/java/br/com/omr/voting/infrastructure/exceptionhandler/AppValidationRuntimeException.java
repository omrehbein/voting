package br.com.omr.voting.infrastructure.exceptionhandler;

import java.util.logging.Logger;

public class AppValidationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 499398346088685252L;
	
	private static final Logger LOGGER = Logger.getLogger( AppValidationRuntimeException.class.getName() );
	
	public AppValidationRuntimeException(String error) 
	{
		super(error);
		LOGGER.severe(this.getMessage());	
	}

}
