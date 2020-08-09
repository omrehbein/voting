package br.com.omr.voting.dto;

import java.util.Date;


public class VotingSessionDto {

	private Integer id;
	

	private AgendaDto agenda;


	private Date startSession;	

	private Date endSession;
	
	private long votesComputed;
	private long agreeVotesComputed;	
	private long disagreeVotesComputed;	
	
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AgendaDto getAgenda() {
		return agenda;
	}

	public void setAgenda(AgendaDto agenda) {
		this.agenda = agenda;
	}

	public Date getStartSession() {
		return startSession;
	}

	public void setStartSession(Date startSession) {
		this.startSession = startSession;
	}

	public Date getEndSession() {
		return endSession;
	}

	public void setEndSession(Date endSession) {
		this.endSession = endSession;
	}

	public long getVotesComputed() {
		return votesComputed;
	}

	public void setVotesComputed(long votesComputed) {
		this.votesComputed = votesComputed;
	}

	public long getAgreeVotesComputed() {
		return agreeVotesComputed;
	}

	public void setAgreeVotesComputed(long agreeVotesComputed) {
		this.agreeVotesComputed = agreeVotesComputed;
	}

	public long getDisagreeVotesComputed() {
		return disagreeVotesComputed;
	}

	public void setDisagreeVotesComputed(long disagreeVotesComputed) {
		this.disagreeVotesComputed = disagreeVotesComputed;
	}


}
