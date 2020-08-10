package br.com.omr.voting.domain.interfaces;

import java.util.List;

import br.com.omr.voting.infrastructure.entity.Agenda;
import br.com.omr.voting.infrastructure.entity.VotingSession;

public interface IAgendaService {
	
	public Agenda createAgenda(String description);
	
	public VotingSession createVotingSession(int agendaId, int timeInMinute);

	public List<Agenda> getAgendas();
}
