package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMRequest;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasIdsResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.service.CombinacaoEMMService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Combinação EMM", description = "Operações relacionadas a combinações de empreendimento, marca e material.")
@RestController
@RequestMapping("/combinacaoEMM")
public class CombinacaoEMMController {

    private final CombinacaoEMMService service;

    public CombinacaoEMMController(CombinacaoEMMService service) {
        this.service = service;
    }

    @GetMapping
    public List<CombinacaoEMMResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/empreendimento/{empreendimentoID}/ids")
    public List<MaterialMarcasIdsResponse> findMaterialMarcasIdsByEmpreendimentoId(@PathVariable Integer empreendimentoID) {
        return service.findMaterialMarcasIdsByEmpreendimentoId(empreendimentoID);
    }

    @GetMapping("/empreendimento/{empreendimentoID}/nomes")
    public List<MaterialMarcasNomeResponse> findMaterialMarcasNomeByEmpreendimentoId(@PathVariable Integer empreendimentoID) {
        return service.findMaterialMarcasNomeByEmpreendimentoId(empreendimentoID);
    }


    @PostMapping("/empreendimento/{empreendimentoID}")
    public List<CombinacaoEMMResponse> addCombinacoes(@PathVariable Integer empreendimentoID,
                                                     @RequestBody List<CombinacaoEMMRequest> requests) {
        return service.createCombinacoes(empreendimentoID, requests);
    }


}
