package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMRequest;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasResponse;
import com.jotanunes.especificacoes.service.CombinacaoEMMService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combinacaoEMM")
public class CombinacaoEMMController {

    private final CombinacaoEMMService service;

    public CombinacaoEMMController(CombinacaoEMMService service) {
        this.service = service;
    }

    @GetMapping("/empreendimento/{empreendimentoID}")
    public List<MaterialMarcasResponse> findByEmpreendimento(@PathVariable Integer empreendimentoID) {
        return service.findByEmpreendimento(empreendimentoID);
    }


    @PostMapping("/empreendimento/{empreendimentoID}")
    public List<CombinacaoEMMResponse> addCombinacoes(@PathVariable Integer empreendimentoID,
                                                      @RequestBody List<CombinacaoEMMRequest> requests) {
        return service.createCombinacoes(empreendimentoID, requests);
    }

}
