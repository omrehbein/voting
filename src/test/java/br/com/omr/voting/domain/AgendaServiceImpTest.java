package br.com.omr.voting.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.Validate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import br.com.omr.voting.domain.exceptions.NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException;
import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IAgendaRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class AgendaServiceImpTest {

	private AgendaServiceImp agendaServiceImp;
	
	@Mock
	private IAgendaRepository agendaRepository;
	@Mock
	private IVotingSessionRepository votingSessionRepository;
	
	
	@BeforeEach
	public void setup() {
		this.agendaServiceImp = new AgendaServiceImp(this.agendaRepository, this.votingSessionRepository);
	}
	
	@Test
    public void createAgenda_InsertNewAgendaByNameTest() 
    {
		//Arrange
		String agendaDescription = "Will we dream together?";
		
		//Act
		Agenda agendaReturned = this.agendaServiceImp.createAgenda(agendaDescription);
		
		//Assert
		assertThat(agendaReturned.getDescription()).isEqualTo(agendaDescription);
		verify(this.agendaRepository, Mockito.times(1)).save(agendaReturned);
    }
	
	@Test
    public void createVotingSession_InsertNewVotingSessionWhenDoesNotExits() 
    {
		//Arrange
		int agendaId = 1; 
		int timeInMinute = 60;
		String agendaDescription = "Will we dream together?";
		
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		agenda.setDescription(agendaDescription);
		
		VotingSession votingSession = null;
		Optional<VotingSession> opVotingSession = Optional.ofNullable(votingSession);
		
		when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(opVotingSession);
		when(this.agendaRepository.findById(agendaId)).thenReturn(Optional.of(agenda));
		
		//Act
		VotingSession votingSessionReturned = this.agendaServiceImp.createVotingSession(agendaId, timeInMinute);
		
		//Assert
		long diffInMillies = Math.abs(votingSessionReturned.getStartSession().getTime() - votingSessionReturned.getEndSession().getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		assertThat(votingSessionReturned.getAgenda()).isEqualTo(agenda);
		assertThat(diff).isEqualTo(timeInMinute);
		
		verify(this.agendaRepository, Mockito.times(1)).findById(agendaId);
		verify(this.votingSessionRepository, Mockito.times(1)).save(votingSessionReturned);
    }
	
	@Test
    public void createVotingSession_InsertNewVotingSessionWhenAlreadyExits() 
    {
		//Arrange
		int agendaId = 1; 
		int timeInMinute = 60;
		String agendaDescription = "Will we dream together?";
		
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		agenda.setDescription(agendaDescription);
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setAgenda(agenda);
		Optional<VotingSession> opVotingSession = Optional.ofNullable(votingSession);
		
		when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(opVotingSession);
		
		//Act
		Exception ex = null;
        try {
        	this.agendaServiceImp.createVotingSession(agendaId, timeInMinute);
        } catch (Exception e) {
			ex = e;
		}
        Validate.isTrue(ex instanceof NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException);
		
		//Assert
		verify(this.agendaRepository, Mockito.times(0)).findById(agendaId);
    }
	
	
	@Test
    public void getAgendas() 
    {
		//Arrange
		
		//Act
		this.agendaServiceImp.getAgendas();
		
		//Assert
		verify(this.agendaRepository, Mockito.times(1)).findAll();
    }
}
