package br.com.omr.voting.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import br.com.omr.voting.domain.interfaces.IAccountingService;
import br.com.omr.voting.dto.VotingSessionDto;
import io.swagger.annotations.Api;


@RestController
@RequestMapping(path="/api/v1/contabilizacao")
@Api(tags="contabilizacao")
public class AccountingController {

    private final IAccountingService accountingService;
	private final ModelMapper modelMapper;
	
    public AccountingController(IAccountingService accountingService, ModelMapper modelMapper) {
		this.accountingService = accountingService;
		this.modelMapper = modelMapper;
	}
    
	@GetMapping(path="{pautaId}/computar")
	public @ResponseBody VotingSessionDto compute ( @PathVariable int pautaId ) {
		return modelMapper.map(this.accountingService.compute(pautaId), VotingSessionDto.class); 
	}
}
