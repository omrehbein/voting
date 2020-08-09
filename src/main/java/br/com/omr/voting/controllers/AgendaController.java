package br.com.omr.voting.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.reflect.TypeToken;

import br.com.omr.voting.domain.interfaces.IAccountingService;
import br.com.omr.voting.domain.interfaces.IAgendaService;
import br.com.omr.voting.dto.AgendaDto;
import br.com.omr.voting.dto.VotingSessionDto;
import io.swagger.annotations.Api;


@RestController
@RequestMapping(path="/api/v1/Pauta")
@Api(tags="Pauta")
public class AgendaController {

    private final IAgendaService agendaService;
    private final IAccountingService accountingService;
	private final ModelMapper modelMapper;
	
    public AgendaController(IAgendaService agendaService, IAccountingService accountingService, ModelMapper modelMapper) {
		this.agendaService = agendaService;
		this.accountingService = accountingService;
		this.modelMapper = modelMapper;
	}
    
	@PostMapping(path="/CriarPauta")
	public @ResponseBody AgendaDto createAgenda (@RequestParam String description) {
		return modelMapper.map(this.agendaService.createAgenda(description), AgendaDto.class); 
	}
	
	@SuppressWarnings("serial")
	@GetMapping(path="/ListarPauta")
	public @ResponseBody List<AgendaDto> getAgendas () {
		return modelMapper.map(this.agendaService.getAgendas(), new TypeToken<List<AgendaDto>>() {}.getType());
	}
	
	@PostMapping(path="/CriarSessaoVotacaoPadrao")
	public @ResponseBody VotingSessionDto createVotingSessionDefault (@RequestParam int agendaId ) {
		return modelMapper.map(this.agendaService.createVotingSession(agendaId), VotingSessionDto.class); 
	}
	
	@PostMapping(path="/CriarSessaoVotacao")
	public @ResponseBody VotingSessionDto createVotingSession (@RequestParam int agendaId, @RequestParam int timeInMinute ) {
		return modelMapper.map(this.agendaService.createVotingSession(agendaId, timeInMinute), VotingSessionDto.class); 
	}
	
	@PutMapping(path="/ComputarVotos")
	public @ResponseBody VotingSessionDto createVotingSession (@RequestParam int agendaId ) {
		return modelMapper.map(this.accountingService.compute(agendaId), VotingSessionDto.class); 
	}
}
