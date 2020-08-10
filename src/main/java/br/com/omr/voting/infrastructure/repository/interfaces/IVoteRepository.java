package br.com.omr.voting.infrastructure.repository.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.omr.voting.infrastructure.entity.Vote;

public interface IVoteRepository extends JpaRepository<Vote, Integer> {
	Optional<Vote> findAllByVotingSessionIdAndCpf(int votingSessionId, String cpf);
	List<Vote> findAllByVotingSessionId(int votingSessionId);
}
