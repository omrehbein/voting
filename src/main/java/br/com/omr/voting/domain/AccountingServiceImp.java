package br.com.omr.voting.domain;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.omr.voting.domain.exceptions.VotingSessionWasNotCreatedForAgendaRuntimeException;
import br.com.omr.voting.domain.interfaces.IAccountingService;
import br.com.omr.voting.infrastructure.entity.Vote;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IVoteRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@Service
public class AccountingServiceImp implements IAccountingService {

	private final IVotingSessionRepository votingSessionRepository;
	private final IVoteRepository voteRepository;
	private static final Logger LOGGER = Logger.getLogger( AccountingServiceImp.class.getName() );
	
	public AccountingServiceImp(IVotingSessionRepository votingSessionRepository, IVoteRepository voteRepository)
	{
		this.votingSessionRepository = votingSessionRepository;
		this.voteRepository = voteRepository;
	}
	
	@Override
	public VotingSession compute(int agendaId) {
		LOGGER.info("Called compute");
		
		VotingSession votingSession = this.votingSessionRepository.findOneByAgendaId(agendaId)
		    .orElseThrow(() -> new VotingSessionWasNotCreatedForAgendaRuntimeException(agendaId));
		
		List<Vote> votes = this.voteRepository.findAllByVotingSessionId(votingSession.getId());
		
		votingSession.setVotesComputed(votes.size());
		votingSession.setAgreeVotesComputed( votes.stream().filter(v -> v.isAgree()).count() );
		votingSession.setDisagreeVotesComputed( votes.stream().filter(v -> !v.isAgree()).count() );
		
		this.votingSessionRepository.save(votingSession);
		
		return votingSession;
		
	}

}
