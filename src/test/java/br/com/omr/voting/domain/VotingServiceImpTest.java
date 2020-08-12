package br.com.omr.voting.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.omr.voting.domain.exceptions.AgendasVotingSessionIsOutOfIntervalRangeRuntimeException;
import br.com.omr.voting.domain.exceptions.AlreadyExistsVoteForAgendaAndCpfRuntimeException;
import br.com.omr.voting.domain.exceptions.VotingSessionWasNotCreatedForAgendaRuntimeException;
import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.Vote;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IVoteRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class VotingServiceImpTest {
	
	private VotingServiceImp votingServiceImp;
	
	@Mock
	private IVotingSessionRepository votingSessionRepository;
	@Mock
	private IVoteRepository voteRepository;
	
	
	@BeforeEach
	public void setup() {
		this.votingServiceImp = new VotingServiceImp(this.votingSessionRepository, this.voteRepository);
	}
	
	@Test
    public void vote_ExecuteSucessAgreeVote() 
    {
		//Arrange
		int agendaId = 1;
		String cpf = "0000000";
		boolean agree = true;
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setAgenda(agenda);
		{
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, -60);
			votingSession.setStartSession(calendar.getTime());
			calendar.add(Calendar.MINUTE, 120);
			votingSession.setEndSession(calendar.getTime());
		}
		
		Vote cpfOnSessionVote = null;
		Optional<Vote> opCpfOnSessionVote = Optional.ofNullable(cpfOnSessionVote);
		
		when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(Optional.of(votingSession));
		when(this.voteRepository.findAllByVotingSessionIdAndCpf(votingSession.getId(), cpf)).thenReturn(opCpfOnSessionVote);
		
		//Act
		Vote voteReturned = this.votingServiceImp.vote(agendaId, cpf, agree);
		
		//Assert
		assertThat(voteReturned.getVotingSession()).isEqualTo(votingSession);
		assertThat(voteReturned.getCpf()).isEqualTo(cpf);
		assertThat(voteReturned.isAgree()).isEqualTo(agree);
		
		verify(this.votingSessionRepository, Mockito.times(1)).findOneByAgendaId(agendaId);
		verify(this.voteRepository, Mockito.times(1)).findAllByVotingSessionIdAndCpf(votingSession.getId(), cpf);
		verify(this.voteRepository, Mockito.times(1)).save(voteReturned);
		
    }
	
	@Test
    public void vote_VotingSessionWasNotCreatedForAgenda() 
    {
		//Arrange
		int agendaId = 1;
		String cpf = "0000000";
		boolean agree = true;
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		
		VotingSession votingSession = null;

		when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(Optional.ofNullable(votingSession));

		//Act
        Exception ex = null;
        try {
        	this.votingServiceImp.vote(agendaId, cpf, agree);
        } catch (Exception e) {
			ex = e;
		}
        
		//Assert
        assertTrue(ex instanceof VotingSessionWasNotCreatedForAgendaRuntimeException); 
    }
	
	@Test
    public void vote_AgendasVotingSessionIsOutOfIntervalRange() 
    {
		//Arrange
		int agendaId = 1;
		String cpf = "0000000";
		boolean agree = true;
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setAgenda(agenda);
		{
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, -120);
			votingSession.setStartSession(calendar.getTime());
			calendar.add(Calendar.MINUTE, -60);
			votingSession.setEndSession(calendar.getTime());
		}
		
		when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(Optional.of(votingSession));
		
		//Act
        Exception ex = null;
        try {
        	this.votingServiceImp.vote(agendaId, cpf, agree);
        } catch (Exception e) {
			ex = e;
		}
        
		//Assert
        assertTrue(ex instanceof AgendasVotingSessionIsOutOfIntervalRangeRuntimeException);
		
    }
	
	@Test
    public void vote_AlreadyExistsVoteForAgendaAndCpf() 
    {
		//Arrange
		int agendaId = 1;
		String cpf = "0000000";
		boolean agree = true;
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setAgenda(agenda);
		{
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MINUTE, -60);
			votingSession.setStartSession(calendar.getTime());
			calendar.add(Calendar.MINUTE, 120);
			votingSession.setEndSession(calendar.getTime());
		}
		
		Vote cpfOnSessionVote = new Vote();
		cpfOnSessionVote.setVotingSession(votingSession);
		cpfOnSessionVote.setCpf(cpf);
		cpfOnSessionVote.setAgree(agree);
		
		Optional<Vote> opCpfOnSessionVote = Optional.ofNullable(cpfOnSessionVote);
		
		when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(Optional.of(votingSession));
		when(this.voteRepository.findAllByVotingSessionIdAndCpf(votingSession.getId(), cpf)).thenReturn(opCpfOnSessionVote);
		
		//Act
        Exception ex = null;
        try {
        	this.votingServiceImp.vote(agendaId, cpf, agree);
        } catch (Exception e) {
			ex = e;
		}
        
		//Assert
        assertTrue(ex instanceof AlreadyExistsVoteForAgendaAndCpfRuntimeException);
		
    }
}
