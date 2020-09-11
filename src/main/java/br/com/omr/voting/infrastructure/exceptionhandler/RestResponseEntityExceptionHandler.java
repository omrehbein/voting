package br.com.omr.voting.infrastructure.exceptionhandler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
	
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    	ErrorReturn errorReturn = new ErrorReturn();
    	
    	errorReturn.setAppValidationError(ex instanceof AppValidationRuntimeException);
    	
    	errorReturn.setMessage(ex.getMessage());
    	
    	HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    	if(errorReturn.isAppValidationError()) {
    		status = ((AppValidationRuntimeException)ex).getHttpStatusResponse();   		
    	} 
    	
    	return handleExceptionInternal(ex, errorReturn, new HttpHeaders(), status, request);
    }
}
