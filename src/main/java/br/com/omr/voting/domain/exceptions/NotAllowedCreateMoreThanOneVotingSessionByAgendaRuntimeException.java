package br.com.omr.voting.domain.exceptions;

public class NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public NotAllowedCreateMoreThanOneVotingSessionByAgendaRuntimeException(int agendaId)
	{
		super(String.format("Not allowed to create more than 1 VotingSession by Agenda %s", agendaId));
	}
}
