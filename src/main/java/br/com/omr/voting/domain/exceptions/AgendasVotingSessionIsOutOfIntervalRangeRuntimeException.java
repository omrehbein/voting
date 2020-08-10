package br.com.omr.voting.domain.exceptions;

import java.util.Date;

public class AgendasVotingSessionIsOutOfIntervalRangeRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public AgendasVotingSessionIsOutOfIntervalRangeRuntimeException(Date start, Date end)
	{
		super(String.format("Agenda's VotingSession is out of interval range (%s and %s) ", start, end ));
	}
}
