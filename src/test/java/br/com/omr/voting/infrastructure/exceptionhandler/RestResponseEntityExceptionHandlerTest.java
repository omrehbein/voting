package br.com.omr.voting.infrastructure.exceptionhandler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class RestResponseEntityExceptionHandlerTest {

	private RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;
	
	@Mock
	private WebRequest webRequest;
	
	@BeforeEach
	public void setup() {
		this.restResponseEntityExceptionHandler = new RestResponseEntityExceptionHandler();
	}
	
	@Test
    void handleConflict_AnyAppValidationRuntimeException() throws Exception 
    {
		//Arrange
		Exception ex = new AppValidationRuntimeException("AppValidationRuntimeException"); 

		//Act
		ResponseEntity<Object> response = this.restResponseEntityExceptionHandler.handleConflict(ex, webRequest);
		
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(ex.getMessage()).isEqualTo("AppValidationRuntimeException");
    }
	
	@Test
    void handleConflict_UnexpectedException() throws Exception 
    {
		//Arrange
		Exception ex = new RuntimeException("UnexpectedException"); 

		//Act
		ResponseEntity<Object> response = this.restResponseEntityExceptionHandler.handleConflict(ex, webRequest);
		
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(ex.getMessage()).isEqualTo("UnexpectedException");
    }
}
