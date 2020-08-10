package br.com.omr.voting.domain.exceptions;

public class VotingSessionWasNotCreatedForAgendaRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public VotingSessionWasNotCreatedForAgendaRuntimeException(int agendaId)
	{
		super(String.format("VotingSession was not created for agenda %s", agendaId ));
	}
}
