package br.com.omr.voting.domain.exceptions;

import org.springframework.http.HttpStatus;

import br.com.omr.voting.infrastructure.exceptionhandler.AppValidationRuntimeException;

public class AgendaNotFoundRuntimeException extends AppValidationRuntimeException {

	private static final long serialVersionUID = 1L;


	public AgendaNotFoundRuntimeException(int agendaId)
	{
		super(String.format("Agenda (%s) not found ", agendaId ), HttpStatus.NOT_FOUND);
	}
}
