package br.com.omr.voting.infrastructure.repository.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.omr.voting.infrastructure.entity.VotingSession;

public interface IVotingSessionRepository extends JpaRepository<VotingSession, Integer> {
	Optional<VotingSession> findOneByAgendaId(int agendaId);
}
