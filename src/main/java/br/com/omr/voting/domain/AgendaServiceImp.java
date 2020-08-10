package br.com.omr.voting.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.omr.voting.domain.exceptions.NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException;
import br.com.omr.voting.domain.interfaces.IAgendaService;
import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IAgendaRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@Service
public class AgendaServiceImp implements IAgendaService{

	private final IAgendaRepository agendaRepository;
	private final IVotingSessionRepository votingSessionRepository;
	private static final Logger LOGGER = Logger.getLogger( AgendaServiceImp.class.getName() );
	
	public AgendaServiceImp(IAgendaRepository agendaRepository, IVotingSessionRepository votingSessionRepository)
	{
		this.agendaRepository = agendaRepository;
		this.votingSessionRepository = votingSessionRepository;
	}
	
	@Override
	public Agenda createAgenda(String description) {	
		LOGGER.log(Level.INFO, "Called createAgenda -> params: description {}", new Object[]{ description });
		
		Agenda agenda = new Agenda();
		agenda.setDescription(description);
		this.agendaRepository.save(agenda);
		return agenda;
	}

	@Override
	public VotingSession createVotingSession(int agendaId, int timeInMinute) {
		LOGGER.log(Level.INFO, "Called createVotingSession -> params: agendaId {} timeInMinute {}", new Object[]{ agendaId, timeInMinute });
		
		Optional<VotingSession> opVotingSession = this.votingSessionRepository.findOneByAgendaId(agendaId);
				
		if (opVotingSession.isPresent()) {
			throw new NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException(agendaId);
		}
		
		Agenda agenda = this.agendaRepository.findById(agendaId).get();
		
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MINUTE, timeInMinute);
		
		
		VotingSession votingSession = new VotingSession();
		votingSession.setAgenda(agenda);
		votingSession.setStartSession(now);
		votingSession.setEndSession(calendar.getTime());
		
		this.votingSessionRepository.save(votingSession);
		return votingSession;
	}

	@Override
	public List<Agenda> getAgendas() {
		LOGGER.log(Level.INFO, "Called getAgendas -> params:");
		return this.agendaRepository.findAll();
	}



}
