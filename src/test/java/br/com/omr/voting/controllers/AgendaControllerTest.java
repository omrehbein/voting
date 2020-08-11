package br.com.omr.voting.controllers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import io.restassured.http.ContentType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.omr.voting.domain.interfaces.IAccountingService;
import br.com.omr.voting.domain.interfaces.IAgendaService;
import br.com.omr.voting.domain.interfaces.IVotingService;
import br.com.omr.voting.dto.AgendaDto;
import br.com.omr.voting.infrastructure.entity.Agenda;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class AgendaControllerTest {
	//@InjectMocks
	AgendaController agendaController;
    
	@Mock
    IVotingService votingService;
	@Mock
	IAccountingService accountingService;
	@Mock
	IAgendaService agendaService;
	
	//@Autowired
	ModelMapper modelMapper = new ModelMapper();
	
	@BeforeEach
	public void setup() {
		this.agendaController = new AgendaController(this.votingService, this.agendaService, this.accountingService, this.modelMapper);
		//standaloneSetup(this.agendaController);
	}
    
	@Test
    public void testAddEmployee() 
    {
		//Arrange
		String agendaDescription = "Will we dream together?";
		
		Agenda agenda = new Agenda();
		agenda.setId(1);
		agenda.setDescription(agendaDescription);
			
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        when(agendaService.createAgenda(agendaDescription)).thenReturn(agenda);
        
        //Act
        AgendaDto agendaDto = agendaController.createAgenda(agendaDescription);
        /*given()
		.accept(ContentType.JSON)
		.when()
			.get("/criar")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
        */
        
        //Assert
        assertThat(agendaDto.getId()).isEqualTo(agenda.getId());
        assertThat(agendaDto.getId()).isEqualTo(agenda.getId());
        

    }
}
