package br.com.omr.voting.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.omr.voting.domain.interfaces.IVotingService;
import br.com.omr.voting.dto.VoteDto;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(path="/api/v1/votacao")
@Api(tags="votacao")
public class VotingController {

    private final IVotingService votingService;
	private final ModelMapper modelMapper;
	
    public VotingController(IVotingService votingService, ModelMapper modelMapper) {
		this.votingService = votingService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping(path="{pautaId}/votar")
	public @ResponseBody VoteDto vote (@PathVariable int pautaId, @RequestParam String cpf, boolean concordar) {
		return modelMapper.map(this.votingService.vote(pautaId, cpf, concordar), VoteDto.class); 
	}
	
}
