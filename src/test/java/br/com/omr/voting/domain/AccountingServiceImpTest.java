package br.com.omr.voting.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import br.com.omr.voting.domain.exceptions.VotingSessionWasNotCreatedForAgendaRuntimeException;
import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.Vote;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IVoteRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AccountingServiceImpTest {

	private AccountingServiceImp accountingServiceImp;
	
	@Mock
	private IVotingSessionRepository votingSessionRepository;
	@Mock
	private IVoteRepository voteRepository;
	
	
	@BeforeEach
	public void setup() {
		this.accountingServiceImp = new AccountingServiceImp(this.votingSessionRepository, this.voteRepository);
	}
	
	@Test
    void compute_CoutAndValidateVotesTest() 
    {
		//Arrange
		int agendaId = 1;
		Agenda agenda = new Agenda();
		agenda.setId(agendaId);
		
		VotingSession votingSession = new VotingSession();
		votingSession.setId(1);
		votingSession.setAgenda(agenda);
		
		Vote vote1 = new Vote();
		vote1.setId(1);
		vote1.setAgree(true);
		vote1.setCpf("00000000");
		vote1.setVotingSession(votingSession);
		
		Vote vote2 = new Vote();
		vote2.setId(2);
		vote2.setAgree(true);
		vote2.setCpf("00000001");
		vote2.setVotingSession(votingSession);
		
		Vote vote3 = new Vote();
		vote3.setId(3);
		vote3.setAgree(false);
		vote3.setCpf("00000003");
		vote3.setVotingSession(votingSession);
        
		votingSession.setVotes(Arrays.asList(vote1, vote2, vote3));
		
        when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(Optional.of(votingSession));
        when(this.voteRepository.findAllByVotingSessionId(votingSession.getId())).thenReturn(Arrays.asList(vote1, vote2, vote3));
        
        //Act
        VotingSession votingSessionReturned = this.accountingServiceImp.compute(agendaId);
        
        
        //Assert
        assertThat(votingSessionReturned.getId()).isEqualTo(votingSession.getId());
        assertThat(votingSessionReturned.getVotesComputed()).isEqualTo(3);
        assertThat(votingSessionReturned.getAgreeVotesComputed()).isEqualTo(2);
        assertThat(votingSessionReturned.getDisagreeVotesComputed()).isEqualTo(1);

        verify(this.votingSessionRepository, Mockito.times(1)).findOneByAgendaId(agendaId);
        verify(this.votingSessionRepository, Mockito.times(1)).save(votingSession);
        verify(this.voteRepository, Mockito.times(1)).findAllByVotingSessionId(votingSession.getId());
    }
	
	@Test
    void compute_AgendSessionDoesNotExitsFail() 
    {
		//Arrange
		int agendaId = 1;
		VotingSession votingSession = null;
		Optional<VotingSession> op = Optional.ofNullable(votingSession);
        when(this.votingSessionRepository.findOneByAgendaId(agendaId)).thenReturn(op);
        
        //Act
        Exception ex = null;
        try {
        	this.accountingServiceImp.compute(agendaId);
        } catch (Exception e) {
			ex = e;
		}
        
        //Assert
        assertTrue(ex instanceof VotingSessionWasNotCreatedForAgendaRuntimeException);
        verify(this.votingSessionRepository, Mockito.times(1)).findOneByAgendaId(agendaId);
    }
}
