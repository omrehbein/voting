package br.com.omr.voting.domain.exceptions;

import br.com.omr.voting.infrastructure.exceptionhandler.AppValidationRuntimeException;

public class AlreadyExistsVoteForAgendaAndCpfRuntimeException extends AppValidationRuntimeException {

	private static final long serialVersionUID = 1L;

	
	public AlreadyExistsVoteForAgendaAndCpfRuntimeException(int agendaId, String cpf)
	{
		super(String.format("Already exists a vote for Agenda %s and Cpf %s", agendaId, cpf ));
	}
}
