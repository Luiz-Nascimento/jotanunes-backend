package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMRequest;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasIdsResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.service.CombinacaoEMMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(
            summary = "Retornar dados de todas as combinações EMM",
            description = "Retorna dados de todas as combinações EMM cadastradas"
    )
    @GetMapping
    public List<CombinacaoEMMResponse> findAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Retornar dados de todas as combinações EMM por empreendimento",
            description = "Retorna dados de todas as combinações EMM cadastradas para o empreendimento com ID especificado"
    )
    @GetMapping("/empreendimento/{empreendimentoID}/ids")
    public List<MaterialMarcasIdsResponse> findMaterialMarcasIdsByEmpreendimentoId(@PathVariable Integer empreendimentoID) {
        return service.findMaterialMarcasIdsByEmpreendimentoId(empreendimentoID);
    }

    @Operation(
            summary = "Retornar nomes de materiais e marcas por empreendimento",
            description = "Retorna os nomes dos materiais e marcas associados às combinações EMM para o empreendimento com ID especificado"
    )
    @GetMapping("/empreendimento/{empreendimentoID}/nomes")
    public List<MaterialMarcasNomeResponse> findMaterialMarcasNomeByEmpreendimentoId(@PathVariable Integer empreendimentoID) {
        return service.findMaterialMarcasNomeByEmpreendimentoId(empreendimentoID);
    }

    @Operation(
            summary = "Adicionar novas combinações EMM para um empreendimento",
            description = "Adiciona novas combinações EMM ao empreendimento com ID especificado"
    )
    @PostMapping("/empreendimento/{empreendimentoID}")
    public List<CombinacaoEMMResponse> addCombinacoes(@PathVariable Integer empreendimentoID,
                                                     @RequestBody @Valid List<CombinacaoEMMRequest> requests) {
        return service.createCombinacoes(empreendimentoID, requests);
    }


}
