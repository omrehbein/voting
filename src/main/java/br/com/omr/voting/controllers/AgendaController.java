package br.com.omr.voting.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.reflect.TypeToken;

import br.com.omr.voting.domain.interfaces.IAccountingService;
import br.com.omr.voting.domain.interfaces.IAgendaService;
import br.com.omr.voting.domain.interfaces.IVotingService;
import br.com.omr.voting.dto.AgendaDto;
import br.com.omr.voting.dto.VoteDto;
import br.com.omr.voting.dto.VotingSessionDto;
import io.swagger.annotations.Api;


@RestController
@RequestMapping(path="/api/v1/pauta")
@Api(tags="pauta")
public class AgendaController {

    private final IVotingService votingService;
    private final IAccountingService accountingService;
    private final IAgendaService agendaService;
	private final ModelMapper modelMapper;
	
    public AgendaController(IVotingService votingService, IAgendaService agendaService, IAccountingService accountingService, ModelMapper modelMapper) {
    	this.votingService = votingService;
    	this.accountingService = accountingService;
		this.agendaService = agendaService;
		this.modelMapper = modelMapper;
	}
    
	@PostMapping(path="/criar")
	public @ResponseBody AgendaDto createAgenda (@RequestParam String descricao) {
		return modelMapper.map(this.agendaService.createAgenda(descricao), AgendaDto.class); 
	}
	
	@GetMapping(path="/listar")
	@SuppressWarnings("serial")
	public @ResponseBody List<AgendaDto> getAgendas() {
		return modelMapper.map(this.agendaService.getAgendas(), new TypeToken<List<AgendaDto>>() {}.getType());
	}
	
	@PatchMapping(path="{pautaId}/abrirsessao")
	public @ResponseBody VotingSessionDto createVotingSession (@PathVariable int pautaId, @RequestParam(value = "tempoEmMinutos", defaultValue = "1") int tempoEmMinutos ) {
		return modelMapper.map(this.agendaService.createVotingSession(pautaId, tempoEmMinutos), VotingSessionDto.class); 
	}
	
	@GetMapping(path="{pautaId}/resultado")
	public @ResponseBody VotingSessionDto compute ( @PathVariable int pautaId ) {
		return modelMapper.map(this.accountingService.compute(pautaId), VotingSessionDto.class); 
	}
	
	@PostMapping(path="{pautaId}/votar")
	public @ResponseBody VoteDto vote (@PathVariable int pautaId, @RequestParam String cpf, boolean concordar) {
		return modelMapper.map(this.votingService.vote(pautaId, cpf, concordar), VoteDto.class); 
	}
	
}
