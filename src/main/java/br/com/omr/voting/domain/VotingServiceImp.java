package br.com.omr.voting.domain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.omr.voting.domain.interfaces.IVotingService;
import br.com.omr.voting.infrastructure.entity.Vote;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.log.ILog;
import br.com.omr.voting.infrastructure.repository.interfaces.IVoteRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;


@Service
public class VotingServiceImp implements IVotingService {

	private final IVotingSessionRepository votingSessionRepository;
	private final IVoteRepository voteRepository;
	private final ILog log;

	public VotingServiceImp(IVotingSessionRepository votingSessionRepository, IVoteRepository voteRepository, ILog log)
	{
		this.votingSessionRepository = votingSessionRepository;
		this.voteRepository = voteRepository;
		this.log = log;
	}
	
	@Override
	public Vote vote(int agendaId, String cpf, boolean agree) {
		this.log.info("Call vote agendaId {} cpf {} agree {}", agendaId, cpf, agree );
		
		VotingSession votingSession = this.votingSessionRepository.findOneByAgendaId(agendaId);
		
		Date now = new Date();
		if (votingSession == null) {
			throw new RuntimeException(String.format("VotingSession was not created for agenda %s", agendaId ));
		} else if (
				!(
				now.after(votingSession.getStartSession()) && 
				now.before(votingSession.getEndSession())
				)
		) {
			throw new RuntimeException(String.format("VotingSession is Finished to vote for Agenda %s", agendaId ));
		}
		
		List<Vote> votes = this.voteRepository.findAllByVotingSessionIdAndCpf(votingSession.getId(), cpf);
		
		if (votes.size() != 0){
			throw new RuntimeException(String.format("Already exists a vote for Agenda %s and Cpf %s", agendaId, cpf ));
		}
		
		Vote vote = new Vote();
		vote.setVotingSession(votingSession);
		vote.setCpf(cpf);
		vote.setAgree(agree);
		
		this.voteRepository.save(vote);
		
		return vote;
		
	}



}
