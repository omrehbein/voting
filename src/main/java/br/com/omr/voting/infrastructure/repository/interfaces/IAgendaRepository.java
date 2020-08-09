package br.com.omr.voting.infrastructure.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.omr.voting.infrastructure.entity.Agenda;

public interface IAgendaRepository extends JpaRepository<Agenda, Integer> {

}
