package br.com.omr.voting.domain.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

import br.com.omr.voting.infrastructure.exceptionhandler.AppValidationRuntimeException;

public class AgendasVotingSessionIsOutOfIntervalRangeRuntimeException extends AppValidationRuntimeException {

	private static final long serialVersionUID = 1L;


	public AgendasVotingSessionIsOutOfIntervalRangeRuntimeException(Date start, Date end)
	{
		super(String.format("Agenda's VotingSession is out of interval range (%s and %s) ", start, end ), HttpStatus.BAD_REQUEST);
	}
}
