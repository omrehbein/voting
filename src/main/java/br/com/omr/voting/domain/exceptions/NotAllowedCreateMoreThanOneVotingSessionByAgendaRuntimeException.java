package br.com.omr.voting.domain.exceptions;

import br.com.omr.voting.infrastructure.exceptionhandler.AppValidationRuntimeException;

public class NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException extends AppValidationRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException(int agendaId)
	{
		super(String.format("Not allowed to create more than 1 VotingSession by Agenda %s", agendaId));
	}
}
