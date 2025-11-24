package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMBulkRequest;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMRequest;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasIdsResponse;
import com.jotanunes.especificacoes.service.CombinacaoEMMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            summary = "Retornar dados de uma combinação EMM",
            description = "Retorna dados de uma combinação EMM apartir de seu id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CombinacaoEMMResponse> findById(@PathVariable Integer id) {
        CombinacaoEMMResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Adicionar novas combinações EMM para um empreendimento",
            description = "Adiciona novas combinações EMM ao empreendimento com ID especificado"
    )
    @PostMapping("/empreendimento/{empreendimentoID}/bulk")
    public List<CombinacaoEMMResponse> addCombinacoes(@PathVariable Integer empreendimentoID,
                                                     @RequestBody @Valid List<CombinacaoEMMBulkRequest> requests) {
        return service.createCombinacoes(empreendimentoID, requests);
    }

    @Operation(
            summary = "Adiciona uma nova combinação para o empreendimento",
            description = "Cria uma nova combinação EMM para um empreendimento"
    )
    @PostMapping("/empreendimento/{empreendimentoID}")
    public ResponseEntity<CombinacaoEMMResponse> add(@PathVariable Integer empreendimentoID,
                                                     @RequestBody @Valid CombinacaoEMMRequest request) {
        CombinacaoEMMResponse response = service.create(empreendimentoID, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Remove uma única combinação",
            description = "Remova uma combinação EMM apartir o ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
