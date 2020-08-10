package br.com.omr.voting.infrastructure.exceptionhandler;

public class ErrorReturn {
	
	public ErrorReturn() 
	{
		this.isAppValidationError = false;
		this.message = "";
	}
	
	boolean isAppValidationError;
	
	private String message;

	public boolean isAppValidationError() {
		return isAppValidationError;
	}

	public void setAppValidationError(boolean isAppValidationError) {
		this.isAppValidationError = isAppValidationError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
