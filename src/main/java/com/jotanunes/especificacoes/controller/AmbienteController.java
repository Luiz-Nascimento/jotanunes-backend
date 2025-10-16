package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.service.AmbienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ambientes", description = "Operações relacionadas a ambientes do empreendimento")
@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    private final AmbienteService service;

    public AmbienteController(AmbienteService service) {
        this.service = service;
    }
    @Operation(
            summary = "Retornar dados de todos ambientes",
            description = "Retorna dados de todos ambientes cadastrados"
    )
    @GetMapping
    public List<AmbienteResponse> listAllAmbientes() {
        return service.getAllAmbientes();
    }

    @Operation(
            summary = "Retornar dados de um ambiente",
            description = "Retorna dados do ambiente com ID especificado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AmbienteResponse> getAmbiente(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getAmbienteById(id));
    }
    @Operation(
            summary = "Retornar dados de um ambiente formatados para documento",
            description = "Retorna dados do ambiente com ID especificado formatados para documento"
    )
    @GetMapping("/doc/{id}")
    public ResponseEntity<AmbienteDocResponse> getAmbienteDocResponse(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getAmbienteDocResponse(id));
    }

    @GetMapping("/{id}/itens")
    public List<ItemResponse> getItensByAmbienteId(@PathVariable Integer id) {
        return service.getItensByAmbienteId(id);
    }
    @Operation(
            summary = "Criar um novo ambiente",
            description = "Cria um novo ambiente associado ao empreendimento com ID especificado"
    )
    @PostMapping
    public ResponseEntity<AmbienteResponse> createAmbiente(@RequestBody @Valid AmbienteRequest data) {
        AmbienteResponse ambienteResponse = service.createAmbienteVazio(data);
        return ResponseEntity.ok().body(ambienteResponse);
    }

    @PostMapping("/com-itens")
    public ResponseEntity<AmbienteResponse> createAmbienteComItens(@RequestBody AmbienteRequest request) {
        AmbienteResponse ambienteResponse = service.createAmbienteModelo(request);
        return ResponseEntity.ok().body(ambienteResponse);
    }
    @Operation(
            summary = "Deletar um ambiente",
            description = "Deleta o ambiente com ID especificado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmbiente(@PathVariable Integer id) {
        service.deleteAmbiente(id);
        return ResponseEntity.noContent().build();
    }


}
