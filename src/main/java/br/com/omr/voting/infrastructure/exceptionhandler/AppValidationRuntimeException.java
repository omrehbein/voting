package br.com.omr.voting.infrastructure.exceptionhandler;

public class AppValidationRuntimeException extends RuntimeException {

	public AppValidationRuntimeException(String error) 
	{
		super(error);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 499398346088685252L;

}
