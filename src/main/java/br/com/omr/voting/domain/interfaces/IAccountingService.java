package br.com.omr.voting.domain.interfaces;

import br.com.omr.voting.infrastructure.entity.VotingSession;

public interface IAccountingService {
	public VotingSession compute(int agendaId);
}
