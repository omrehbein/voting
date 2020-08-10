package br.com.omr.voting.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.omr.voting.domain.interfaces.IAgendaService;
import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.VotingSession;
import br.com.omr.voting.infrastructure.repository.interfaces.IAgendaRepository;
import br.com.omr.voting.infrastructure.repository.interfaces.IVotingSessionRepository;

@Service
public class AgendaServiceImp implements IAgendaService{

	private final IAgendaRepository agendaRepository;
	private final IVotingSessionRepository votingSessionRepository;
	
	public AgendaServiceImp(IAgendaRepository agendaRepository, IVotingSessionRepository votingSessionRepository)
	{
		this.agendaRepository = agendaRepository;
		this.votingSessionRepository = votingSessionRepository;
	}
	
	@Override
	public Agenda createAgenda(String description) {	
		Agenda agenda = new Agenda();
		agenda.setDescription(description);
		this.agendaRepository.save(agenda);
		return agenda;
	}

	@Override
	public VotingSession createVotingSession(int agendaId, int timeInMinute) {
		
		
		VotingSession votingSession = this.votingSessionRepository.findOneByAgendaId(agendaId);
		
		if (votingSession != null){
			throw new RuntimeException(String.format("Not allowed to create more than 1 VotingSession by Agenda %s", agendaId));
		}
		
		Agenda agenda = this.agendaRepository.findById(agendaId).get();
		
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MINUTE, timeInMinute);
		
		
		votingSession = new VotingSession();
		votingSession.setAgenda(agenda);
		votingSession.setStartSession(now);
		votingSession.setEndSession(calendar.getTime());
		
		this.votingSessionRepository.save(votingSession);
		return votingSession;
	}

	@Override
	public List<Agenda> getAgendas() {
		return this.agendaRepository.findAll();
	}



}
