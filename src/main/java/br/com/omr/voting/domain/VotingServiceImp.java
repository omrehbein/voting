package br.com.omr.voting.domain;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.omr.voting.domain.exceptions.AlreadyExistsVoteForAgendaAndCpfRuntimeException;
import br.com.omr.voting.domain.exceptions.AgendasVotingSessionIsOutOfIntervalRangeRuntimeException;
import br.com.omr.voting.domain.exceptions.VotingSessionWasNotCreatedForAgendaRuntimeException;
import br.com.omr.voting.domain.interfaces.IVotingService;
import br.com.omr.voting.infrastructure.entity.Vote;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IVoteRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@Service
public class VotingServiceImp implements IVotingService {

	private final IVotingSessionRepository votingSessionRepository;
	private final IVoteRepository voteRepository;
	private static final Logger LOGGER = Logger.getLogger( VotingServiceImp.class.getName() );

	public VotingServiceImp(IVotingSessionRepository votingSessionRepository, IVoteRepository voteRepository)
	{
		this.votingSessionRepository = votingSessionRepository;
		this.voteRepository = voteRepository;
	}
	
	
	private void validateVotingSessionInterval(int agendaId, VotingSession votingSession) 
	{
		Date now = new Date();
		if (
			!(
				now.after(votingSession.getStartSession()) && 
				now.before(votingSession.getEndSession())
			)
		) {
			throw new AgendasVotingSessionIsOutOfIntervalRangeRuntimeException(votingSession.getStartSession(), votingSession.getEndSession());
		}
	}
	
	private void validateVotingSessionSameCpf(int agendaId, VotingSession votingSession, String cpf) 
	{
		Optional<Vote> votes = this.voteRepository.findAllByVotingSessionIdAndCpf(votingSession.getId(), cpf);
		
		if (votes.isPresent()) {
			throw new AlreadyExistsVoteForAgendaAndCpfRuntimeException(agendaId, cpf);
		}
	}
	
	@Override
	public Vote vote(int agendaId, String cpf, boolean agree) {
		LOGGER.info("Called vote");
		
		VotingSession votingSession = this.votingSessionRepository.findOneByAgendaId(agendaId)
			    .orElseThrow(() -> new VotingSessionWasNotCreatedForAgendaRuntimeException(agendaId));
		
		this.validateVotingSessionInterval(agendaId, votingSession);
		this.validateVotingSessionSameCpf(agendaId, votingSession, cpf); 

		Vote vote = new Vote();
		vote.setVotingSession(votingSession);
		vote.setCpf(cpf);
		vote.setAgree(agree);
		
		this.voteRepository.save(vote);
		
		return vote;
		
	}

}
