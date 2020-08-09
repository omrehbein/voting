package br.com.omr.voting.domain.interfaces;

import br.com.omr.voting.infrastructure.entity.Vote;

public interface IVotingService {
	
	public Vote vote(int agendaId, String cpf, boolean agree);
	
}
