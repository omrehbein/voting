package br.com.omr.voting.infrastructure.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class VotingSession {

	public VotingSession() 
	{
		this.startSession = new Date();
		this.endSession = new Date();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	private Agenda agenda;

	@NotNull
	private Date startSession;	

	@NotNull
	private Date endSession;
	
	@OneToMany( fetch = FetchType.LAZY, targetEntity = Vote.class)
	private List<Vote> votes;
	
	private long votesComputed;
	
	private long agreeVotesComputed;
	
	private long disagreeVotesComputed;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
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

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
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
