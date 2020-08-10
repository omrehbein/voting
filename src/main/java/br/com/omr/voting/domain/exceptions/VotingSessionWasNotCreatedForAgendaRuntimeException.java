package br.com.omr.voting.domain.exceptions;

import br.com.omr.voting.infrastructure.exceptionhandler.AppValidationRuntimeException;

public class VotingSessionWasNotCreatedForAgendaRuntimeException extends AppValidationRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public VotingSessionWasNotCreatedForAgendaRuntimeException(int agendaId)
	{
		super(String.format("VotingSession was not created for agenda %s", agendaId ));
	}
}
