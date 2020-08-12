package br.com.omr.voting.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.omr.voting.domain.interfaces.IAccountingService;
import br.com.omr.voting.domain.interfaces.IAgendaService;
import br.com.omr.voting.domain.interfaces.IVotingService;
import br.com.omr.voting.dto.AgendaDto;
import br.com.omr.voting.dto.VoteDto;
import br.com.omr.voting.dto.VotingSessionDto;
import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.Vote;
import br.com.omr.voting.infrastructure.entity.VotingSession;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AgendaControllerTest {
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
    void createAgendaCall() 
    {
		//Arrange
		String agendaDescription = "Will we dream together?";
		
		Agenda agenda = new Agenda();
		agenda.setId(1);
		agenda.setDescription(agendaDescription);
        
        when(this.agendaService.createAgenda(agendaDescription)).thenReturn(agenda);
        
        //Act
        AgendaDto agendaDto = this.agendaController.createAgenda(agendaDescription);
        
        //Assert
        assertThat(agendaDto.getId()).isEqualTo(agenda.getId());
        assertThat(agendaDto.getDescription()).isEqualTo(agenda.getDescription());
    }
	
	@Test
    void getAgendasCall() 
    {
		//Arrange
		String agendaDescription = "Will we dream together?";
		
		Agenda agenda = new Agenda();
		agenda.setId(1);
		agenda.setDescription(agendaDescription);
		
        when(this.agendaService.getAgendas()).thenReturn(Arrays.asList(agenda));
        
        //Act
        List<AgendaDto> agendaDtoList = this.agendaController.getAgendas();
        
        //Assert
        assertThat(agendaDtoList.size()).isEqualTo(1);
    }
	
	@Test
    void createVotingSessionCall() 
    {
		//Arrange
		int pautaId = 1;
		int tempoEmMinutos = 60;
		
		Agenda agenda = new Agenda();
		agenda.setId(1);
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setStartSession(new Date());
		votingSession.setEndSession(new Date());
		votingSession.setAgenda(agenda);
		
        when(this.agendaService.createVotingSession(pautaId, tempoEmMinutos)).thenReturn(votingSession);
        
        //Act
        VotingSessionDto votingSessionDto = this.agendaController.createVotingSession(pautaId, tempoEmMinutos);
        
        //Assert
        assertThat(votingSessionDto.getId()).isEqualTo(votingSession.getId());
        assertThat(votingSessionDto.getStartSession()).isEqualTo(votingSession.getStartSession());
        assertThat(votingSessionDto.getEndSession()).isEqualTo(votingSession.getEndSession());
        assertThat(votingSessionDto.getAgenda().getId()).isEqualTo(votingSession.getAgenda().getId());
    }
	
	@Test
    void computeCall() 
    {
		//Arrange
		int pautaId = 1;
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setStartSession(new Date());
		votingSession.setEndSession(new Date());
		votingSession.setAgreeVotesComputed(1);
		votingSession.setDisagreeVotesComputed(1);
		votingSession.setVotesComputed(2);		
		
        when(this.accountingService.compute(pautaId)).thenReturn(votingSession);
        
        //Act
        VotingSessionDto votingSessionDto = this.agendaController.compute(pautaId);
        
        //Assert
        assertThat(votingSessionDto.getId()).isEqualTo(votingSession.getId());
        assertThat(votingSessionDto.getStartSession()).isEqualTo(votingSession.getStartSession());
        assertThat(votingSessionDto.getEndSession()).isEqualTo(votingSession.getEndSession());
        assertThat(votingSessionDto.getAgreeVotesComputed()).isEqualTo(votingSession.getAgreeVotesComputed());
        assertThat(votingSessionDto.getDisagreeVotesComputed()).isEqualTo(votingSession.getDisagreeVotesComputed());
        assertThat(votingSessionDto.getVotesComputed()).isEqualTo(votingSession.getVotesComputed());
    }
	
	@Test
    void voteCall() 
    {
		//Arrange
		int agendaId = 1;
		String cpf = "00000001";
		boolean agree = true;
		
		Vote vote = new Vote();
		vote.setId(1);
		vote.setCpf(cpf);
		vote.setAgree(agree);
		
        when(this.votingService.vote(agendaId, cpf, agree)).thenReturn(vote);
        
        //Act
        VoteDto voteDto = this.agendaController.vote(agendaId, cpf, agree);
        
        //Assert
        assertThat(voteDto.getId()).isEqualTo(vote.getId());
        assertThat(voteDto.getCpf()).isEqualTo(vote.getCpf());
        assertThat(voteDto.isAgree()).isEqualTo(vote.isAgree());
    }
}
